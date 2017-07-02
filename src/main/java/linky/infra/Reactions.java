package linky.infra;

import com.google.common.collect.Maps;
import linky.exceptions.NoReactionFound;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

@Component
public class Reactions {
	
	private Map<Type, Reaction> mapping = Maps.newHashMap();
//	private final LoadingCache<Type, Reaction> cachedReactions;
	
	@Autowired
	public Reactions(ListableBeanFactory beanFactory) {
		reactions(beanFactory).forEach(reaction -> mapping.put(reaction.commandType().getType(), reaction));
	}

	private Collection<Reaction> reactions(ListableBeanFactory beanFactory) {
		return beanFactory.getBeansOfType(Reaction.class).values();
	}

	public <C extends Command<R>, R extends Command.R> Reaction<C, R> byCommand(C command) {
		Reaction<C, R> reaction = mapping.get(command.type());
		if (reaction == null) {
			throw new NoReactionFound(command.type());
		}
		return reaction;
	}
}
