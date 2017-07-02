package linky.infra;

import org.springframework.transaction.support.TransactionTemplate;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;

public interface Command<T extends Command.R> {
	interface R {
		class Void implements Command.R {
		}
	}
	
	String toLogString();
	
	default T execute (Now now) {
		return now.execute(this);
	}

	interface TxFlag {
		TxFlag READ_ONLY = tx -> tx.setReadOnly(true);
		void apply(TransactionTemplate tx);
	}
	
	default Type type() {
		return this.getClass();
	}

	default Collection<TxFlag> txFlags() {
		return Collections.emptyList();
	}
}
