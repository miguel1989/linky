package linky.infra;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SideEffects {

	private Map<Type, Collection<SideEffect>> mapping = new ConcurrentHashMap<>();

	@Autowired
	public SideEffects(ListableBeanFactory beanFactory) {
		sideEffects(beanFactory).forEach(sideEffect -> {
			mapping.computeIfAbsent(sideEffect.eventType().getType(), k -> new ArrayList<>());
			mapping.get(sideEffect.eventType().getType()).add(sideEffect);
		});
	}

	private Collection<SideEffect> sideEffects(ListableBeanFactory beanFactory) {
		return beanFactory.getBeansOfType(SideEffect.class).values();
	}

	public Collection<SideEffect> byEvent(DomainEvent domainEvent) {
		return mapping.get(domainEvent.type());
	}
}
