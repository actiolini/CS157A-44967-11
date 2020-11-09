<%@ page import="java.sql.*"%>
<html>
  <head>
      <!-- Required meta tags -->
      <meta charset="utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

      <!-- Bootstrap CSS -->
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

      <title>Movie Buddy - Home</title>
    </head>
  <body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarToggler" aria-controls="navbarToggler" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <a class="navbar-brand" href="#">Movie Buddy</a>

        <div class="collapse navbar-collapse" id="navbarToggler">
          <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
            <li class="nav-item active">
              <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                  Menu
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                  <a class="dropdown-item" href="#">Releasing movies</a>
                  <a class="dropdown-item" href="#">Future Release</a>
                  <a class="dropdown-item" href="#">Top rated movies</a>
                  <a class="dropdown-item" href="#">Most popular movies</a>
                </div>
              </li>
            <li class="nav-item">
                <form class="form-inline my-2 my-lg-0">
                    <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
                    <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
                </form>
            </li>
          </ul>
          <a class="nav-link " href="#">Sign In / Register</a>
        </div>
    </nav>
    <%
      String db = "";
      String user; // assumes database name is the same as username
      user = "";
      String password = "";
      try {

          java.sql.Connection con;
          Class.forName("com.mysql.cj.jdbc.Driver");
          con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs157a?serverTimezone=EST5EDT",user, password);
          out.println(db + " database successfully opened.<br/><br/>");
    %>

    <div class="container">
            <h1 class="display-3">Releasing now</h1>
            <div class = "">
              <div id="carouselIndicators" class="carousel slide" data-ride="carousel">
                <ol class="carousel-indicators">
                  <li data-target="#carouselIndicators" data-slide-to="0" class="active"></li>
                  <li data-target="#carouselIndicators" data-slide-to="1"></li>
                </ol>
                <div class="carousel-inner">
                  <div class="carousel-item active">
                    <img src="movie1.jpg" class="d-block w-100" alt="movie1">
                    <div class="carousel-caption d-none d-md-block">
                        <h5>Movie 1</h5>
                        <p>Movie 1 Description</p>
                  </div>
              </div>
                  <div class="carousel-item">
                    <img src="movie2.jpg" class="d-block w-100" alt="movie2">
                    <div class="carousel-caption d-none d-md-block">
                        <h5>Movie 2</h5>
                        <p>Movie 2 Description</p>
                      </div>
                  </div>
                </div>
                <a class="carousel-control-prev" href="#carouselIndicators" role="button" data-slide="prev">
                  <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                  <span class="sr-only">Previous</span>
                </a>
                <a class="carousel-control-next" href="#carouselIndicators" role="button" data-slide="next">
                  <span class="carousel-control-next-icon" aria-hidden="true"></span>
                  <span class="sr-only">Next</span>
                </a>
            </div>

            </div>
              <hr>
              <h1 class="display-4">Showtimes</h1>
              <div class="card">
                <div class="card-body">
                  <div class="row">
                    <div class="col">
                      <div class="text-center">
                        <img src="movie1.jpg" class="rounded mx-auto w-75" alt="...">
                      </div>
                    </div>
                    <div class="col">
                      <h1>Movie 1</h1>
                      <hr>
                      <button type="button" class="btn btn-info">Movie Type</button>
                      <br>
                      <br>
                      <p>Length: 00:00</p>
                      <a href="#" class="card-link">Details</a>
                      <a href="#" class="card-link">Trailer</a>
                      <hr>
                      <h3>Description</h3>
                    </div>
                  </div>
                  <hr>
                  <div class="row">
                    <div class="col">
                      <nav>
                        <div class="nav nav-tabs" id="nav-tab1" role="tablist">
                          <a class="nav-link active" id="nav-Monday-tab1" data-toggle="tab" href="#nav-Monday1" role="tab" aria-controls="nav-Monday1" aria-selected="true">Monday</a>
                          <a class="nav-link" id="nav-Tuesday-tab1" data-toggle="tab" href="#nav-Tuesday1" role="tab" aria-controls="nav-Tuesday1" aria-selected="false">Tuesday</a>
                          <a class="nav-link" id="nav-Wednesday-tab1" data-toggle="tab" href="#nav-Wednesday1" role="tab" aria-controls="nav-Wednesday1" aria-selected="false">Wednesday</a>
                          <a class="nav-link" id="nav-Thursday-tab1" data-toggle="tab" href="#nav-Thursday1" role="tab" aria-controls="nav-Thursday1" aria-selected="false">Thursday</a>
                          <a class="nav-link" id="nav-Friday-tab1" data-toggle="tab" href="#nav-Friday1" role="tab" aria-controls="nav-Friday1" aria-selected="false">Friday</a>
                          <a class="nav-link" id="nav-Satuarday-tab1" data-toggle="tab" href="#nav-Satuarday1" role="tab" aria-controls="nav-Satuarday1" aria-selected="false">Satuarday</a>
                          <a class="nav-link" id="nav-Sunday-tab1" data-toggle="tab" href="#nav-Sunday1" role="tab" aria-controls="nav-Sunday1" aria-selected="false">Sunday</a>
                        </div>
                      </nav>
                      <div class="tab-content" id="nav-tabContent1">
                        <div class="tab-pane fade show active" id="nav-Monday1" role="tabpanel" aria-labelledby="nav-Monday-tab1">
                          <br>
                          <div class="container">
                             <%
                              Statement stmt = con.createStatement();
                              ResultSet rs = stmt.executeQuery("SELECT show_time FROM movie_schedule WHERE theatre_id = 1 AND movie_id = 1 AND show_date = ???");
                              while (rs.next()) {
                                  out.println("<a href=\"#\" class=\"card-link\">" + rs.getString(1) + "</a>");
                              }
                              rs.close();
                              stmt.close();
                             %>
                          </div>
                        </div>
                        <div class="tab-pane fade" id="nav-Tuesday1" role="tabpanel" aria-labelledby="nav-Tuesday-tab1">
                          <br>
                          <div class="container">
                             <%
                              stmt = con.createStatement();
                              rs = stmt.executeQuery("SELECT show_time FROM movie_schedule WHERE theatre_id = 1 AND movie_id = 1 AND show_date = ???");
                              while (rs.next()) {
                                  out.println("<a href=\"#\" class=\"card-link\">" + rs.getString(1) + "</a>");
                              }
                              rs.close();
                              stmt.close();
                             %>
                          </div>
                          </div>
                        <div class="tab-pane fade" id="nav-Wednesday1" role="tabpanel" aria-labelledby="nav-Wednesday-tab1">
                          <br>
                          <div class="container">
                             <%
                              stmt = con.createStatement();
                              rs = stmt.executeQuery("SELECT show_time FROM movie_schedule WHERE theatre_id = 1 AND movie_id = 1 AND show_date = ???");
                              while (rs.next()) {
                                  out.println("<a href=\"#\" class=\"card-link\">" + rs.getString(1) + "</a>");
                              }
                              rs.close();
                              stmt.close();
                             %>
                          </div>
                          </div>
                        <div class="tab-pane fade" id="nav-Thursday1" role="tabpanel" aria-labelledby="nav-Thursday-tab1">
                          <br>
                          <div class="container">
                             <%
                              stmt = con.createStatement();
                              rs = stmt.executeQuery("SELECT show_time FROM movie_schedule WHERE theatre_id = 1 AND movie_id = 1 AND show_date = ???");
                              while (rs.next()) {
                                  out.println("<a href=\"#\" class=\"card-link\">" + rs.getString(1) + "</a>");
                              }
                              rs.close();
                              stmt.close();
                             %>
                          </div>
                          </div>
                        <div class="tab-pane fade" id="nav-Friday1" role="tabpanel" aria-labelledby="nav-Friday-tab1">
                          <br>
                          <div class="container">
                             <%
                              stmt = con.createStatement();
                              rs = stmt.executeQuery("SELECT show_time FROM movie_schedule WHERE theatre_id = 1 AND movie_id = 1 AND show_date = ???");
                              while (rs.next()) {
                                  out.println("<a href=\"#\" class=\"card-link\">" + rs.getString(1) + "</a>");
                              }
                              rs.close();
                              stmt.close();
                             %>
                          </div>
                          </div>
                        <div class="tab-pane fade" id="nav-Satuarday1" role="tabpanel" aria-labelledby="nav-Satuarday-tab1">
                          <br>
                          <div class="container">
                             <%
                              stmt = con.createStatement();
                              rs = stmt.executeQuery("SELECT show_time FROM movie_schedule WHERE theatre_id = 1 AND movie_id = 1 AND show_date = ???");
                              while (rs.next()) {
                                  out.println("<a href=\"#\" class=\"card-link\">" + rs.getString(1) + "</a>");
                              }
                              rs.close();
                              stmt.close();
                             %>
                          </div>
                          </div>
                        <div class="tab-pane fade" id="nav-Sunday1" role="tabpanel" aria-labelledby="nav-Sunday-tab1">
                          <br>
                          <div class="container">
                             <%
                              stmt = con.createStatement();
                              rs = stmt.executeQuery("SELECT show_time FROM movie_schedule WHERE theatre_id = 1 AND movie_id = 1 AND show_date = ???");
                              while (rs.next()) {
                                  out.println("<a href=\"#\" class=\"card-link\">" + rs.getString(1) + "</a>");
                              }
                              rs.close();
                              stmt.close();
                             %>
                          </div>
                          </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <br>
              <div class="card">
                <div class="card-body">
                  <div class="row">
                    <div class="col">
                      <div class="text-center">
                        <img src="movie2.jpg" class="rounded mx-auto w-75" alt="...">
                      </div>
                    </div>
                    <div class="col">
                      <h1>Movie 2</h1>
                      <hr>
                      <button type="button" class="btn btn-info">Movie Type</button>
                      <br>
                      <br>
                      <p>Length: 00:00</p>
                      <a href="#" class="card-link">Details</a>
                      <a href="#" class="card-link">Trailer</a>
                      <hr>
                      <h3>Description</h3>
                    </div>
                  </div>
                  <hr>
                  <div class="row">
                    <div class="col">
                      <nav>
                        <div class="nav nav-tabs" id="nav-tab2" role="tablist">
                          <a class="nav-link active" id="nav-Monday-tab2" data-toggle="tab" href="#nav-Monday2" role="tab" aria-controls="nav-Monday2" aria-selected="true">Monday</a>
                          <a class="nav-link" id="nav-Tuesday-tab2" data-toggle="tab" href="#nav-Tuesday2" role="tab" aria-controls="nav-Tuesday2" aria-selected="false">Tuesday</a>
                          <a class="nav-link" id="nav-Wednesday-tab2" data-toggle="tab" href="#nav-Wednesday2" role="tab" aria-controls="nav-Wednesday2" aria-selected="false">Wednesday</a>
                          <a class="nav-link" id="nav-Thursday-tab2" data-toggle="tab" href="#nav-Thursday2" role="tab" aria-controls="nav-Thursday2" aria-selected="false">Thursday</a>
                          <a class="nav-link" id="nav-Friday-tab2" data-toggle="tab" href="#nav-Friday2" role="tab" aria-controls="nav-Friday2" aria-selected="false">Friday</a>
                          <a class="nav-link" id="nav-Satuarday-tab2" data-toggle="tab" href="#nav-Satuarday2" role="tab" aria-controls="nav-Satuarday2" aria-selected="false">Satuarday</a>
                          <a class="nav-link" id="nav-Sunday-tab2" data-toggle="tab" href="#nav-Sunday2" role="tab" aria-controls="nav-Sunday2" aria-selected="false">Sunday</a>
                        </div>
                      </nav>
                      <div class="tab-content" id="nav-tabContent2">
                        <div class="tab-pane fade show active" id="nav-Monday2" role="tabpanel" aria-labelledby="nav-Monday-tab2">
                          <br>
                          <div class="container">
                            <a href="#" class="card-link">00:00 am</a>
                            <a href="#" class="card-link">00:00 am</a>
                            <a href="#" class="card-link">00:00 am</a>
                            <a href="#" class="card-link">00:00 am</a>
                          </div>
                        </div>
                        <div class="tab-pane fade" id="nav-Tuesday2" role="tabpanel" aria-labelledby="nav-Tuesday-tab2">
                          <br>
                          <div class="container">
                            <a href="#" class="card-link">01:00 am</a>
                            <a href="#" class="card-link">01:00 am</a>
                            <a href="#" class="card-link">01:00 am</a>
                            <a href="#" class="card-link">01:00 am</a>
                          </div>
                          </div>
                        <div class="tab-pane fade" id="nav-Wednesday2" role="tabpanel" aria-labelledby="nav-Wednesday-tab2">
                          <br>
                          <div class="container">
                            <a href="#" class="card-link">02:00 am</a>
                            <a href="#" class="card-link">02:00 am</a>
                            <a href="#" class="card-link">02:00 am</a>
                            <a href="#" class="card-link">02:00 am</a>
                          </div>
                          </div>
                        <div class="tab-pane fade" id="nav-Thursday2" role="tabpanel" aria-labelledby="nav-Thursday-tab2">
                          <br>
                          <div class="container">
                            <a href="#" class="card-link">03:00 am</a>
                            <a href="#" class="card-link">03:00 am</a>
                            <a href="#" class="card-link">03:00 am</a>
                            <a href="#" class="card-link">03:00 am</a>
                          </div>
                          </div>
                        <div class="tab-pane fade" id="nav-Friday2" role="tabpanel" aria-labelledby="nav-Friday-tab2">
                          <br>
                          <div class="container">
                            <a href="#" class="card-link">04:00 am</a>
                            <a href="#" class="card-link">04:00 am</a>
                            <a href="#" class="card-link">04:00 am</a>
                            <a href="#" class="card-link">04:00 am</a>
                          </div>
                          </div>
                        <div class="tab-pane fade" id="nav-Satuarday2" role="tabpanel" aria-labelledby="nav-Satuarday-tab2">
                          <br>
                          <div class="container">
                            <a href="#" class="card-link">05:00 am</a>
                            <a href="#" class="card-link">05:00 am</a>
                            <a href="#" class="card-link">05:00 am</a>
                            <a href="#" class="card-link">05:00 am</a>
                          </div>
                          </div>
                        <div class="tab-pane fade" id="nav-Sunday2" role="tabpanel" aria-labelledby="nav-Sunday-tab2">
                          <br>
                          <div class="container">
                            <a href="#" class="card-link">06:00 am</a>
                            <a href="#" class="card-link">06:00 am</a>
                            <a href="#" class="card-link">06:00 am</a>
                            <a href="#" class="card-link">06:00 am</a>
                          </div>
                          </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
        </div>
        <br>
        <div class="card">
          <div class="card-body">
            Some Footer
          </div>
        </div>

    <table border="1">
      <tr>
        <td>ID</td>
        <td>Name</td>
        <td>Age</td>
   </tr>
    <%
            con.close();
        } catch(SQLException e) {
            out.println("SQLException caught: " + e.getMessage());
        }
    %>
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
            integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
            integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
            crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
            integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
            crossorigin="anonymous"></script>
  </body>
</html>
