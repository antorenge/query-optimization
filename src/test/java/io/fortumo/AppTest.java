package io.fortumo;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void test_that_it_starts() throws Exception {
        final App app = new App(new String[]{});
        app.start(false);
        this.testQuery();
        app.stop();
    }

    private void testQuery() throws IOException {
        final String testUrl = "http://localhost:12000/?country=EE";
        final URLConnection con = new URL(testUrl).openConnection();
        final String someContent = new BufferedReader(
                new InputStreamReader(con.getInputStream())
        ).lines().collect(Collectors.joining("\n"));
        assertThat(someContent).isNotEmpty();
        System.out.println("DB Search Result " + someContent);
    }
}
