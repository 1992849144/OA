//显示所有个人日程


//初始加载table模块
layui.use(['table','layer','jquery'],function () {

    //声明变量table,指定layui的table模块
    var table = layui.table;
    var layer = layui.layer;
    var $ = layui.$;

    //准备将页面上的table渲染成 数据表格
    table.render({
        elem: '#demo'
        , url: '/userManage/getSysUser' //它是一个请求地址，用于请求后台数据
        , page: true //开启分页
        , limit: 5 //默认每一页显示5条
        // ,toolbar:"#add"//显示数据表格的工具栏
        , toolbar: true//显示数据表格的工具栏
        , limits: [1, 2, 3, 5, 10, 20, 30, 50] //设置可选择的每页显示的条数据
        , cols: [[ //表头
            {field: 'id', title: '编号', align: "center", width: "10%", sort: true, fixed: 'left'}
            , {field: 'username', title: '用户名', align: "center", width: "15%", sort: true,}
            , {field: 'nickname', title: '昵称', align: "center", width: "15%", sort: true}
            , {field: 'gender', title: '性别', align: "center", width: "10%", sort: true,
                templet:function (res) {
                if (res.gender==0)  return '<span>'+'男'+'</span>'
                else  return '<span>'+'女'+'</span>'
                    }
                }
            , {field: 'departName', title: '部门名称', align: "center", width: "10%", sort: true}
            , {field: 'name', title: '职位名称', align: "center", width: "10%", sort: true}
            , {field: 'picture', title: '图片', align: "center", width: "20%", sort: true,templet:'#imgTpl'}
            , {field: 'op', title: '操作', align: "center", width: "10%", sort: true, toolbar: "#barDemo"}
        ]]
    });


    /********************工具条监听事件*****************************************************/
    //监听工具条
    table.on('tool(test)', function(obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）
        if (layEvent === 'edit') { //删除
            layer.open({
                type: 2,
                title:"分配权限",
                shade: true,//以模态窗口显示
                shade:0.5,//设置透明度
                area: ['600px','500px'],
                anim: 1,//进入的动画效果
                maxmin: false,//不使用最大化，最小化按钮
                content: '/forward/systemManagement/authorityDistribution',
                zIndex: layer.zIndex, //重点1
                success: function(layero){
                    layer.setTop(layero); //重点2  置顶在上面，

                    //获得弹出层datail.jsp的body部份
                    var body = layui.layer.getChildFrame("body");
                    body.find("[name='id']").val(data.id);
                    body.find("[name='username']").val(data.username);
                    body.find("[name='nickname']").val(data.nickname);
                    body.find("[name='gender']").val(data.gender==0?"男":"女");
                    body.find("[name='departName']").val(data.departName);
                    body.find("[name='name']").val(data.name);
                }
            })
        }
    });

    /******************给搜索按钮绑定事件*********************************/
    $("#search").click(function () {
        var username=$("[name='username' ]").val();//用户名
        var nickname=$("[name='nickname' ]").val();//昵称
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
        $("[name='username' ]").val("");//用户名
        $("[name='nickname' ]").val("");//昵称
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

    /***********************自己封装一个日期转换函数，将时间戳转换成日期格式*************************************************/
        //指定日期转换格式
    var format = function(time, format){
            var t = new Date(time);
            var tf = function(i){return (i < 10 ? '0' : '') + i};
            return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a){
                switch(a){
                    case 'yyyy':
                        return tf(t.getFullYear());
                        break;
                    case 'MM':
                        return tf(t.getMonth() + 1);
                        break;
                    case 'mm':
                        return tf(t.getMinutes());
                        break;
                    case 'dd':
                        return tf(t.getDate());
                        break;
                    case 'HH':
                        return tf(t.getHours());
                        break;
                    case 'ss':
                        return tf(t.getSeconds());
                        break;
                }
            })
        }
})

