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
    if(tutorialIdString == null) {
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
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Manage Tutorial | Computer Science and Programming Tutorials</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta charset="UTF-8" />
        <meta name="google-site-verification" content="q94Vj4nrbIhqG6KIgr4iAWZmLVQa3Pm5UV2gGWSAwHE" />
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.9/css/all.css" crossorigin="anonymous" type="text/css" />
        <link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet" />
        <link rel="stylesheet" href="/main/main.css" type="text/css" />
        <link rel="stylesheet" href="/navbar/navbar.css" type="text/css" />
        <link rel="stylesheet" href="/sidebar/sidebar.css" type="text/css" />
        <link rel="stylesheet" href="/infobar/infobar.css" type="text/css" />
        <link rel="stylesheet" href="/content/content.css" type="text/css" />
        <link rel="icon" type="image/png" href="/image/cstutorials/icon-sm" />
    </head>
    <body>
        <!-- navigation bar -->
        <nav class="navbar navbar-expand-lg is-navbar">
            <a class="navbar-brand ml-sm-3 ml-md-5" href="/"><img height="50" src="/image/cstutorials/icon-lg" alt="cstutorials icon" /></a>
            
            <div class="ml-auto mr-auto d-none d-sm-inline-block">
                <form action="/search" method="GET">
                    <div class="input-group">
                        <input type="text" name="s" class="form-control" placeholder="Search All Tutorials and Articles..." />
                        <div class="input-group-append">
                            <button class="btn btn-outline-info" type="submit"><i class="fa fa-search"></i></button>
                        </div>
                    </div>
                </form>
            </div>

            <div class="d-none d-lg-inline-block ml-auto mr-5">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item active dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" 
                           role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Admin
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item" href="/managetopic">Topic</a>
                            <a class="dropdown-item" href="/managetopictutoriallink">Topic-Tutorial</a>
                            <a class="dropdown-item" href="/managetutotial">Tutorial</a>
                            <a class="dropdown-item" href="/managetutorialarticlelink">Tutorial-Article</a>
                            <a class="dropdown-item" href="/managearticle">Article</a>
                            <a class="dropdown-item" href="/manageimages">Images</a>
                            <a class="dropdown-item" href="/logout">Logout</a>
                        </div>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/explore"><i class="fa fa-compass"></i> Explore</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#!"><i class="fa fa-bars"></i> Articles</a>
                    </li>
                </ul>
            </div>
        </nav>

        <!-- container -->
        <div class="container home-page-container">
            <div class="row">
                <!-- main content -->
                <div class="col-12 p-4 is-content">
                    <div class="row mb-4 mt-3">
                        <div class="col-12">
                            <a class="btn btn-sm btn-outline-secondary" href="/managetopic">Topic</a>
                            <a class="btn btn-sm btn-outline-secondary" href="/managetopictutoriallink">Topic-Tutorial</a>
                            <a class="btn btn-sm btn-secondary" href="/managetutorial">Tutorial</a>
                            <a class="btn btn-sm btn-outline-secondary" href="/managetutorialarticlelink">Tutorial-Article</a>
                            <a class="btn btn-sm btn-outline-secondary" href="/managearticle">Article</a>
                            <a class="btn btn-sm btn-outline-secondary" href="/manageimages">Images</a>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xl-10 offset-xl-1">
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
                            <div class="row">
                                <div class="col-12">
                                    <select id="tid-select" name="tid" class="form-control" onchange="changeTopicIdSelection()">
                                        <% for(int i=0; i<allTutorials.size(); i++) { %>
                                            <option value="<%=allTutorials.get(i).getId()%>"><%=allTutorials.get(i).getTitle()%></option>
                                        <% } %>
                                    </select>
                                </div>
                            </div>
                            <hr />
                            <div class="row">
                                <div class="col-12">
                                    <div class="row">
                                        <div class="col-12">
                                            <small>Title : <%=currentTutorial.getTitle()%></small><br />
                                            <small>ID : <%=currentTutorial.getId()%></small><br />
                                            <small>Key : <%=currentTutorial.getKey()%></small>
                                            <br />
                                            <form action="/updatetopicstatus" method="GET" class="mt-3">
                                                <div class="input-group">
                                                    <input type="text" name="status" class="form-control" value="<%=currentTutorial.getStatus()%>" />
                                                    <div class="input-group-append">
                                                        <button class="btn btn-outline-info" type="submit">Update Status</button>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                                    <hr />
                                    <div class="row">
                                        <div class="col-12">
                                            <div class="card card-body">
                                                <h6>New Tutorial</h6>
                                                <hr />
                                                <form action="/newtopic" method="GET">
                                                    <div class="input-group">
                                                        <input type="text" name="title" class="form-control" placeholder="New Title" />
                                                        <div class="input-group-append">
                                                            <button class="btn btn-outline-success" type="submit">Create New</button>
                                                        </div>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <br>
            <hr />
            <p class="text-muted float-right"><small>Developed By <span class="text-info" data-toggle="modal" data-target="#loginModal">Ishaan Shringi</span></small></p>
        </div>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
        <script lang="text/javascript">
            function changeTopicIdSelection() {
                window.location.href = "/managetutorial?tid=" + $("#tid-select").val();
            }
        </script>
    </body>
</html>