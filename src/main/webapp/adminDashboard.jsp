<%@page import="dao.VoteDAO"%>
<%@page import="dao.PageViewDAO"%>
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
    PageViewDAO requestcounterdao = new PageViewDAO();
    VoteDAO votesdao = new VoteDAO();
    
    List<Tutorial> tempTutorials;
    List<Article> allArticles = articledao.getAllArticles(false);
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
    Integer tempViews, tempUpVotes, tempDownVotes;
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Admin Dashbard | Computer Science and Programming Tutorials</title>
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
                            <a class="btn btn-sm btn-default is-btn-primary" href="/admindashboard">Dashboard</a>
                            <a class="btn btn-sm btn-default is-btn-primary-outline" href="/managetopic">Topic</a>
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
                            <br />
                            <div class="row admin-form">
                                <div class="col-12">
                                    <table class="table table-hover">
                                        <thead>
                                            <tr>
                                                <th>
                                                    ID
                                                </th>
                                                <th>
                                                    Title
                                                </th>
                                                <th>
                                                    Views
                                                </th>
                                                <th>
                                                    UpVotes
                                                </th>
                                                <th>
                                                    DownVotes
                                                </th>
                                                <th></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <% for(int i=0; i<allArticles.size(); i++) { 
                                                tempViews = requestcounterdao.getViewsByArticle(allArticles.get(i).getId());
                                                tempUpVotes = votesdao.getVoteCountByArticleAndType(allArticles.get(i).getId(),"up");
                                                tempDownVotes = votesdao.getVoteCountByArticleAndType(allArticles.get(i).getId(),"down");
                                            %>
                                                <tr>
                                                    <td>
                                                        <%=allArticles.get(i).getId()%>
                                                    </td>
                                                    <td>
                                                        <%=allArticles.get(i).getTitle()%>
                                                    </td>
                                                    <td>
                                                        <%=tempViews==null?0:tempViews%>
                                                    </td>
                                                    <td>
                                                        <%=tempUpVotes==null?0:tempUpVotes%>
                                                    </td>
                                                    <td>
                                                        <%=tempDownVotes==null?0:tempDownVotes%>
                                                    </td>
                                                    <td>
                                                        <a class="btn btn-default is-btn-primary" href="/managearticle?aid=<%=allArticles.get(i).getId()%>"><i class="fa fa-edit"></i> Edit</a>
                                                        <a class="btn btn-default is-btn-primary" href="/article/<%=allArticles.get(i).getKey()%>">Visit</a>
                                                    </td>
                                                </tr>
                                            <% } %>
                                        </tbody>
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

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
        <script lang="text/javascript">
        </script>
    </body>
</html>