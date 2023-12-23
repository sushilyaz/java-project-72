package hexlet.code;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.util.ParseHtml;
import hexlet.code.util.NamedRoutes;

import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.MockResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;


public class AppTest {
    private Javalin app;
    private static MockWebServer serv;
    private static String website;

    @BeforeEach
    public final void setUp() throws IOException, SQLException {
        app = App.getApp();
    }

    @AfterEach
    public final void shutDown() throws IOException, SQLException {
        app.close();
    }

    @BeforeAll
    public static final void startServer() throws Exception {
        serv = new MockWebServer();
        website = "https://ru.hexlet.io";
        String content = ParseHtml.getHtmlContent(website);
        serv.enqueue(new MockResponse().setBody(content));
        serv.start();
    }

    @AfterAll
    public static final void stopServer() throws Exception {
        serv.shutdown();
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

    @Test
    public void testWithMockito() throws Exception {
        JavalinTest.test(app, (server1, client) -> {
            client.post(NamedRoutes.urlsPath(), "url=" + website);
            var urlId = UrlRepository.find(website).get().getId();
            client.post(NamedRoutes.urlCheckPath(urlId));
            var check = UrlCheckRepository.find(urlId).get(0);
            assertThat(check.getStatusCode()).isEqualTo(200);
            assertThat(check.getTitle()).isEqualTo("Хекслет — "
                    + "онлайн-школа программирования, "
                    + "онлайн-обучение ИТ-профессиям");
            assertThat(check.getH1()).isEqualTo("Лучшая школа "
                    + "программирования по версии пользователей Хабра");
            assertThat(check.getDescription()).isEqualTo("Хекслет — "
                    + "лучшая школа программирования по версии пользователей Хабра. "
                    + "Авторские программы обучения с практикой и готовыми проектами в резюме. "
                    + "Помощь в трудоустройстве после успешного окончания обучения");
        });
    }
}
