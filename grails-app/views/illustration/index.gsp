<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'illustration.label', default: 'Illustration')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#list-illustration" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="list-illustration" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <table>
                <thead>
                <tr>

                    <th class="sortable"><a
                            href="/projet/illustration/index?sort=filename&amp;max=10&amp;order=asc">Filename</a></th>

                    <th class="sortable"><a href="/projet/illustration/index?sort=annonce&amp;max=10&amp;order=asc">Annonce</a>
                    </th>

                </tr>
                </thead>
                <tbody>
                <g:each in="${illustrationList}" var="illustration">
                    <tr class="even">

                        <td>
                            <g:link controller="illustration" action="show" id="${illustration.id}">
                                <asset:image src="${illustration.filename}"></asset:image>
                            </g:link>
                        </td>



                        <td>
                            <g:link controller="annonce" action="show" id="${illustration.annonce.id}">
                                ${illustration.annonce.title}
                            </g:link>
                        </td>

                    </tr>
                </g:each>
                </tbody>
            </table>

            <div class="pagination">
                <g:paginate total="${illustrationCount ?: 0}" />
            </div>
        </div>
    </body>
</html>