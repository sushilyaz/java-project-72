package gg.jte.generated.ondemand;
import hexlet.code.dto.UrlPage;
public final class JteshowUrlGenerated {
	public static final String JTE_NAME = "showUrl.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,1,1,2,5,5,7,7,7,13,13,13,17,17,17,21,21,21,45,45,45,46};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, UrlPage page) {
		gg.jte.generated.ondemand.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n    <div class=\"container-lg mt-5\">\n        <h1>Сайт: ");
				jteOutput.setContext("h1", null);
				jteOutput.writeUserContent(page.getUrl().getName());
				jteOutput.writeContent("</h1>\n\n        <table class=\"table table-bordered table-hover mt-3\">\n            <tbody>\n            <tr>\n                <td>ID</td>\n                <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(page.getUrl().getId());
				jteOutput.writeContent("</td>\n            </tr>\n            <tr>\n                <td>Имя</td>\n                <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(page.getUrl().getName());
				jteOutput.writeContent("</td>\n            </tr>\n            <tr>\n                <td>Дата создания</td>\n                <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(page.getUrl().getCreatedAt().toString());
				jteOutput.writeContent("</td>\n            </tr>\n            </tbody>\n        </table>\n\n        <h2 class=\"mt-5\">Проверки</h2>\n        <form method=\"post\" action=\"/urls/1/checks\">\n            <button type=\"submit\" class=\"btn btn-primary\">Запустить проверку</button>\n        </form>\n\n        <table class=\"table table-bordered table-hover mt-3\">\n            <thead>\n            <tr><th class=\"col-1\">ID</th>\n                <th class=\"col-1\">Код ответа</th>\n                <th>title</th>\n                <th>h1</th>\n                <th>description</th>\n                <th class=\"col-2\">Дата проверки</th>\n            </tr></thead>\n            <tbody>\n\n            </tbody>\n        </table>\n    </div>\n    ");
			}
		});
		jteOutput.writeContent("\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		UrlPage page = (UrlPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
