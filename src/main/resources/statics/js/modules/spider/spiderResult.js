$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'spider/spiderResult/list',
        datatype: "json",
        colModel: [
            {label: '类型', name: 'type', index: 'type', width: 80},
            {label: '标题', name: 'title', index: 'title', width: 160},
            // {label: '商品id', name: 'paramId', index: 'param_id', width: 80},
            {label: '库存', name: 'paramOne', index: 'param_one', width: 40},
            {label: '网址', name: 'paramTwo', index: 'param_two', width: 80},
            {label: '价格', name: 'paramThree', index: 'param_three', width: 50},
            {label: '排序', name: 'orderNum', index: 'order_num', width: 40},
            {label: '备注', name: 'remark', index: 'remark', width: 80}
        ],
        viewrecords: true,
        height: "100%",
        rowNum: 50,
        rowList: [50, 100, 300],
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
            $(".ui-jqgrid-labels").css({"background": "#ecf0f5"});
            // 表格到底，平分
            var height = $(window).height() - 130;
            $("#jqGrid").setGridHeight(height);
            var grid = $("#jqGrid");
            var ids = grid.getDataIDs();
            for (var i = 0; i < ids.length; i++) {
                grid.setRowData(ids[i], false, {height: height / 10});
            }
        }
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        q: {
            title: null
        },
        showList: true,
        title: null,
        spiderResult: {}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
        },
        updateOrder: function (event) {
            var url = "spider/spiderResult/updateOrder";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify({'keyword': vm.q.title}),
                success: function (r) {
                    if (r.code === 0) {
                        alert(r.msg);
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        saveOrUpdate: function (event) {
            var url = vm.spiderResult.id == null ? "spider/spiderResult/save" : "spider/spiderResult/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.spiderResult),
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
        getInfo: function (id) {
            $.get(baseURL + "spider/spiderResult/info/" + id, function (r) {
                vm.spiderResult = r.spiderResult;
            });
        },
        reload: function (event) {
            vm.showList = true;
            // 点击查询回到第一页
            var page = 1;
            // var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'title': vm.q.title},
                page: page
            }).trigger("reloadGrid");
        }
    }
});