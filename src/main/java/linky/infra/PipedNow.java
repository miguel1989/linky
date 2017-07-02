package linky.infra;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Optional;

@Component
public class PipedNow implements Now {

	private final Reactions reactions;
	private final Validations validations;
	private final PlatformTransactionManager txManager;

	@Autowired
	public PipedNow(Reactions reactions, Validations validations, PlatformTransactionManager txManager) {
		this.reactions = reactions;
		this.validations = validations;
		this.txManager = txManager;
	}

	@Override
	public <C extends Command<R>, R extends Command.R> R execute(C command) {
		Now piped =
				new Loggable(
						new Transactional(
								new Validating(
										new Reacting())));
		return piped.execute(command);
	}

	private class Loggable implements Now {
		private final Now origin;
		private final Logger logger = LoggerFactory.getLogger(Loggable.class);

		Loggable(Now origin) {
			this.origin = origin;
		}

		@Override
		public <C extends Command<R>, R extends Command.R> R execute(C command) {
			logger.info("--- Start execute command {} ---", command.toLogString());
			R response = origin.execute(command);
			logger.info("--- End execute command {} ---", command.toLogString());
			return response;
		}
	}

	private class Transactional implements Now {
		private final Now origin;

		Transactional(Now origin) {
			this.origin = origin;
		}

		@Override
		public <C extends Command<R>, R extends Command.R> R execute(C command) {
			TransactionTemplate tx = new TransactionTemplate(txManager);
			command.txFlags().forEach(flag -> flag.apply(tx));
			return tx.execute(transactionStatus -> origin.execute(command));
		}
	}

	private class Validating implements Now {

		private final Now origin;

		Validating(Now origin) {
			this.origin = origin;
		}

		@Override
		public <C extends Command<R>, R extends Command.R> R execute(C command) {
			Optional<Validation<C>> optionalValidation = validations.byCommand(command);
			optionalValidation.ifPresent(validation -> validation.validate(command));
			return origin.execute(command);
		}
	}

	private class Reacting implements Now {

		@Override
		public <C extends Command<R>, R extends Command.R> R execute(C command) {
			Reaction<C, R> reaction = reactions.byCommand(command);
			return reaction.react(command);
		}
	}
}
