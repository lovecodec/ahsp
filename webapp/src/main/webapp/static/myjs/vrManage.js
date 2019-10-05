// function checkedCount() {
//     //遍历检验有多少checkbox被选中
//     var count = 0;
//     for (var i = 0; i <= 6; i++) {
//         var checkboxId = "checkbox" + i;
//         var checkbox = document.getElementById(checkboxId);
//         if (checkbox.checked) {
//             count++;
//         }
//     }
//     if (count == 0) {
//         $.alert({
//             title: '提示!',
//             content: '请选择要操作的元素!',
//         });
//         return false;
//     } else if (count == 1) {
//         return true;
//     } else {
//         $.alert({
//             title: '提示!',
//             content: '对不起，一次只能对一个元素进行操作!',
//         });
//         return false;
//     }
// }
function deleteVr(){
    $.confirm({
        title: '提示!',
        content: '确认删除吗？',
        buttons : {
            确认 : function(){
                if(checkedCount()){
                    var index = 0;
                    for (var i = 0; i <= 6; i++) {
                        var checkboxId = "checkbox" + i;
                        var checkbox = document.getElementById(checkboxId);
                        if (checkbox.checked) {
                            index = i;
                        }
                    }
                    //获取对应元素的appCode和grantCode
                    var appCode = $(".appCode" + index).text();
                    var grantCode = $(".grantCode" + index).text();
                    $.ajax({
                       type : 'post',
                       url : '../../deleteVr.action',
                       async : 'true',
                       cache : 'false',
                       data : 'appCode=' + appCode + "&grantCode=" + grantCode,
                       success : function(message){
                           $.alert(message.message_content);
                           window.location.reload();
                       }
                    });
                }
            },
            取消 : function () {}
        }
    })
}

function addVr(){
    //获取各元素的值
    var appCode = $("#app_code").val();
    var grantCode = $("#grant_code").val();
    var vrType = $("#vr_type").val();
    var vrName = $("#vr_name").val();
    var appDate = $("#app_date").val();
    var noticeType = $("#notice_type").val();
    var owner = $("#vr_owner").val();
    var pubNo = $("#pub_no").val();
    $.ajax({
        type : 'post',
        url : '../../addVr.action',
        cache : false,
        async : true,
        data : 'appCode=' + appCode + "&grantCode=" + grantCode + "&vrType=" + vrType + "&vrName=" + vrName
        + "&appDate=" + appDate + "&noticeType=" + noticeType + "&owner=" + owner + "&pubNo=" + pubNo,
        success : function (message) {
            $.alert(message.message_content);
            window.location.reload();
        }
    });
}

//多条件组合查询vr
function findVrByCriteria(pc) {
    //获取各个元素的值
    var appCode = $("#appCode").val();
    var grantCode = $("#grantCode").val();
    var vrType = $("#vrType").val();
    var vrName = $("#vrName").val();
    var appDate = $("#appDate").val();
    var noticeType = $("#noticeType").val();
    var owner = $("#owner").val();
    var pubNo = $("#pubNo").val();
    $.ajax({
       type : 'post',
       url : '../../findVrByCriteria.action',
       cache : 'false',
       async : true,
       data :  'appCode=' + appCode + "&grantCode=" + grantCode + "&vrType=" + vrType + "&vrName=" + vrName
       + "&appDate=" + appDate + "&noticeType=" + noticeType + "&owner=" + owner + "&pubNo=" + pubNo + "&pc=" + pc,
       success : function (result) {
           //删除多余的元素
           $("#pagination").empty();
           //获取表格
           vr.beanList = result.beanList;
           //生成分页框
           commonPagination(result,'findVrByCriteria','#pagination');
       }
    });
}