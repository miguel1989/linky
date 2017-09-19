package linky.dao;

import linky.dao.specification.LinkSpecification;
import linky.domain.Link;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class LinkDaoTest {

	@Autowired
	private LinkDao linkDao;

	@Before
	public void setup() {
		linkDao.deleteAll();
		createTestLinks();
	}

	@After
	public void tearDown() {
		linkDao.deleteAll();
	}

	@Test
	public void findByCreatedBy() {
		List<Link> links = linkDao.findByCreatedBy("batman");
		assertThat(links, hasSize(2));
		check(links.get(0), "gogle", "www.google.lv");
		check(links.get(1), "gogle2", "www.google2.lv");

		links = linkDao.findByCreatedBy("wrong");
		assertThat(links, hasSize(0));
	}

	@Test
	public void findByName() {
		Optional<Link> optional = linkDao.findByName("gogle");
		assertThat(optional.isPresent(), is(true));

		optional = linkDao.findByName("gogle1");
		assertThat(optional.isPresent(), is(false));
	}

	@Test
	public void findByNameLike() {
//		List<Link> links = linkDao.findByNameLikeOrUrlLike("%gogle%", "");
		List<Link> links = linkDao.findAll(LinkSpecification.byNameOrUrl("%gogle%", null));
		assertThat(links, hasSize(2));
		check(links.get(0), "gogle", "www.google.lv");
		check(links.get(1), "gogle2", "www.google2.lv");

		links = linkDao.findAll(LinkSpecification.byNameOrUrl("%ya%", null));
		assertThat(links, hasSize(1));
		check(links.get(0), "yaho", "www.yahoo.lv");
	}

	@Test
	public void findByUrlLike() {
		List<Link> links = linkDao.findAll(LinkSpecification.byNameOrUrl(null, "%www.google%"));
		assertThat(links, hasSize(2));
		check(links.get(0), "gogle", "www.google.lv");
		check(links.get(1), "gogle2", "www.google2.lv");

		links = linkDao.findAll(LinkSpecification.byNameOrUrl(null, "%www.bing.lv%"));
		assertThat(links, hasSize(1));
		check(links.get(0), "bing", "www.bing.lv");
	}

	@Test
	public void findByNameLikeAndUrlLike() {
		List<Link> links = linkDao.findAll(LinkSpecification.byNameOrUrl("%gogle2%", "%www.google%"));
		assertThat(links, hasSize(1));
		check(links.get(0), "gogle2", "www.google2.lv");

		links = linkDao.findAll(LinkSpecification.byNameOrUrl("%gogle3%", "%www.google%"));
		assertThat(links, hasSize(0));

		links = linkDao.findAll(LinkSpecification.byNameOrUrl(null, null));
		assertThat(links, hasSize(4));
	}

	@Test
	public void findByNameLikeAndUrlLikePageable() {
		for(int i = 0; i < 10; i ++) {
			linkDao.save(
					new Link("testNAME"+i, "www.facebook.com", "cukerman")
			);
		}
		
		Pageable pageable = new PageRequest(0, 5);
		Page<Link> pageLinks = linkDao.findAll(LinkSpecification.byNameOrUrl("%NAME%", "%face%"), pageable);
		assertThat(pageLinks.getTotalElements(), is(10L));
		assertThat(pageLinks.getContent(), hasSize(5));
		assertThat(pageLinks.getTotalPages(), is(2));
	}

	private void createTestLinks() {
		Link link1 = new Link("gogle", "www.google.lv", "batman");
		Link link2 = new Link("gogle2", "www.google2.lv", "batman");
		Link link3 = new Link("bing", "www.bing.lv", "superman");
		Link link4 = new Link("yaho", "www.yahoo.lv", "spiderman");

		linkDao.save(link1);
		linkDao.save(link2);
		linkDao.save(link3);
		linkDao.save(link4);
	}

	private void check(Link link, String expectedName, String expectedUrl) {
		assertThat(link.id(), notNullValue());
		assertThat(link.createdAt(), notNullValue());
		assertThat(link.name(), is(expectedName));
		assertThat(link.url(), is(expectedUrl));
		assertThat(link.visits(), hasSize(0));
	}
}
