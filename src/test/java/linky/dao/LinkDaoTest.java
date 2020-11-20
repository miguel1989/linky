package linky.dao;

import linky.dao.specification.LinkSpecification;
import linky.domain.Link;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
        Page<Link> pageLinks =  linkDao.findByCreatedBy("batman", pageable);
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
    public void findByNameLike() {
//		List<Link> links = linkDao.findByNameLikeOrUrlLike("%gogle%", "");
        List<Link> links = linkDao.findAll(LinkSpecification.byNameOrUrl("%gogle%", null));
        assertEquals(2, links.size());
        check(links.get(0), "gogle", "www.google.lv");
        check(links.get(1), "gogle2", "www.google2.lv");

        links = linkDao.findAll(LinkSpecification.byNameOrUrl("%ya%", null));
        assertEquals(1, links.size());
        check(links.get(0), "yaho", "www.yahoo.lv");
    }

    @Test
    public void findByUrlLike() {
        List<Link> links = linkDao.findAll(LinkSpecification.byNameOrUrl(null, "%www.google%"));
        assertEquals(2, links.size());
        check(links.get(0), "gogle", "www.google.lv");
        check(links.get(1), "gogle2", "www.google2.lv");

        links = linkDao.findAll(LinkSpecification.byNameOrUrl(null, "%www.bing.lv%"));
        assertEquals(1, links.size());
        check(links.get(0), "bing", "www.bing.lv");
    }

    @Test
    public void findByNameLikeAndUrlLike() {
        List<Link> links = linkDao.findAll(LinkSpecification.byNameOrUrl("%gogle2%", "%www.google%"));
        assertEquals(1, links.size());
        check(links.get(0), "gogle2", "www.google2.lv");

        links = linkDao.findAll(LinkSpecification.byNameOrUrl("%gogle3%", "%www.google%"));
        assertEquals(0, links.size());

        links = linkDao.findAll(LinkSpecification.byNameOrUrl(null, null));
        assertEquals(4, links.size());
    }

    @Test
    public void findByNameLikeAndUrlLikePageable() {
        for (int i = 0; i < 10; i++) {
            linkDao.save(
                    new Link("testNAME" + i, "www.facebook.com", "cukerman")
            );
        }

        Pageable pageable = PageRequest.of(0, 5);
        Page<Link> pageLinks = linkDao.findAll(LinkSpecification.byNameOrUrl("%NAME%", "%face%"), pageable);
        assertEquals(10L, pageLinks.getTotalElements());
        assertEquals(5, pageLinks.getContent().size());
        assertEquals(2, pageLinks.getTotalPages());
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
        assertNotNull(link.id());
        assertNotNull(link.createdAt());
        assertEquals(expectedName, link.name());
        assertEquals(expectedUrl, link.url());
        assertEquals(0, link.visits().size());
    }
}
