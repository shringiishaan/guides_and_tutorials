<%@page import="dao.TutorialDAO"%>
<%@page import="java.util.List"%>
<%@page import="model.Tutorial"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    TutorialDAO tutorialdao = new TutorialDAO();
    List<Tutorial> allTutorials = tutorialdao.getAllTutorials();
%>
<!DOCTYPE html>
<html>
    <head>
        <title>The Computer Guide | Home</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta charset="UTF-8" />
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.9/css/all.css" crossorigin="anonymous" type="text/css" />
        <link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet" />
        <link rel="stylesheet" href="/main/main.css" type="text/css" />
        <link rel="stylesheet" href="/navbar/navbar.css" type="text/css" />
        <link rel="stylesheet" href="/sidebar/sidebar.css" type="text/css" />
        <link rel="stylesheet" href="/infobar/infobar.css" type="text/css" />
        <link rel="stylesheet" href="/content/content.css" type="text/css" />
    </head>
    <body>
        <!-- navigation bar -->
        <nav class="navbar navbar-expand-lg fixed-top navbar-dark is-navbar">
            <a class="navbar-brand" href="/">Computer Guide</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="/">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Tutorials</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Articles</a>
                    </li>
                </ul>
            </div>
        </nav>

        <!-- container -->
        <div class="container-fluid">
            <div class="row">
                <!-- side bar -->
                <div class="is-sidebar col-lg-3 d-none d-lg-block p-4">
                    <a class="is-title">Tutorials</a>
                    <%
                        for (int i = 0; i < allTutorials.size(); i++) {
                    %>
                        <a class="is-label" href="/article/<%=allTutorials.get(i).getId()%>"><%=allTutorials.get(i).getTitle()%></a>
                    <%
                        }
                    %>
                </div>

                <!-- main content -->
                <div class="col-lg-6 offset-lg-3 pt-5 is-content">
                    <div class="row">
                        <div class="col-md-8 offset-md-2">
                            <%
                                if (session.getAttribute("error") != null) {
                                %>
                                    <div class="alert alert-warning alert-dismissible fade show" role="alert">
                                        <%=session.getAttribute("error")%>
                                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                <%
                                    session.removeAttribute("error");
                                }
                                if (session.getAttribute("message") != null) {
                                %>
                                    <div class="alert alert-info alert-dismissible fade show" role="alert">
                                        <%=session.getAttribute("message")%>
                                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                <%
                                    session.removeAttribute("message");
                                }
                            %>
                            <h4>ERROR : 404</h4>
                            <hr/>
                            <h5>Page not found :(</h5>
                        </div>
                    </div>
                </div>

                <!-- information bar -->
                <div class="col-lg-3 offset-lg-9 p-4 is-infobar">
                    <div class="card mb-3">
                        <div class="card-header">
                            <div class="row">
                                <div class="col">
                                    Top Tutorials
                                </div>
                            </div>
                        </div>
                        <div class="card-body">
                            <a href="#!">Java Advanced Guide</a>
                            <a href="#!">Web Development with Java</a>
                        </div>
                    </div>
                    <div class="card mb-3">
                        <div class="card-header">
                            <div class="row">
                                <div class="col">
                                    Top Articles
                                </div>
                            </div>
                        </div>
                        <div class="card-body">
                            <a href="#!">Java Advanced Guide</a>
                            <a href="#!">Web Development with Java</a>
                            <a href="#!">Java Advanced Guide</a>
                            <a href="#!">Web Development with Java</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    </body>
</html>