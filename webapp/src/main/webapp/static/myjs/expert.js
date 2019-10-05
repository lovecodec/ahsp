/**
 * 
 */
//根据data制作出分页栏-->仅当无条件查询时可用
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
	var firstPage = "<li>" + "<a herf='javascript:void(0)' onclick='loadAllExpert("
			+ 1 + ")'>" + "首页</a>";
	$("#pagination").append(firstPage);
	// 上一页
	if (data.pc == 1) {
		var previous = "<li class='disabled'><a href='javascript:void(0)' aria-label='Previous'>"
				+ "<span aria-hidden='true'>&laquo;</span></a></li>";
		$("#pagination").append(previous);
	} else {
		var previous = "<li><a href='javascript:void(0)' aria-label='Previous' onclick='loadAllExpert("
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
			var whichPage = "<li><a herf='javascript:void(0)' onclick='loadAllExpert("
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
		var next = "<li><a href='javascript:void(0)' aria-label='Next' onclick='loadAllExpert("
				+ (data.pc + 1)
				+ ")'>"
				+ "<span aria-hidden='true'>&raquo;</span></a></li>";
		$("#pagination").append(next);
	}
	// 尾页
	var lastPage = "<li>" + "<a herf='javascript:void(0)' onclick='loadAllExpert("
			+ data.tp + ")'>" + "尾页</a>";
	$("#pagination").append(lastPage);
}

