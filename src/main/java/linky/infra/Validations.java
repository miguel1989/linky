package linky.infra;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Validations {

	private Map<Type, Validation> mapping = new ConcurrentHashMap<>();

	@Autowired
	public Validations(ListableBeanFactory beanFactory) {
		validations(beanFactory).forEach(validation -> mapping.put(validation.commandType().getType(), validation));
	}

	private Collection<Validation> validations(ListableBeanFactory beanFactory) {
		return beanFactory.getBeansOfType(Validation.class).values();
	}
	
	<C extends Command> Optional<Validation<C>> byCommand(C command) {
		return Optional.ofNullable(mapping.get(command.type()));
	}
}
