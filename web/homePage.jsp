<%@page import="dao.TutorialDAO"%>
<%@page import="java.util.List"%>
<%@page import="model.Tutorial"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    TutorialDAO tutorialdao = new TutorialDAO();
    List<Tutorial> allTutorials = tutorialdao.getAllTutorials();
%>
<!DOCTYPE html>
<html>
    <head>
        <title>The Computer Guide | Home</title>
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
            <a class="navbar-brand" href="#">Computer Guide</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item active">
                        <a class="nav-link" href="#">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Tutorials</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Articles</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#!" data-toggle="modal" data-target="#loginModal">Login</a>
                    </li>
                </ul>
            </div>
        </nav>

        <!-- container -->
        <div class="container-fluid">
            <div class="row">
                <!-- side bar -->
                <div class="is-sidebar col-lg-3 d-none d-lg-block p-4">
                    <a class="is-title">Categories</a>
                    <%
                        for (int i = 0; i < allTutorials.size(); i++) {
                    %>
                        <a class="is-label" href="/article/<%=allTutorials.get(i).getIdentifier()%>"><%=allTutorials.get(i).getTitle()%></a>
                    <%
                        }
                    %>
                </div>

                <!-- main content -->
                <div class="col-lg-6 offset-lg-3 pt-3 pt-lg-4 is-content">
                    <%
                        if (session.getAttribute("error") != null) {
                        %>
                            <i>ERROR : <%=session.getAttribute("error")%></i><hr/>
                        <%
                            session.removeAttribute("error");
                        }
                        if (session.getAttribute("message") != null) {
                        %>
                            <i>MESSAGE : <%=session.getAttribute("message")%></i><hr/>
                        <%
                            session.removeAttribute("message");
                        }
                    %>
                    <h3>The Computer Guide</h3>
                    <hr />
                    <p>Curabitur sodales ligula in libero. Sed dignissim lacinia nunc. <b>Praesent mauris</b>. Curabitur 
                        tortor. Pellentesque nibh. Aenean quam. In scelerisque sem at dolor. <b>Mauris massa</b>. Maecenas mattis. Sed 
                        convallis tristique sem. Proin ut ligula vel nunc egestas porttitor. Morbi lectus risus, iaculis vel, 
                        suscipit quis, luctus non, massa. Fusce ac turpis quis ligula lacinia aliquet. Mauris ipsum. 
                        Nulla metus metus, ullamcorper vel, tincidunt sed, euismod in, nibh. 
                    </p>
                    <h4>Another Heading</h4>
                    <hr/>
                    <p>
                        <i>Lorem ipsum dolor sit amet, consectetur adipiscing elit</i>. Quisque volutpat condimentum velit.
                        Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Nam nec
                        ante. <b>Morbi lectus risus, iaculis vel, suscipit quis, luctus non, massa</b>. Sed lacinia, urna non 
                        tincidunt mattis, tortor neque adipiscing diam, a cursus ipsum ante quis turpis. Nulla facilisi. 
                        Ut fringilla. Suspendisse potenti. Nunc feugiat mi a tellus consequat imperdiet. Vestibulum sapien. 
                        Proin quam. Etiam ultrices. 
                    </p>
                    <h5>A Sub-Heading</h5>
                    <p>
                        Suspendisse in justo eu magna luctus suscipit. Sed lectus. Integer euismod lacus luctus magna. 
                        Quisque cursus, metus vitae pharetra auctor, sem massa mattis sem, at interdum 
                        magna augue eget diam. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia 
                        Curae; Morbi lacinia molestie dui. Praesent blandit dolor. <i>Morbi lectus risus, iaculis vel, 
                            suscipit quis, luctus non, massa</i>. Sed non quam. In vel mi sit amet augue congue elementum. 
                        Morbi in ipsum sit amet pede facilisis laoreet. Donec lacus nunc, viverra nec, blandit vel, egestas 
                        et, augue. Vestibulum tincidunt malesuada tellus. Ut ultrices ultrices enim. <b>Vestibulum ante ipsum 
                            primis in faucibus orci luctus et ultrices posuere cubilia Curae; Morbi lacinia 
                            molestie dui</b>. Curabitur sit amet mauris. Morbi in dui quis est pulvinar ullamcorper. 
                    </p>
                    <p>
                        Nulla facilisi. Integer lacinia sollicitudin massa. <i>Class aptent taciti sociosqu ad litora 
                            torquent per conubia nostra, per inceptos himenaeos</i>. Cras metus. Sed aliquet risus a tortor. 
                        Integer id quam. Morbi mi. Quisque nisl felis, venenatis tristique, dignissim in, ultrices sit 
                        amet, augue. Proin sodales libero eget ante. Nulla quam. Aenean laoreet. Vestibulum nisi lectus, 
                        commodo ac, facilisis ac, ultricies eu, pede. 
                    </p>
                    <h4>Next Heading</h4>
                    <hr/>
                    <p>
                        Mauris eu dolor quis felis aliquam malesuada. In imperdiet, ex at auctor sodales, risus nisi egestas 
                        felis, tempor sagittis odio mi eget nisl. In congue felis et risus vestibulum, in interdum nulla iaculis. 
                        Etiam ac erat cursus, finibus orci in, dictum ligula. Pellentesque sit amet pharetra nibh. 
                        Quisque fringilla velit in maximus fringilla. Morbi commodo magna varius porta posuere. 
                        Nullam id luctus eros, vel congue leo.
                    </p>
                    <p>
                        Donec pulvinar venenatis augue vel suscipit. Nam ornare sodales varius. Praesent tincidunt elit posuere iaculis 
                        auctor. Vestibulum faucibus blandit lacus id semper. Nullam tempor nunc 
                        in interdum rhoncus. Morbi eu pretium diam. Suspendisse potenti.
                    </p>
                    <p>
                        Aenean eu viverra urna. Nunc congue fringilla tellus in mattis. Quisque 
                        nibh lacus, viverra eu urna sed, lacinia rutrum elit. Praesent purus arcu, iaculis vitae consectetur id, elementum 
                        ut sapien. Integer lobortis lectus eu condimentum ultricies. Proin eget dapibus quam. 
                        In vitae nisi non erat scelerisque laoreet. Nulla consectetur sapien sit amet enim condimentum blandit ut 
                        eget nunc. Donec sagittis consequat mauris, sit amet sodales elit. 
                        Aenean dictum sem non mauris egestas tempus. Pellentesque hendrerit in 
                        odio sed fermentum. Aliquam vel lacus ac ex cursus aliquam eu ut justo. Ut vel porta magna, 
                        sit amet dignissim tortor. Nullam mollis tristique leo a pharetra. Vivamus 
                        condimentum malesuada tortor, dapibus semper purus egestas vitae. Sed nisl eros, ultricies id 
                        nulla eget, sagittis condimentum ex.
                    </p>
                    <p>
                        Fusce dapibus ligula hendrerit diam convallis hendrerit. Suspendisse maximus et nunc 
                        vitae accumsan. Pellentesque id dapibus mauris. Integer ultricies justo vitae varius pellentesque. 
                        Etiam quam erat, tincidunt a augue et, tincidunt luctus est. Nam nec lobortis augue. 
                        Etiam lorem elit, hendrerit vel mi sit amet, condimentum pellentesque eros. Maecenas risus felis, 
                        gravida a tempus in, cursus quis est. Curabitur ac lorem in mauris dictum interdum.             
                    </p>
                </div>

                <!-- information bar -->
                <div class="col-lg-3 offset-lg-9 p-4 is-infobar">
                    <div class="card mb-3">
                        <div class="card-header">
                            <div class="row">
                                <div class="col">
                                    Recommended Tutorials
                                </div>
                            </div>
                        </div>
                        <div class="card-body">
                            <a href="#!">Java Advanced Guide</a>
                            <a href="#!">Web Development with Java</a>
                        </div>
                    </div>
                    <div class="card mb-3">
                        <div class="card-header">
                            <div class="row">
                                <div class="col">
                                    Related Tutorials
                                </div>
                            </div>
                        </div>
                        <div class="card-body">
                            <a href="#!">Java Advanced Guide</a>
                            <a href="#!">Web Development with Java</a>
                            <a href="#!">Java Advanced Guide</a>
                            <a href="#!">Web Development with Java</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Modal -->
        <div class="modal fade" id="exploreModal" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Explore</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        Tutorial topics | Search | Categories
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary">Save changes</button>
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
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            <input type="submit" name="submit" value="Login" class="btn btn-primary" />
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    </body>
</html>