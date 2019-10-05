var getResult;
function stopSpider(spiderType) {
    var startBtn = "<button class='btn btn-success btn-sm' onclick='startSpider(" + spiderType + ")'>运行</button>";
    $(".spider-currentStatus").empty();
    $(".spider-option").empty();
    $(".spider-currentStatus").append("已停止");
    $(".spider-option").append(startBtn);

    stopGettingResult();
    $.ajax({
        type: 'post',
        url: '../../stopSpider.action',
        async: true,
        cache: false,
        success: function (message) {
            alert(message.message_content);
        }
    });
}

function startSpider(spiderType) {
    var stopBtn = "<button class='btn btn-danger btn-sm' onclick='stopSpider(" + spiderType + ")'>停止</button>";
    $(".spider-currentStatus").empty();
    $(".spider-option").empty();
    $(".spider-currentStatus").append("正在运行");
    $(".spider-option").append(stopBtn);

    var parameterStr = getParameter(spiderType);
    $.ajax({
        type: 'post',
        url: '../../startSpider.action',
        async: true,
        cache: false,
        data: parameterStr,
        success: function (message) {

        }
    });
    //显示爬取结果
    showPartSpiderResult(spiderType);
}

function showPartSpiderResult(spiderType) {
    var requestUrl;
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
    getResult = setInterval(function () {
        $.ajax({
            type: 'post',
            url: requestUrl,
            async: true,
            cache: false,
            data : "pc=1&state=1" ,
            success: function (result) {
                //根据不同的爬虫类型解析不同的数据
                if(spiderType == 1){
                    if(result.beanList.length == 0)
                        expertVue.flag = '1';
                    else{
                        expertVue.flag = '2';
                        expertVue.beanList = result.beanList;
                    }
                }else if(spiderType == 2){
                    //解析公告
                    if(result.beanList.length == 0)
                        noticeVue.flag = '1';
                    else{
                        noticeVue.flag = '2';
                        noticeVue.beanList = result.beanList;
                    }
                }else if(spiderType == 3){
                    //解析论文
                    if(result.beanList.length == 0)
                        articleVue.flag = '1';
                    else{
                        articleVue.flag = '2';
                        articleVue.beanList = result.beanList;
                    }
                }else if(spiderType == 4){
                    //解析品种权详情
                    if(result.beanList.length == 0)
                        noticeDetailVue.flag = '1';
                    else{
                        noticeDetailVue.flag = '2';
                        noticeDetailVue.beanList = result.beanList;
                    }

                }else if(spiderType == 5){
                    //解析新闻
                    if(result.beanList.length == 0)
                        newsVue.flag = '1';
                    else{
                        newsVue.flag = '2';
                        newsVue.beanList = result.beanList;
                    }
                }else if(spiderType == 6){
                    //解析专利
                    if(result.beanList.length == 0)
                        patentVue.flag = '1';
                    else{
                        patentVue.flag = '2';
                        patentVue.beanList = result.beanList;
                    }
                }else if(spiderType == "7"){
                    if(result.beanList.length == 0)
                        varietyVue.flag = '1';
                    else{
                        varietyVue.flag = '2';
                        varietyVue.beanList = result.beanList;
                    }
                }else{
                    if(result.beanList.length == 0)
                        vrVue.flag = '1';
                    else{
                        vrVue.flag = '2';
                        vrVue.beanList = result.beanList;
                    }
                }
            }
        });
    },2000);

}

//根据爬虫的类型来获取参数，返回一个字符串
function getParameter(spiderType) {
    //获取定时时间和线程数量
    var spiderRunningTime = $("#spider-time option:selected").val();
    var threadNum = $("#threadNum").val();
    //默认线程数量为1
    if (threadNum == "") threadNum = "1";
    var str = "spiderRunningTime=" + spiderRunningTime + "&threadNum=" + threadNum + "&spiderType=" + spiderType;
    //判断爬虫类型
    //说明是百度学术爬虫
    if (spiderType == "1") {
        //获取复选框的值
        var accuracyVal = $("#spider-accuracy option:selected").val();
        str = str + "&accuracy=" + accuracyVal;
        if (accuracyVal == "0") {
            return str;
        }
        //获取参数专家信息和工作地点
        var expertName = $("#expertName").val();
        var workplace = $("#workplace").val();
        return str + "&expertName=" + expertName + "&workplace=" + workplace;
    }
    else if (spiderType == "2") {
        var noticeType = $("#noticeType option:selected").val();
        return str + "&noticeType=" + noticeType;
    }
    else if (spiderType == "3") {
        //获取复选框的值
        var accuracyVal = $("#spider-accuracy option:selected").val();
        str = str + "&accuracy=" + accuracyVal;
        if (accuracyVal == "0") {
            return str;
        }else if(accuracyVal == "1"){
            //获取专家姓名，所在单位
            var expertName = $("#expertName").val();
            var workplace = $("#workplace").val();
            return str + "&expertName=" + expertName + "&workplace=" + workplace;
        }else{
            //获取论文名称，发表年份
            var articleName = $("#articleName").val();
            var publishdate_from = $("#publishdate_from").val();
            var publishdate_to = $("#publishdate_to").val();
            return str + "&articleName=" + articleName + "&publishdate_from=" + publishdate_from + "&publishdate_to=" + publishdate_to;
        }
    }
    else if (spiderType == "4") {
        //获取复选框的值
        var accuracyVal = $("#spider-accuracy option:selected").val();
        //获取公告类型
        var noticeType = $("#noticeType option:selected").val();
        str = str + "&accuracy=" + accuracyVal + "&noticeType=" + noticeType;
        if (accuracyVal == "0") {
            return str;
        }
        //获取作物名称
        var cropName = $("#cropName").val();
        return str + "&cropName=" + cropName;
    }
    else if (spiderType == "5") {
        //获取新闻关键词
        var newsWord = $("#newsWord").val();
        return str + "&newsWord=" + newsWord;
    }
    else if (spiderType == "6") {
        //获取复选框的值
        var accuracyVal = $("#spider-accuracy option:selected").val();
        str = str + "&accuracy=" + accuracyVal;
        if (accuracyVal == "0") {
            return str;
        }
        //获取爬取条件
        var zlmc = $("#zlmc").val();
        var fmr = $("#fmr").val();
        var sqr = $("#sqr").val();
        return str + "&zlmc=" + zlmc + "&fmr=" + fmr + "&sqr=" + sqr;
    }
    else if (spiderType == "7") {
        //获取复选框的值
        var accuracyVal = $("#spider-accuracy option:selected").val();
        str = str + "&accuracy=" + accuracyVal;
        if (accuracyVal == "0") {
            return str;
        }
        //获取爬取条件
        var JudgementNo = $("#JudgementNo").val();
        var VarietyNameLike = $("#VarietyNameLike").val();
        var JudgementYear = $("#JudgementYear").val();
        var ApplyCompanyLike = $("#ApplyCompanyLike").val();
        var CropID = $("#CropID option:selected").val();
        var isTransgenosis = $("#isTransgenosis option:selected").val();
        var JudgementRegionID = $("#JudgementRegionID").val();
        return str + "&JudgementNo=" + JudgementNo + "&VarietyNameLike=" + VarietyNameLike + "&JudgementYear=" + JudgementYear + "&ApplyCompanyLike=" + ApplyCompanyLike + "&CropID=" + CropID + "&TransgenosisFlag=" + isTransgenosis
            + "&JudgementRegionID" + JudgementRegionID;
    } else {
        //获取复选框的值
        var accuracyVal = $("#spider-accuracy option:selected").val();
        str = str + "&accuracy=" + accuracyVal;
        if(accuracyVal == "0"){
            return str;
        }
        //获取爬取条件
        var type = $("#type option:selected").val();
        var appCode = $("#appCode").val();
        var applier = $("#applier").val();
        var name = $("#name").val();
        var appaddress = $("#appaddress").val();
        return str + "&type=" + type + "&appCode=" + appCode + "&applier=" + applier + "&name=" + name + "&appaddress=" +appaddress;
    }
}
//结束获取爬取信息
function stopGettingResult() {
    clearInterval(getResult);
}
