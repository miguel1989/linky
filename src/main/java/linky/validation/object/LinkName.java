package linky.validation.object;

import com.google.common.base.Strings;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

public class LinkName {

	private static final String REGEX = "^[a-zA-Z0-9_-]{1,255}$";
	private final Pattern LINK_PATTERN = compile(REGEX);

	private final String text;

	public LinkName(String text) {
		this.text = text;
	}

	public boolean isValid() {
		//the same as matcher(text).find()
		return !Strings.isNullOrEmpty(text) && LINK_PATTERN.asPredicate().test(text);
	}

	interface Uniqueness {
		boolean guaranteed(String text);
	}

	interface Abuseness {
		boolean isOk(String text);
	}
}
