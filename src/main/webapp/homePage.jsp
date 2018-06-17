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
    
    Boolean isUser = false, 
            isAdmin = false;
    User user = null;
    
    if(session.getAttribute("userId") != null) {
        UserDAO userdao = new UserDAO();
        user = userdao.getUserById((Integer)session.getAttribute("userId"));
        if(user.getType().equals("user")) {
            isUser=true;
        }
        else if(user.getType().equals("admin")) {
            isAdmin=true;
        }
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Computer Science and Programming Tutorials</title>
        <meta name="description" content="Tutorials and articles on topics that will brush up your computer programming skills and 
                test your fundamental understanding of the subject." />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta charset="UTF-8" />
        <meta name="google-site-verification" content="q94Vj4nrbIhqG6KIgr4iAWZmLVQa3Pm5UV2gGWSAwHE" />
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.9/css/all.css" crossorigin="anonymous" type="text/css" />
        <link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet" />
        <link rel="stylesheet" href="/main/main.css" type="text/css" />
        <link rel="icon" type="image/png" href="/image/cstutorials/icon-sm" />
    </head>
    <body>
        <!-- navigation bar -->
        <nav class="navbar navbar-expand-lg is-navbar">
            <a class="navbar-brand ml-sm-3 ml-md-5" href="/"><img height="50" src="/image/cstutorials/icon-lg" alt="cstutorials icon" /></a>
            
            <div class="d-none d-lg-inline-block ml-auto mr-5">
                <ul class="navbar-nav ml-auto">
                    <%
                        if(isAdmin) {
                            %><li class="nav-item">
                                <a class="nav-link" href="/admindashboard">Admin</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/logout">Logout</a>
                            </li><%
                        }
                    %>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" 
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
        <div class="container home-page-container">
            <div class="row">
                <!-- main content -->
                <div class="col-12 p-4 is-content">
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
                                    <p>
                                        These topics will brush up your computer programming skills and 
                                        test your fundamental understanding of the subject.
                                    </p>
                                    <div class="row mb-4 mt-3">
                                        <div class="col-lg-6 offset-lg-3">
                                            <form id="is-searchForm-home">
                                                <div class="input-group">
                                                    <input type="text" class="form-control search-input" placeholder="Search All Tutorials and Articles..." />
                                                    <div class="input-group-append">
                                                        <button class="btn btn-info is-btn-primary" type="submit"><i class="fa fa-search"></i></button>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                    <% for(int i=0; i<allTopics.size(); i++) { %>
                                        <div class="row mt-3">
                                            <div class="col-12 text-center">
                                                <h5 class="text-muted"><%=allTopics.get(i).getTitle()%></h5>
                                            </div>
                                        </div>
                                        <hr />
                                        <div class="row is-nav-list">
                                        <%
                                            tempTutorials = tutorialdao.getTutorialsByTopicIdAndStatus(allTopics.get(i).getId(),(isAdmin?null:"final"));
                                            if(tempTutorials!=null)
                                            for(int k=0; k<tempTutorials.size(); k++) {
                                                tempArticles = articledao.getArticlesByTutorialIdAndStatus(tempTutorials.get(k).getId(),isAdmin?null:"final",false);
                                                %><div class="col-sm-6 col-md-4">
                                                    <div class="card card-body is-tutorial-card">
                                                        <h6 class="card-title">
                                                            <%=tempTutorials.get(k).getTitle()%>
                                                            <% if(isAdmin) { %><sup class="<%=
                                                                (tempTutorials.get(k).getStatus().equals("final"))?"text-success":"text-warning"
                                                                %>"></i>(<%=tempTutorials.get(k).getStatus()%>)</i></sup>
                                                            <% } %>
                                                        </h6>
                                                        <%
                                                            for(int j=0; j<tempArticles.size(); j++) {
                                                        %><a class="card-article-link" href="/article/<%=tempArticles.get(j).getKey()%>">
                                                                        <%=tempArticles.get(j).getTitle()%>
                                                                        <% if(isAdmin) { %><sup class="<%=
                                                                                (tempArticles.get(j).getStatus().equals("final"))?"text-success":"text-warning"
                                                                                %>"><i><%=tempArticles.get(j).getStatus()%></i></sup>
                                                                        <% } %>
                                                                </a><%
                                                            }
                                                        %>
                                                    </div>
                                                </div><%
                                            } 
                                        %>
                                        </div>
                                    <% } %>
                                    <hr />
                                    <p>
                                        More tutorials coming soon on <b>Operating Systems</b>, <b>Computer Networks</b>, <b>Database Systems</b>,
                                        <b>Computer Architecture</b>. 
                                        Any suggestions welcome.
                                    </p>
                                    <div class="text-center">
                                        <button data-toggle="modal" data-target="#feedbackModal" class="btn btn-default is-btn-primary">Give Feedback <i class="fa fa-comment ml-2"></i></button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>               

        <div class="container-fluid row pt-2 px-4 m-0 is-footer">
            <div class="col-12 text-right" style="color:#fff;">
                <small class="d-block">
                    Developed By 
                    <span data-toggle="modal" data-target="#loginModal"><b>Ishaan Shringi</b></span>
                </small>
                <a target="_black" href="https://www.linkedin.com/in/shringiishaan" style="color:#0077B5;"><i class="fab fa-linkedin ml-2"></i></a>
                <a target="_black" href="https://github.com/shringiishaan/guides_and_tutorials" style="color:#333;"><i class="fab fa-github ml-2"></i></a>
            </div>
        </div>
       
        <div class="modal fade" id="feedbackModal" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-body">
                        <p>Contact Administrator</p>
                        <div class="form-group">
                            <input id="is-feedback-ownerkey" class="form-control" placeholder="Name or Email-ID (optional)" />
                            <small class="text-muted ml-1 m-0 p-0">skip this to send anonymously</small>
                        </div>
                        <div class="form-group">
                            <textarea id="is-feedback-data" class="form-control" placeholder="Your message here..."></textarea>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default is-btn-primary-outline" data-dismiss="modal">Close</button>
                        <button onclick="sendFeedback()" class="btn btn-default is-btn-primary">Send</button>
                    </div>
                </div>
            </div>
        </div>
       
        <div class="modal fade" id="postFeedbackModal" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-md" role="document">
                <div class="modal-content">
                    <div class="modal-body">
                        <p>Thank You for your feedback!</p>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-default is-btn-primary" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-sm" role="document">
                <div class="modal-content">
                    <form action="/login" method="post">
                        <div class="modal-body">
                            <div class="form-group">
                                <input type="text" class="form-control" name="email" placeholder="Email" />
                            </div>
                            <div class="form-group">
                                <input type="password" class="form-control" name="password" placeholder="Password" />
                            </div>
                        </div>
                        <div class="modal-footer">
                            <input type="text" name="redirectURL" hidden="true" value="/" />
                            <button type="button" class="btn btn-outline-secondary" data-dismiss="modal">Close</button>
                            <input type="submit" name="submit" value="Login" class="btn btn-info" />
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <div id="is-tm-wrapper" style="display:none;">
        </div>
        
        <div id="is-tm-content-search" class="is-tm-content pb-5" style="display:none;overflow-y:auto;">
            <div class="row p-4">
                <div class="col-sm-6 col-md-4 col-md-4 offset-md-4">
                    <div>
                        <form id="is-searchForm-main" class="is-tm-searchbar">
                            <div class="input-group">
                                <input type="text" class="search-input form-control" placeholder="Search" />
                                <div class="input-group-append">
                                    <button class="btn btn-default is-btn-primary" ><i class="fa fa-search"></i></button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="row px-4">
                <div class="col-lg-10 offset-lg-1 is-tutorial-card">
                    <h6 class="card-title">Search Results (<span id="searchresultscounter">0</span>)</h6>
                    <hr />
                    <div id="searchresultswrapper"></div>
                    <hr/>
                </div>
            </div>
        </div>
        
        <div id="is-float-bottom">
            <a id="is-tm-scrolltop-btn" style='display:none;' onclick="scrollToTop()" class="btn btn-default"><i class="fa fa-angle-double-up"></i></a>
            <a id="is-tm-close-btn" onclick="closeTM()" class="btn btn-default" style="display:none;"><i class="fa fa-times"></i></a>
        </div>
        
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
        <script lang="text/javascript">
            
            currentTM = null;
            
            $(function(){
                $('#is-searchForm-main').on('submit', function(event){
                    event.preventDefault();
                    displaySearchResults($("#is-searchForm-main .search-input").val());
                });
                
                $('#is-searchForm-home').on('submit', function(event){
                    event.preventDefault();
                    displaySearchResults($("#is-searchForm-home .search-input").val());
                });
                
                $(window).scroll(function() {
                    if(currentTM===null) {
                        if($(window).scrollTop()<300) {
                            $("#is-tm-scrolltop-btn").fadeOut();
                        }
                        else {
                            $("#is-tm-scrolltop-btn").fadeIn();
                        }
                    }
                });
            });
            
            function displaySearchResults(query) {
                if(currentTM===null || currentTM!=="search") {
                    openTM('search');
                    $("#is-searchForm-main .search-input").val(query);
                }
                
                $.get("/search?s="+query, function(data) {
                    $("#searchresultswrapper").html("");
                    $("#searchresultscounter").html(data.results.length);
                    if(data.results.length!=0) {
                        for(result in data.results) {
                            $("#searchresultswrapper").append(
                                    "<div onclick=\"location.href='"+data.results[result].link+"'\" class='card-article-link px-2' style='cursor:pointer'>"+
                                        "<h6>"+data.results[result].title+"</h6>"+
                                        "<p>"+data.results[result].description+"</p>"+
                                    "</div>");
                        }
                    }
                    else {
                        $("#searchresultswrapper").html("<small class='text-center d-block'><i>No results found for \""+query+"\"</i></small>");
                        $("#searchresultscounter").html("0");
                    }
                },"json")
                .fail(function(data) {
                    console.log(data.responseText);
                    $("#searchresultswrapper").html("<small class='text-center d-block'><i>Unable to fetch results</i></small>");
                    $("#searchresultscounter").html("0");
                });
            }
            
            function openTM(modalName) {
                if(currentTM === modalName) {
                    closeTM();
                }
                else if(currentTM !== null) {
                    $("#is-tm-content-"+currentTM).fadeOut("fast");
                    $("#is-tm-content-"+modalName).fadeIn("slow"); 
                    $("#is-tm-"+currentTM+"-btn").removeClass("active");
                    $("#is-tm-"+modalName+"-btn").addClass("active");
                    $("#is-tm-close-btn").fadeIn();
                    currentTM = modalName;
                }
                else {
                    $("#is-tm-wrapper").show();
                    $("#is-tm-content-"+modalName).fadeIn();
                    $("#is-tm-"+modalName+"-btn").addClass("active");
                    $("#is-tm-close-btn").fadeIn();
                    currentTM = modalName;
                }
            }
            
            function closeTM() {
                $("#is-tm-"+currentTM+"-btn").removeClass("active");
                $(".is-tm-content").fadeOut("fast");
                $("#is-tm-wrapper").fadeOut("slow");
                $("#is-tm-close-btn").hide();
                if($(window).scrollTop()>300) {
                    $("#is-tm-scrolltop-btn").fadeIn();
                }
                currentTM = null;
            }
            
            function sendFeedback() {
                ownerKey = $("#is-feedback-ownerkey").val();
                data = $("#is-feedback-data").val();
                $.post('/newfeedback', {ownerKey:ownerKey, data:data},function(returnedData){});
                $("#feedbackModal").modal("hide");
                $("#postFeedbackModal").modal("show");
            }
            
            function scrollToTop() {
		$('html, body').animate({scrollTop:'0px'});
            }
            
        </script>
    </body>
</html>