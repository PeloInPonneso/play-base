import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static play.test.Helpers.contentType;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.start;
import models.User;

import org.junit.Before;
import org.junit.Test;

import play.mvc.Content;

public class ApplicationTest {

	@Before
    public void setUp() {
        start(fakeApplication());
    }
	
    @Test
    public void simpleCheck() {
        int a = 1 + 1;
        assertThat(a).isEqualTo(2);
    }

    @Test
    public void renderTemplate() {
        Content html = views.html.index.render(null);
        assertThat(contentType(html)).isEqualTo("text/html");
    }

    @Test
    public void testUser() {
    	User admin = User.find.where().eq("username", "admin").findUnique();
    	assertNotNull(admin);
    }
}
