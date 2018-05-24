<%@page import="model.Topic"%>
<%@page import="dao.TopicDAO"%>
<%@page import="dao.ArticleDAO"%>
<%@page import="model.Article"%>
<%@page import="dao.UserDAO"%>
<%@page import="model.User"%>
<%@page import="dao.TutorialDAO"%>
<%@page import="java.util.List"%>
<%@page import="model.Tutorial"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    TutorialDAO tutorialdao = new TutorialDAO();
    ArticleDAO articledao = new ArticleDAO();
    TopicDAO topicdao = new TopicDAO();
    
    List<Tutorial> tempTutorials;
    List<Article> tempArticles;
    List<Topic> allTopics = topicdao.getAllTopics();
    
    Boolean isUser = false, 
            isAdmin = false;
    User user = null;
    
    if(session.getAttribute("userId") != null) {
        UserDAO userdao = new UserDAO();
        user = userdao.getUserById((Integer)session.getAttribute("userId"));
        if(user.getType().equals("user")) {
            isUser=true;
        }
        else if(user.getType().equals("admin")) {
            isAdmin=true;
        }
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <title>The Science of Computing | Home</title>
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
            <a class="navbar-brand" href="/"><i><small>The</small> Science <small>Of</small> Computing</i></a>
            
            <button class="btn btn-info d-inline-block d-lg-none float-right" data-toggle="modal" data-target="#navigationModal">
                <i class="fa fa-bars"></i>
            </button>

            <div class="d-none d-lg-inline-block ml-auto">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item active">
                        <a class="nav-link" href="/"><i class="fa fa-home"></i> Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#!" data-toggle="modal" data-target="#navigationModal"><i class="fa fa-bars"></i> Topics</a>
                    </li>
                    <%
                        if(isAdmin) {
                            %><li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" 
                                   role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    Admin
                                </a>
                                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                                    <a class="dropdown-item" href="/ManageTopics">Topics</a>
                                    <a class="dropdown-item" href="/ManageImages">Images</a>
                                </div>
                            </li><%
                        }
                        if(isUser||isAdmin) {
                            %><li class="nav-item">
                                <a class="nav-link" href="/Logout">Logout</a>
                            </li><%
                        }
                    %>
                </ul>
            </div>
        </nav>

        <!-- container -->
        <div class="container">
            <div class="row">
                <!-- main content -->
                <div class="col-12 p-4 pt-3 pt-lg-4 is-content">
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
                    <div class="row">
                        <div class="col-12">
                            <p></p>
                        </div>
                    </div>
                    <div class="row mb-4 mt-3">
                        <div class="col-lg-6 offset-lg-3">
                            <form action="/Search" method="GET">
                                <div class="input-group">
                                    <input type="text" name="s" class="form-control" placeholder="Search All Tutorials and Articles..." />
                                    <div class="input-group-append">
                                        <button class="btn btn-outline-info" type="submit"><i class="fa fa-search"></i></button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xl-10 offset-xl-1">
                            <div class="row">
                                <div class="col-lg-9">
                                    <h4 class="text-center text-info"><a href="/Topic/java"><img class="img-thumbnail" style="height:70px;border:none;" src="/Image/tutorial-icon/java" /> Java</a></h4>
                                    <hr />
                                    <div class="text-center">
                                        <a class="btn btn-outline-info mb-2" href="/StartTopicTutorial/java"><i class="fa mr-2 fa-sm fa-play"></i> Start Tutorial</a>
                                        <a class="btn btn-outline-info mb-2" href="/Topic/java"><i class="fa mr-2 fa-sm fa-sitemap"></i> Explore</a>
                                    </div>
                                    <div class="row mb-3">
                                        <div class="col-sm-6 col-md-4">
                                            <h6>Top Articles</h6>
                                            <%
                                                tempArticles = articledao.getArticlesByTopicIdAndStatus("java",(isAdmin?null:"final"),false);
                                                if(tempArticles!=null)
                                                for(int k=0; k<tempArticles.size(); k++) {
                                                    %><small><a href="/Article/<%=tempArticles.get(k).getId()%>" class="d-block"><%=tempArticles.get(k).getTitle()%></a></small><%
                                                } 
                                            %>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-2">
                                            <h6>Tutorials</h6>
                                        </div>
                                    </div>
                                    <div class="row">
                                    <%
                                        tempTutorials = tutorialdao.getTutorialsByTopicIdAndStatus("java",(isAdmin?null:"final"));
                                        if(tempTutorials!=null)
                                        for(int k=0; k<tempTutorials.size(); k++) {
                                            tempArticles = articledao.getArticlesByTutorialIdAndStatus(tempTutorials.get(k).getId(),isAdmin?null:"final",false);
                                            %><div class="col-sm-6 col-md-4">
                                                <div class="card is-tutorial-card">
                                                    <div class="card-body">
                                                        <h6 class="card-title">
                                                            <%=tempTutorials.get(k).getTitle()%>
                                                            <% if(isAdmin) { %><sup class="<%=
                                                                (tempTutorials.get(k).getStatus().equals("final"))?"text-success":"text-warning"
                                                                %>"></i>(<%=tempTutorials.get(k).getStatus()%>)</i></sup>
                                                            <% } %>
                                                        </h6>
                                                        <%
                                                            for(int j=0; j<tempArticles.size(); j++) {
                                                                %><a class="card-text" href="/Article/<%=tempArticles.get(j).getId()%>">
                                                                        <%=tempArticles.get(j).getTitle()%>
                                                                        <% if(isAdmin) { %><sup class="<%=
                                                                                (tempArticles.get(j).getStatus().equals("final"))?"text-success":"text-warning"
                                                                                %>"><i><%=tempArticles.get(j).getStatus()%></i></sup>
                                                                        <% } %>
                                                                </a><%
                                                            }
                                                        %>
                                                    </div>
                                                </div>
                                            </div><%
                                        } 
                                    %>
                                    </div>
                                </div>
                                <div class="col-lg-3">
                                    <h4 class="text-center text-info"><a href="/Topic/operating-systems"><img class="img-thumbnail" style="height:70px;border:none;" src="/Image/tutorial-icon/operating-systems" /> Operating Systems</a></h4>
                                    <hr />
                                    <div class="text-center">
                                        <a class="btn btn-outline-info mb-2" href="/StartTopicTutorial/operating-systems"><i class="fa mr-2 fa-sm fa-play"></i> Start Tutorial</a>
                                        <a class="btn btn-outline-info mb-2" href="/Topic/operating-systems"><i class="fa mr-2 fa-sm fa-sitemap"></i> Explore</a>
                                    </div>
                                    <div class="row mt-2">
                                        <div class="col-2">
                                            <h6>Tutorials</h6>
                                        </div>
                                    </div>
                                    <div class="row"
                                    <%
                                        tempTutorials = tutorialdao.getTutorialsByTopicIdAndStatus("operating-systems",(isAdmin?null:"final"));
                                        if(tempTutorials!=null)
                                        for(int k=0; k<tempTutorials.size(); k++) {
                                            tempArticles = articledao.getArticlesByTutorialIdAndStatus(tempTutorials.get(k).getId(),isAdmin?null:"final",false);
                                            %><div class="col-sm-6 offset-sm-3 col-md-4 offset-md-4 col-lg-12 offset-lg-0">
                                                <div class="card is-tutorial-card">
                                                    <div class="card-body">
                                                        <h6 class="card-title">
                                                            <%=tempTutorials.get(k).getTitle()%>
                                                            <% if(isAdmin) { %><sup class="<%=
                                                                (tempTutorials.get(k).getStatus().equals("final"))?"text-success":"text-warning"
                                                                %>"></i>(<%=tempTutorials.get(k).getStatus()%>)</i></sup>
                                                            <% } %>
                                                        </h6>
                                                        <%
                                                            for(int j=0; j<tempArticles.size(); j++) {
                                                                %><a class="card-text" href="/Article/<%=tempArticles.get(j).getId()%>">
                                                                        <%=tempArticles.get(j).getTitle()%>
                                                                        <% if(isAdmin) { %><sup class="<%=
                                                                                (tempArticles.get(j).getStatus().equals("final"))?"text-success":"text-warning"
                                                                                %>"><i><%=tempArticles.get(j).getStatus()%></i></sup>
                                                                        <% } %>
                                                                </a><%
                                                            }
                                                        %>
                                                    </div>
                                                </div>
                                            </div><%
                                        } 
                                    %>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div
            <br>
            <hr />
            <p class="text-muted float-right"><small>Developed By <span class="text-info" data-toggle="modal" data-target="#loginModal">Ishaan Shringi</span></small></p>
        </div>
                        
        <div class="modal fade" id="navigationModal" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-sm" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <span class="text-muted mt-1 mr-2">All Topics</span>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">  
                    <%
                        for(int i=0; i<allTopics.size(); i++) {
                            %><div class="card card-body mb-2 text-center">
                                <div class="d-block">
                                    <img class="img-thumbnail" style="height:50px;border:none;" src="/Image/tutorial-icon/<%=allTopics.get(i).getId()%>" />
                                </div>
                                <h6><%=allTopics.get(i).getTitle()%></h6>
                                <hr />
                                <a class="btn btn- btn-info mb-2" href="/StartTopicTutorial/<%=allTopics.get(i).getId()%>"><i class="fa mr-2 fa-sm fa-play"></i> Start Tutorial</a>
                                <a class="btn btn- btn-info" href="/Topic/<%=allTopics.get(i).getId()%>"><i class="fa mr-2 fa-sm fa-sitemap"></i> Explore</a>
                            </div><%
                        }
                    %>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-sm btn-outline-secondary" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-sm" role="document">
                <div class="modal-content">
                    <form action="/Login" method="post">
                        <div class="modal-body">
                            <div class="form-group">
                                <input type="text" class="form-control" name="email" placeholder="Email" />
                            </div>
                            <div class="form-group">
                                <input type="password" class="form-control" name="password" placeholder="Password" />
                            </div>
                        </div>
                        <div class="modal-footer">
                            <input type="text" name="redirectURL" hidden="true" value="/" />
                            <button type="button" class="btn btn-outline-secondary" data-dismiss="modal">Close</button>
                            <input type="submit" name="submit" value="Login" class="btn btn-info" />
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    </body>
</html>