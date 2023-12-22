package hexlet.code.dto;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import lombok.Getter;

import java.util.Map;

@Getter
public class UrlsPage extends BasePage {
    private Map<Url, UrlCheck> page;

    public UrlsPage(Map<Url, UrlCheck> page) {
        this.page = page;
    }
}
