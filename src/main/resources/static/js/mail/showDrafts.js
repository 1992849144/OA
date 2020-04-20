//根据用户，获得草稿箱


//显示所有发送的邮件

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
        , url: '/mail/getMessageByUserId' //它是一个请求地址，用于请求后台数据
        , page: true //开启分页
        , limit: 5 //默认每一页显示5条
        // ,toolbar:"#add"//显示数据表格的工具栏
        , toolbar: true//显示数据表格的工具栏
        , limits: [1, 2, 3, 5, 10, 20, 30, 50] //设置可选择的每页显示的条数据
        , cols: [[ //表头
            {field: 'title', title: '消息标题', align: "center", width: "15%", sort: true, fixed: 'left'}
            , {field: 'messageTypeName', title: '消息类型', align: "center", width: "10%", sort: true,}
            , {field: 'content', title: '消息内容', align: "center", width: "10%", sort: true,event:"show",
                templet:function (res) {
                    var n="";
                    for (var i=0;i<res.content.length;i++){
                        var s = res.content.charAt(i);
                        if (i<6){
                            n+=s;
                            if (i==res.content.length-1){
                                return '<span>'+n+'</span>'
                            }
                        } else {
                            n+='...';
                            return '<span>'+n+'</span>'
                        }
                    }
                }
            }
            , {field: 'nickname', title: '创建者', align: "center", width: "10%", sort: true,}
            , {field: 'ifowner', title: '发送对象', align: "center", width: "10%", sort: true,
                templet:function (res) {
                    if (res.ifowner=='所有人')  return '<span>'+res.ifowner+'</span>'
                    else return '<span>'+res.nickname+'...'+'</span>'
                }
            }
            , {field: 'begintime', title: '开始时间', align: "center", width: "15%", sort: true,
                templet: "<div>{{layui.util.toDateString(d.beginTime,'yyyy年MM月dd日')}}</div>"
            }
            , {field: 'endtime', title: '结束时间', align: "center", width: "15%", sort: true,
                templet: "<div>{{layui.util.toDateString(d.endTime,'yyyy年MM月dd日')}}</div>"
            }
            ,{field:'ifPublish', title: '11',hide:true}
            , {field: 'op', title: '操作', align: "center", width: "15%", sort: true, toolbar: "#barDemo"}
        ]]
        ,done:function(res,curr,count) { // 隐藏列
            $(".layui-table-box").find("[data-field='cust_id']").css("display", "none");
        }
    });

    /********************工具条监听事件*****************************************************/
    //监听工具条
    table.on('tool(test)', function(obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）
        if (layEvent === 'edit') { //查看
            $.ajax({
                url:"/mail/updateMessageToUser",
                type:"post",
                data:{messageId:data.messageId},
                success:function () {

                }
            })

            layer.open({
                type: 2,
                title:"修改日程记录",
                shade: true,//以模态窗口显示
                shade:0.5,//设置透明度
                area: ['800px','500px'],
                anim: 1,//进入的动画效果
                maxmin: false,//不使用最大化，最小化按钮
                content: '/forward/mail/showMessage',
                zIndex: layer.zIndex, //重点1
                success: function(layero){
                    layer.setTop(layero); //重点2  置顶在上面，

                    //获得弹出层datail.jsp的body部份
                    var body = layui.layer.getChildFrame("body");
                    body.find("[name='title']").val(data.title);
                    body.find("[name='type']").val(data.messageTypeName);
                    body.find("[name='startTime']").val(layui.util.toDateString(data.beginTime,'yyyy-MM-dd'));
                    body.find("[name='endTime']").val(layui.util.toDateString(data.endTime,'yyyy-MM-dd'));
                    body.find("[name='fromUserId']").val(data.nickname);
                    body.find("[name='recordTime']").val(layui.util.toDateString(data.recordTime,'yyyy-MM-dd'));
                    body.find("[name='content']").val(data.content);
                    $.ajax({
                        url:"/mail/getChoosePersonByMessageId",
                        type:"post",
                        data:{messageId:data.messageId},
                        dataType:"json",
                        success:function (data) {
                            var n='';
                           $.each(data,function (index,k) {
                               n+=k.nickname;
                               if (index!=data.length-1){
                                   n+='; ';
                               }
                           })
                            body.find("[name='ifowner']").val(n);
                        }
                    })
                }
            })
        }
    });

    /******************给搜索按钮绑定事件*********************************/
    $("#search").click(function () {
        var time=$("[name='time' ]");//时间
        var times='';
        var startTime=$("[name='startTime' ]").val();//开始时间
        var endTime=$("[name='endTime' ]").val();//结束时间
        for (var i=0;i<time.length;i++){
            if (time[i].checked) {
                times=time[i].value;
            }
        }
        table.reload('demo', {
            where: { //设定异步数据接口的额外参数，任意设
                times: times,
                startTime: startTime
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
                times: ""
                ,startTime: ""
                ,endTime: ""
            }
            ,page: {
                curr: 1 //重新从第 1 页开始
            }
        }); //只重
    })

    $("#add").click(function () {
        window.location.href="/forward/mail/addMessage";
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
