<!doctype html>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>
    <g:layoutTitle default="Grails"/>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
    <asset:link rel="icon" href="favicon.ico" type="image/x-ico" />

    <asset:stylesheet src="application.css"/>

    <g:layoutHead/>
    <style>
    a {color: orangered;}
    </style>
</head>
<body>



    <nav class="navbar navbar-expand-lg navbar-light bg-dark" style="background-color: #e3f2fd ;>
    <a class="navbar-brand" href="">
<asset:image src="coin.png" width="200" height="100"/>
    </a>
    <p class="text-primary">                     </p>

        <h3 style="color: orange">Lecoincoin</h3>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/users">Users</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/announces">Annonces</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/annonce/indexClient">Annonce Client</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/illustrations">Illustrations</a>
                </li>





            </ul>

        </div>
        <div class="navbar-brand" aria-expanded="false" style= "color:white;">
            <ul class="nav navbar-nav navbar-right">
                <sec:ifLoggedIn>
                    Welcome <sec:username/>
                    <g:link controller='logout'>logout</g:link>
                </sec:ifLoggedIn>
                <sec:ifNotLoggedIn>
                    <a href="/user">Sign Up</a>
                </sec:ifNotLoggedIn>

</ul>

        </div>
    </nav>



<g:layoutBody/>

<div class="footer" role="contentinfo"></div>

<div id="spinner" class="spinner" style="display:none;">
    <g:message code="spinner.alt" default="Loading&hellip;"/>
</div>

<asset:javascript src="application.js"/>

</body>
</html>