package linky.dao;

import linky.domain.Link;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LinkDaoTest {

	@Autowired
	private LinkDao linkDao;

	@Before
	public void setup() {
		linkDao.deleteAll();
	}

	@Test
	public void findByCreatedBy() {
		Link link1 = new Link("gogle", "www.google.lv", "batman");
		Link link2 = new Link("yaho", "www.yahoo.lv", "batman");
		Link link3 = new Link("qwerty", "www.bing.lv", "superman");
		linkDao.save(link1);
		linkDao.save(link2);
		linkDao.save(link3);

		List<Link> links = linkDao.findByCreatedBy("batman");
		assertThat(links).hasSize(2);
		check(links.get(0), "gogle", "www.google.lv");
		check(links.get(1), "yaho", "www.yahoo.lv");
		
		links = linkDao.findByCreatedBy("wrong");
		assertThat(links).hasSize(0);
	}
	
	private void check(Link link, String expectedName, String expectedUrl) {
		assertThat(link.id()).isNotNull();
		assertThat(link.createdAt()).isNotNull();
		assertThat(link.name()).isEqualTo(expectedName);
		assertThat(link.url()).isEqualTo(expectedUrl);
		assertThat(link.visits()).hasSize(0);
	}
}
