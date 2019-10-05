function solve_back_index_news(data) {
    var news1 = data[0];
    var news2 = data[1];
    var news3 = data[2];
    var newsStr1 = "<div class='news-item'><div class='col-md-5 w3l_nl'><h6>" + news1.news_time + "</h6><h4>" + news1.news_title + "</h4>"
    + "<p>" + news1.news_summary + "</p><a href='" + news1.news_href + "'>更多</a></div>"
    + "<div class='col-md-7 w3l_nr'><img src='static/images/n1.jpg' class='img-responsive'></div><div class='clearfix'></div></div>";
    var newsStr2 = "<div class='news-item'><div class='col-md-7 w3l_nl2'><img src='static/images/n2.jpg' class='img-responsive'></div>" +
        "<div class='col-md-5 w3l_nl1'><h6>" + news2.news_time + "</h6><h4>" + news2.news_title + "</h4>"
        + "<p>" + news2.news_summary + "</p><a href='" + news2.news_href + "'>更多</a></div></div>";
    var newsStr3 = "<div class='news-item'><div class='col-md-5 w3l_nl2'><h6>" + news3.news_time + "</h6><h4>" + news3.news_title + "</h4>"
        + "<p>" + news3.news_summary + "</p><a href='" + news3.news_href + "'>更多</a></div>"
        + "<div class='col-md-7 w3l_nr2'><img src='static/images/n3.jpg' class='img-responsive'></div><div class='clearfix'></div></div>";

    $("#newsContainer").append(newsStr1);
    $("#newsContainer").append(newsStr2);
    $("#newsContainer").append(newsStr3);
}