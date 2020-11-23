package linky.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class SearchContent {
	private static final Logger LOG = LoggerFactory.getLogger(SearchContent.class);

	private final Object entity;

	public SearchContent(Object entity) {
		this.entity = entity;
	}

	public String extract() {
		Set<String> searchContent = new HashSet<>();
		try {
			for (Field field : entity.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				if (field.getName().equals("search")) {
					continue;
				}
				Object fieldValue = field.get(entity);
				if (fieldValue == null) {
					continue;
				}
				if ((fieldValue instanceof String) || fieldValue.getClass().isEnum() || ClassUtils.isPrimitiveOrWrapper(fieldValue.getClass())) {
					searchContent.add(String.valueOf(fieldValue));
				}
			}
		} catch (Exception ex) {
			LOG.error("Failed to make entity searchable {}", ex.getMessage());
		}
		return String.join(" ", searchContent);
	}
}
