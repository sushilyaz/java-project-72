package hexlet.code.controller;

import java.net.MalformedURLException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;
import java.net.URL;
import java.util.Optional;

import hexlet.code.dto.UrlPage;
import hexlet.code.dto.UrlsPage;
import hexlet.code.repository.UrlRepository;
import hexlet.code.model.Url;

import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

public class UrlController {
    public static void create(Context ctx) throws SQLException {
        String url = ctx.formParam("url");
        URL urlObj = null;
        String name = "";
        try {
            urlObj = new URL(url);
            if (urlObj.getPort() != -1) {
                name = urlObj.getProtocol() + "://" + urlObj.getHost() + ":" + String.valueOf(urlObj.getPort());
            } else {
                name = urlObj.getProtocol() + "://" + urlObj.getHost();
            }
        } catch (MalformedURLException e) {
            // добавить обработчик флеш сообщения
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.sessionAttribute("flash-type", "danger");
            ctx.redirect(NamedRoutes.rootPath());
            return;
        }
        if (UrlRepository.find(name).isEmpty()) {
            var resUrl = new Url(name, new Timestamp(System.currentTimeMillis()));
            UrlRepository.save(resUrl);
            ctx.sessionAttribute("flash", "Страница успешно добавлена");
            ctx.sessionAttribute("flash-type", "success");
            ctx.redirect(NamedRoutes.urlsPath());
        } else {
            ctx.sessionAttribute("flash", "Страница уже существует");
            ctx.sessionAttribute("flash-type", "info");
            ctx.redirect(NamedRoutes.urlsPath());
        }
    }

    public static void index(Context ctx) throws SQLException {
        var urls = UrlRepository.getEntities();
        var page = new UrlsPage(urls);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flash-type"));
        ctx.render("indexUrls.jte", Collections.singletonMap("page", page));
    }
    public static void show(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", int.class).get();
        var url = UrlRepository.findById(id)
                .orElseThrow(() -> new NotFoundResponse("Url not found"));
        var page = new UrlPage(url);
        ctx.render("showUrl.jte", Collections.singletonMap("page", page));
    }


}
