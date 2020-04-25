
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
        , url: '/checkWorkAttendance/getAllInfo' //它是一个请求地址，用于请求后台数据
        , page: true //开启分页
        , limit: 5 //默认每一页显示5条
        // ,toolbar:"#add"//显示数据表格的工具栏
        , toolbar: true//显示数据表格的工具栏
        , limits: [1, 2, 3, 5, 10, 20, 30, 50] //设置可选择的每页显示的条数据
        , cols: [[ //表头
             {field: 'username', title: '签到员工', align: "center", width: "10%", sort: true,}
            , {field: 'nickname', title: '签到员工姓名', align: "center", width: "10%", sort: true,}
            , {field: 'onDutyTime', title: '签到时间', align: "center", width: "20%", sort: true,
                templet: "<div>{{layui.util.toDateString(d.onDutyTime,'yyyy年MM月dd日 HH:mm:ss')}}</div>"
            }
            , {field: 'signDesc', title: '签到备注', align: "center", width: "10%", sort: true,}
            , {field: 'offDutyTime', title: '签退时间', align: "center", width: "20%", sort: true,
                templet: "<div>{{layui.util.toDateString(d.offDutyTime,'yyyy年MM月dd日 HH:mm:ss')}}</div>"
            }
            , {field: 'offDesc', title: '签退标记', align: "center", width: "10%", sort: true,}
            , {field: 'departName', title: '所属部门', align: "center", width: "10%", sort: true,}
            , {field: 'branchName', title: '所属机构', align: "center", width: "10%", sort: true,}
        ]]
    });



    /******************给搜索按钮绑定事件*********************************/
    $("#btn").click(function () {
        var stateTime = $("#stateTime").val();
        var endTime = $("#endTime").val();
        var branchName = $("#branchName").val();
        var departName = $("#departName").val();
        var name = $("[name=name]").val();
        table.reload('demo', {
            where: { //设定异步数据接口的额外参数，任意设
                stateTime:stateTime,
                endTime:endTime,
                branchName:branchName,
                departName:departName,
                name:name
            }
            ,page: {
                curr: 1 //重新从第 1 页开始
            }
        }); //只重
    })

});


