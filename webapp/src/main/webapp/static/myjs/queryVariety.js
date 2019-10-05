/**
 * 
 */
// 制作出表格
function getTable(data) {
	// 首先遍历表格
	var list = data.beanList;
	for (var i = 0; i < list.length; i++) {
		var variety = list[i];
		var tr = "<tr class='tr'>" + "<td>" + variety.judgementNo + "</td>" + "<td>"
				+ variety.vname + "</td>" + "<td>" + variety.judgementYear
				+ "</td>" + "<td>" + variety.type + "</td>" + "<td>"
				+ variety.judgeId + "</td>" + "<td>" + variety.isTransgenosis
				+ "</td>" + "<td>" + variety.appCompany + "</td></tr>"
		$("#table").append(tr);
	}
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
	var firstPage = "<li>" + "<a herf='javascript:void(0)' onclick='jumpPage("
			+ 1 + ")'>" + "首页</a>";
	$("#pagination").append(firstPage);
	// 上一页
	if (data.pc == 1) {
		var previous = "<li class='disabled'><a href='javascript:void(0)' aria-label='Previous'>"
				+ "<span aria-hidden='true'>&laquo;</span></a></li>";
		$("#pagination").append(previous);
	} else {
		var previous = "<li><a href='javascript:void(0)' aria-label='Previous' onclick='jumpPage("
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
			var whichPage = "<li><a herf='javascript:void(0)' onclick='jumpPage("
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
		var next = "<li><a href='javascript:void(0)' aria-label='Next' onclick='jumpPage("
				+ (data.pc + 1)
				+ ")'>"
				+ "<span aria-hidden='true'>&raquo;</span></a></li>";
		$("#pagination").append(next);
	}
	// 尾页
	var lastPage = "<li>" + "<a herf='javascript:void(0)' onclick='jumpPage("
			+ data.tp + ")'>" + "尾页</a>";
	$("#pagination").append(lastPage);
}

// 根据data制作出分页栏-->仅当多条件查询时可用
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
	var str = "\"" + data.url + "\"";
	var currentPage = "<li class='disabled'>"
			+ "<a href='javascript:void(0)'>第" + data.pc + "页</a></li>"; // 当前页
	$("#pagination").append(currentPage);
	var firstPage = "<li>"
			+ "<a herf='javascript:void(0)' onclick='findVarietyByCriteria("
			+ 1 + ")'>" + "首页</a>";
	$("#pagination").append(firstPage);
	// 上一页
	if (data.pc == 1) {
		var previous = "<li class='disabled'><a href='javascript:void(0)' aria-label='Previous'>"
				+ "<span aria-hidden='true'>&laquo;</span></a></li>";
		$("#pagination").append(previous);
	} else {
		var previous = "<li><a href='javascript:void(0)' aria-label='Previous' onclick='findVarietyByCriteria("
				+ (data.pc - 1)
				+ ")'>"
				+ "<span aria-hidden='true'>&laquo;</span></a></li>";
		$("#pagination").append(previous);
	}

	for (var i = begin; i <= end; i++) {
		if (i == data.pc) {
			var whichPage = "<li class='active'><a herf='javascript:void(0)'>"
					+ i + "</a>";
			$("#pagination").append(whichPage);
		} else {
			var whichPage = "<li><a herf='javascript:void(0)' onclick='findVarietyByCriteria("
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
		var next = "<li><a href='javascript:void(0)' aria-label='Next' onclick='findVarietyByCriteria("
				+ (data.pc + 1)
				+ ")'>"
				+ "<span aria-hidden='true'>&raquo;</span></a></li>";
		$("#pagination").append(next);
	}

	// 尾页
	var lastPage = "<li>"
			+ "<a herf='javascript:void(0)' onclick='findVarietyByCriteria("
			+ data.tp + ")'>" + "尾页</a>";
	$("#pagination").append(lastPage);

}



function jumpPage(pc) {
	var param = 'pc=' + pc;
	$.ajax({
		type : 'post',
		url : '../findAllVariety.action',
		data : param,
		async : true,
		cache : false,
		success : function(data) {
			// 先删除原先的数据，然后生成新的数据
			$(".tr").detach();
			$("#pagination li").detach();
			getTable(data);
			// 表格遍历完成后，生成分页栏
			getPagination(data);
		}
	});
}

function findVarietyByCriteria(pc) {
	// 获取元素的值
	var judgementNo = $("#judgementNo").val();
	var vname = $("#vname").val();
	var judgementYear = $("#judgementYear").val();
	var appCompany = $("#appCompany").val();
	var type = $("#type").val();
	var isTransgenosis = $("#isTransgenosis").val();
	var judgeId = $("#judgeId").val();
	// 一共要提交的参数是
	var param = 'judgementNo=' + judgementNo + '&vname=' + vname
			+ '&judgementYear=' + judgementYear + '&appCompany=' + appCompany
			+ '&type=' + type + '&isTransgenosis=' + isTransgenosis
			+ '&judgeId=' + judgeId + '&pc=' + pc;

	$.ajax({
		type : 'post',
		url : '../findVariety.action',
		data : param,
		async : true,
		cache : false,
		success : function(data) {
			// 先删除原先的数据，然后生成新的数据
			$(".tr").detach();
			$("#pagination li").detach();
			// 生成表格
			getTable(data);
			// 生成导航栏
			pagination(data);
		}
	});
}
