
/**
 *
 */
var idTmr;
//获取当前浏览器类型
function getExplorer() {
    var explorer = window.navigator.userAgent;
    //ie
    if (explorer.indexOf("MSIE") >= 0) {
        return 'ie';
    }
    //firefox
    else if (explorer.indexOf("Firefox") >= 0) {
        return 'Firefox';
    }
    //Chrome
    else if (explorer.indexOf("Chrome") >= 0) {
        return 'Chrome';
    }
    //Opera
    else if (explorer.indexOf("Opera") >= 0) {
        return 'Opera';
    }
    //Safari
    else if (explorer.indexOf("Safari") >= 0) {
        return 'Safari';
    }
}

//获取到类型需要判断当前浏览器需要调用的方法，目前项目中火狐，谷歌，360没有问题
//win10自带的IE无法导出
function exportExcel(tableid) {
    if (getExplorer() == 'ie') {
        var curTbl = document.getElementById(tableid);
        var oXL = new ActiveXObject("Excel.Application");
        var oWB = oXL.Workbooks.Add();
        var xlsheet = oWB.Worksheets(1);
        var sel = document.body.createTextRange();
        sel.moveToElementText(curTbl);
        sel.select();
        sel.execCommand("Copy");
        xlsheet.Paste();
        oXL.Visible = true;

        try {
            var fname = oXL.Application.GetSaveAsFilename("Excel.xls", "Excel Spreadsheets (*.xls), *.xls");
        } catch (e) {
            print("Nested catch caught " + e);
        } finally {
            oWB.SaveAs(fname);
            oWB.Close(savechanges = false);
            oXL.Quit();
            oXL = null;
            idTmr = window.setInterval("Cleanup();", 1);
        }

    }
    else {
        tableToExcel(tableid)
    }
}

function Cleanup() {
    window.clearInterval(idTmr);
    CollectGarbage();
}

//判断浏览器后调用的方法，把table的id传入即可
var tableToExcel = (function () {
    var uri = 'data:application/vnd.ms-excel;base64,',
        template = '<html><head><meta charset="UTF-8"></head><body><table>{table}</table></body></html>',
        base64 = function (s) {
            return window.btoa(unescape(encodeURIComponent(s)))
        },
        format = function (s, c) {
            return s.replace(/{(\w+)}/g,
                function (m, p) {
                    return c[p];
                })
        }
    return function (table, name) {
        if (!table.nodeType) table = document.getElementById(table)
        var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML}
        window.location.href = uri + base64(format(template, ctx))
    }
})()

// 根据data制作出分页栏-->通用
function commonPagination(data, methodName, paginationName) {
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