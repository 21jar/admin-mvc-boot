$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'test/signUser/list',
        datatype: "json",
        colModel: [
			{ label: '日期', name: 'date', index: 'date', width: 80 },
			{ label: '编号', name: 'no', index: 'no', width: 80 },
			{ label: '部门', name: 'deptName', index: 'dept_name', width: 80 },
			{ label: '姓名', name: 'name', index: 'name', width: 80 },
			{ label: '最初温度', name: 'temperatureStart', index: 'temperature_start', width: 80 },
			{ label: '最终温度', name: 'temperatureEnd', index: 'temperature_end', width: 80 }
		],
		viewrecords: true,
        height: 385,
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
            name: "",
			date: ""
        },
		uploadData: null,
		showList: true,
		showUpload: true,
		title: null,
		signUser: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		download: function (event) {
			top.layer.confirm('确认要导出Excel吗?', {icon: 3, title:'系统提示'}, function(index){
				//do something
				//导出之前备份
				// var url =  $("#searchForm").attr("action");
				// $("#searchForm").attr("action","${url}");
				// $("#searchForm").submit();
				//
				// //导出excel之后还原
				// $("#searchForm").attr("action",url);
				// top.layer.close(index);
				window.location.href=baseURL + "test/signUser/download?name="+vm.q.name+"&date="+vm.q.date;
				top.layer.close(index);
			});
			// $.get(baseURL + "test/signUser/download", {'name': vm.q.name,'date':vm.q.date});
		},
		upload: function (event) {
			top.layer.open({
				type: 1,
				area: [500, 300],
				title:"导入数据",
				content:$("#importBox").html() ,
				btn: ['下载模板','确定', '关闭'],
				btn1: function(index, layero){
					$.get(baseURL + "test/signUser/template/");
				},
				btn2: function(index, layero){
					var uploadFile = top.$("#uploadFile")[0].files[0];
					var formData = new FormData();//这里需要实例化一个FormData来进行文件上传
					formData.append("file",top.$("#uploadFile")[0].files[0]);
					$.ajax({
						type: "POST",
						url: baseURL + "test/signUser/upload",
						contentType: false,
						data: formData,
						processData : false,
						success: function(r){
							if(r.code === 0){
								alert('上传成功', function(index){
									vm.reload();
								});
							}else{
								alert(r.msg);
							}
						}
					});
					top.layer.close(index);
				},

				btn3: function(index){
					top.layer.close(index);
				}
			});
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.signUser = {};
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
			var url = vm.signUser.id == null ? "test/signUser/save" : "test/signUser/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.signUser),
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
				    url: baseURL + "test/signUser/delete",
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
			$.get(baseURL + "test/signUser/info/"+id, function(r){
                vm.signUser = r.signUser;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                postData:{'name': vm.q.name,'date':vm.q.date},
                page:page
            }).trigger("reloadGrid");
		}
	}
});