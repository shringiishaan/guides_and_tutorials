<%@page import="model.Image"%>
<%@page import="dao.ImageDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Topic"%>
<%@page import="dao.TopicDAO"%>
<%@page import="model.User"%>
<%@page import="dao.UserDAO"%>
<%@page import="dao.ArticleDAO"%>
<%@page import="java.util.List"%>
<%@page import="model.Article"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if(session.getAttribute("userId")==null) {
        response.sendRedirect("/");
        return;
    }
    User user = null;
    UserDAO userdao = new UserDAO();
    user = userdao.getUserById((Integer)session.getAttribute("userId"));
    if(user==null || !user.getType().equals("admin")) {
        response.sendRedirect("/");
        return;
    }
    TopicDAO topicdao = new TopicDAO();
    List<Topic> allTopics = topicdao.getAllTopics();
    
    String currentKeyword1 = request.getParameter("currentKeyword1");
    
    ImageDAO imagedao = new ImageDAO();
    List<String> keywords = imagedao.getAllDistinctKeyword1();
    if(currentKeyword1==null && keywords!=null && keywords.size()>0) {
        response.sendRedirect("/manageimages?currentKeyword1="+keywords.get(0));
        return;
    }
    List<Image> images = (currentKeyword1==null)?null:imagedao.getImagesByKeyword1(currentKeyword1);
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Manage Images | Computer Science and Programming Tutorials</title>
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
                    <li class="nav-item active">
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
                            <a class="btn btn-sm btn-default is-btn-primary-outline" href="/managetutorial">Tutorial</a>
                            <a class="btn btn-sm btn-default is-btn-primary-outline" href="/managearticle">Article</a>
                            <a class="btn btn-sm btn-default is-btn-primary-outline" href="/managecomments">Comments</a>
                            <a class="btn btn-sm btn-default is-btn-primary-outline" href="/managefeedbacks">Feedbacks</a>
                            <a class="btn btn-sm btn-default is-btn-primary" href="/manageimages">Images</a>
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
                                <div class="col-lg-4">
                                    <select class="form-control" onchange="changeKeywordSelection()" id="currentKeyword1">
                                        <%
                                            if(keywords!=null)
                                            for(int i=0; i<keywords.size(); i++) {
                                                out.println("<option value='" + keywords.get(i)+"' ");
                                                if(currentKeyword1.equals(keywords.get(i))) {
                                                    out.println(" selected='true' ");
                                                }
                                                out.println(" >"+keywords.get(i)+"</option>");
                                            }
                                        %>
                                    </select>
                                    <hr />
                                    <form action="/uploadimage" method="post" enctype="multipart/form-data">
                                        <input type="file" class="form-control mb-2" name="image"  />
                                        <input type="text" name="keyword1" class="form-control mb-2" placeholder="Keyword 1" value="<%=currentKeyword1%>" />
                                        <input type="text" name="keyword2" class="form-control mb-2" placeholder="Keyword 2" />
                                        <input name="redirectURL" hidden="true" value="/manageimages?currentKeyword1=<%=currentKeyword1%>" />
                                        <button type="submit" class="btn btn-default is-btn-primary">Upload New Image</button>
                                    </form>
                                </div>
                                <div class="col-lg-8">
                                    <table class="table table-hover">
                                    <%
                                        if(images!=null)
                                        for (int i = 0; i < images.size(); i++) {
                                        %>
                                        <tr>
                                            <td>
                                                <img class="img-thumbnail" style="max-width:150px;" src="/image/<%=images.get(i).getKeyword1()%>/<%=images.get(i).getKeyword2()%>" />
                                            </td>
                                            <td>
                                                <%=images.get(i).getId()%>
                                            </td>
                                            <td>
                                                <%=images.get(i).getData().length()%> Bytes
                                            </td>
                                            <td>
                                                <%=images.get(i).getKeyword2()%>
                                            </td>
                                            <td>
                                                <form action="/deleteimage" method="POST">
                                                    <input name="imageId" hidden="true" value="<%=images.get(i).getId()%>" />
                                                    <input name="redirectURL" hidden="true" value="/manageimages?currentKeyword1=<%=currentKeyword1%>" />
                                                    <button class="btn btn-warning" type="submit">Delete</button>
                                                </form>
                                            </td>
                                        </tr>
                                        <%
                                        }
                                    %>
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

        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
        <script lang="text/javascript">
            function changeKeywordSelection() {
                window.location.href = "/manageimages?currentKeyword1=" + $("#currentKeyword1").val();
            }
            
        </script>
    </body>
</html>