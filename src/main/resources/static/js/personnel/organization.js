//机构

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
        , url: '/PersonnelManagement/getAllOrg' //它是一个请求地址，用于请求后台数据
        , page: true //开启分页
        , limit: 5 //默认每一页显示5条
        // ,toolbar:"#add"//显示数据表格的工具栏
        , toolbar: true//显示数据表格的工具栏
        , limits: [1, 2, 3, 5, 10, 20, 30, 50] //设置可选择的每页显示的条数据
        , cols: [[ //表头
            {field: 'branchId', title: '机构编号', align: "center", width: "20%", sort: true, fixed: 'left'}
            , {field: 'branchName', title: '机构名称', align: "center", width: "25%", sort: true,}
            , {field: 'branchShortName', title: '机构简称', align: "center", width: "25%", sort: true,}
            , {field: 'op', title: '操作', align: "center", width: "30%", sort: true, toolbar: "#barDemo"}
        ]]
    });


    /********************工具条监听事件*****************************************************/
    //监听工具条
    table.on('tool(test)', function(obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）
        if (layEvent === 'edit') { //编辑
            layer.open({
                type: 2,
                title:"修改机构",
                shade: true,//以模态窗口显示
                shade:0.5,//设置透明度
                area: ['400px','400px'],
                anim: 1,//进入的动画效果
                maxmin: false,//不使用最大化，最小化按钮
                content: '/forward/PersonnelManagement/updateOrg',
                zIndex: layer.zIndex, //重点1
                success: function(layero){
                    layer.setTop(layero); //重点2  置顶在上面，
                    //获得弹出层datail.jsp的body部份
                    var body = layui.layer.getChildFrame("body");
                    body.find("[name='branchId']").val(data.branchId);
                    body.find("[name='branchName']").val(data.branchName);
                    body.find("[name='branchShortName']").val(data.branchShortName);

                }
            })
        }
        else if (layEvent === 'del') { //删除
            layer.confirm('真的删除行么', function (index) {
                layer.close(index);
                $.ajax({
                    url:"/PersonnelManagement/delete",
                    type:"post",
                    data:{branchId:data.branchId},
                    success:function () {
                        table.reload('demo', {
                            where: { //设定异步数据接口的额外参数，任意设

                            }
                            ,page: {
                                curr: 1 //重新从第 1 页开始
                            }
                        }); //只重
                    }
                })
            })
        }
        else if (layEvent === 'add') {//添加
            layer.open({
                type: 2,
                title:"添加机构",
                shade: true,//以模态窗口显示
                shade:0.5,//设置透明度
                area: ['400px','300px'],
                anim: 1,//进入的动画效果
                maxmin: false,//不使用最大化，最小化按钮
                content: '/forward/PersonnelManagement/addOrg',
                zIndex: layer.zIndex, //重点1
                success: function(layero){
                    var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象
                    iframeWin.layui.form.render();
                    layer.setTop(layero); //重点2  置顶在上面，
                    //获得弹出层datail.jsp的body部份
                    var body = layui.layer.getChildFrame("body");
                    body.find("[name='branchName']").val("");
                    body.find("[name='branchShortName']").val("");
                }
            })
        }
    });

    /******************给搜索按钮绑定事件*********************************/
    $("#search").click(function () {
        var orgName=$("[name='orgName' ]").val();//机构名称
        var orgAbbreviation=$("[name='orgAbbreviation' ]").val();//机构简称
        table.reload('demo', {
            where: { //设定异步数据接口的额外参数，任意设
                orgName: orgName
                ,orgAbbreviation: orgAbbreviation
            }
            ,page: {
                curr: 1 //重新从第 1 页开始
            }
        }); //只重
    })

    /*****给重置按钮绑定事件************/
    $("#reset").click(function () {
        //清空
        $("[name='orgName' ]").val("");//机构名称
        $("[name='orgAbbreviation' ]").val("");//机构简称
        table.reload('demo', {
            where: { //设定异步数据接口的额外参数，任意设
                orgName: ""
                ,orgAbbreviation: ""
            }
            ,page: {
                curr: 1 //重新从第 1 页开始
            }
        }); //只重
    })

})

