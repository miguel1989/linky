package linky.converter;

import linky.domain.User;
import linky.dto.UserBean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements Converter<User, UserBean> {
	
	@Override
	public UserBean convert(User user) {
		return new UserBean(user);
	}
}
