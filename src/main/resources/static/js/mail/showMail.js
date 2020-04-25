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
        , url: '/mail/init' //它是一个请求地址，用于请求后台数据
        , page: true //开启分页
        , limit: 5 //默认每一页显示5条
        // ,toolbar:"#add"//显示数据表格的工具栏
        ,toolbar:"default"//显示默认工具栏
        // , toolbar: true//显示数据表格的工具栏
        , limits: [1, 2, 3, 5, 10, 20, 30, 50] //设置可选择的每页显示的条数据
        , cols: [[ //表头
            {field: 'no',type:"checkbox", title: '选择',align:'center', width:'5%', sort: true, fixed: 'left'},
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
            , {field: 'begintime', title: '开始时间', align: "center", width: "14%", sort: true,
                templet: "<div>{{layui.util.toDateString(d.beginTime,'yyyy年MM月dd日')}}</div>"
            }
            , {field: 'endtime', title: '结束时间', align: "center", width: "13%", sort: true,
                templet: "<div>{{layui.util.toDateString(d.endTime,'yyyy年MM月dd日')}}</div>"
            }
            , {field: 'recordtime', title: '创建时间', align: "center", width: "13%", sort: true,
                templet: "<div>{{layui.util.toDateString(d.recordTime,'yyyy年MM月dd日')}}</div>"
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
        if (layEvent === 'edit') { //修改
            window.location.href="/mail/showUpdateMessage?messageId="+data.messageId;
        }
        else if (layEvent === 'del') { //删除
            layer.confirm('真的删除行么', function (index) {
                layer.close(index);
                $.ajax({
                    url:"/mail/delMessage",
                    type:"post",
                    data:{messageId:data.messageId},
                    success:function () {
                        layer.alert("删除成功");
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
        else if(layEvent==='show'){
            layer.open({
                title: '该消息基本内容如下'
                ,content: "消息内容是："+data.content
            });
        }
        else if(layEvent==='update'){
            window.location.href="/mail/updateIfPublish?messageId="+data.messageId;
        }
        else if (layEvent === 'LAYTABLE_TIPS') {
            layer.alert('Hi，头部工具栏扩展的右侧图标。');
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

    table.on('toolbar(test)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id);
        switch(obj.event){
            case 'delete':
                var data = checkStatus.data;
                layer.confirm('真的删除行么', function (index) {
                    layer.close(index);
                    $.ajax({
                        url:"/mail/deleteMultipleMessage",
                        type:"post",
                        data:{jsondata:JSON.stringify(data)},
                        success:function () {
                            layer.alert("删除成功");
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
                break;
        };
    });
})
