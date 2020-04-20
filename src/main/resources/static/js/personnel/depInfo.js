//部门


layui.use(['table','layer','jquery'],function () {
    //声明变量table,指定layui的table模块
    var table = layui.table;
    var layer = layui.layer;
    var $ = layui.$;
    //准备将页面上的table渲染成 数据表格
    table.render({
        elem: '#demo'
        //,height: 312
        , url: '/depInfo/getAllDep' //它是一个请求地址，用于请求后台数据
        , page: true //开启分页
        , limit: 5 //默认每一页显示5条
        // ,toolbar:"#add"//显示数据表格的工具栏
        , toolbar: true//显示数据表格的工具栏
        , limits: [1, 2, 3, 5, 10, 20, 30, 50] //设置可选择的每页显示的条数据
        , cols: [[ //表头
            {field: 'departId', title: '人员编号', align: "center", width: "10%", sort: true, fixed: 'left'}
            , {field: 'departName', title: '部门名称', align: "center", width: "10%", sort: true,}
            , {field: 'branchName', title: '机构', align: "center", width: "20%", sort: true,}
            , {field: 'nickname', title: '负责人', align: "center", width: "10%", sort: true,}
            , {field: 'connectTelNo', title: '联系电话', align: "center", width: "10%", sort: true,}
            , {field: 'connectMobileTelNo', title: '移动电话', align: "center", width: "15%", sort: true,}
            , {field: 'faxes', title: '传真', align: "center", width: "10%", sort: true,}
            , {field: 'op', title: '操作', align: "center", width: "15%", sort: true, toolbar: "#barDemo"}
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
                title:"修改部门信息",
                shade: true,//以模态窗口显示
                shade:0.5,//设置透明度
                area: ['650px','500px'],
                anim: 1,//进入的动画效果
                maxmin: false,//不使用最大化，最小化按钮
                content: '/forward/PersonnelManagement/updateDep',
                zIndex: layer.zIndex, //重点1
                success: function(layero){
                    layer.setTop(layero); //重点2  置顶在上面，
                    //获得弹出层datail.jsp的body部份
                    var body = layui.layer.getChildFrame("body");
                    body.find("[name='departId']").val(data.departId);
                    body.find("[name='branchId']").val(data.branchName);
                    body.find("[name='departName']").val(data.departName);
                    body.find("[name='principalUser']").val(data.nickname);
                    body.find("[name='connectTelNo']").val(data.connectTelNo);
                    body.find("[name='connectMobileTelNo']").val(data.connectMobileTelNo);
                    body.find("[name='faxes']").val(data.faxes);
                    body.find("[name='departNames']").val(data.departName);
                }
            })
        }
        else if (layEvent === 'del') { //删除
            layer.confirm('真的删除行么', function (index) {
                layer.close(index);
                $.ajax({
                    url:"/depInfo/delete",
                    type:"post",
                    data:{departId:data.departId},
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
                title:"添加部门信息",
                shade: true,//以模态窗口显示
                shade:0.5,//设置透明度
                area: ['650px','500px'],
                anim: 1,//进入的动画效果
                maxmin: false,//不使用最大化，最小化按钮
                content: '/forward/PersonnelManagement/addDep',
                zIndex: layer.zIndex, //重点1
                success: function(layero){
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
        var departName=$("[name='departName' ]").val();//部门名称
        var branchId=$("[name='branchId' ]").val();//所属机构
        var principalUser=$("[name='principalUser' ]").val();//部门负责人
        table.reload('demo', {
            where: { //设定异步数据接口的额外参数，任意设
                departName: departName
                ,branchId: branchId
                ,principalUser: principalUser
            }
            ,page: {
                curr: 1 //重新从第 1 页开始
            }
        }); //只重
    })

    /*****给重置按钮绑定事件************/
    $("#reset").click(function () {
        //清空
        $("[name='departName' ]").val("");//部门名称
        $("[name='branchId' ]").val("");//所属机构
        $("[name='principalUser' ]").val("");//部门负责人
        table.reload('demo', {
            where: { //设定异步数据接口的额外参数，任意设
                departName: ""
                ,branchId: ""
                ,principalUser: ""
            }
            ,page: {
                curr: 1 //重新从第 1 页开始
            }
        }); //只重
    })

})

