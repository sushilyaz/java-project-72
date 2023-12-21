package hexlet.code;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;

public class AppTest {
    Javalin app;

    @BeforeEach
    public final void setUp() throws IOException, SQLException {
        app = App.getApp();
    }

    @Test
    public void testMainPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/");
            assertThat(response.code()).isEqualTo(200);
            var body = response.body().string();
            assertThat(body.contains("Анализатор страниц"));
            assertThat(body.contains("Проверить"));
            assertThat(body.contains("created by"));
        });
    }

    @Test
    public void testUrlsPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls");
            assertThat(response.code()).isEqualTo(200);
            var body = response.body().string();
            assertThat(body.contains("<h1>Сайты<h1>"));
            assertThat(body.contains("Последняя проверка"));
            assertThat(body.contains("created by"));
        });
    }

    @Test
    public void testUrlPage() throws SQLException {
        var url = new Url("https://github.com", new Timestamp(System.currentTimeMillis()));
        UrlRepository.save(url);
        JavalinTest.test(app, (server, client) -> {
            var response1 = client.post("/urls");
            assertThat(response1.code()).isEqualTo(200);
            var response2 = client.get("/urls/" + url.getId());
            var body = response2.body().string();
            assertThat(response2.code()).isEqualTo(200);
            assertThat(body.contains("Проверки"));
            assertThat(body.contains("Сайт: https://github.com"));
        });
    }

    @Test
    public void testPostPage() throws SQLException {
        var url = new Url("https://github.com", new Timestamp(System.currentTimeMillis()));
        UrlRepository.save(url);
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls");
            assertThat(response.code()).isEqualTo(200);
            var body = response.body().string();
            assertThat(body.contains("<a href=\"/urls/1\">https://github.com</a>"));
        });
    }

}
