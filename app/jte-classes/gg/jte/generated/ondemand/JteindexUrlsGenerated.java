package gg.jte.generated.ondemand;
import hexlet.code.dto.UrlsPage;
import hexlet.code.util.NamedRoutes;
public final class JteindexUrlsGenerated {
	public static final String JTE_NAME = "indexUrls.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,2,2,4,4,7,7,8,8,9,9,9,9,10,10,10,12,12,27,27,30,30,30,33,33,33,33,33,33,33,33,33,33,33,33,38,38,44,44,44};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, UrlsPage page) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n    ");
				if (page != null && page.getFlash() != null) {
					jteOutput.writeContent("\n        <div class=\"alert alert-");
					jteOutput.setContext("div", "class");
					jteOutput.writeUserContent(page.getFlashType());
					jteOutput.setContext("div", null);
					jteOutput.writeContent("\" role=\"alert\">\n            ");
					jteOutput.setContext("div", null);
					jteOutput.writeUserContent(page.getFlash());
					jteOutput.writeContent("\n        </div>\n    ");
				}
				jteOutput.writeContent("\n        <main class=\"flex-grow-1\">\n            <section>\n                <div class=\"container-lg mt-5\">\n                    <h1>Сайты</h1>\n                    <table class=\"table table-bordered table-hover mt-3\">\n                        <thead>\n                        <tr>\n                            <th class=\"col-1\">ID</th>\n                            <th>Имя</th>\n                            <th class=\"col-2\">Последняя проверка</th>\n                            <th class=\"col-1\">Код ответа</th>\n                        </tr>\n                        </thead>\n                        <tbody>\n                        ");
				for (var url : page.getUrls()) {
					jteOutput.writeContent("\n                            <tr>\n                                <td>\n                                    ");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(url.getId());
					jteOutput.writeContent("\n                                </td>\n                                <td>\n                                    <a");
					var __jte_html_attribute_0 = NamedRoutes.urlPath(url.getId());
					if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
						jteOutput.writeContent(" href=\"");
						jteOutput.setContext("a", "href");
						jteOutput.writeUserContent(__jte_html_attribute_0);
						jteOutput.setContext("a", null);
						jteOutput.writeContent("\"");
					}
					jteOutput.writeContent(">");
					jteOutput.setContext("a", null);
					jteOutput.writeUserContent(url.getName());
					jteOutput.writeContent("</a>\n                                </td>\n                                <td> </td>\n                                <td> </td>\n                            </tr>\n                        ");
				}
				jteOutput.writeContent("\n                        </tbody>\n                    </table>\n                </div>\n            </section>\n        </main>\n    ");
			}
		});
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		UrlsPage page = (UrlsPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
