/**
 * 
 */

function getTable(data) {
	// 先删除原先的数据，然后生成新的数据
	$("td").detach();
	$("#pagination li").detach();
	// 首先遍历表格
	var list = data.beanList;
	for (var i = 0; i < list.length; i++) {
		var vr = list[i];
		var tr = "<tr>"
				+ "<td><a href='javascript:void(0)' data-toggle='modal' data-target='#myModal' onclick='loadVrInfo("
				+ "\""
				+ vr.vrName
				+ "\""
				+ ",1"
				+ ")'>"
				+ vr.appCode
				+ "</a></td>"
				+ "<td><a href='javascript:void(0)' data-toggle='modal' data-target='#myModal' onclick='loadVrInfo("
				+ "\""
				+ vr.vrName
				+ "\""
				+ ",2"
				+ ")'>"
				+ vr.grantCode
				+ "</a></td>"
				+ "<td>"
				+ vr.vrType
				+ "</td>"
				+ "<td>"
				+ vr.vrName
				+ "</td>"
				+ "<td>"
				+ vr.appDate
				+ "</td>"
				+ "<td>"
				+ vr.noticeType
				+ "</td>"
				+ "<td>"
				+ vr.owner
				+ "</td><td>"
				+ vr.pubNo + "</td></tr>"
		$("#table").append(tr);
	}
}
// 根据data制作出分页栏-->仅当多条件组合查询时可用
function pagination(data) {
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
	$("#pagination").append(currentPage);
	var firstPage = "<li>"
			+ "<a herf='javascript:void(0)' onclick='loadVrByCriteria(" + 1
			+ ")'>" + "首页</a>";
	// 上一页
	if (data.pc == 1) {
		$("#pagination").append(firstPage);
		var previous = "<li class='disabled'><a href='javascript:void(0)' aria-label='Previous'>"
				+ "<span aria-hidden='true'>&laquo;</span></a></li>";
		$("#pagination").append(previous);
	} else {
		$("#pagination").append(firstPage);
		var previous = "<li><a href='javascript:void(0)' aria-label='Previous' onclick='loadVrByCriteria("
				+ (data.pc - 1)
				+ ")'>"
				+ "<span aria-hidden='true'>&laquo;</span></a></li>";
		$("#pagination").append(previous);
	}
	// 中间页
	for (var i = begin; i <= end; i++) {
		if (i == data.pc) {
			var whichPage = "<li class='active'><a herf='javascript:void(0)'>"
					+ i + "</a>";
			$("#pagination").append(whichPage);
		} else {
			var whichPage = "<li><a herf='javascript:void(0)' onclick='loadVrByCriteria("
					+ i + ")'>" + i + "</a>";
			$("#pagination").append(whichPage);
		}
	}
	// 下一页
	if (data.pc == data.tp) {
		var next = "<li class='disabled'><a href='javascript:void(0)' aria-label='Next'>"
				+ "<span aria-hidden='true'>&raquo;</span></a></li>";
		$("#pagination").append(next);
	} else {
		var next = "<li><a href='javascript:void(0)' aria-label='Next' onclick='loadVrByCriteria("
				+ (data.pc + 1)
				+ ")'>"
				+ "<span aria-hidden='true'>&raquo;</span></a></li>";
		$("#pagination").append(next);
	}
	// 尾页
	var lastPage = "<li>"
			+ "<a herf='javascript:void(0)' onclick='loadVrByCriteria("
			+ data.tp + ")'>" + "尾页</a>";
	$("#pagination").append(lastPage);
}

// 根据data制作出分页栏-->仅当无条件查询时可用
function getPagination(data) {
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
	$("#pagination").append(currentPage);
	var firstPage = "<li>" + "<a herf='javascript:void(0)' onclick='loadAllVr("
			+ 1 + ")'>" + "首页</a>";
	$("#pagination").append(firstPage);
	// 上一页
	if (data.pc == 1) {
		var previous = "<li class='disabled'><a href='javascript:void(0)' aria-label='Previous'>"
				+ "<span aria-hidden='true'>&laquo;</span></a></li>";
		$("#pagination").append(previous);
	} else {
		var previous = "<li><a href='javascript:void(0)' aria-label='Previous' onclick='loadAllVr("
				+ (data.pc - 1)
				+ ")'>"
				+ "<span aria-hidden='true'>&laquo;</span></a></li>";
		$("#pagination").append(previous);
	}
	// 中间页
	for (var i = begin; i <= end; i++) {
		if (i == data.pc) {
			var whichPage = "<li class='active'><a herf='javascript:void(0)'>"
					+ i + "</a>";
			$("#pagination").append(whichPage);
		} else {
			var whichPage = "<li><a herf='javascript:void(0)' onclick='loadAllVr("
					+ i + ")'>" + i + "</a>";
			$("#pagination").append(whichPage);
		}
	}
	// 下一页
	if (data.pc == data.tp) {
		var next = "<li class='disabled'><a href='javascript:void(0)' aria-label='Next'>"
				+ "<span aria-hidden='true'>&raquo;</span></a></li>";
		$("#pagination").append(next);
	} else {
		var next = "<li><a href='javascript:void(0)' aria-label='Next' onclick='loadAllVr("
				+ (data.pc + 1)
				+ ")'>"
				+ "<span aria-hidden='true'>&raquo;</span></a></li>";
		$("#pagination").append(next);
	}
	// 尾页
	var lastPage = "<li>" + "<a herf='javascript:void(0)' onclick='loadAllVr("
			+ data.tp + ")'>" + "尾页</a>";
	$("#pagination").append(lastPage);
}
// 解析申请公告详情的数据
function getAppVrInfo(data) {

	var cropName = data.cropName; // 植物种类
	var breedName = data.breedName; // 暂定名称
	var appNo = data.appNo; // 申请号
	var appDate = data.appDate; // 申请日
	var applicant = data.applicant; // 申请人
	var pubDate = data.pubDate; // 申请公告日

	var cropNameStr = "<div id='rows'><div class='col-md-6 key'>植物种类：</div><div class='col-md-6'>"
			+ cropName + "</div><div style='clear:both'></div></div>";
	var breedNameStr = "<div id='rows'><div class='col-md-6 key'>暂定名称：</div><div class='col-md-6'>"
			+ breedName + "</div><div style='clear:both'></div></div>";
	var appNoStr = "<div id='rows'><div class='col-md-6 key'>申请号：</div><div class='col-md-6'>"
			+ appNo + "</div><div style='clear:both'></div></div>";
	var appDateStr = "<div id='rows'><div class='col-md-6 key'>申请日：</div><div class='col-md-6'>"
			+ appDate + "</div><div style='clear:both'></div></div>";
	var applicantStr = "<div id='rows'><div class='col-md-6 key'>申请人：</div><div class='col-md-6'>"
			+ applicant + "</div><div style='clear:both'></div></div>";
	var pubDateStr = "<div id='rows'><div class='col-md-6 key'>申请公告日：</div><div class='col-md-6'>"
			+ pubDate + "</div><div style='clear:both'></div></div>";
	$(".modal-body").append(cropNameStr);
	$(".modal-body").append(breedNameStr);
	$(".modal-body").append(appNoStr);
	$(".modal-body").append(appDateStr);
	$(".modal-body").append(applicantStr);
	$(".modal-body").append(pubDateStr);
}
// 解析授权公告详情的数据
function getGrantVrInfo(data) {
	var cropName = data.cropName; // 植物种类
	var breedName = data.breedName; // 暂定名称
	var appNo = data.appNo; // 申请号
	var appDate = data.appDate; // 申请日
	var applicant = data.applicant; // 申请人
	var state = data.state; // 审查状态
	var pubDate = data.pubDate; // 申请公告日
	var grantNo = data.grantNo; // 授权号
	var grantDate = data.grantDate; // 授权日
	var pubNo = data.pubNo; // 公告号
	var owner = data.owner; // 品种权人
	var ownerAddress = data.ownerAddress; // 品种权人地址

	var cropNameStr = "<div id='rows'><div class='col-md-6 key'>植物种类：</div><div class='col-md-6'>"
			+ cropName + "</div><div style='clear:both'></div></div>";
	var breedNameStr = "<div id='rows'><div class='col-md-6 key'>暂定名称：</div><div class='col-md-6'>"
			+ breedName + "</div><div style='clear:both'></div></div>";
	var appNoStr = "<div id='rows'><div class='col-md-6 key'>申请号：</div><div class='col-md-6'>"
			+ appNo + "</div><div style='clear:both'></div></div>";
	var appDateStr = "<div id='rows'><div class='col-md-6 key'>申请日：</div><div class='col-md-6'>"
			+ appDate + "</div><div style='clear:both'></div></div>";
	var applicantStr = "<div id='rows'><div class='col-md-6 key'>申请人：</div><div class='col-md-6'>"
			+ applicant + "</div><div style='clear:both'></div></div>";
	var stateStr = "<div id='rows'><div class='col-md-6 key'>审查状态：</div><div class='col-md-6'>"
		+ applicant + "</div><div style='clear:both'></div></div>";
	var pubDateStr = "<div id='rows'><div class='col-md-6 key'>申请公告日：</div><div class='col-md-6'>"
			+ pubDate + "</div><div style='clear:both'></div></div>";
	var grantNoStr = "<div id='rows'><div class='col-md-6 key'>授权号：</div><div class='col-md-6'>"
		+ grantNo + "</div><div style='clear:both'></div></div>";
	
	var grantDateStr = "<div id='rows'><div class='col-md-6 key'>授权日：</div><div class='col-md-6'>"
		+ grantDate + "</div><div style='clear:both'></div></div>";
	var pubNoStr = "<div id='rows'><div class='col-md-6 key'>公告号：</div><div class='col-md-6'>"
		+ pubNo + "</div><div style='clear:both'></div></div>";
	var ownerStr = "<div id='rows'><div class='col-md-6 key'>品种权人：</div><div class='col-md-6'>"
		+ owner + "</div><div style='clear:both'></div></div>";
	var ownerAddressStr = "<div id='rows'><div class='col-md-6 key'>品种权地址：</div><div class='col-md-6'>"
		+ ownerAddress + "</div><div style='clear:both'></div></div>";
	
	$(".modal-body").append(cropNameStr);
	$(".modal-body").append(breedNameStr);
	$(".modal-body").append(appNoStr);
	$(".modal-body").append(appDateStr);
	$(".modal-body").append(applicantStr);
	$(".modal-body").append(stateStr);
	
	$(".modal-body").append(pubDateStr);
	$(".modal-body").append(grantNoStr);
	$(".modal-body").append(grantDateStr);
	$(".modal-body").append(pubNoStr);
	$(".modal-body").append(ownerStr);
	$(".modal-body").append(ownerAddressStr);
}



function loadVrByCriteria(pc) {
	//获取参数
	var noticeType = $("#noticeType").val();
	var vrType = $("#vrType").val();
	var vrName = $("#vrName").val();
	var appCode = $("#appCode").val();
	var appDate = $("#appDate").val();
	var owner = $("#owner").val();
	var grantCode = $("#grantCode").val();
	var pubNo = $("#pubNo").val();
	//生成要发送的参数，键值对形式
	var param = "noticeType=" + noticeType + "&vrType=" + vrType
			+ "&vrName=" + vrName + "&appCode=" + appCode + "&appDate="
			+ appDate + "&owner=" + owner + "&grantCode=" + grantCode
			+ "&pubNo=" + pubNo + "&pc=" + pc;
	$.ajax({
				type : 'post',
				url : '../findVrByCriteria.action',
				async : true,
				cache : false,
				data : param,
				success : function(data) {
					//获取表格
					getTable(data);
					//生成分页栏
					pagination(data);
				}
			});
}

function loadVrInfo(vrname,type) {
	//type=1表示申请公告  type=2表示授权公告
	var notice_type = "";
	if(type==1){
		notice_type = '申请公告';
	}else{
		notice_type = '授权公告';
	}
	$.ajax({
		type : 'post',
		url : '../loadVrInfo.action',
		async : true,
		cache : false,
		data : "vrname=" + vrname + "&type=" + notice_type,
		success : function(data){
			//清空main-body内的数据
			$(".modal-body").empty();
			//生成数据
			if(type==1){
				getAppVrInfo(data);						
			}else{
				getGrantVrInfo(data);
			}
		}
	});
}