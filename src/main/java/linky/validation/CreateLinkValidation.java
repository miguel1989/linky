package linky.validation;

import com.google.common.base.Strings;
import linky.command.CreateLink;
import linky.dao.LinkDao;
import linky.exception.ValidationFailed;
import linky.infra.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateLinkValidation implements Validation<CreateLink> {

	@Autowired
	private LinkDao linkDao;
	
	@Override
	public void validate(CreateLink command) {
		if (command == null) {
			throw new ValidationFailed("Command can not be null");
		}
		
		if (Strings.isNullOrEmpty(command.userId())) {
			throw new ValidationFailed("UserId is empty");
		}
	}
}
