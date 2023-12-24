package hexlet.code.controller;

import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.FormatTimestamp;
import hexlet.code.util.NamedRoutes;
import hexlet.code.util.ParseHtml;
import io.javalin.http.Context;
import kong.unirest.Unirest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import io.javalin.http.NotFoundResponse;

import java.sql.SQLException;
import java.sql.Timestamp;

public class UrlCheckController {
    public static void create(Context ctx) throws SQLException {
        var urlId = ctx.pathParamAsClass("id", Long.class).get();

        var url = UrlRepository.findById(urlId)
                .orElseThrow(() -> new NotFoundResponse("Запись с таким ID не найдена"));
        var name = url.getName();

        try {
            var response = Unirest.get(name).asString();
            Document responseBody = Jsoup.parse(response.getBody());

            int statusCode = response.getStatus();

            String h1 = responseBody.selectFirst("h1") != null
                    ? responseBody.selectFirst("h1").text() : "";

            String title = responseBody.title();

            String description = !responseBody.select("meta[name=description]").isEmpty()
                    ? responseBody.select("meta[name=description]").get(0).attr("content") : "";

            var createdAt = new Timestamp(System.currentTimeMillis());

            var urlCheck = new UrlCheck(urlId, statusCode, h1, title, description, createdAt);
            urlCheck.setUrlId(urlId);
            UrlCheckRepository.save(urlCheck);
            ctx.sessionAttribute("flash", "Страница успешно проверена");
            ctx.sessionAttribute("flash-type", "success");
            ctx.redirect(NamedRoutes.urlPath(urlId));
        } catch (RuntimeException e) {
            ctx.sessionAttribute("message", "Проверка не прошла");
            ctx.redirect(NamedRoutes.urlPath(urlId));
        }
    }
}
