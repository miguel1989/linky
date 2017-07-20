package linky.validation.object;

import linky.dao.LinkDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueLinkName implements LinkName.Uniqueness {

	final LinkDao linkDao;

	@Autowired
	public UniqueLinkName(LinkDao linkDao) {
		this.linkDao = linkDao;
	}

	@Override
	public boolean guaranteed(String text) {
		return !linkDao.findByName(text).isPresent();
	}
}
