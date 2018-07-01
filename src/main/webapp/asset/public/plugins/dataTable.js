if($.fn.dataTable){
	$.extend($.fn.dataTable.defaults, {
		dom:
			"<'row'<'col-sm-12'tr>>" +
			"<'row'<'col-sm-5'i><'col-sm-7'p>>",
		renderer: 'bootstrap'
	} );
	$.extend($.fn.dataTable.defaults, {
		ordering:false,
		searching:true,
		language: {
			"sProcessing": "处理中...",
			"sLengthMenu": "每页 _MENU_ 项记录",
			"sZeroRecords": "没有记录",
			"sInfo": "第 _START_ 至 _END_ 项记录，共 _TOTAL_ 项",
			"sInfoEmpty": "显示第 0 至 0 项记录，共 0 项",
			"sInfoFiltered": "(由 _MAX_ 项记录过滤)",
			"sInfoPostFix": "",
			"sSearch": "搜索:",
			"sUrl": "",
			"sEmptyTable": "表中数据为空",
			"sLoadingRecords": "载入中...",
			"sInfoThousands": ",",
			"oPaginate": {
				"sFirst": "首页",
				"sPrevious": "上页",
				"sNext": "下页",
				"sLast": "末页"
			},
			"oAria": {
				"sSortAscending": ": 以升序排列此列",
				"sSortDescending": ": 以降序排列此列"
			}
		}
	});
}

var dataTable = function(el,url,option){
	$.extend(option, {
		ajax:function (data, callback, settings) {
			if(option.before && typeof option.before == 'function'){
				data = option.before(data);
			}
			$.ajax({
				url : url,
				data : data,
				type : 'post',
				dataType : 'json',
				success : function(result) {
					callback(result.dataTable);
				},
				error : function(msg) {
					alert('加载数据失败','error');
				}
			});
		},
		"serverSide":true
	});
	return $(el).DataTable(option);
}