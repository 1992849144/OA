//用户


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
        , url: '/userManage/getAllUserInfo' //它是一个请求地址，用于请求后台数据
        , page: true //开启分页
        , limit: 5 //默认每一页显示5条
        // ,toolbar:"#add"//显示数据表格的工具栏
        , toolbar: true//显示数据表格的工具栏
        , limits: [1, 2, 3, 5, 10, 20, 30, 50] //设置可选择的每页显示的条数据
        , cols: [[ //表头
            {field: 'id', title: '用户编号', align: "center", width: "10%", sort: true, fixed: 'left'}
            , {field: 'username', title: '用户ID', align: "center", width: "10%", sort: true,}
            , {field: 'nickname', title: '姓名', align: "center", width: "15%", sort: true,}
            , {field: 'password', title: '密码', align: "center", width: "25%", sort: true,}
            , {field: 'name', title: '角色', align: "center", width: "10%", sort: true,}
            , {field: 'gender', title: '性别', align: "center", width: "8%", sort: true,
                templet:function (res) {
                    if (res.gender==0) return   '<span>'+'男'+'</span>'
                    else return   '<span>'+'女'+'</span>'
                }
            }
            , {field: 'info', title: '用户详情', align: "center", width: "7%", sort: true, toolbar: "#info"}
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
            window.location.href = "/userManage/showUpdatUser?id="+data.id;
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
            window.location.href = "/forward/PersonnelManagement/addUser";
        } else if (layEvent === 'info'){//详情
            layer.open({
                type: 2,
                title:"用户详细信息",
                shade: true,//以模态窗口显示
                shade:0.5,//设置透明度
                area: ['600px','400px'],
                anim: 1,//进入的动画效果
                maxmin: false,//不使用最大化，最小化按钮
                content: '/forward/PersonnelManagement/userInfo',
                zIndex: layer.zIndex, //重点1
                success: function(layero){
                    layer.setTop(layero); //重点2  置顶在上面，
                    //获得弹出层datail.jsp的body部份
                    var body = layui.layer.getChildFrame("body");

                }
            })
        }
    });

    /******************给搜索按钮绑定事件*********************************/
    $("#search").click(function () {
        var username=$("[name='username' ]").val();//机构名称
        var nickname=$("[name='nickname' ]").val();//机构简称
        table.reload('demo', {
            where: { //设定异步数据接口的额外参数，任意设
                username: username
                ,nickname: nickname
            }
            ,page: {
                curr: 1 //重新从第 1 页开始
            }
        }); //只重
    })

    /*****给重置按钮绑定事件************/
    $("#reset").click(function () {
        //清空
        $("[name='username' ]").val("");//机构名称
        $("[name='nickname' ]").val("");//机构简称
        table.reload('demo', {
            where: { //设定异步数据接口的额外参数，任意设
                username: ""
                ,nickname: ""
            }
            ,page: {
                curr: 1 //重新从第 1 页开始
            }
        }); //只重
    })

})

