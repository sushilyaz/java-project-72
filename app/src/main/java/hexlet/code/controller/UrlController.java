package hexlet.code.controller;

import java.net.MalformedURLException;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.net.URL;
import java.util.Map;
import java.util.HashMap;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.ArrayList;



import hexlet.code.dto.UrlCheckPage;
import hexlet.code.dto.UrlPage;
import hexlet.code.dto.UrlsPage;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.model.Url;

import hexlet.code.util.FormatTimestamp;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

public class UrlController {
    public static void create(Context ctx) throws SQLException {
        String url = ctx.formParam("url");
        URL urlObj = null;
        String name = "";
        var createdAt = FormatTimestamp.formatTimestamp(new Timestamp(System.currentTimeMillis()));
        try {
            urlObj = new URL(url);
            if (urlObj.getPort() != -1) {
                name = urlObj.getProtocol() + "://" + urlObj.getHost() + ":" + String.valueOf(urlObj.getPort());
            } else {
                name = urlObj.getProtocol() + "://" + urlObj.getHost();
            }
        } catch (MalformedURLException e) {
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.sessionAttribute("flash-type", "danger");
            ctx.redirect(NamedRoutes.rootPath());
            return;
        }

        if (UrlRepository.find(name).isEmpty()) {
            var resUrl = new Url(name, createdAt);
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
        Map<Url, UrlCheck> res = new HashMap<>();
        for (var url : urls) {
            var checkUrl = UrlCheckRepository.findLatestCheck(url.getId());
            if (checkUrl.isPresent()) {
                res.put(url, checkUrl.get());
            } else {
                res.put(url, new UrlCheck(0, null));
            }
        }
        Map<Url, UrlCheck> sortedRes = new HashMap<>();
        sortedRes = res.entrySet()
                .stream()
                .sorted(Comparator.comparing(entry -> entry.getKey().getId()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        var page = new UrlsPage(sortedRes);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flash-type"));
        ctx.render("indexUrls.jte", Collections.singletonMap("page", page));
    }

    public static void show(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.findById(id)
                .orElseThrow(() -> new NotFoundResponse("Url not found"));
        List<UrlCheck> urlChecks = new ArrayList<>();
        urlChecks = UrlCheckRepository.getEntities(id);
        var page = new UrlPage(url);
        var pageCheck = new UrlCheckPage(urlChecks);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flash-type"));
        ctx.render("showUrl.jte", Map.of("page", page, "pageCheck", pageCheck));
    }


}
