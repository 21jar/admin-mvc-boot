$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/log/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', width: 30, key: true },
			{ label: '用户名', name: 'username', width: 50 }, 			
			{ label: '用户操作', name: 'operation', width: 70 }, 			
			{ label: '请求方法', name: 'method', width: 150 }, 			
			{ label: '请求参数', name: 'params', width: 80 },
            { label: '执行时长(毫秒)', name: 'time', width: 80 },
			{ label: 'IP地址', name: 'ip', width: 70 }, 			
			{ label: '创建时间', name: 'createDate', width: 90 }			
        ],
		viewrecords: true,
        height: "100%",
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: false,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
            // 改变表头颜色
            $(".ui-jqgrid-labels").css({ "background" : "#ecf0f5" });
            // 表格到底部，行高平分
            var height = $(window).height() - 130;
            $("#jqGrid").setGridHeight(height);
            var grid = $("#jqGrid");
            var ids = grid.getDataIDs();
            for (var i = 0; i < ids.length; i++) {
                grid.setRowData(ids[i], false, { height : height/10 });
            }
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			key: null
		},
	},
	methods: {
		query: function () {
			vm.reload();
		},
		reload: function (event) {
            // 点击查询回到第一页
            var page = 1;
            // var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
				postData:{'key': vm.q.key},
                page:page
            }).trigger("reloadGrid");
		}
	}
});