package linky.dao;

import linky.domain.Link;
import linky.domain.SearchContent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class LinkDaoTest {

	@Autowired
	private LinkDao linkDao;

	@BeforeEach
	public void setup() {
		linkDao.deleteAll();
		createTestLinks();
	}

	@AfterEach
	public void tearDown() {
		linkDao.deleteAll();
	}

	@Test
	public void findByCreatedBy() {
		List<Link> links = linkDao.findByCreatedBy("batman");
		assertEquals(2, links.size());
		check(links.get(0), "gogle", "www.google.lv");
		check(links.get(1), "gogle2", "www.google2.lv");

		links = linkDao.findByCreatedBy("wrong");
		assertEquals(0, links.size());
	}

	@Test
	public void findByCreatedByPageable() {
		Pageable pageable = PageRequest.of(0, 5);
		Page<Link> pageLinks = linkDao.findByCreatedBy("batman", pageable);
		assertEquals(2L, pageLinks.getTotalElements());
		assertEquals(2, pageLinks.getContent().size());
		assertEquals(1, pageLinks.getTotalPages());
		check(pageLinks.getContent().get(0), "gogle", "www.google.lv");
		check(pageLinks.getContent().get(1), "gogle2", "www.google2.lv");

		pageLinks = linkDao.findByCreatedBy("wrong", pageable);
		assertEquals(0L, pageLinks.getTotalElements());
		assertEquals(0, pageLinks.getContent().size());
		assertEquals(0, pageLinks.getTotalPages());
	}

	@Test
	public void findByName() {
		Optional<Link> optional = linkDao.findByName("gogle");
		assertTrue(optional.isPresent());

		optional = linkDao.findByName("gogle1");
		assertFalse(optional.isPresent());
	}

	@Test
	public void findBySearchPageable() {
		Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "id");
		Page<Link> pageLinks = linkDao.findBySearchLikeIgnoreCase("%GoglE%", pageable);

		assertEquals(2L, pageLinks.getTotalElements());
		assertEquals(2, pageLinks.getContent().size());
		assertEquals(1, pageLinks.getTotalPages());

		check(pageLinks.getContent().get(0), "gogle2", "www.google2.lv");
		check(pageLinks.getContent().get(1), "gogle", "www.google.lv");

		pageLinks = linkDao.findBySearchLikeIgnoreCase("%YA%", pageable);
		assertEquals(1L, pageLinks.getTotalElements());
		assertEquals(1, pageLinks.getContent().size());
		assertEquals(1, pageLinks.getTotalPages());
		check(pageLinks.getContent().get(0), "yaho", "www.yahoo.lv");

		pageLinks = linkDao.findBySearchLikeIgnoreCase("%%", pageable);
		assertEquals(4L, pageLinks.getTotalElements());
		assertEquals(4, pageLinks.getContent().size());
		assertEquals(1, pageLinks.getTotalPages());
	}

	private void createTestLinks() {
		Link link1 = new Link("gogle", "www.google.lv", "batman");
		Link link2 = new Link("gogle2", "www.google2.lv", "batman");
		Link link3 = new Link("bing", "www.bing.lv", "superman");
		Link link4 = new Link("yaho", "www.yahoo.lv", "spiderman");

		link1.updateSearch();
		link2.updateSearch();
		link3.updateSearch();
		link4.updateSearch();

		linkDao.save(link1);
		linkDao.save(link2);
		linkDao.save(link3);
		linkDao.save(link4);
	}

	private void check(Link link, String expectedName, String expectedUrl) {
		assertNotNull(link.id());
		assertNotNull(link.createdAt());
		assertEquals(expectedName, link.name());
		assertEquals(expectedUrl, link.url());
		assertEquals(0, link.visits().size());
	}
}
