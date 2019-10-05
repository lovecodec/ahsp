/**
 *
 */
function transArticle() {
    $(".articleInfo").show();
    $(".patentInfo").hide();
}

function transPatent() {
    $(".articleInfo").hide();
    $(".patentInfo").show();
}

// 解析文章基本信息
function solveArticle(data) {

    var list = data.beanList;

    for (var i = 0; i < list.length; i++) {
        var article = list[i];
        var str = "<div class='article-item'><p class='article-item-title'>"
            + "<a href='"
            + article.url
            + "'>"
            + article.title
            + "</a></p>"
            + "<p class='article-item-info'>"
            + article.info + "</p></div>";
        $(".articleInfo-content").append(str);
    }

}

// 解析专家基本信息
function solveExpertInfo(data) {
    var name = data.name;
    var workplace = data.workplace;
    var articleCount = data.articleCount;
    var patentCount = data.patentCount;
    var domain = data.domain;
    /* 生成论文和专利数量 */
    var str = "<span class='value'>" + articleCount
        + "</span><span class='value'>" + patentCount + "</span>";
    $(".count").append(str);
    /* 姓名 */
    var nameStr = "<h2>" + name + "</h2>";
    $(".expertName").append(nameStr);
    var workplaceStr = "<p>" + workplace + "</p>";
    $(".workplace").append(workplaceStr);
    var domainStr = "<p><span style='color:#999'>领域：</span>" + domain + "</p>"
    $(".domain").append(domainStr);

}

// 解析专利基本信息
function solvePatent(data) {
    var list = data.beanList;

    for (var i = 0; i < list.length; i++) {
        var patent = list[i];
        var str = "<div class='patent-item'>"
            + "<p class='patent-item-title'><a href='#' title='" + patent.zlmc + "'>" + patent.zlmc + "</a></p>"
            + "<div class='col-md-2'><img src='../static/images/getimg.jpg'/></div>"
            + "<div class='col-md-2 detail'>申请号：" + patent.sqh
            + "</div><div class='col-md-2 detail'>申请日：" + patent.sqr
            + "</div><div class='col-md-2 detail'>公开号：" + patent.gkh
            + "</div><div class='col-md-2 detail'>公开日：" + patent.gkr
            + "</div><div class='col-md-2 detail'>公告号：" + patent.sqggh
            + "</div><div class='col-md-2 detail'>公告日：" + patent.sqggr
            + "</div><div class='col-md-2 detail'>主分类：" + patent.zflh
            + "</div><div class='col-md-2 detail'>申请人：" + patent.sqren
            + "</div><div class='col-md-2 detail'>当前权力人：" + patent.dqqlr
            + "</div><div class='col-md-2 detail'>发明人：" + patent.fmr
            + "</div><div class='col-md-10 detail'>摘要：" + patent.zy
            + "</div><div style='clear:both'></div></div>";
        $(".patentInfo-content").append(str);

    }
}

// 根据data制作出分页栏-->仅当无条件时可用
function getPagination(data, methodName,paginationName) {
    /* 1.计算出begin和and */
    if (data.tp <= 8) {
        begin = 1;
        end = data.tp;
    } else {
        begin = data.pc - 4;
        end = data.pc + 3;
        if (begin < 1) {
            begin = 1;
            end = 8;
        }
        if (end > data.tp) {
            begin = data.tp - 7;
            end = data.tp;
        }
    }
    // 开始制作分页栏
    var currentPage = "<li class='disabled'>"
        + "<a href='javascript:void(0)'>第" + data.pc + "页</a></li>"; // 当前页
    $(paginationName).append(currentPage);
    var firstPage = "<li>"
        + "<a herf='javascript:void(0)' onclick='" + methodName + "(" + 1
        + ")'>" + "首页</a>";
    // 上一页
    if (data.pc == 1) {
        $(paginationName).append(firstPage);
        var previous = "<li class='disabled'><a href='javascript:void(0)' aria-label='Previous'>"
            + "<span aria-hidden='true'>&laquo;</span></a></li>";
        $(paginationName).append(previous);
    } else {
        $(paginationName).append(firstPage);
        var previous = "<li><a href='javascript:void(0)' aria-label='Previous' onclick='" + methodName + "("
            + (data.pc - 1)
            + ")'>"
            + "<span aria-hidden='true'>&laquo;</span></a></li>";
        $(paginationName).append(previous);
    }
    // 中间页
    for (var i = begin; i <= end; i++) {
        if (i == data.pc) {
            var whichPage = "<li class='active'><a herf='javascript:void(0)'>"
                + i + "</a>";
            $(paginationName).append(whichPage);
        } else {
            var whichPage = "<li><a herf='javascript:void(0)' onclick='" + methodName + "("
                + i + ")'>" + i + "</a>";
            $(paginationName).append(whichPage);
        }
    }
    // 下一页
    if (data.pc == data.tp) {
        var next = "<li class='disabled'><a href='javascript:void(0)' aria-label='Next'>"
            + "<span aria-hidden='true'>&raquo;</span></a></li>";
        $(paginationName).append(next);
    } else {
        var next = "<li><a href='javascript:void(0)' aria-label='Next' onclick='" + methodName + "("
            + (data.pc + 1)
            + ")'>"
            + "<span aria-hidden='true'>&raquo;</span></a></li>";
        $(paginationName).append(next);
    }
    // 尾页
    var lastPage = "<li>"
        + "<a herf='javascript:void(0)' onclick='" + methodName + "(" + data.tp
        + ")'>" + "尾页</a>";
    $(paginationName).append(lastPage);
}