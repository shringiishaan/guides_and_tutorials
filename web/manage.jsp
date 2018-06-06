<%@page import="dao.ArticleDAO"%>
<%@page import="model.Article"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Topic"%>
<%@page import="dao.TopicDAO"%>
<%@page import="model.User"%>
<%@page import="dao.UserDAO"%>
<%@page import="dao.TutorialDAO"%>
<%@page import="java.util.List"%>
<%@page import="model.Tutorial"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%  
    User user = null;
    UserDAO userdao = new UserDAO();
    if(session.getAttribute("userId")==null) {
        response.sendRedirect("/Logout");
        return;
    }
    user = userdao.getUserById((Integer)session.getAttribute("userId"));
    if(user==null || !user.getType().equals("admin")) {
        response.sendRedirect("/Logout");
        return;
    }
    
    List<Tutorial> tempTutorials=null;
    List<Article> tempArticles=null;
    
    ArticleDAO articledao = new ArticleDAO();
    TutorialDAO tutorialdao = new TutorialDAO();
    TopicDAO topicdao = new TopicDAO();
    
    List<Topic> allTopics = topicdao.getAllTopics();
    
    String currentTopicId = request.getParameter("ctpi");
    String currentTutorialId = request.getParameter("ctti");
    
    if(currentTopicId==null || currentTopicId.isEmpty()) {
        currentTopicId = allTopics.get(0).getId();
        currentTutorialId = tutorialdao.getMaximumPriorityTutorialByTopicId(currentTopicId).getId();
        response.sendRedirect("/manage.jsp?ctpi=" + currentTopicId + "&ctti=" + currentTutorialId);
    }
    else if(currentTutorialId==null || currentTutorialId.isEmpty()) {
        currentTutorialId = tutorialdao.getMaximumPriorityTutorialByTopicId(currentTopicId).getId();
        response.sendRedirect("/manage.jsp?ctpi=" + currentTopicId + "&ctti=" + currentTutorialId);
    }

%>
<!DOCTYPE html>
<html>
    <head>
        <title>The Computer Guide | Manage Topics</title>
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
                    <li class="nav-item dropdown active">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" 
                           role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Admin
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item" href="/managetopics">Topics</a>
                            <a class="dropdown-item" href="/managetmages">Images</a>
                        </div>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/Logout">Logout</a>
                    </li>
                </ul>
            </div>
        </nav>

        <!-- container -->
        <div class="container">
            <div class="row">

                <!-- main content -->
                <div class="col-12 pt-3 is-content">
                    <div class="row">
                        <div class="col-12">
                            <div>
                                <a class="btn btn-sm btn-outline-info" href="#!">Dashboard</a> 
                                <a class="btn btn-sm btn-info" href="#!">Manage</a>
                                <a class="btn btn-sm btn-outline-info" href="#!">Images</a> 
                            </div>
                            <hr />
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
                                <div class="col-lg-4">
                                    <div class="card card-body">
                                        <h6>Topics</h6>
                                        <hr />
                                        <% for(int i=0; i<allTopics.size(); i++) { %>
                                            <a class="<%=allTopics.get(i).getId().equals(currentTopicId)?"text-primary":"text-secondary"%>" 
                                               href="/manage.jsp?ctpi=<%=allTopics.get(i).getId()%>"><%=allTopics.get(i).getTitle()%></a>
                                        <% } %>
                                    </div>
                                    <div class="card card-body mt-3">
                                        <h6>Topic-Articles</h6>
                                        <hr />
                                        <% 
                                            tempArticles = articledao.getArticlesByTopicIdAndStatus(currentTopicId,null,false);
                                            for(int i=0; i<tempArticles.size(); i++) { %>
                                            <div class="row">
                                                <div class="col-10">
                                                    <a class="text-secondary"><%=tempArticles.get(i).getTitle()%></a>
                                                </div>
                                                <div class="col-2">
                                                    <i onclick="manageTopicArticle(this)" is-id="<%=tempArticles.get(i).getId()%>" style="cursor:pointer;" class="fa fa-cog"></i>
                                                </div>
                                            </div>
                                        <% } %>
                                    </div>
                                </div>
                                <div class="col-lg-4">
                                    <div class="card card-body">
                                        <h6>Topic-Tutorials</h6>
                                        <hr />
                                        <% 
                                            tempTutorials = tutorialdao.getTutorialsByTopicIdAndStatus(currentTopicId,null);
                                            for(int i=0; i<tempTutorials.size(); i++) { %>
                                            <div class="row">
                                                <div class="col-10">
                                                    <a class="<%=tempTutorials.get(i).getId().equals(currentTutorialId)?"text-primary":"text-secondary"%>" 
                                                        href="/manage.jsp?ctpi=<%=currentTopicId%>&ctti=<%=tempTutorials.get(i).getId()%>">
                                                        <%=tempTutorials.get(i).getTitle()%></a>
                                                </div>
                                                <div class="col-2">
                                                    <i onclick="manageTopicTutorial(this)" is-id="<%=tempTutorials.get(i).getId()%>" style="cursor:pointer;" class="fa fa-cog"></i>
                                                </div>
                                            </div>
                                        <% } %>
                                    </div>
                                </div>
                                <div class="col-lg-4">
                                    <div class="card card-body">
                                        <h6>Tutorial-Articles</h6>
                                        <hr />
                                        <% 
                                            tempArticles = articledao.getArticlesByTutorialIdAndStatus(currentTutorialId,null,false);
                                            for(int i=0; i<tempArticles.size(); i++) { %>
                                            <div class="row">
                                                <div class="col-10">
                                                    <a class="text-secondary"><%=tempArticles.get(i).getTitle()%></a>
                                                </div>
                                                <div class="col-2">
                                                    <i onclick="manageTopicTutorialArticle(this)" is-id="<%=tempArticles.get(i).getId()%>" style="cursor:pointer;" class="fa fa-cog"></i>
                                                </div>
                                            </div>
                                        <% } %>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-4">
                                    <div class="card card-body mt-3">
                                        <h6>No-link Tutorials</h6>
                                        <hr />
                                        <% 
                                            tempTutorials = tutorialdao.getUnlinkedTutorials();
                                            for(int i=0; i<tempTutorials.size(); i++) { %>
                                            <div class="row">
                                                <div class="col-10">
                                                    <a class="text-secondary"><%=tempTutorials.get(i).getTitle()%></a>
                                                </div>
                                                <div class="col-2">
                                                    <i onclick="manageNoLinkTutorial(this)" is-id="<%=tempTutorials.get(i).getId()%>" style="cursor:pointer;" class="fa fa-cog"></i>
                                                </div>
                                            </div>
                                        <% } %>
                                    </div>
                                </div>
                                <div class="col-lg-4">
                                    <div class="card card-body mt-3">
                                        <h6>No-Link Articles</h6>
                                        <hr />
                                        <% 
                                            tempArticles = articledao.getUnlinkedArticles();
                                            for(int i=0; i<tempArticles.size(); i++) { %>
                                            <div class="row">
                                                <div class="col-10">
                                                    <a class="text-secondary"><%=tempArticles.get(i).getTitle()%></a>
                                                </div>
                                                <div class="col-2">
                                                    <i onclick="manageNoLinkArticle(this)" is-id="<%=tempArticles.get(i).getId()%>" style="cursor:pointer;" class="fa fa-cog"></i>
                                                </div>
                                            </div>
                                        <% } %>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <hr />  
        </div>
                            
        <div class="modal fade" id="manageTopicTutorialModal" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-md" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h6>Topic-Tutorial</h6>
                    </div>
                    <div class="modal-body">
                        <p>
                            
                        </p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-outline-secondary" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
                            
        <div class="modal fade" id="manageTopicArticleModal" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-md" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h6>Topic-Article</h6>
                    </div>
                    <div class="modal-body">
                        <p>
                            <form method="POST" action="/setarticlescope">
                                <div class="input-group">
                                    <input type="text" name="scope" class="form-control" value="" />
                                    <input type="text" name="articleId" hidden="true" value="" />
                                    <div class="input-group-append">
                                        <input type="text" name="redirectURL" hidden="true" value="/ManageTopics" />
                                        <button class="btn btn-sm btn-outline-info" type="submit">Update Scope</button>
                                    </div>
                                </div>
                            </form>
                        </p>
                        <p>
                            <form method="POST" action="/setarticlestatus">
                                <div class="input-group">
                                    <input type="text" name="status" class="form-control" value="" />
                                    <input type="text" name="articleId" hidden="true" value="" />
                                    <div class="input-group-append">
                                        <input type="text" name="redirectURL" hidden="true" value="/ManageTopics" />
                                        <button class="btn btn-sm btn-outline-info" type="submit">Update Status</button>
                                    </div>
                                </div>
                            </form>
                        </p>
                        <p>
                            <form method="POST" action="/removetopicarticlelink">
                                <div class="input-group">
                                    <input type="text" name="topicId" hidden="true" value="" />
                                    <input type="text" name="articleId" hidden="true" value="" />
                                    <div class="input-group-append">
                                        <input type="text" name="redirectURL" hidden="true" value="/ManageTopics" />
                                        <button class="btn btn-sm btn-outline-warning" type="submit">Remove Link</button>
                                    </div>
                                </div>
                            </form>
                        </p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-outline-secondary" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
                            
        <div class="modal fade" id="manageTopicTutorialArticleModal" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-md" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h6>Topic-Tutorial-Article</h6>
                    </div>
                    <div class="modal-body">
                        <p>
                            
                        </p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-outline-secondary" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
                            
        <div class="modal fade" id="manageNoLinkTutorialModal" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-md" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h6>No-Link Tutorial</h6>
                    </div>
                    <div class="modal-body">
                        <p>
                            
                        </p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-outline-secondary" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
                            
        <div class="modal fade" id="manageNoLinkArticleModal" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-md" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h6>No-Link Article</h6>
                    </div>
                    <div class="modal-body">
                        <p>
                            
                        </p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-outline-secondary" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
        <script lang="text/javascript">
            
            var currentTopicId = "<%=currentTopicId%>";
            var currentTutorialId = "<%=currentTutorialId%>";
            
            var activeId = "";
            
            function manageTopicTutorial(var1) {
                activeId = $(var1).attr("is-id");
                $("#manageTopicTutorialModal").modal("show");
            }
            
            function manageTopicArticle(var1) {
                activeId = $(var1).attr("is-id");
                $("#manageTopicArticleModal").modal("show");
            }
            
            function manageTopicTutorialArticle(var1) {
                activeId = $(var1).attr("is-id");
                $("#manageTopicTutorialArticleModal").modal("show");
            }
            
            function manageNoLinkTutorial(var1) {
                activeId = $(var1).attr("is-id");
                $("#manageNoLinkTutorialModal").modal("show");
            }
            
            function manageNoLinkArticle(var1) {
                activeId = $(var1).attr("is-id");
                $("#manageNoLinkArticleModal").modal("show");
            }
            
        </script>
    </body>
</html>