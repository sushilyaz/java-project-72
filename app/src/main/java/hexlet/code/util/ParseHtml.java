package hexlet.code.util;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ParseHtml {
    public static String getHtmlContent(String url) {
        try {
            // Отправка HTTP GET запроса и получение HTML-кода страницы
            HttpResponse<String> response = Unirest.get(url).asString();
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            // Закрытие соединения после использования
            Unirest.shutDown();
        }
    }

    public static String getTitle(Document doc) {
        return doc.title();
    }

    public static String getH1(Document doc) {
        // Извлечение и вывод h1 (первого встреченного элемента)
        Element h1Element = doc.select("h1").first();
        String h1 = (h1Element != null) ? h1Element.text() : "";
        return h1;
    }

    public static String getDescription(Document doc) {
        // Извлечение и вывод мета-тега description
        Element descriptionMetaTag = doc.select("meta[name=description]").first();
        String description = (descriptionMetaTag != null) ? descriptionMetaTag.attr("content") : "";
        return description;
    }
}
