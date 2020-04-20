//显示所有部门日程


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
        , url: '/scheduleList/getScheduleListByIsremindEqualZero' //它是一个请求地址，用于请求后台数据
        , page: true //开启分页
        , limit: 5 //默认每一页显示5条
        // ,toolbar:"#add"//显示数据表格的工具栏
        , toolbar: true//显示数据表格的工具栏
        , limits: [1, 2, 3, 5, 10, 20, 30, 50] //设置可选择的每页显示的条数据
        , cols: [[ //表头
            {field: 'scheduleid', title: '编号', align: "center", width: "10%", sort: true, fixed: 'left'}
            , {field: 'title', title: '主题', align: "center", width: "15%", sort: true,}
            , {field: 'meetingformatid', title: '会议类型', align: "center", width: "10%", sort: true,
                templet:function (res) {
                    return '<span>'+res.meetingformat.meetingformatname+'</span>'
                }
            }
            , {field: 'startTime', title: '开始时间', align: "center", width: "15%", sort: true,
                templet: "<div>{{layui.util.toDateString(d.startTime,'yyyy年MM月dd日')}}</div>"
            }
            , {field: 'endTime', title: '结束时间', align: "center", width: "15%", sort: true,
                templet: "<div>{{layui.util.toDateString(d.endTime,'yyyy年MM月dd日')}}</div>"
            }
            , {field: 'miaoshu', title: '日程描述', align: "center", width: "10%", sort: true}
            , {field: 'sysUser', title: '创建人', align: "center", width: "10%", sort: true,
                templet:function (res) {
                    return '<span>'+res.sysUser.nickname+'</span>'
                }}
            , {field: 'op', title: '操作', align: "center", width: "15%", sort: true, toolbar: "#barDemo"}
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
                title:"修改日程记录",
                shade: true,//以模态窗口显示
                shade:0.5,//设置透明度
                area: ['800px','500px'],
                anim: 1,//进入的动画效果
                maxmin: false,//不使用最大化，最小化按钮
                content: '/forward/ScheduleList/updateDepartmentSchedule',
                zIndex: layer.zIndex, //重点1
                success: function(layero){
                    layer.setTop(layero); //重点2  置顶在上面，

                    //获得弹出层datail.jsp的body部份
                    var body = layui.layer.getChildFrame("body");
                    body.find("[name='scheduleid']").val(data.scheduleid);
                    body.find("[name='title']").val(data.title);
                    body.find("[name='meetingformatid']").val(data.meetingformatid);
                    body.find("[name='startTime']").val(layui.util.toDateString(data.startTime,'yyyy-MM-dd'));
                    body.find("[name='endTime']").val(layui.util.toDateString(data.endTime,'yyyy-MM-dd'));
                    body.find("[name='miaoshu']").val(data.miaoshu);
                    body.find("[name='userIds']").val(data.userId);
                    body.find("[name='appointments']").val(data.appointment);
                    body.find("[name='colours']").val(data.colour);
                    body.find("[name='placeids']").val(data.placeid);
                    body.find("[name='me']").val(data.meetingformatid);
                    body.find("[name='userId']").val(data.userId);
                    body.find("[name='createtime']").val(layui.util.toDateString(data.createtime,'yyyy-MM-dd'));
                    $.ajax({
                        url:"/show",
                        type:"post",
                        data:{id:data.userId},
                        dataType:"json",
                        success:function (datas) {
                            body.find("[name='userid']").val(datas.nickname);
                        }
                    })

                    $.ajax({
                        url:"/show",
                        type:"post",
                        data:{id:data.appointment},
                        dataType:"json",
                        success:function (datas) {
                            body.find("[name='primarypersonName']").val(datas.nickname);
                        }
                    })

                }
            })
        }
        else if (layEvent === 'del') { //删除
            layer.confirm('真的删除行么', function (index) {
                layer.close(index);
                $.ajax({
                    url:"/scheduleList/delDepartmentScheduleList",
                    type:"post",
                    data:{scheduleId:data.scheduleid},
                    success:function (data) {
                        if (data==1){
                            layer.alert("删除成功");
                        } else {
                            layer.alert("删除失败，你的权限不足");
                        }

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
        else if (layEvent === 'LAYTABLE_TIPS') {
            layer.alert('Hi，头部工具栏扩展的右侧图标。');
        }
    });

    /******************给搜索按钮绑定事件*********************************/
    $("#search").click(function () {
        var title=$("[name='title' ]").val();//主题
        var startTime=$("[name='startTime' ]").val();//开始时间
        var endTime=$("[name='endTime' ]").val();//结束时间
        table.reload('demo', {
            where: { //设定异步数据接口的额外参数，任意设
                title: title
                ,startTime: startTime
                ,endTime: endTime
            }
            ,page: {
                curr: 1 //重新从第 1 页开始
            }
        }); //只重
    })

    /*****给重置按钮绑定事件************/
    $("#reset").click(function () {
        //清空
        $("[name='title' ]").val("");//主题
        $("[name='startTime' ]").val("");//开始时间
        $("[name='endTime' ]").val("");//结束时间
        table.reload('demo', {
            where: { //设定异步数据接口的额外参数，任意设
                title: ""
                ,startTime: ""
                ,endTime: ""
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

