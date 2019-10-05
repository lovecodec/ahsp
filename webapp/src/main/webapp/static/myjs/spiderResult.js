/**
 * 管理爬取结果或者待审核结果
 */
function checkedCount() {
    //遍历检验有多少checkbox被选中
    var count = 0;
    for (var i = 0; i <= 6; i++) {
        var checkboxId = "checkbox" + i;
        var checkbox = document.getElementById(checkboxId);
        if (checkbox != null && checkbox.checked) {
            count++;
        }
    }
    if (count == 0) {
        $.alert({
            title: '提示!',
            content: '请选择要操作的元素!',
        });
        return false;
    } else if (count == 1) {
        return true;
    } else {
        $.alert({
            title: '提示!',
            content: '对不起，一次只能对一个元素进行操作!',
        });
        return false;
    }
}

//只能对单个元素进行操作时，返回的index
function getSingleCheckboxIndex() {
    var index = 0;
    for (var i = 0; i <= 6; i++) {
        var checkboxId = "checkbox" + i;
        var checkbox = document.getElementById(checkboxId);
        if (checkbox != null && checkbox.checked) {
            index = i;
        }
    }
    return index;
}

//对多个元素进行操作时，返回的index值，返回值是数组
function getCheckboxIndex() {
    var indexArr = new Array();

    var count = 0;
    for (var i = 0; i <= 6; i++) {
        var checkboxId = "checkbox" + i;
        var checkbox = document.getElementById(checkboxId);
        if (checkbox != null && checkbox.checked) {
            indexArr[count++] = i;
        }
    }
    return indexArr;
}


function getResultByState(spiderType, pc, state) {
    var requestUrl = "";
    switch (spiderType) {
        case 1 :
            requestUrl = "../../getExpertByCrawler.action";
            break;
        case 2 :
            requestUrl = "../../getNoticeByCrawler.action";
            break;
        case 3 :
            requestUrl = "../../getArticleByCrawler.action";
            break;
        case 4:
            requestUrl = "../../getNoticeDetailByCrawler.action";
            break;
        case 5:
            requestUrl = "../../getNewsByCrawler.action";
            break;
        case 6:
            requestUrl = "../../getPatentByCrawler.action";
            break;
        case 7:
            requestUrl = "../../getVarietyByCrawler.action";
            break;
        case 8:
            requestUrl = "../../getVrByCrawler.action";
            break;
    }
    commonVue.state = state;
    commonVue.spiderType = spiderType;
    $.ajax({
        type: 'post',
        url: requestUrl,
        cache: false,
        async: true,
        data: "state=" + state + "&pc=" + pc,
        success: function (result) {
            //解析数据
            commonVue.tp = result.tp;
            commonVue.tr = result.tr;
            commonVue.ps = result.ps;
            commonVue.pc = result.pc;
            commonVue.beanList = result.beanList;
        }
    });
}


//通用方法，删除所有数据，传入接口即可
function deleteAllData(interfaceName) {
    $.confirm({
        title: '提示',
        content: '确认删除？',
        buttons: {
            确认: function () {
                //发送ajax请求，删除数据
                $.ajax({
                    type: 'post',
                    url: interfaceName,
                    data: 'state=' + commonVue.state,
                    cache: false,
                    async: true,
                    success: function (message) {
                        $.alert({
                            title : message.message_type,
                            content : message.message_content,
                        });
                        //刷新页面
                        getResultByState(commonVue.spiderType, commonVue.pc, commonVue.state);
                    }
                });
            },
            取消: function () {
            }
        }
    });
}

//通用方法，添加所有数据，即将缓存中的数据存放在数据库中
//参数为接口名称
function addAllData(interfaceName) {
    $.ajax({
        type: 'post',
        url: interfaceName,
        cache: false,
        async: true,
        data: 'state=' + commonVue.state,
        success: function (message) {
            //输出消息
            $.alert({
                title : message.message_type,
                content : message.message_content
            });
            //刷新页面
            getResultByState(commonVue.spiderType, commonVue.pc, commonVue.state);
        }
    });
}

//通用方法，更新数据，传入接口名称和参数名称
function updateData(interfaceName, parameterStr) {
    //缓存中的数据不允许更新
    if (commonVue.state != 3) {
        $.alert("缓存中的数据不需要更新");
        return;
    }
    $.ajax({
        type: 'post',
        url: interfaceName,
        async: true,
        cache: false,
        data: parameterStr,
        success: function (message) {
            $.alert({
                title : message.message_type,
                content : message.message_content
            });
            //关闭模态框
            $('#myModal1').modal('toggle');
            //刷新页面
            getResultByState(commonVue.spiderType, commonVue.pc, commonVue.state);
        }
    });
}

//通用方法，添加单个数据到数据库中
function addSingleData(interfaceName, parameterStr) {
    $.ajax({
        type: 'post',
        url: interfaceName,
        cache: false,
        async: true,
        data: parameterStr,
        success: function (message) {
            $.alert({
                title : message.message_type,
                content : message.message_content
            });
            //关闭模态框
            $('#myModal').modal('toggle');
        }
    });
}

//通用方法，从数据库删除多个数据
//id的css样式，例如专家id为#eid,论文id为#aid
function deleteData(idcss, interfaceName) {
    if (commonVue.state != 3) {
        $.alert("不能选择删除缓存中的数据，请使用全部删除！");
        return;
    }
    var indexArr = getCheckboxIndex();
    var idString = "";
    if (indexArr.length > 0) {
        $.confirm({
            title: '提示',
            content: '确认删除？',
            buttons: {
                确认: function () {
                    //解析出eid
                    for (var i = 0; i < indexArr.length; i++) {
                        var index = indexArr[i];
                        idString += $("" + idcss + index).text() + ",";
                    }
                    //发送ajax请求，删除数据
                    $.ajax({
                        type: 'post',
                        url: interfaceName,
                        data: 'idString=' + idString,
                        cache: false,
                        async: true,
                        success: function (message) {
                            $.alert({
                               title : message.message_type,
                               content : message.message_content
                            });
                            //刷新页面
                            getResultByState(commonVue.spiderType, commonVue.pc, commonVue.state);
                        }
                    });
                },
                取消: function () {
                }
            }
        });
    } else {
        $.alert("你还没选中数据！");
    }
}