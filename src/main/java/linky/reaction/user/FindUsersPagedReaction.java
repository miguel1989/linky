package linky.reaction.user;

import linky.command.user.FindUsersPaged;
import linky.dao.UserDao;
import linky.dao.specification.UserSearchSpecification;
import linky.domain.User;
import linky.dto.PageUserBean;
import linky.dto.RestResponsePage;
import linky.dto.UserBean;
import linky.infra.Reaction;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindUsersPagedReaction implements Reaction<FindUsersPaged, PageUserBean> {

	private final UserDao userDao;

	@Autowired
	public FindUsersPagedReaction(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public PageUserBean react(FindUsersPaged command) {
		Pageable pageable = PageRequest.of(command.page(), command.size(), command.sortDirection(), command.sortField());
		String searchStr = StringUtils.defaultIfBlank(StringUtils.trim(command.search()), "");

		Page<User> pagedUsers = userDao.findAll(new UserSearchSpecification(searchStr).build(), pageable);
		List<UserBean> userBeanList = pagedUsers.getContent().stream().map(UserBean::new).collect(Collectors.toList());

		return new PageUserBean(
				new RestResponsePage<>(userBeanList, pageable, pagedUsers.getTotalElements())
		);
	}
}
