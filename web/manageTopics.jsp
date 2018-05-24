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
                            <a class="dropdown-item" href="/ManageTopics">Topics</a>
                            <a class="dropdown-item" href="/ManageImages">Images</a>
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
                <div class="col-12 pt-5 is-content">
                    <div class="row">
                        <div class="col-12">
                            <h3>Manage Topics</h3>
                            <br/>
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
                            <table class="table table-borderless">
                                <thead>
                                    <tr>
                                        <th></th>
                                        <th></th>
                                        <th></th>
                                        <th></th>
                                        <th></th>
                                        <th></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <%  
                                    //topics
                                    for (int i = 0; i < allTopics.size(); i++) {
                                    %>
                                        <tr class="table-warning">
                                            <td><small><b>Topic : </b></small></td>
                                            <td>
                                                <%=allTopics.get(i).getTitle()%>
                                                <br>
                                                <i>(<%=allTopics.get(i).getTitle()%>)</i>
                                            </td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                        </tr>
                                            <%
                                                //topic - articles
                                                tempArticles = articledao.getArticlesByTopicIdAndStatus(allTopics.get(i).getId(),null,false);
                                                for(int k=0; k<tempArticles.size(); k++) {
                                                    %>
                                                        <tr class="table-secondary">
                                                            <td><small><b>Topic-Article : </b></small></td>
                                                            <td>
                                                                <%=tempArticles.get(k).getTitle()%>
                                                                <br />
                                                                <i>(<%=tempArticles.get(k).getId()%>)</i>
                                                            </td>
                                                            <td>
                                                                <form method="POST" action="/SetArticleScope">
                                                                    <div class="input-group">
                                                                        <input type="text" name="scope" class="form-control" value="<%=tempArticles.get(k).getScope()%>" />
                                                                        <input type="text" name="articleId" hidden="true" value="<%=tempArticles.get(k).getId()%>" />
                                                                        <div class="input-group-append">
                                                                            <input type="text" name="redirectURL" hidden="true" value="/ManageTopics" />
                                                                            <button class="btn btn-sm btn-outline-info" type="submit">Update Scope</button>
                                                                        </div>
                                                                    </div>
                                                                </form>
                                                            </td>
                                                            <td>
                                                                <form method="POST" action="/SetArticleStatus">
                                                                    <div class="input-group">
                                                                        <input type="text" name="status" class="form-control" value="<%=tempArticles.get(k).getStatus()%>" />
                                                                        <input type="text" name="articleId" hidden="true" value="<%=tempArticles.get(k).getId()%>" />
                                                                        <div class="input-group-append">
                                                                            <input type="text" name="redirectURL" hidden="true" value="/ManageTopics" />
                                                                            <button class="btn btn-sm btn-outline-info" type="submit">Update Status</button>
                                                                        </div>
                                                                    </div>
                                                                </form>
                                                            </td>
                                                            <td></td>
                                                            <td>
                                                                <form method="POST" action="/RemoveTopicArticleLink">
                                                                    <div class="input-group">
                                                                        <input type="text" name="topicId" hidden="true" value="<%=allTopics.get(i).getId()%>" />
                                                                        <input type="text" name="articleId" hidden="true" value="<%=tempArticles.get(k).getId()%>" />
                                                                        <div class="input-group-append">
                                                                            <input type="text" name="redirectURL" hidden="true" value="/ManageTopics" />
                                                                            <button class="btn btn-sm btn-outline-warning" type="submit">Remove Link</button>
                                                                        </div>
                                                                    </div>
                                                                </form>
                                                            </td>
                                                        </tr>
                                                    <%
                                                }

                                                //topic - tutorials
                                                tempTutorials = tutorialdao.getTutorialsByTopicIdAndStatus(allTopics.get(i).getId(),null);
                                                for(int j=0; j<tempTutorials.size(); j++) {
                                                    %>
                                                                <tr class="table-info">
                                                                    <td><small><b>Topic-Tutorial : </b></small></td>
                                                                    <td>
                                                                        <%=tempTutorials.get(j).getTitle()%>
                                                                        <br>
                                                                        <i>(<%=tempTutorials.get(j).getId()%>)</i>
                                                                    </td>
                                                                    <td></td>
                                                                    <td>
                                                                        <form method="POST" action="/SetTutorialStatus">
                                                                            <div class="input-group">
                                                                                <input type="text" name="status" class="form-control" value="<%=tempTutorials.get(j).getStatus()%>" />
                                                                                <input type="text" name="tutorialId" hidden="true" value="<%=tempTutorials.get(j).getId()%>" />
                                                                                <div class="input-group-append">
                                                                                    <input type="text" name="redirectURL" hidden="true" value="/ManageTopics" />
                                                                                    <button class="btn btn-sm btn-outline-info" type="submit">Update Status</button>
                                                                                </div>
                                                                            </div>
                                                                        </form>
                                                                    </td>
                                                                    <td>
                                                                        <form method="POST" action="/SetTopicTutorialPriority">
                                                                            <div class="input-group">
                                                                                <input type="text" name="priority" class="form-control" value="<%=tutorialdao.getPriorityByTopicIdAndTutorialId(allTopics.get(i).getId(),tempTutorials.get(j).getId())%>" />
                                                                                <input type="text" name="topicId" hidden="true" value="<%=allTopics.get(i).getId()%>" />
                                                                                <input type="text" name="tutorialId" hidden="true" value="<%=tempTutorials.get(j).getId()%>" />
                                                                                <div class="input-group-append">
                                                                                    <input type="text" name="redirectURL" hidden="true" value="/ManageTopics" />
                                                                                    <button class="btn btn-sm btn-outline-info" type="submit">Update Priority</button>
                                                                                </div>
                                                                            </div>
                                                                        </form>
                                                                    </td>
                                                                    <td>
                                                                        <form method="POST" action="/RemoveTopicTutorialLink">
                                                                            <div class="input-group">
                                                                                <input type="text" name="topicId" hidden="true" value="<%=allTopics.get(i).getId()%>" />
                                                                                <input type="text" name="tutorialId" hidden="true" value="<%=tempTutorials.get(j).getId()%>" />
                                                                                <div class="input-group-append">
                                                                                    <input type="text" name="redirectURL" hidden="true" value="/ManageTopics" />
                                                                                    <button class="btn btn-sm btn-outline-warning" type="submit">Remove Link</button>
                                                                                </div>
                                                                            </div>
                                                                        </form>
                                                                    </td>
                                                                </tr>
                                                                
                                                        <%
                                                            // topic - tutorial - articles
                                                            tempArticles = articledao.getArticlesByTutorialIdAndStatus(tempTutorials.get(j).getId(),null,false);
                                                            for(int k=0; k<tempArticles.size(); k++) {
                                                                %>
                                                                    <tr class="table-success">
                                                                        <td><small><b>Topic-Tutorial-Article&nbsp;: </b></small></td>
                                                                        <td>
                                                                            <%=tempArticles.get(k).getTitle()%>
                                                                            <br />
                                                                            <i>(<%=tempArticles.get(k).getId()%>)</i>
                                                                        </td>
                                                                        <td>
                                                                            <form method="POST" action="/SetArticleScope">
                                                                                <div class="input-group">
                                                                                    <input type="text" name="scope" class="form-control" value="<%=tempArticles.get(k).getScope()%>" />
                                                                                    <input type="text" name="articleId" hidden="true" value="<%=tempArticles.get(k).getId()%>" />
                                                                                    <div class="input-group-append">
                                                                                        <input type="text" name="redirectURL" hidden="true" value="/ManageTopics" />
                                                                                        <button class="btn btn-sm btn-outline-info" type="submit">Update Scope</button>
                                                                                    </div>
                                                                                </div>
                                                                            </form>
                                                                        </td>
                                                                        <td>
                                                                            <form method="POST" action="/SetArticleStatus">
                                                                                <div class="input-group">
                                                                                    <input type="text" name="status" class="form-control" value="<%=tempArticles.get(k).getStatus()%>" />
                                                                                    <input type="text" name="articleId" hidden="true" value="<%=tempArticles.get(k).getId()%>" />
                                                                                    <div class="input-group-append">
                                                                                        <input type="text" name="redirectURL" hidden="true" value="/ManageTopics" />
                                                                                        <button class="btn btn-sm btn-outline-info" type="submit">Update Status</button>
                                                                                    </div>
                                                                                </div>
                                                                            </form>
                                                                        </td>
                                                                        <td>
                                                                            <form method="POST" action="/SetTutorialArticlePriority">
                                                                                <div class="input-group">
                                                                                    <input type="text" name="priority" class="form-control" value="<%=articledao.getPriorityByTutorialIdAndArticleId(tempTutorials.get(j).getId(),tempArticles.get(k).getId())%>" />
                                                                                    <input type="text" name="tutorialId" hidden="true" value="<%=tempTutorials.get(j).getId()%>" />
                                                                                    <input type="text" name="articleId" hidden="true" value="<%=tempArticles.get(k).getId()%>" />
                                                                                    <div class="input-group-append">
                                                                                        <input type="text" name="redirectURL" hidden="true" value="/ManageTopics" />
                                                                                        <button class="btn btn-sm btn-outline-info" type="submit">Update Priority</button>
                                                                                    </div>
                                                                                </div>
                                                                            </form>
                                                                        </td>
                                                                        <td>
                                                                            <form method="POST" action="/RemoveTutorialArticleLink">
                                                                                <div class="input-group">
                                                                                    <input type="text" name="tutorialId" hidden="true" value="<%=tempTutorials.get(j).getId()%>" />
                                                                                    <input type="text" name="articleId" hidden="true" value="<%=tempArticles.get(k).getId()%>" />
                                                                                    <div class="input-group-append">
                                                                                        <input type="text" name="redirectURL" hidden="true" value="/ManageTopics" />
                                                                                        <button class="btn btn-sm btn-outline-warning" type="submit">Remove Link</button>
                                                                                    </div>
                                                                                </div>
                                                                            </form>
                                                                        </td>
                                                                    </tr>
                                                                <%
                                                            }
                                                }
                                    }

                                    %>
                                        <tr class="table-secondary">
                                            <td><small><b>UNLINKED </b></small></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                        </tr>
                                    <%
                                        //Unlinked Tutorials
                                        tempTutorials = tutorialdao.getUnlinkedTutorials();
                                        if(tempTutorials!=null)
                                        for(int j=0; j<tempTutorials.size(); j++) {
                                            %><tr class="table-secondary">
                                                <td><small><b>Unlinked-Tutorial : </b></small></td>
                                                <td><%=tempTutorials.get(j).getTitle()%></td>
                                                <td>
                                                    <form method="POST" action="/NewTopicTutorialLink">
                                                        <div class="input-group">
                                                            <input type="text" name="topicId" class="form-control" placeholder="Topic Id" />
                                                            <div class="input-group-append">
                                                                <input type="text" name="tutorialId" hidden="true" value="<%=tempTutorials.get(j).getId()%>" />
                                                                <input type="text" name="redirectURL" hidden="true" value="/ManageTopics" />
                                                                <button class="btn btn-sm btn-outline-info" type="submit">New Link</button>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </td>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                            </tr><%
                                        }

                                        //Unlinked Articles
                                        tempArticles = articledao.getUnlinkedArticles();
                                        if(tempArticles!=null)
                                        for(int j=0; j<tempArticles.size(); j++) {
                                            %><tr class="table-secondary">
                                                <td><small><b>Unlinked-Article : </b></small></td>
                                                <td><%=tempArticles.get(j).getTitle()%></td>
                                                <td></td>
                                                <td></td>
                                                <td>
                                                    <form method="POST" action="/NewTopicArticleLink">
                                                        <div class="input-group">
                                                            <input type="text" name="topicId" class="form-control" placeholder="Topic Id" />
                                                            <div class="input-group-append">
                                                                <input type="text" name="articleId" hidden="true" value="<%=tempArticles.get(j).getId()%>" />
                                                                <input type="text" name="redirectURL" hidden="true" value="/ManageTopics" />
                                                                <button class="btn btn-sm btn-outline-info" type="submit">New Link</button>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </td>
                                                <td>
                                                    <form method="POST" action="/NewTutorialArticleLink">
                                                        <div class="input-group">
                                                            <input type="text" name="tutorialId" class="form-control" placeholder="Tutorial Id" />
                                                            <div class="input-group-append">
                                                                <input type="text" name="articleId" hidden="true" value="<%=tempArticles.get(j).getId()%>" />
                                                                <input type="text" name="redirectURL" hidden="true" value="/ManageTopics" />
                                                                <button class="btn btn-sm btn-outline-info" type="submit">New Link</button>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </td>
                                            </tr><%
                                        }
                                    %>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-4">
                            <div class="card card-body">
                                <form method="POST" action="/NewTopic">
                                    <div class="input-group">
                                        <input type="text" name="title" class="form-control" placeholder="New Title" />
                                        <div class="input-group-append">
                                            <input type="text" name="redirectURL" hidden="true" value="/ManageTopics" />
                                            <button class="btn btn-sm btn-outline-info" type="submit">New Topic</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <div class="col-lg-4">
                            <div class="card card-body">
                                <form method="POST" action="/NewArticle">
                                    <div class="input-group">
                                        <input type="text" name="title" class="form-control" placeholder="New Title" />
                                        <div class="input-group-append">
                                            <input type="text" name="redirectURL" hidden="true" value="/ManageTopics" />
                                            <button class="btn btn-sm btn-outline-info" type="submit">New Article</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <div class="col-lg-4">
                            <div class="card card-body">
                                <form method="POST" action="/NewTutorial">
                                    <div class="input-group">
                                        <input type="text" name="title" class="form-control" placeholder="New Title" />
                                        <div class="input-group-append">
                                            <input type="text" name="redirectURL" hidden="true" value="/ManageTopics" />
                                            <button class="btn btn-sm btn-outline-info" type="submit">New Tutorial</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <div class="row mt-2">
                        <div class="col-lg-4">
                            <div class="card card-body">
                                <form method="POST" action="/DeleteTopic">
                                    <div class="input-group">
                                        <input type="text" name="topicId" class="form-control" placeholder="Topic ID" />
                                        <div class="input-group-append">
                                            <input type="text" name="redirectURL" hidden="true" value="/ManageTopics" />
                                            <button class="btn btn-sm btn-outline-danger" type="submit">Delete Topic</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <div class="col-lg-4">
                            <div class="card card-body">
                                <form method="POST" action="/DeleteArticle">
                                    <div class="input-group">
                                        <input type="text" name="articleId" class="form-control" placeholder="Article ID" />
                                        <div class="input-group-append">
                                            <input type="text" name="redirectURL" hidden="true" value="/ManageTopics" />
                                            <button class="btn btn-sm btn-outline-danger" type="submit">Delete Article</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <div class="col-lg-4">
                            <div class="card card-body">
                                <form method="POST" action="/DeleteTutorial">
                                    <div class="input-group">
                                        <input type="text" name="tutorialId" class="form-control" placeholder="Tutorial ID" />
                                        <div class="input-group-append">
                                            <input type="text" name="redirectURL" hidden="true" value="/ManageTopics" />
                                            <button class="btn btn-sm btn-outline-danger" type="submit">Delete Tutorial</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
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