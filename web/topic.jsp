<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@page import="model.Comment"%>
<%@page import="dao.CommentDAO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="model.Topic"%>
<%@page import="dao.TopicDAO"%>
<%@page import="dao.UserDAO"%>
<%@page import="model.User"%>
<%@page import="java.util.List"%>
<%@page import="model.Article"%>
<%@page import="dao.ArticleDAO"%>
<%@page import="model.Tutorial"%>
<%@page import="dao.TutorialDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String requestedPath = request.getRequestURI().substring(request.getContextPath().length());
    String[] parts = requestedPath.split("/");
    
    if(parts.length!=3 || !parts[0].isEmpty() || !parts[1].equals("Topic")) {
        request.getRequestDispatcher("/error404.jsp").forward(request, response);
        return;
    }
    
    List<Article> tempArticles=null;
    List<Tutorial> topicTutorials=null;
    Topic currentTopic=null;
    List<Topic> allTopics=null;
    
    TutorialDAO tutorialdao = new TutorialDAO();
    ArticleDAO articledao = new ArticleDAO();
    UserDAO userdao = new UserDAO();
    TopicDAO topicdao = new TopicDAO();
    
    String requestTopicId = parts[2];
    currentTopic = topicdao.getTopicById(requestTopicId);
    if(currentTopic==null) {
        request.getRequestDispatcher("/error404.jsp").forward(request, response);
        return;
    }
   
    User tempUser = null;
    User user = null;
    Boolean isUser=false, isAdmin=false;
    
    if(session.getAttribute("userId") != null) {
        user = userdao.getUserById((Integer)session.getAttribute("userId"));
        if(user.getType().equals("admin")) {
            isAdmin=true;
        }
        else {
            isUser=true;
        }
    }
    
    allTopics = topicdao.getAllTopics();
%>
<!DOCTYPE html>
<html>
    <head>
        <title><%=currentTopic.getTitle()%> | The Science of Computing</title>
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
        <link rel="stylesheet" href="/prism/prism.css" />
    </head>
    <body>
        <!-- navigation bar -->
        <nav class="navbar navbar-expand-lg fixed-top navbar-dark is-navbar">
            <a class="navbar-brand" href="/Topic/<%=currentTopic.getId()%>"><%=currentTopic.getTitle()%></a>
            
            <button class="btn btn-info d-inline-block d-lg-none float-right" data-toggle="modal" data-target="#navigationModal">
                <i class="fa fa-bars"></i>
            </button>

            <div class="d-none d-lg-inline-block ml-auto">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="/"><i class="fa fa-home"></i> Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#!" data-toggle="modal" data-target="#navigationModal"><i class="fa fa-bars"></i> Explore</a>
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
                    %>
                </ul>
            </div>
        </nav>

        <!-- container -->
        <div class="container-fluid">
            <div class="row pt-5">
                <div class="col-lg-8 offset-lg-2">
                    <h4 class="text-center"><%=currentTopic.getTitle()%></h4>
                    <hr />
                    <div class="row mb-3">
                        <div class="col-lg-6 offset-lg-3">
                            <form action="/Search" method="GET">
                                <div class="input-group">
                                    <input type="text" name="s" class="form-control" placeholder="Search <%=currentTopic.getTitle()%> Tutorials and Articles..." />
                                    <input name="t" hidden="true" value="<%=currentTopic.getId()%>" />
                                    <div class="input-group-append">
                                        <button class="btn btn-outline-info" type="submit"><i class="fa fa-search"></i></button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-8 offset-lg-2">
                            <h6 class="text-center text-muted">Articles</h6>
                            <hr/>
                            <div class="row">
                                <div class="col-lg-6">
                                    <div class='card card-body mb-2'>
                                        <h6>Top Articles</h6>
                                        <hr />
                                        <%
                                            tempArticles = articledao.getArticlesByTopicIdAndStatus(currentTopic.getId(),(isAdmin?null:"final"), false);
                                            for(int j=0; j<tempArticles.size(); j++) { 
                                                %><a href="/Article/<%=tempArticles.get(j).getId()%>"><%=tempArticles.get(j).getTitle()%></a><%
                                            }
                                        %>
                                    </div>
                                </div>
                                <div class="col-lg-6">
                                    <div class='card card-body mb-2'>
                                        <h6>Latest Articles</h6>
                                        <hr />
                                        <%
                                            tempArticles = articledao.getArticlesByTopicIdAndStatus(currentTopic.getId(),(isAdmin?null:"final"), false);
                                            for(int j=0; j<tempArticles.size(); j++) { 
                                                %><a href="/Article/<%=tempArticles.get(j).getId()%>"><%=tempArticles.get(j).getTitle()%></a><%
                                            }
                                        %>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row pt-3">
                        <div class="col-12">
                            <h6 class="text-center text-muted">Tutorials</h6>
                            <hr/>
                            <div class="row">
                                <%
                                    topicTutorials = tutorialdao.getTutorialsByTopicIdAndStatus(currentTopic.getId(),"final");
                                    %>
                                    <%
                                    for (int i=0; i<topicTutorials.size(); i++) {
                                        %>
                                        <div class="col-lg-4 col-md-6">
                                            <div class='card card-body mb-2'>
                                                <h6><%=(topicTutorials.get(i)).getTitle()%></h6>
                                                <hr />
                                                <%
                                                    tempArticles = articledao.getArticlesByTutorialIdAndStatus((topicTutorials.get(i)).getId(),(isAdmin?null:"final"), false);
                                                    for(int j=0; j<tempArticles.size(); j++) { 
                                                        %><a href="/Article/<%=tempArticles.get(j).getId()%>"><%=tempArticles.get(j).getTitle()%></a><%
                                                    }
                                                %>
                                            </div>
                                        </div>
                                        <%
                                    }
                                %>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Modal -->
        <div class="modal fade" id="navigationModal" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <span class="text-muted mt-1 mr-2"><i>Current Topic : </i></span>
                        <div class="dropdown">
                            <button class="btn btn-sm btn-outline-info dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <%=currentTopic.getTitle()%>
                            </button>
                            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                <%
                                    for (int i = 0; i < allTopics.size(); i++) {
                                        if(allTopics.get(i).getId().equals(currentTopic.getId())) {
                                            continue;
                                        }
                                        %><a class="dropdown-item" href="/Topic/<%=allTopics.get(i).getId()%>"><%=allTopics.get(i).getTitle()%></a><%
                                    }
                                %>
                            </div>
                        </div>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                    </div>
                    <div class="modal-footer">
                        <a href="/" class="btn btn-sm btn-outline-info"><i class="fa fa-home"></i> Go Home</a>
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
                                <input type="text" name="redirectURL" hidden="true" value="/Topic/<%=currentTopic.getId()%>" />
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-outline-secondary" data-dismiss="modal">Close</button>
                            <input type="submit" name="submit" value="Login" class="btn btn-info" />
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
        
        <script src="/prism/prism.js"></script>
    </body>
</html>