
//初始加载table模块
layui.use(['table','layer','jquery'],function () {
    //声明变量table,指定layui的table模块
    var table = layui.table;
    var layer = layui.layer;
    var $ = layui.$;
    //准备将页面上的table渲染成 数据表格
    table.render({
        elem: '#demo'
        //,height: 312
        , url: '/checking/getAllCheckInfo' //它是一个请求地址，用于请求后台数据
        , page: true //开启分页
        , limit: 5 //默认每一页显示5条
        // ,toolbar:"#add"//显示数据表格的工具栏
        , toolbar: true//显示数据表格的工具栏
        , limits: [1, 2, 3, 5, 10, 20, 30, 50] //设置可选择的每页显示的条数据
        , cols: [[ //表头
            {field: 'username', title: '姓名', align: "center", width: "10%", sort: true,}
            , {field: 'chuQin', title: '出勤率（%）', align: "center", width: "10%", sort: true,
                templet:"<div>{{showSkuName(d.chuQin)}}</div>"
            }
            , {field: 'chiDao', title: '迟到次数', align: "center", width: "20%", sort: true,}
            , {field: 'zaoTui', title: '早退次数', align: "center", width: "10%", sort: true,}
            , {field: 'count', title: '旷工次数', align: "center", width: "20%", sort: true,}
            , {field: 'departName', title: '所属部门', align: "center", width: "10%", sort: true,}
            , {field: 'branchName', title: '所属机构', align: "center", width: "20%", sort: true,}
        ]]
    });



    /******************给搜索按钮绑定事件*********************************/
    $("#btn").click(function () {
        var stateTime = $("#stateTime").val();
        var endTime = $("#endTime").val();
        var branchName = $("#branchName").val();
        var departName = $("#departName").val();
        table.reload('demo', {
            where: { //设定异步数据接口的额外参数，任意设
                stateTime:stateTime,
                endTime:endTime,
                branchName:branchName,
                departName:departName
            }
            ,page: {
                curr: 1 //重新从第 1 页开始
            }
        }); //只重
    })

});

/**************编写函数，处理商品标题，只显示：手机名称与型号***************************/

function showSkuName(title) {
    //按空格，将标题切割成数组
    var names = title.substring(0,5);
    return names;
}
