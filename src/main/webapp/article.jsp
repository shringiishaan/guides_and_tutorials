<%@page import="dao.VoteDAO"%>
<%@page import="dao.PageViewDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="dao.RecommendationDAO"%>
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
    
    if(parts.length!=3 || !parts[0].isEmpty() || !parts[1].equals("article")) {
        request.getRequestDispatcher("/Error").forward(request, response);
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
    
    String requestArticleKey = parts[2];
    currentArticle = articledao.getArticleByKey(requestArticleKey, true);
    if(currentArticle==null) {
        request.getRequestDispatcher("/Error").forward(request, response);
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
    
    relatedTutorials = tutorialdao.getRelatedTutorialsByArticleId(currentArticle.getId(),isAdmin?null:"final");
    
    //set next previous articles, tutorials
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
    
    if(nextArticle==null && nextTutorial!=null) {
        nextArticle = articledao.getMaximumPriorityArticleByTutorialIdAndStatus(nextTutorial.getId(), isAdmin?null:"final", false);
    }
    if(previousArticle==null && previousTutorial!=null) {
        previousArticle = articledao.getMinimumPriorityArticleByTutorialIdAndStatus(previousTutorial.getId(), isAdmin?null:"final", false);
    }
    
    RecommendationDAO recommendationsdao = new RecommendationDAO();
    List<HashMap<String,String>> recommendations_internal = new ArrayList<>();
    List<HashMap<String,String>> recommendations_external = new ArrayList<>();
    List<HashMap<String,String>> recommendations = recommendationsdao.getRecommendationsByArticleId(currentArticle.getId());
    for(int i=0; i<recommendations.size(); i++) {
        if(recommendations.get(i).get("type").equals("internal")) {
            recommendations_internal.add(recommendations.get(i));
        }
        else if(recommendations.get(i).get("type").equals("external")) {
            recommendations_external.add(recommendations.get(i));
        }
    }
    
    PageViewDAO requestCounterDAO = new PageViewDAO();
    if(session.getAttribute("view-count-"+currentArticle.getId())==null) {
        if(!requestCounterDAO.verifyArticleId(currentArticle.getId())) {
            requestCounterDAO.initializePageCountByArticleId(currentArticle.getId());
        }
        requestCounterDAO.incrementCountForArticle(currentArticle.getId());
        session.setAttribute("view-count-"+currentArticle.getId(),true);
    }
    Integer pageCount = requestCounterDAO.getViewsByArticle(currentArticle.getId());
    
    VoteDAO articlevotesdao = new VoteDAO();
    boolean isVotedUp = false;
    boolean isVotedDown = false;
    Integer upVoteCount = 0;
    Integer downVoteCount = 0;
    if(!(session.getAttribute("vote-up-"+currentArticle.getId())==null)) {
        isVotedUp = true;
    }
    if(!(session.getAttribute("vote-down-"+currentArticle.getId())==null)) {
        isVotedDown = true;
    }
    if(articlevotesdao.verifyArticleVoteType(currentArticle.getId(), "up")) {
        upVoteCount = articlevotesdao.getVoteCountByArticleAndType(currentArticle.getId(), "up");
    }
    if(articlevotesdao.verifyArticleVoteType(currentArticle.getId(), "down")) {
        downVoteCount = articlevotesdao.getVoteCountByArticleAndType(currentArticle.getId(), "down");
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <title><%=currentArticle.getTitle()%> | currentTutorial.getTitle()%></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta charset="UTF-8" />
        <meta name="description" content="<%=currentArticle.getDescription()%>" />
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.9/css/all.css" crossorigin="anonymous" type="text/css" />
        <link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet" />
        <link rel="stylesheet" href="/main/main.css" type="text/css" />
        <link rel="stylesheet" href="/prism/prism.css" />
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
                           <i class="fa fa-bars mr-1"></i> <%=currentTopic.getTitle()%>
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <% for(int i=0; i<allTopics.size(); i++) {
                                if(allTopics.get(i).getId().compareTo(currentTopic.getId())==0) continue; %>
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
                
                <!-- side bar -->
                <div class="col-md-3 col-lg-2 is-sidebar is-nav-list d-none d-md-inline-block p-0 pt-1">
                    <%
                        for (int i = 0; i < relatedTutorials.size(); i++) {
                            if(!(relatedTutorials.get(i)).getStatus().equals("final") && !isAdmin) {
                                continue;
                            }
                            %><a class="is-title">
                                <%=(relatedTutorials.get(i)).getTitle()%>
                                <% if(isAdmin) { %><sup class="<%=
                                        ((relatedTutorials.get(i)).getStatus().equals("final"))?"text-success":"text-warning"
                                        %>"><i>(<%=(relatedTutorials.get(i)).getStatus()%>)</i></sup>
                                <% } %>
                            </a>
                            <%
                                tempArticles = articledao.getArticlesByTutorialIdAndStatus((relatedTutorials.get(i)).getId(),(isAdmin?null:"final"), false);
                                for(int j=0; j<tempArticles.size(); j++) { 
                                    %>
                                <a class='<%=(currentArticle.getId().equals(tempArticles.get(j).getId()))?"is-active":""%>
                                        is-label' href='/article/<%=tempArticles.get(j).getKey()%>'>
                                    <%=tempArticles.get(j).getTitle()%>
                                    <% if(isAdmin) { %><sup class="<%=
                                            (tempArticles.get(j).getStatus().equals("final"))?"text-success":"text-warning"
                                            %>"><i>(<%=tempArticles.get(j).getStatus()%>)</i></sup>
                                    <% } %>
                                </a>
                            <% }
                        }
                    %>
                </div>

                <!-- main content -->
                <div class="col-md-9 col-lg-10 pt-3">
                    <div class="row">
                        <div class="col-lg-9">
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
                            
                            <h3><%=currentArticle.getTitle()%></h3>
                            
                            <small class="text-muted is-article-util-panel">
                                <div class="element mr-2"><i class="fa fa-eye"></i> <%=pageCount%> Views</div>
                                <div onclick="triggerUpVote()" class="element is-pointer mr-2"><i class="fa fa-thumbs-up"></i> Up-Vote (<span class="is-upvote-count"><%=upVoteCount%></span>)</div>
                                <div onclick="triggerDownVote()" class="element is-pointer mr-2"><i class="fa fa-thumbs-down"></i> Needs Improvement (<span class="is-downvote-count"><%=downVoteCount%></span>)</div>
                                <div data-toggle="modal" data-target="#feedbackModal" class="element is-pointer"><i class="fa fa-comment"></i> Give Feedback</div>
                            </small>
                            <hr />
                            <%
                                if(previousArticle!=null) {
                                    %><a class="btn btn-sm btn-default is-btn-primary-outline" href="/article/<%=previousArticle.getKey()%>">
                                        <i class="fa fa-angle-left"></i> Previous Article</a><%
                                }
                                if(nextArticle!=null) {
                                    %><a class="btn btn-sm btn-default is-btn-primary-outline float-right" href="/article/<%=nextArticle.getKey()%>">Next Article <i class="fa fa-angle-right"></i></a><%
                                }
                                if(user!=null && user.getType().equals("admin")) {
                                    %><a class="btn btn-sm btn-default is-btn-primary ml-2"  href="/managearticle?aid=<%=currentArticle.getId()%>">Edit</a><%
                                }
                            %>
                            <div class="clearfix"></div>
                            <%
                                if(isAdmin) {
                                    %><form action="/updatearticletitle" method="POST" class="mt-3">
                                        <div class="input-group">
                                            <input name="articleId" value="<%=currentArticle.getId()%>" hidden="true" />
                                            <input type="text" name="redirectURL" hidden="true" value="/article/<%=currentArticle.getKey()%>" />
                                            <input type="text" name="title" class="form-control" value="<%=currentArticle.getTitle()%>" />
                                            <div class="input-group-append">
                                                <button class="btn default is-btn-primary" type="submit">Update Title</button>
                                            </div>
                                        </div>
                                    </form>
                                    <form action="/updatearticlekey" method="POST" class="mt-3">
                                        <div class="input-group">
                                            <input name="articleId" value="<%=currentArticle.getId()%>" hidden="true" />
                                            <input type="text" name="redirectURL" hidden="true" value="/managearticle?aid=<%=currentArticle.getId()%>" />
                                            <input type="text" name="key" class="form-control" value="<%=currentArticle.getKey()%>" />
                                            <div class="input-group-append">
                                                <button class="btn default is-btn-primary" type="submit">Update Key</button>
                                            </div>
                                        </div>
                                    </form>
                                    <form method="POST" action="/updatearticlestatus" class="mt-3">
                                        <div class="input-group">
                                            <input type="text" name="status" class="form-control" value="<%=currentArticle.getStatus()%>" />
                                            <input type="text" name="articleId" hidden="true" value="<%=currentArticle.getId()%>" />
                                            <input type="text" name="redirectURL" hidden="true" value="/article/<%=currentArticle.getKey()%>" />
                                            <div class="input-group-append">
                                                <button class="btn btn-sm btn-default is-btn-primary" type="submit">Update Status</button>
                                            </div>
                                        </div>
                                    </form>
                                    <form method="POST" action="/updatearticledescription" class="mt-3">
                                        <div class="input-group">
                                            <textarea name="description" rows="4" maxlength="250" class="form-control"><%=currentArticle.getDescription()%></textarea>
                                            <input type="text" name="articleId" hidden="true" value="<%=currentArticle.getId()%>" />
                                            <input type="text" name="redirectURL" hidden="true" value="/article/<%=currentArticle.getKey()%>" />
                                            <div class="input-group-append">
                                                <button class="btn btn-sm btn-default is-btn-primary" type="submit">Update Description</button>
                                            </div>
                                        </div>
                                    </form>
                                    <form method="POST" action="/updatearticledata" class="mt-3">
                                        <div class="input-group">
                                            <textarea name="data" class="form-control"><%=currentArticle.getData()%></textarea>
                                            <input type="text" name="articleId" hidden="true" value="<%=currentArticle.getId()%>" />
                                            <input type="text" name="redirectURL" hidden="true" value="/article/<%=currentArticle.getKey()%>" />
                                            <div class="input-group-append">
                                                <button class="btn btn-sm btn-default is-btn-primary" type="submit">Update Data</button>
                                            </div>
                                        </div>
                                    </form><%
                                }
                            %>
                            <hr />
                            <div class="mb-3">
                                <small class="text-muted">
                                    <i class="fa fa-clock"></i> Last modified on <%= new SimpleDateFormat("MMM d, yyyy").format(currentArticle.getModifiedTime().getTime()) %>
                                </small>
                            </div>
                            <div class="is-article-data">
                                <%=currentArticle.getData()%>
                            </div>
                            <div class="mt-2">
                                <small class="text-muted is-article-util-panel">
                                    <div onclick="triggerUpVote()" class="element is-pointer mr-2"><i class="fa fa-thumbs-up"></i> Up-Vote (<span class="is-upvote-count"><%=upVoteCount%></span>)</div>
                                    <div onclick="triggerDownVote()" class="element is-pointer mr-2"><i class="fa fa-thumbs-down"></i> Needs Improvement (<span class="is-downvote-count"><%=downVoteCount%></span>)</div>
                                    <div data-toggle="modal" data-target="#feedbackModal" class="element is-pointer"><i class="fa fa-comment"></i> Give Feedback</div>
                                </small>
                            </div>
                            <hr />
                            <%
                                if(previousArticle!=null) {
                                    %><a class="btn btn-sm btn-default is-btn-primary-outline" href="/article/<%=previousArticle.getKey()%>"><i class="fa fa-angle-left"></i> Previous Article</a><%
                                }
                                if(nextArticle!=null) {
                                    %><a class="btn btn-sm btn-default is-btn-primary-outline float-right" href="/article/<%=nextArticle.getKey()%>">Next Article <i class="fa fa-angle-right"></i></a><%
                                }
                            %>
                        </div>
                        <div class="col-xs-12 col-lg-3 is-nav-list pt-3 is-recommendation-bar">
                            <div class="d-block">
                                <form id="is-searchForm-sidebar">
                                    <div class="input-group">
                                        <input type="text" class="form-control search-input" placeholder="Search all Tutorials..." />
                                        <div class="input-group-append">
                                            <button class="btn btn-info is-btn-primary"><i class="fa fa-search"></i></button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <%
                                if(recommendations_internal!=null && recommendations_internal.size()>0) {
                                    %><a class="is-title">Recommended Links</a><%
                                    for(int j=0; j<recommendations_internal.size(); j++) { 
                                        %><a class='is-label' href='<%=recommendations_internal.get(j).get("link")%>'><%=recommendations_internal.get(j).get("title")%></a><%
                                    }
                                }

                                if(recommendations_external!=null && recommendations_external.size()>0) {
                                    %><a class="is-title">Recommended External Links</a><%
                                    for(int j=0; j<recommendations_external.size(); j++) { 
                                        %><a class='is-label' target="_blank" href='<%=recommendations_external.get(j).get("link")%>'><%=recommendations_external.get(j).get("title")%></a><%
                                    }
                                }

                                tempArticles = articledao.getTopArticles(5);
                                if(tempArticles!=null && tempArticles.size()>0) {
                                    %><a class="is-title">Top Articles</a><%
                                    for(int j=0; j<tempArticles.size(); j++) { 
                                        %><a class='is-label' href='/article/<%=tempArticles.get(j).getKey()%>'><%=tempArticles.get(j).getTitle()%></a><%
                                    }
                                }
                            %>
                        </div>
                    </div>
                    <div class="row mt-5 pb-4">
                        <div class="col-lg-9 px-sm-5 px-md-3">
                            <%
                                CommentDAO commentdao = new CommentDAO();
                                List<Comment> comments = commentdao.getCommentsByArticleId(currentArticle.getId());
                                %><h5>Comments (<%=comments.size()%>)</h5><hr /><%
                                for(Comment comment : comments) {
                                    %><div class="card card-body is-comment p-1 px-2 mb-3">
                                        <p class="m-0 mb-2 p-0"><%=comment.getMessage()%></p>
                                        <small><i class="fa fa-clock mr-1"></i> <%= new SimpleDateFormat("MMM d, yyyy HH:mm").format(comment.getCreateTime().getTime()) %>
                                            <i class="ml-2 fa fa-user mr-1"></i> <%
                                                if(comment.getOwnerKey().equals("admin")) {
                                                    out.println("Administrator");
                                                }
                                                else if(comment.getOwnerKey().equals("anonymous")) {
                                                    out.println("Anonymous");
                                                }
                                                else {
                                                    out.println(comment.getOwnerKey());
                                                } 
                                        %></small> 
                                    </div><%
                                }
                            %>
                            <div class="row mt-3">
                                <div class="col-12">
                                    <button  data-toggle="modal" data-target="#commentModal" class="btn btn-default is-btn-primary">Add Comment</button>
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
            
        <% if(!isAdmin) { %>
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
                                    <input type="text" name="redirectURL" hidden="true" value="/article/<%=currentArticle.getKey()%>" />
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default is-btn-primary-outline" data-dismiss="modal">Close</button>
                                <input type="submit" name="submit" value="Login" class="btn btn-info" />
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        <% } %>
       
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
       
        <div class="modal fade" id="upvoteModal" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-md" role="document">
                <div class="modal-content">
                    <div class="modal-body">
                        <p>Thank you for the vote! Would you like to give us a Feedback?</p>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-default is-btn-primary-outline" data-dismiss="modal">Close</button>
                        <button data-dismiss="modal" data-toggle="modal" data-target="#feedbackModal" class="btn btn-default is-btn-primary">Give Feedback</button>
                    </div>
                </div>
            </div>
        </div>
       
        <div class="modal fade" id="downvoteModal" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-md" role="document">
                <div class="modal-content">
                    <div class="modal-body">
                        <p>Thank you for the vote we will definitely consider improvement! Would you like to give us a Feedback?</p>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-default is-btn-primary-outline" data-dismiss="modal">Close</button>
                        <button data-dismiss="modal" data-toggle="modal" data-target="#feedbackModal" class="btn btn-default is-btn-primary">Give Feedback</button>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="modal fade" id="commentModal" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-md" role="document">
                <div class="modal-content">
                    <form action="/newcomment" method="post">
                        <div class="modal-body">
                            <div class="form-group">
                                <input type="text" class="form-control" name="ownerKey" placeholder="Name or Email-ID (optional)" />
                                <small class="text-muted ml-1 m-0 p-0">skip this to post anonymously</small>
                            </div>
                            <div class="form-group">
                                <textarea class="form-control" name="message" placeholder="Enter text here.."></textarea>
                                <input name="articleId" value="<%=currentArticle.getId()%>" hidden="true" />
                                <input type="text" name="redirectURL" hidden="true" value="/article/<%=currentArticle.getKey()%>" />
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default is-btn-primary-outline" data-dismiss="modal">Close</button>
                            <input type="submit" name="submit" value="Post Comment" class="btn btn-default is-btn-primary" />
                        </div>
                    </form>
                </div>
            </div>
        </div>
        
        <div id="is-tm-wrapper" style="display:none;">
        </div>      
        
        <div id="is-tm-content-quicklinks" class="is-tm-content pb-4" style="display:none;overflow-y:auto;">
            <div style="width:100%;height:60px;">
                <div class="row p-4 text-center">
                        <div class="col-sm-8 col-md-6 col-lg-4 btn-group mx-auto" style="z-index:1500;">
                            <button type="button" class="btn btn-default is-btn-primary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style="width:100%">
                                <i class="fa fa-bars mr-2"></i> <%=currentTopic.getTitle()%>
                            </button>
                            <div class="dropdown-menu" style="width:100%">
                                <% for(int i=0; i<allTopics.size(); i++) { 
                                    if(currentTopic.getId().compareTo(allTopics.get(i).getId())==0) continue; %>
                                    <a class="dropdown-item" href="/starttopictutorial/<%=allTopics.get(i).getId()%>"><%=allTopics.get(i).getTitle()%></a>
                                <% } %>
                            </div>
                        </div>
                </div>
            </div>
            <div class="row p-4">
                <div class="col-md-10 offset-md-1">
                    <div class="row">
                    <%
                        relatedTutorials = tutorialdao.getTutorialsByTopicIdAndStatus(currentTopic.getId(),(isAdmin?null:"final"));
                        if(relatedTutorials!=null)
                        for(int k=0; k<relatedTutorials.size(); k++) {
                            tempArticles = articledao.getArticlesByTutorialIdAndStatus(relatedTutorials.get(k).getId(),isAdmin?null:"final",false);
                            %><div class="col-sm-6 col-md-4">
                                <div class="card card-body is-tutorial-card mb-2">
                                    <h6 class="card-title">
                                        <%=relatedTutorials.get(k).getTitle()%>
                                        <% if(isAdmin) { %><sup class="<%=
                                            (relatedTutorials.get(k).getStatus().equals("final"))?"text-success":"text-warning"
                                            %>"></i>(<%=relatedTutorials.get(k).getStatus()%>)</i></sup>
                                        <% } %>
                                    </h6>
                                    <%
                                        for(int j=0; j<tempArticles.size(); j++) {
                                    %><a class="card-article-link <%=(tempArticles.get(j).getId().equals(currentArticle.getId()))?"is-active":""%>" href="/article/<%=tempArticles.get(j).getKey()%>">
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
                </div>
            </div>
        </div>
        
        <div id="is-tm-content-search" class="is-tm-content pb-5" style="display:none;overflow-y:auto;">
            <div class="row p-4">
                <div class="col-sm-6 offset-sm-3 col-md-4 col-md-4 offset-md-4">
                    <div>
                        <form id="is-searchForm-main" class="is-tm-searchbar">
                            <div class="input-group">
                                <input type="text" class="search-input form-control" placeholder="Search all Tutorials..." />
                                <div class="input-group-append">
                                    <button class="btn btn-info is-btn-primary" ><i class="fa fa-search"></i></button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="row px-4">
                <div class="col-md-10 offset-md-1">
                    <div class="row">
                        <div class="col-12">
                            <div class="card card-body is-tutorial-card">
                                <h6 class="card-title">Search Results (<span id="searchresultscounter">0</span>)</h6>
                                <hr />
                                <div id="searchresultswrapper"></div>
                                <hr/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                    <%
                        if(recommendations_internal!=null && recommendations_internal.size()>0) {
                            %><div class="col-sm-6 col-md-4">
                                <div class="card card-body is-tutorial-card">
                                    <h6 class="card-title">Recommended Links</h6><%
                                    for(int j=0; j<recommendations_internal.size(); j++) { 
                                        %><a class='card-article-link' href='<%=recommendations_internal.get(j).get("link")%>'><%=recommendations_internal.get(j).get("title")%></a><%
                                    }
                                %></div>
                            </div><%
                        }

                        if(recommendations_external!=null && recommendations_external.size()>0) {
                            %><div class="col-sm-6 col-md-4">
                                <div class="card card-body is-tutorial-card">
                                    <h6 class="card-title">Recommended External Links</h6><%
                                    for(int j=0; j<recommendations_external.size(); j++) { 
                                        %><a class='card-article-link' target="_blank" href='<%=recommendations_external.get(j).get("link")%>'><%=recommendations_external.get(j).get("title")%></a><%
                                    }
                                %></div>
                            </div><%
                        }

                        tempArticles = articledao.getTopArticles(5);
                        if(tempArticles!=null && tempArticles.size()>0) {
                            %><div class="col-sm-6 col-md-4">
                                <div class="card card-body is-tutorial-card">
                                    <h6 class="card-title">Top Articles</h6><%
                                    for(int j=0; j<tempArticles.size(); j++) { 
                                        %><a class='card-article-link' href='/article/<%=tempArticles.get(j).getKey()%>'><%=tempArticles.get(j).getTitle()%></a><%
                                    }
                                %></div>
                            </div><%
                        }
                    %>
                    </div>
                </div>
            </div>
        </div>
        
        <div id="is-float-bottom">
            <a id="is-tm-quicklinks-btn" onclick="openTM('quicklinks')" class="btn btn-default"><i class="fa fa-sitemap"></i> <span class="d-none d-md-inline-block ml-2">Quick Links</span></a>
            <a id="is-tm-search-btn" style='display:none;' onclick="openTM('search')" class="btn btn-default"><i class="fa fa-search"></i> <span class="d-none d-md-inline-block ml-2">Explore</span></a>
            <a id="is-tm-scrolltop-btn" style='display:none;' onclick="scrollToTop()" class="btn btn-default"><i class="fa fa-angle-double-up"></i></a>
            <a id="is-tm-close-btn" style='display:none;' onclick="closeTM()" class="btn btn-default"><i class="fa fa-times"></i></a>
        </div>
        
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
        <script src="/prism/prism.js"></script>
        <script lang="text/javascript">
            
            currentTM = null;
            
            $(function(){
                $('#is-searchForm-main').on('submit', function(event){
                    event.preventDefault();
                    displaySearchResults($("#is-searchForm-main .search-input").val());
                });
                
                $('#is-searchForm-sidebar').on('submit', function(event){
                    event.preventDefault();
                    displaySearchResults($("#is-searchForm-sidebar .search-input").val());
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
                    $("#is-tm-"+currentTM+"-btn").removeClass("active");
                    $("#is-tm-content-"+modalName).fadeIn("slow");
                    $("#is-tm-"+modalName+"-btn").addClass("active");
                    currentTM = modalName;
                }
                else {
                    $("#is-tm-scrolltop-btn").hide();
                    $("#is-tm-wrapper").show();
                    $("#is-tm-search-btn").fadeIn();
                    $("#is-tm-content-"+modalName).fadeIn();
                    $("#is-tm-"+modalName+"-btn").addClass("active");
                    $("#is-tm-close-btn").show();
                    currentTM = modalName;
                }
            }
            
            function closeTM() {
                $("#is-tm-"+currentTM+"-btn").removeClass("active");
                $(".is-tm-content").fadeOut("fast");
                $("#is-tm-wrapper").fadeOut("slow");
                $("#is-tm-search-btn").hide();
                $("#is-tm-close-btn").hide();
                if($(window).scrollTop()>300) {
                    $("#is-tm-scrolltop-btn").fadeIn();
                }
                currentTM = null;
            }
            
            articleUpVote = <%=isVotedUp%>;
            articleDownVote = <%=isVotedDown%>;
            
            function triggerUpVote() {
                if(!articleUpVote) {
                    $.ajax({
                        url: '/newvote?articleId=<%=currentArticle.getId()%>&type=up',
                        type: 'GET',
                        datatype:"JSON",
                        contentType: "application/json;charset=utf-8",
                        error: function(data) {
                        },
                        success: function(data) {
                        }
                    });
                    
                    count = parseInt($(".is-upvote-count").html());
                    $(".is-upvote-count").html(count+1);
                    
                    articleUpVote = true;
                }
                $("#upvoteModal").modal('show');
            }
            
            function triggerDownVote() {
                if(!articleDownVote) {
                    $.ajax({
                        url: '/newvote?articleId=<%=currentArticle.getId()%>&type=down',
                        type: 'GET',
                        datatype:"JSON",
                        contentType: "application/json;charset=utf-8",
                        error: function(data) {
                        },
                        success: function(data) {
                        }
                    });
                    
                    count = parseInt($(".is-downvote-count").html());
                    $(".is-downvote-count").html(count+1);
                    
                    
                    articleDownVote = true;
                }
                $("#downvoteModal").modal('show');
            }
            
            function sendFeedback() {
                ownerKey = $("#is-feedback-ownerkey").val();
                data = $("#is-feedback-data").val();
                $.post('/newfeedback', { ownerKey: ownerKey, data : data},function(returnedData){});
                $("#feedbackModal").modal("hide");
                $("#postFeedbackModal").modal("show");
            }
            
            function scrollToTop() {
		$('html, body').animate({scrollTop:'0px'});
            }
            
        </script>
    </body>
</html>