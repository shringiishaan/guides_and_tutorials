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
    
    if(parts.length!=3 || !parts[0].isEmpty() || !parts[1].equals("Article")) {
        request.getRequestDispatcher("/error404.jsp").forward(request, response);
        return;
    }
    
    Article currentArticle=null, 
            nextArticle=null, 
            previousArticle=null, 
            tempArticle=null;
    List<Article> tempArticles=null;
    Tutorial currentTutorial=null, 
            tempTutorial=null,
            nextTutorial=null,
            previousTutorial=null;
    List<Tutorial> relatedTutorials=null;
    Topic currentTopic=null;
    List<Topic> allTopics=null;
    
    TutorialDAO tutorialdao = new TutorialDAO();
    ArticleDAO articledao = new ArticleDAO();
    UserDAO userdao = new UserDAO();
    TopicDAO topicdao = new TopicDAO();
    
    String requestArticleId = parts[2];
    currentArticle = articledao.getArticleById(requestArticleId, true);
    if(currentArticle==null) {
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
    
    relatedTutorials = tutorialdao.getRelatedTutorialsByArticleId(currentArticle.getId(),isAdmin?null:"final",currentArticle.getScope());
    
    //set next previous articles, tutorials
    if(currentArticle.getScope().equals("tutorial")) {
        for(int j=0; j<relatedTutorials.size(); j++) {
            tempTutorial = relatedTutorials.get(j);
            tempArticles = articledao.getArticlesByTutorialIdAndStatus(tempTutorial.getId(),isAdmin?null:"final",false);
            for(int i=0; i<tempArticles.size(); i++) {
                tempArticle = tempArticles.get(i);
                if(currentArticle.getId().equals(tempArticle.getId())) {
                    currentTutorial = tempTutorial;
                    if(j>0)
                        previousTutorial = relatedTutorials.get(j-1);
                    if(j<(relatedTutorials.size()-1))
                        nextTutorial = relatedTutorials.get(j+1);
                    if(i>0)
                        previousArticle = tempArticles.get(i-1);
                    if(i<(tempArticles.size()-1))
                        nextArticle = tempArticles.get(i+1);
                    break;
                }
            }
            if(currentTutorial!=null) 
                break;
        }
        currentTopic = topicdao.getTopicById(topicdao.getTopicIdByTutorialId(currentTutorial.getId()));
    }
    else {
        currentTopic = topicdao.getTopicById(topicdao.getTopicIdByArticleId(currentArticle.getId()));
    }
    
    if(currentArticle.getScope().equals("tutorial") && nextArticle==null && nextTutorial!=null) {
        nextArticle = articledao.getMaximumPriorityArticleByTutorialIdAndStatus(nextTutorial.getId(), isAdmin?null:"final", false);
    }
    if(currentArticle.getScope().equals("tutorial") && previousArticle==null && previousTutorial!=null) {
        previousArticle = articledao.getMinimumPriorityArticleByTutorialIdAndStatus(previousTutorial.getId(), isAdmin?null:"final", false);
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <title><% if(currentArticle.getScope().equals("tutorial")) out.print(currentTutorial.getTitle()+" | "); %> <%=currentArticle.getTitle()%> | The Computer Guide</title>
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
            <div class="row">
                <!-- side bar -->
                <div class="col-lg-3 is-sidebar position-fixed d-none d-lg-block pt-3 pl-xl-5 pl-md-4">
                    <%
                        for (int i = 0; i < relatedTutorials.size(); i++) {
                            if(!(relatedTutorials.get(i)).getStatus().equals("final") && !isAdmin) {
                                continue;
                            }
                            %><div class='row'>
                                <div class='col-12'>
                                    <a class="is-title">
                                        <%=(relatedTutorials.get(i)).getTitle()%>
                                        <% if(isAdmin) { %><sup class="<%=
                                                ((relatedTutorials.get(i)).getStatus().equals("final"))?"text-success":"text-warning"
                                                %>"><i>(<%=(relatedTutorials.get(i)).getStatus()%>)</i></sup>
                                        <% } %>
                                    </a>
                                </div>
                            </div> 
                            <div class='row'>
                                <div class='col-12'>
                                    <%
                                        tempArticles = articledao.getArticlesByTutorialIdAndStatus((relatedTutorials.get(i)).getId(),(isAdmin?null:"final"), false);
                                        for(int j=0; j<tempArticles.size(); j++) { 
                                            %>
                                        <div class='row is-element-hover'>
                                            <div class='col-12'>
                                                <a class='<%=(currentArticle.getScope().equals("tutorial") && currentArticle.getId().equals(tempArticles.get(j).getId()))?"is-active":""%>
                                                        is-label' href='/Article/<%=tempArticles.get(j).getId()%>'>
                                                    <%=tempArticles.get(j).getTitle()%>
                                                    <% if(isAdmin) { %><sup class="<%=
                                                            (tempArticles.get(j).getStatus().equals("final"))?"text-success":"text-warning"
                                                            %>"><i>(<%=tempArticles.get(j).getStatus()%>)</i></sup>
                                                    <% } %>
                                                </a>
                                            </div>
                                        </div> 
                                        <% }
                                    %>
                                </div>
                            </div> 
                            <%
                        }
                    %>
                </div>

                <!-- main content -->
                <div class="offset-lg-3 col-lg-9 pt-4 px-sm-4 pr-lg-5">
                    <h3><%=currentArticle.getTitle()%></h3>
                    <small class="text-muted"><i class="fa fa-clock"></i> Last modified on <%= new SimpleDateFormat("MMM d, yyyy").format(currentArticle.getModifiedTime().getTime()) %></small>
                    <%
                        if(currentArticle.getScope().equals("tutorial")) {
                            %><hr /><%
                            if(previousArticle!=null) {
                                %><a class="btn btn-sm btn-outline-info" href="/Article/<%=previousArticle.getId()%>">
                                    <i class="fa fa-angle-left"></i> Previous <span class="d-none d-sm-inline-block">Article</span></a><%
                            }
                            if(nextArticle!=null) {
                                %><a class="btn btn-sm btn-outline-info float-right" href="/Article/<%=nextArticle.getId()%>">Next <span class="d-none d-sm-inline-block">Article</span> <i class="fa fa-angle-right"></i></a><%
                            }
                        }
                        if(user!=null && user.getType().equals("admin")) {
                            %><a class="btn btn-sm btn-outline-info ml-2" data-toggle="modal" data-target="#editArticleModal" href="#!">Edit</a><%
                            %><a class="btn btn-sm btn-outline-danger ml-2" data-toggle="modal" data-target="#deleteArticleModal" href="#!">Delete</a><%
                        }
                    %>
                    <div class="clearfix"></div>
                    <%
                        if(isAdmin) {
                            %><br />
                            <form method="POST" action="/SetArticleStatus">
                                <div class="input-group p-2">
                                    <input type="text" name="status" class="form-control" value="<%=currentArticle.getStatus()%>" />
                                    <input type="text" name="articleId" hidden="true" value="<%=currentArticle.getId()%>" />
                                    <input type="text" name="redirectURL" hidden="true" value="/article/<%=currentArticle.getId()%>" />
                                    <div class="input-group-append">
                                        <button class="btn btn-sm btn-outline-secondary" type="submit">Update Status</button>
                                    </div>
                                </div>
                            </form><%
                        }
                    %>
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
                    <hr />
                    <div class="is-article-data">
                        <%=currentArticle.getData()%>
                    </div>
                    <%
                        if(currentArticle.getScope().equals("tutorial")) {
                            if(previousArticle!=null) {
                                %><a class="btn btn-sm btn-outline-info" href="/Article/<%=previousArticle.getId()%>"><i class="fa fa-angle-left"></i> Previous <span class="d-none d-sm-inline-block">Article</span></a><%
                            }
                            if(nextArticle!=null) {
                                %><a class="btn btn-sm btn-outline-info float-right" href="/Article/<%=nextArticle.getId()%>">Next <span class="d-none d-sm-inline-block">Article</span> <i class="fa fa-angle-right"></i></a><%
                            }
                        }
                    %>
                    <div class="row mt-5">
                        <div class="col-12 px-sm-5 px-md-3">
                            <%
                                CommentDAO commentdao = new CommentDAO();
                                List<Comment> comments = commentdao.getCommentsByArticleId(currentArticle.getId());
                                %><h5>Discussion (<%=comments.size()%>)</h5><hr /><%
                                for(Comment comment : comments) {
                                    if(comment.getOwnerId()!=0) 
                                        tempUser = userdao.getUserById(comment.getOwnerId());
                                    %><div class="row mt-2">
                                        <div class="col-12">
                                            <div class="card">
                                                <div class="card-body p-2 px-3">
                                                    <%=comment.getMessage()%>
                                                    <br>
                                                    <small><i class="fa fa-clock"></i> <%= new SimpleDateFormat("MMM d, yyyy HH:mm").format(comment.getCreateTime().getTime()) %>
                                                        <i class="ml-2 fa fa-user"></i> <%=(comment.getOwnerId()!=0)?tempUser.getName():"Anonymous"%></small> 
                                                </div>
                                            </div>
                                        </div>
                                    </div><%
                                }
                            %>
                            <div class="row mt-2">
                                <div class="col-12">
                                    <form action="/NewComment" method="post">
                                        <input name="articleId" value="<%=currentArticle.getId()%>" hidden="true" />
                                        <textarea class="form-control" name="message" placeholder="Add comment ..."></textarea>
                                        <button type="submit" class="btn btn-info btn-sm mt-2">Comment</button>
                                        <br><small><span class="text-muted"><a href="/Login">login</a> or post a comment anonymously</span></small>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <br>
                    <hr />
                    <p class="text-muted float-right"><small>Developed By <span class="text-info">Ishaan Shringi</span></small></p>
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
                        <%
                            for (int i = 0; i < relatedTutorials.size(); i++) {
                                out.println("<a class='is-title'>"+(relatedTutorials.get(i)).getTitle()+"</a>");
                                tempArticles = articledao.getArticlesByTutorialIdAndStatus((relatedTutorials.get(i)).getId(),(isAdmin?null:"final"),false);
                                for(int j=0; j<tempArticles.size(); j++) {
                                    out.println("<a class='");
                                    if(currentArticle.getId().equals(tempArticles.get(j).getId())) {
                                        out.println("is-active");
                                    }
                                    out.println(" is-label' href='/Article/"+tempArticles.get(j).getId()+"'>"+tempArticles.get(j).getTitle()+"</a>");
                                }
                            }
                        %>
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
                                <input type="text" name="redirectURL" hidden="true" value="/Article/<%=currentArticle.getId()%>" />
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
                            
        <div class="modal fade" id="editArticleModal" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-md" role="document">
                <div class="modal-content">
                    <form action="/EditArticle" method="post">
                        <div class="modal-body">
                            <a href="/article/<%=currentArticle.getId()%>" class="btn btn-sm btn-secondary mt-2 ml-2">Cancel</a>
                            <div class="form-group">
                                <input name="id" value="<%=currentArticle.getId()%>" type="text" hidden="true" />
                                <input type="text" name="redirectURL" hidden="true" value="/article/<%=currentArticle.getId()%>" />
                            </div>
                            <div class="form-group">
                                <textarea style="width:100%" name="data" rows="15"><%=currentArticle.getData()%></textarea>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-outline-secondary" data-dismiss="modal">Close</button>
                            <a class="btn btn-outline-secondary" href="/EditArticle/<%=currentArticle.getId()%>">Full-Screen Editor</a>
                            <input type="submit" name="submit" value="Update Content" class="btn btn-info" />
                        </div>
                    </form>
                </div>
            </div>
        </div>
                            
        <div class="modal fade" id="deleteArticleModal" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-md" role="document">
                <div class="modal-content">
                    <div class="modal-body">
                        <p class="text-danger">Do you really want to delete this article?</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-outline-secondary" data-dismiss="modal">Close</button>
                        <a class="btn btn-outline-danger" href="/DeleteArticle/<%=currentArticle.getId()%>">Delete</a>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
        
        <script src="/prism/prism.js"></script>
    </body>
</html>