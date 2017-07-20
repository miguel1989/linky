package linky.validation.object;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AbuseLinkName implements LinkName.Abuseness {
	
	@Value("#{'${reserved.words}'.split(',')}")
	private List<String> reservedWords;
	@Value("#{'${abuse.words}'.split(',')}")
	private List<String> abuseWords;

	@Override
	public boolean isOk(String text) {
		String lowerText = text.toLowerCase();
		return reservedWords.stream().filter(lowerText::equals).count() == 0
				&& abuseWords.stream().filter(lowerText::contains).count() == 0;
	}
}
