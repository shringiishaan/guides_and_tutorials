<%@page import="java.util.List"%>
<%@page import="model.Article"%>
<%@page import="dao.ArticleDAO"%>
<%@page import="model.Tutorial"%>
<%@page import="dao.TutorialDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String requestedPath = request.getRequestURI().substring(request.getContextPath().length());
    String[] parts = requestedPath.split("/");
    
    if(parts.length<3 || parts.length>4 || !parts[0].isEmpty() || !parts[1].equals("article")) {
        request.getRequestDispatcher("/error404.jsp").forward(request, response);
        return;
    }
    
    String tutorialIdentifier, articleIdentifier;
    if(parts.length==3) {
        tutorialIdentifier = parts[2];
        articleIdentifier = "overview";
    }
    else {
        tutorialIdentifier = parts[2];
        articleIdentifier = parts[3];
    }
    
    TutorialDAO tutorialdao = new TutorialDAO();
    Tutorial tutorial = tutorialdao.getTutorialByIdentifier(tutorialIdentifier);
    if(tutorial==null) {
        request.getRequestDispatcher("/error404.jsp").forward(request, response);
        return;
    }
    
    ArticleDAO articledao = new ArticleDAO();
    Article article = articledao.getArticleByTutorialAndIdentifier(tutorial.getId(), articleIdentifier);
    if(article==null) {
        request.getRequestDispatcher("/error404.jsp").forward(request, response);
        return;
    }
    article.fetchData();
    List<Article> tutorialArticles = articledao.getArticlesByTutorialId(tutorial.getId());
%>
<!DOCTYPE html>
<html>
    <head>
        <title><%=tutorial.getTitle()+" - "+article.getTitle()+" | The Computer Guide"%></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta charset="UTF-8" />
        <link rel="stylesheet" href="/main/main.css" type="text/css" />
        <link rel="stylesheet" href="/content/content.css" type="text/css" />
        <link rel="stylesheet" href="/navbar/navbar.css" type="text/css" />
        <link rel="stylesheet" href="/sidebar/sidebar.css" type="text/css" />
        <link rel="stylesheet" href="/infobar/infobar.css" type="text/css" />
        <link rel="stylesheet" href="/utils/modal.css" type="text/css" />
        <link rel="stylesheet" href="/utils/dropdown.css" type="text/css" />
        <link rel="stylesheet" href="/utils/button.css" type="text/css" />
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.9/css/all.css" crossorigin="anonymous" type="text/css" />
        <link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet" />

    </head>
    <body>

        <!-- navigation bar -->
        <div class="is-nb">
            <div class="section left">
                <a class="element is-sb-toggle"><i class="fas fa-bars"></i></a>
            </div>
            <div class="section left">
                <a class="element"><%=tutorial.getTitle()%></a>
            </div>
            <div class="is-med-and-up section right">
                <a class="element">Categories</a>
            </div>
            <div class="is-med-and-up section right">
                <div class="is-dropdown">
                    <a href="#" class="element dd-trigger">More&nbsp;&nbsp;<i class="fas fa-caret-down"></i></a>
                    <div class="dd-content">
                        <a href="#" class="dd-element">option 1</a>
                        <a href="#" class="dd-element">option 2</a>
                        <a href="#" class="dd-element">option 3</a>
                        <a href="#" class="dd-element">option 4</a>
                    </div>
                </div>
            </div>
        </div>

        <!-- side bar -->
        <div class="is-sb">
            <div class="section">
                <i class="fas is-icon fa-home"></i>
                <a href="/" class="label">Home</a>
            </div>
            <div class="section" id="is-sb-sec-explore">
                <i class="fas is-icon fa-search"></i>
                <div class="label is-modal-open" target-modal="explore-modal">Explore</div>
            </div>
            <div class="section" id="is-sb-sec-bgtj" collapse="false">
                <i class="fas is-icon fa-book"></i>
                <div class="label"><%=tutorial.getTitle()%>&nbsp;&nbsp;<i class="is-caret fas"></i></div>
                <%
                    for(int i=0; i<tutorialArticles.size(); i++) {
                %>
                    <a class="element" href="/article/<%=tutorial.getIdentifier()%>/<%=tutorialArticles.get(i).getIdentifier()%>"><%=tutorialArticles.get(i).getTitle()%></a>
                <%
                    }
                %>
            </div>
        </div>

        <!-- container -->
        <div class="is-container">

            <!-- main content -->
            <div class="is-content">
                <h2><%=article.getTitle()%></h2>
                <hr />
                <a href="/EditArticle/<%=tutorial.getIdentifier()%>/<%=article.getIdentifier()%>" class="is-btn">edit</a>
                <a href="/NewArticle" class="is-btn">new</a>
                <hr />
                <%=article.getData()%>
            </div>

            <!-- information bar -->
            <div class="is-ib">
                <div class="section" id="is-ib-sec-recommended" collapse="false">
                    <div class="label">Recommended Tutorials&nbsp;&nbsp;<i class="is-caret fas"></i></div>
                    <a class="element" href="#">Java Advanced Guide</a>
                    <a class="element" href="#">Web Development with Java</a>
                </div>
                <div class="section" id="is-ib-sec-related" collapse="false">
                    <div class="label">Related Tutorials&nbsp;&nbsp;<i class="is-caret fas"></i></div>
                    <a class="element" href="#">Java Advanced Guide</a>
                    <a class="element" href="#">Web Development with Java</a>
                    <a class="element" href="#">Java Advanced Guide</a>
                    <a class="element" href="#">Web Development with Java</a>
                </div>
            </div>
        </div>

        <div class="is-modal" id="explore-modal">
            <div class="body">
                <div class="header">
                    <h3>Categories</h3>
                </div>
                <div class="content">
                    <center>
                        <a class="is-btn" href="#">C</a>
                        <a class="is-btn" href="#">Java</a>
                        <a class="is-btn" href="#">Web Development</a>
                        <a class="is-btn" href="#">Operating Systems</a>
                    </center>
                </div>
                <div class="footer">
                    <button class="right is-btn" >Submit</button>
                    <button class="right is-modal-close is-btn" target-modal="explore-modal">Close</button>
                    <div class="clear-float"></div>
                </div>
            </div>
        </div>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="/main/main.js"></script>
        <script src="/content/content.js"></script>
        <script src="/navbar/navbar.js"></script>
        <script src="/sidebar/sidebar.js"></script>
        <script src="/infobar/infobar.js"></script>
        <script src="/utils/modal.js"></script>
        <script src="/utils/dropdown.js"></script>
    </body>
</html>