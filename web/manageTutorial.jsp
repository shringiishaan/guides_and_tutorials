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
    User user = null;
    
    if(session.getAttribute("userId") == null) {
        response.sendRedirect("/logout");
        return;
    }
    
    UserDAO userdao = new UserDAO();
    user = userdao.getUserById((Integer)session.getAttribute("userId"));
    if(!user.getType().equals("admin")) {
        response.sendRedirect("/logout");
        return;
    }
    
    TutorialDAO tutorialdao = new TutorialDAO();
    List<Tutorial> allTutorials = tutorialdao.getAllTutorials();
    
    Integer tutorialId = null;
    String tutorialIdString = request.getParameter("tid");
    if(tutorialIdString == null || tutorialIdString.isEmpty()) {
        response.sendRedirect("/managetutorial?tid="+allTutorials.get(0).getId());
        return;
    }
    else {
        tutorialId = Integer.parseInt(tutorialIdString);
    }
    
    Tutorial currentTutorial = null;
    
    for(int i=0; i<allTutorials.size(); i++) {
        if(allTutorials.get(i).getId().compareTo(tutorialId)==0) {
            currentTutorial = allTutorials.get(i);
            break;
        }
    }
    
    TopicDAO topicdao = new TopicDAO();
    List<Topic> allTopics = topicdao.getAllTopics();
    
    ArticleDAO articledao = new ArticleDAO();
    List<Article> tempArticles = null;
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Manage Tutorial | Computer Science and Programming Tutorials</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta name="robots" content="noindex" />
        <meta charset="UTF-8" />
        <meta name="google-site-verification" content="q94Vj4nrbIhqG6KIgr4iAWZmLVQa3Pm5UV2gGWSAwHE" />
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.9/css/all.css" crossorigin="anonymous" type="text/css" />
        <link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet" />
        <link rel="stylesheet" href="/main/main.css" type="text/css" />
        <link rel="stylesheet" href="/admin/admin.css" type="text/css" />
        <link rel="icon" type="image/png" href="/image/cstutorials/icon-sm" />
    </head>
    <body>
        <!-- navigation bar -->
        <nav class="navbar navbar-expand-lg is-navbar">
            <a class="navbar-brand ml-sm-3 ml-md-5" href="/"><img height="50" src="/image/cstutorials/icon-lg" alt="cstutorials icon" /></a>
            
            <div class="d-none d-lg-inline-block ml-auto mr-5">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item active">
                        <a class="nav-link" href="/admindashboard">Admin</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/logout">Logout</a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="/admindashboard" id="navbarDropdown" 
                           role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                           <i class="fa fa-bars mr-1"></i> All Topics
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <% for(int i=0; i<allTopics.size(); i++) { %>
                                <a class="dropdown-item" href="/starttopictutorial/<%=allTopics.get(i).getId()%>"><%=allTopics.get(i).getTitle()%></a>
                            <% } %>
                        </div>
                    </li>
                </ul>
            </div>
        </nav>

        <!-- container -->
        <div class="container-fluid">
            <div class="row">
                <!-- main content -->
                <div class="col-12 p-4 is-content">
                    <div class="row mb-4 mt-3">
                        <div class="col-12">
                            <a class="btn btn-sm btn-default is-btn-primary-outline" href="/admindashboard">Dashboard</a>
                            <a class="btn btn-sm btn-default is-btn-primary-outline" href="/managetopic">Topic</a>
                            <a class="btn btn-sm btn-default is-btn-primary" href="/managetutorial">Tutorial</a>
                            <a class="btn btn-sm btn-default is-btn-primary-outline" href="/managearticle">Article</a>
                            <a class="btn btn-sm btn-default is-btn-primary-outline" href="/managecomments">Comments</a>
                            <a class="btn btn-sm btn-default is-btn-primary-outline" href="/managefeedbacks">Feedbacks</a>
                            <a class="btn btn-sm btn-default is-btn-primary-outline" href="/manageimages">Images</a>
                            <hr />
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-12">
                            <div class="row">
                                <div class="col-lg-12">
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
                                </div>
                            </div>
                            <div class="row admin-form">
                                <div class="col-lg-5">
                                    <select id="tid-select" name="tid" class="form-control mb-2" onchange="changeTutorialIdSelection()">
                                        <% for(int i=0; i<allTutorials.size(); i++) { %>
                                            <option 
                                                <% if(allTutorials.get(i).getId().compareTo(currentTutorial.getId())==0) { %>selected="true"<% } %>
                                                value="<%=allTutorials.get(i).getId()%>"><%=allTutorials.get(i).getId()%>. <%=allTutorials.get(i).getTitle()%></option>
                                        <% } %>
                                    </select>
                                    <form action="/newtutorial" method="post" class="mt-2">
                                        <div class="input-group">
                                            <input type="text" name="title" class="form-control" placeholder="New Title" />
                                            <div class="input-group-append">
                                                <button class="btn btn-default is-btn-primary" type="submit">New Tutorial</button>
                                            </div>
                                        </div>
                                    </form>
                                    <hr />
                                    <button class="btn btn-default is-btn-primary" data-toggle="modal" data-target="#deleteTutorialModal">Delete</button>
                                    <form action="/updatetutorialtitle" method="POST" class="mt-2">
                                        <div class="input-group">
                                            <input name="tutorialId" value="<%=currentTutorial.getId()%>" hidden="true" />
                                            <input name="redirectURL" value="/managetutorial?tid=<%=currentTutorial.getId()%>" hidden="true" />
                                            <input type="text" name="title" class="form-control" value="<%=currentTutorial.getTitle()%>" />
                                            <div class="input-group-append">
                                                <button class="btn btn-default is-btn-primary" type="submit">Update Title</button>
                                            </div>
                                        </div>
                                    </form>
                                    <form action="/updatetutorialkey" method="POST" class="mt-2">
                                        <div class="input-group">
                                            <input name="tutorialId" value="<%=currentTutorial.getId()%>" hidden="true" />
                                            <input name="redirectURL" value="/managetutorial?tid=<%=currentTutorial.getId()%>" hidden="true" />
                                            <input type="text" name="key" class="form-control" value="<%=currentTutorial.getKey()%>" />
                                            <div class="input-group-append">
                                                <button class="btn btn-default is-btn-primary" type="submit">Update Key</button>
                                            </div>
                                        </div>
                                    </form>
                                    <form action="/updatetutorialstatus" method="POST" class="mt-2">
                                        <div class="input-group">
                                            <input name="tutorialId" value="<%=currentTutorial.getId()%>" hidden="true" />
                                            <input name="redirectURL" value="/managetutorial?tid=<%=currentTutorial.getId()%>" hidden="true" />
                                            <input type="text" name="status" class="form-control" value="<%=currentTutorial.getStatus()%>" />
                                            <div class="input-group-append">
                                                <button class="btn btn-default is-btn-primary" type="submit">Update Status</button>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                                <div class="col-lg-7">
                                    <div class="row">
                                        <div class="col-12">
                                            <table class="table table-hover">
                                            <%  tempArticles = articledao.getArticlesByTutorialIdAndStatus(currentTutorial.getId(),null,false);
                                                for(int i=0; i<tempArticles.size(); i++) { %>
                                                    <tr>
                                                        <td>
                                                            <%=tempArticles.get(i).getTitle()%>
                                                        </td>
                                                        <td>    
                                                            <form action="/updatetutorialarticlepriority" method="POST">
                                                                <div class="input-group">
                                                                    <input name="tutorialId" value="<%=currentTutorial.getId()%>" hidden="true" />
                                                                    <input name="articleId" value="<%=tempArticles.get(i).getId()%>" hidden="true" />
                                                                    <input name="redirectURL" value="/managetutorial?tid=<%=currentTutorial.getId()%>" hidden="true" />
                                                                    <input type="text" name="priority" class="form-control" value="<%=articledao.getPriorityByTutorialIdAndArticleId(currentTutorial.getId(),tempArticles.get(i).getId())%>" />
                                                                    <div class="input-group-append">
                                                                        <button class="btn btn-default is-btn-primary" type="submit">Update Priority</button>
                                                                    </div>
                                                                </div>
                                                            </form>
                                                        </td>
                                                        <td>    
                                                            <form action="/deletetutorialarticlelink" method="post">
                                                                <input name="tutorialId" value="<%=currentTutorial.getId()%>" hidden="true" />
                                                                <input name="articleId" value="<%=tempArticles.get(i).getId()%>" hidden="true" />
                                                                <input name="redirectURL" value="/managetutorial?tid=<%=currentTutorial.getId()%>" hidden="true" />
                                                                <button class="btn btn-warning" type="submit"><i class="fa fa-times"></i></button>
                                                            </form>
                                                        </td>
                                                        <td>    
                                                            <a class="btn btn-default is-btn-primary" href="/managearticle?aid=<%=tempArticles.get(i).getId()%>" type="submit"><i class="fa fa-edit"></i></a>
                                                        </td>
                                                    </tr>
                                            <% } %>
                                            <tr>
                                                <td colspan="4">
                                                    <form action="/newtutorialarticlelink" method="POST">
                                                        <select name="articleId" class="form-control">
                                                            <% 
                                                                tempArticles = articledao.getUnlinkedArticles();
                                                                for(int i=0; i<tempArticles.size(); i++) { %>
                                                                <option value="<%=tempArticles.get(i).getId()%>"><%=tempArticles.get(i).getTitle()%></option>
                                                            <% } %>
                                                        </select>
                                                        <input name="tutorialId" value="<%=currentTutorial.getId()%>" hidden="true" />
                                                        <input name="redirectURL" value="/managetutorial?tid=<%=currentTutorial.getId()%>" hidden="true" />
                                                        <input value="Link New Article" type="submit" class="btn btn-sm btn-default is-btn-primary mt-2" />
                                                    </form>
                                                </td>
                                            </tr>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="container-fluid row pb-5 pt-2 px-4 m-0 is-footer">
            <div class="col-12">
                <small class="float-right" style="color:#fff;">Developed By <span data-toggle="modal" data-target="#loginModal"><b>Ishaan Shringi</b></span></small>
            </div>
        </div>

        <div class="modal fade" id="deleteTutorialModal" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-md" role="document">
                <div class="modal-content">
                    <div class="modal-body">
                        <p class="text-danger">Do you really want to delete this Tutorial?</p>
                    </div>
                    <div class="modal-footer">
                        <form action="/deletetutorial" method="POST">
                            <input name="tutorialId" value="<%=currentTutorial.getId()%>" hidden="true" />
                            <input name="redirectURL" value="/" hidden="true" />
                            <button type="button" class="btn btn-default is-btn-primary-outline" data-dismiss="modal">Cancel</button>
                            <button type="submit" class="btn btn-danger">Delete</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
        <script lang="text/javascript">
            function changeTutorialIdSelection() {
                window.location.href = "/managetutorial?tid=" + $("#tid-select").val();
            }
        </script>
    </body>
</html>