$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'work/shareCheck/list',
        datatype: "json",
        colModel: [			
			{ label: 'um', name: 'um', index: 'um', width: 80 },
			{ label: '计算机名', name: 'itName', index: 'it_name', width: 80 },
			{ label: '路径', name: 'fileUrl', index: 'file_url', width: 80 },
			{ label: '类型', name: 'type', index: 'type', width: 80 },
			{ label: '部门', name: 'deptName', index: 'dept_name', width: 80 },
			{ label: '是否存在', name: 'exist', index: 'exist', width: 80 },
			{ label: '是否删除', name: 'clear', index: 'clear', width: 80 },
			{ label: '保留原因', name: 'reason', index: 'reason', width: 80 },
			{ label: '备注', name: 'remark', index: 'remark', width: 80 }
		],
		viewrecords: true,
        height: "100%",
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
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
			// 表格到底，平分
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
            um: null
        },
		showList: true,
		title: null,
		shareCheck: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.shareCheck = {};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			var url = vm.shareCheck.id == null ? "work/shareCheck/save" : "work/shareCheck/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.shareCheck),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "work/shareCheck/delete",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(id){
			$.get(baseURL + "work/shareCheck/info/"+id, function(r){
                vm.shareCheck = r.shareCheck;
            });
		},
		reload: function (event) {
			vm.showList = true;
			// 点击查询回到第一页
			var page = 1;
			// var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                postData:{'um': vm.q.um},
                page:page
            }).trigger("reloadGrid");
		}
	}
});