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
    
    Integer topicId = null;
    String topicIdString = request.getParameter("tid");
    if(topicIdString == null || topicIdString.isEmpty()) {
        response.sendRedirect("/managetopic?tid="+allTopics.get(0).getId());
        return;
    }
    else {
        topicId = Integer.parseInt(topicIdString);
    }
    
    Topic currentTopic = null;
    
    for(int i=0; i<allTopics.size(); i++) {
        if(allTopics.get(i).getId().compareTo(topicId)==0) {
            currentTopic = allTopics.get(i);
            break;
        }
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Manage Topic | Computer Science and Programming Tutorials</title>
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
                            <a class="btn btn-sm btn-default is-btn-primary" href="/managetopic">Topic</a>
                            <a class="btn btn-sm btn-default is-btn-primary-outline" href="/managetutorial">Tutorial</a>
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
                                    <select id="tid-select" name="tid" class="form-control" onchange="changeTopicIdSelection()">
                                        <% for(int i=0; i<allTopics.size(); i++) { %>
                                            <option value="<%=allTopics.get(i).getId()%>"
                                                    <%=(allTopics.get(i).getId().compareTo(currentTopic.getId())==0)?"selected='true'":""%>
                                                    ><%=allTopics.get(i).getId()%>. <%=allTopics.get(i).getTitle()%></option>
                                        <% } %>
                                    </select>
                                    <form action="/newtopic" method="POST" class="mt-2 mb-2">
                                        <div class="input-group">
                                            <input type="text" name="title" class="form-control" placeholder="New Title" />
                                            <div class="input-group-append">
                                                <button class="btn is-btn-primary" type="submit">New Topic</button>
                                            </div>
                                        </div>
                                    </form>
                                    <button data-toggle="modal" data-target="#deleteTopicModal" class="btn btn-default is-btn-primary" type="submit">Delete</button>
                                    <hr />
                                    <form action="/updatetopictitle" method="POST" class="mt-2">
                                        <div class="input-group">
                                            <input name="topicId" value="<%=currentTopic.getId()%>" hidden="true" />
                                            <input name="redirectURL" value="/managetopic?tid=<%=currentTopic.getId()%>" hidden="true" />
                                            <input type="text" name="title" class="form-control" value="<%=currentTopic.getTitle()%>" />
                                            <div class="input-group-append">
                                                <button class="btn is-btn-primary" type="submit">Update Title</button>
                                            </div>
                                        </div>
                                    </form>
                                    <form action="/updatetopickey" method="POST" class="mt-2">
                                        <div class="input-group">
                                            <input name="topicId" value="<%=currentTopic.getId()%>" hidden="true" />
                                            <input name="redirectURL" value="/managetopic?tid=<%=currentTopic.getId()%>" hidden="true" />
                                            <input type="text" name="key" class="form-control" value="<%=currentTopic.getKey()%>" />
                                            <div class="input-group-append">
                                                <button class="btn is-btn-primary" type="submit">Update Key</button>
                                            </div>
                                        </div>
                                    </form>
                                    <form action="/updatetopicpriority" method="POST" class="mt-2">
                                        <div class="input-group">
                                            <input name="topicId" value="<%=currentTopic.getId()%>" hidden="true" />
                                            <input name="redirectURL" value="/managetopic?tid=<%=currentTopic.getId()%>" hidden="true" />
                                            <input type="text" name="priority" class="form-control" value="<%=currentTopic.getPriority()%>" />
                                            <div class="input-group-append">
                                                <button class="btn is-btn-primary" type="submit">Update Priority</button>
                                            </div>
                                        </div>
                                    </form>
                                    <form action="/updatetopicstatus" method="POST" class="mt-2">
                                        <div class="input-group">
                                            <input name="topicId" value="<%=currentTopic.getId()%>" hidden="true" />
                                            <input name="redirectURL" value="/managetopic?tid=<%=currentTopic.getId()%>" hidden="true" />
                                            <input type="text" name="status" class="form-control" value="<%=currentTopic.getStatus()%>" />
                                            <div class="input-group-append">
                                                <button class="btn is-btn-primary" type="submit">Update Status</button>
                                            </div>
                                        </div>
                                    </form>
                                </div>  
                                <div class="col-lg-7">
                                    <table class="table table-hover">
                                        <%  tempTutorials = tutorialdao.getTutorialsByTopicIdAndStatus(currentTopic.getId(), null);
                                            for(int i=0; i<tempTutorials.size(); i++) { %>
                                                <tr>
                                                    <td>
                                                        <%=tempTutorials.get(i).getTitle()%>
                                                    </td>
                                                    <td>
                                                        <form action="/updatetopictutorialpriority" method="POST" >
                                                            <div class="input-group">
                                                                <input name="topicId" value="<%=currentTopic.getId()%>" hidden="true" />
                                                                <input name="tutorialId" value="<%=tempTutorials.get(i).getId()%>" hidden="true" />
                                                                <input name="redirectURL" value="/managetopic?tid=<%=currentTopic.getId()%>" hidden="true" />
                                                                <input type="text" name="priority" class="form-control" value="<%=tutorialdao.getPriorityByTopicIdAndTutorialId(currentTopic.getId(),tempTutorials.get(i).getId())%>" />
                                                                <div class="input-group-append">
                                                                    <button class="btn btn-default is-btn-primary " type="submit">Update Priority</button>
                                                                </div>
                                                            </div>
                                                        </form>
                                                    </td>
                                                    <td>
                                                        <form action="/deletetopictutoriallink" method="POST" >
                                                            <div class="input-group">
                                                                <input name="topicId" value="<%=currentTopic.getId()%>" hidden="true" />
                                                                <input name="tutorialId" value="<%=tempTutorials.get(i).getId()%>" hidden="true" />
                                                                <input name="redirectURL" value="/managetopic?tid=<%=currentTopic.getId()%>" hidden="true" />
                                                                <button class="btn btn-warning" type="submit"><i class="fa fa-times"></i></button>
                                                            </div>
                                                        </form>
                                                    </td>
                                                    <td>
                                                        <a class="btn btn-default is-btn-primary" href="/managetutorial?tid=<%=tempTutorials.get(i).getId()%>"><i class="fa fa-edit"></i></button>
                                                    </td>
                                                </tr>
                                        <% } %>
                                        <tr>
                                            <td colspan="2">
                                                <form action="/newtopictutoriallink" method="POST">
                                                    <div class="input-group">
                                                        <select name="tutorialId" class="form-control">
                                                            <% 
                                                                tempTutorials = tutorialdao.getUnlinkedTutorials();
                                                                for(int i=0; i<tempTutorials.size(); i++) { %>
                                                                <option value="<%=tempTutorials.get(i).getId()%>"><%=tempTutorials.get(i).getTitle()%></option>
                                                            <% } %>
                                                        </select>
                                                    </div>
                                                    <input name="topicId" value="<%=currentTopic.getId()%>" hidden="true" />
                                                    <input name="redirectURL" value="/managetopic?tid=<%=currentTopic.getId()%>" hidden="true" />
                                                    <div class="input-group">
                                                        <input type="submit" value="Link New Tutorial" class="btn btn-sm btn-default is-btn-primary mt-2" />
                                                    </div>
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

        <div class="container-fluid row pb-5 pt-2 px-4 m-0 is-footer">
            <div class="col-12">
                <small class="float-right" style="color:#fff;">Developed By <span data-toggle="modal" data-target="#loginModal"><b>Ishaan Shringi</b></span></small>
            </div>
        </div>

        <div class="modal fade" id="deleteTopicModal" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-md" role="document">
                <div class="modal-content">
                    <div class="modal-body">
                        <p class="text-danger">Do you really want to delete this topic?</p>
                    </div>
                    <div class="modal-footer">
                        <form action="/deletetopic" method="POST">
                            <input name="topicId" value="<%=currentTopic.getId()%>" hidden="true" />
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
            function changeTopicIdSelection() {
                window.location.href = "/managetopic?tid=" + $("#tid-select").val();
            }
        </script>
    </body>
</html>