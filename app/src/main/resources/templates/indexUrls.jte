@import hexlet.code.dto.UrlsPage
@import hexlet.code.model.Url
@import hexlet.code.model.UrlCheck
@import hexlet.code.util.NamedRoutes
@import java.text.*
@import java.util.Map
@param UrlsPage page

@template.page (

    content = @`
    @if(page != null && page.getFlash() != null)
        <div class="alert alert-${page.getFlashType()}" role="alert">
            ${page.getFlash()}
        </div>
    @endif
        <main class="flex-grow-1">
            <section>
                <div class="container-lg mt-5">
                    <h1>Сайты</h1>
                    <table class="table table-bordered table-hover mt-3">
                        <thead>
                        <tr>
                            <th class="col-1">ID</th>
                            <th>Имя</th>
                            <th class="col-2">Последняя проверка</th>
                            <th class="col-1">Код ответа</th>
                        </tr>
                        </thead>
                        <tbody>
                        @if (!page.getPage().isEmpty())
                            @for (Map.Entry<Url, UrlCheck> entry : page.getPage().entrySet())
                                <tr>
                                    <td>
                                        ${entry.getKey().getId()}
                                    </td>
                                    <td>
                                        <a href="${NamedRoutes.urlPath(entry.getKey().getId())}">${entry.getKey().getName()}</a>
                                    </td>
                                    @if (entry.getValue().getCreatedAt() != null)
                                        <td>
                                            ${new SimpleDateFormat("dd/MM/yy HH:mm").format(entry.getValue().getCreatedAt())}
                                        </td>
                                    @else
                                        <td> </td>
                                    @endif
                                    @if (entry.getValue().getStatusCode() != 0)
                                        <td>
                                            ${entry.getValue().getStatusCode()}
                                        </td>
                                    @else
                                        <td> </td>
                                    @endif
                                </tr>
                            @endfor
                        @endif
                        </tbody>
                    </table>
                </div>
            </section>
        </main>
    `
)