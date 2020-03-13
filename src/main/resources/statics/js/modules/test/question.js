$(function () {
    $("#div1").hide();
    $("#jqGrid").jqGrid({
        url: baseURL + 'test/question/list',
        datatype: "json",
        colModel: [
            {label: '标题', name: 'title', index: 'title', width: 80},
            {label: '类型', name: 'type', index: 'type', width: 80},
            {label: '内容', name: 'content', index: 'content', width: 80},
            {label: '排序', name: 'orderNum', index: 'order_num', width: 80},
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
            // 改变表头颜色
            $(".ui-jqgrid-labels").css({ "background" : "#ecf0f5" });
            $(".td").css({ "text-overflow" : "ellipsis" });
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

var E = window.wangEditor
var editor = new E('#div1')
// editor.customConfig.uploadImgShowBase64 = true
editor.customConfig.uploadImgServer = '/sys/oss/uploadList'
// 要跟后台参数名一致
editor.customConfig.uploadFileName = 'files';
editor.create()

var vm = new Vue({
    el: '#rrapp',
    data: {
        q: {
            name: null
        },
        showList: true,
        title: null,
        question: {}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.question = {};
			$("#div1").show();
            editor.txt.html("");
        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";
            $("#div1").show();
            vm.getInfo(id)
        },
        saveOrUpdate: function (event) {
            var url = vm.question.id == null ? "test/question/save" : "test/question/update";
            vm.question.content = editor.txt.html();
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.question),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function (index) {
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        del: function (event) {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "test/question/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.code == 0) {
                            alert('操作成功', function (index) {
                                $("#jqGrid").trigger("reloadGrid");
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        getInfo: function (id) {
            $.get(baseURL + "test/question/info/" + id, function (r) {
                vm.question = r.question;
                editor.txt.html(vm.question.content);
            });
        },
        reload: function (event) {
            $("#div1").hide();
            vm.showList = true;
            // 点击查询回到第一页
            var page = 1;
            // var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'name': vm.q.name},
                page: page
            }).trigger("reloadGrid");
        }
    }
});