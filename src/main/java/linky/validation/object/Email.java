package linky.validation.object;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

public class Email {
	private static final String LOCAL_PART = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*";
	private static final String AT_SIGN = "@";
	private static final String HOSTNAME_FOLLOWED_BY_DOT = "(?:[a-zA-Z0-9-]+\\.)+";
	private static final String COUNTRY_CODE = "[a-zA-Z]{2,6}";
	private static final String END_OF_LINE = "$";
	private final Pattern RFC_5322 = compile(
			LOCAL_PART + AT_SIGN + HOSTNAME_FOLLOWED_BY_DOT + COUNTRY_CODE + END_OF_LINE);

	private final String text;

	public Email(String text) {
		this.text = text;
	}

	public boolean isValid() {
		return RFC_5322.asPredicate().test(text);
	}

	@Override
	public String toString() {
		return text;
	}
}
