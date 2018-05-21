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
        response.sendRedirect("/ManageImages?currentKeyword1="+keywords.get(0));
        return;
    }
    List<Image> images = (currentKeyword1==null)?null:imagedao.getImagesByKeyword1(currentKeyword1);
%>
<!DOCTYPE html>
<html>
    <head>
        <title>The Computer Guide | Manage Images</title>
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
                <div class="col-12 p-4 pt-3 pt-lg-4  is-content">
                    <div class="row">
                        <div class="col-12">
                            <h3>Articles</h3>
                            <hr/>
                            <form method="GET" action="/ManageImages">
                                <div class="row">
                                    <div class="col-10">
                                        <select class="form-control" name="currentKeyword1">
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
                                    </div>
                                    <div class="col-2">
                                        <button type="submit" class="btn btn-outline-info">Change</button>
                                    </div>
                                </div>
                            </form>
                            <hr/>
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
                                <%
                                    if(images!=null)
                                    for (int i = 0; i < images.size(); i++) {
                                    %>
                                    <div class="col-md-3">
                                        <img class="img-thumbnail" src="/Image/<%=images.get(i).getId()%>" />
                                        <center>
                                            <small><i>ID</i> : <%=images.get(i).getId()%></small>
                                            <br><small><i>Size</i> : <%=images.get(i).getData().length()%></small>
                                            <br><small><i>Keyword2</i> : <%=images.get(i).getKeyword2()%></small>
                                            <br>
                                            <small>
                                                <form action="DeleteImage" method="POST">
                                                    <input name="imageId" hidden="true" value="<%=images.get(i).getId()%>" />
                                                    <input name="redirectURL" hidden="true" value="/ManageImages?currentKeyword1=<%=currentKeyword1%>" />
                                                    <button class="btn btn-sm btn-outline-danger" type="submit">Delete</button>
                                                </form>
                                            </small>
                                        </center>
                                    </div>
                                    <%
                                    }
                                %>
                            </div>
                            <hr/>
                            <form action="/UploadImage" method="post" enctype="multipart/form-data" class="row">
                                <div class="col-md-3">
                                    <input type="file" class="form-control" name="image"  />
                                </div>
                                <div class="col-md-3">
                                    <input type="text" name="keyword1" class="form-control" placeholder="Keyword 1" value="<%=currentKeyword1%>" />
                                </div>
                                <div class="col-md-3">
                                    <input type="text" name="keyword2" class="form-control" placeholder="Keyword 2" />
                                    <input name="redirectURL" hidden="true" value="/ManageImages?currentKeyword1=<%=currentKeyword1%>" />
                                </div>
                                <div class="col-md-3">
                                    <button type="submit" class="btn btn-outline-info">Upload</button>
                                </div>
                            </form>
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