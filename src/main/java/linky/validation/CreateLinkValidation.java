package linky.validation;

import linky.command.CreateLink;
import linky.exception.ValidationFailed;
import linky.infra.Validation;
import org.springframework.stereotype.Component;

import static com.google.common.base.Preconditions.checkNotNull;

@Component
public class CreateLinkValidation implements Validation<CreateLink> {
	@Override
	public void validate(CreateLink command) {
		checkNotNull(command.userId());
		throw new ValidationFailed("test msg");
	}
}
