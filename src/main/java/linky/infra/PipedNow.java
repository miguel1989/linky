package linky.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Component
public class PipedNow implements Now {

	@Autowired
	private Reactions reactions;
	
	@Autowired
	private PlatformTransactionManager txManager;
	
	@Override
	public <C extends Command<R>, R extends Command.R> R execute(C command) {
		Reaction<C, R> reaction = reactions.byCommand(command);

		TransactionTemplate tx = new TransactionTemplate(txManager);
		command.txFlags().forEach(flag -> flag.apply(tx));
		
		R response = tx.execute(transactionStatus -> reaction.react(command));
		return response;
	}
}
