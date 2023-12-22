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

import java.sql.SQLException;
import java.sql.Timestamp;

public class UrlCheckController {
    public static void create(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Integer.class);
        var url = UrlRepository.findById(id.get());
        var domain = url.get().getName();
        var urlId = id.get();
        int statusCode = Unirest.get(domain).asString().getStatus();
        // Использование jsoup для парсинга HTML
        String htmlContent = ParseHtml.getHtmlContent(domain);
        Document doc = Jsoup.parse(htmlContent);
        String h1 = ParseHtml.getH1(doc);
        String title = ParseHtml.getTitle(doc);
        String description = ParseHtml.getDescription(doc);
        var createdAt = FormatTimestamp.formatTimestamp(new Timestamp(System.currentTimeMillis()));
        var urlCheck = new UrlCheck(urlId, statusCode, h1, title, description, createdAt);
        UrlCheckRepository.save(urlCheck);
        ctx.sessionAttribute("flash", "Страница успешно проверена");
        ctx.sessionAttribute("flash-type", "success");
        ctx.redirect(NamedRoutes.urlPath(id.get()));
    }
}
