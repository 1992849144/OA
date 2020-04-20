/**
 * Created by Administrator on 2017/11/8.
 */

//日程控件

$("#calePan").panel({
    fit:true
})
$('#calePan').fullCalendar({
    buttonText: {
        today: '今天',
        month: '月',
        week: '周',
        day: '日'
    },
    allDayText: "全天",
    timeFormat: {
        '': 'H:mm{-H:mm}'
    },
    weekMode: "variable",
    columnFormat: {
        month: 'dddd',
        week: 'dddd M-d',
        day: 'dddd M-d'
    },
    titleFormat: {
        month: 'yyyy年 MMMM月',
        week: "[yyyy年] MMMM月d日 { '&#8212;' [yyyy年] MMMM月d日}",
        day: 'yyyy年 MMMM月d日 dddd'
    },
    monthNames: ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"],
    dayNames: ["星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"],
    header: {
        left: 'prev,next today',
        center: 'title',
        right: 'month,agendaWeek,agendaDay'
    },
    eventClick: function (date, allDay, jsEvent, view) {
        $("[name='title']").html(date.title)
        $("[name='name']").html(date.user)
        var starttime = dateformat(new Date(date.start));
        var endtime = dateformat(new Date(date.end));
        $("[name='startTime']").html(starttime);
        $("[name='endTime']").html(endtime);
        $("[name='type']").html(date.rctype);
        $("#addPans").dialog({
            closed:false
        })
    },
    events: function (start, end, callback) {
        $.ajax({
            url: "/scheduleList/init",
            cache: false,
            type: "post",
            datatype: 'json',
            success: function (data) {
                var events = [];
                $.each(data, function (i, item) {
                    console.log(item);
                    var color;
                    var starttime = dateformat(new Date(item.start_time));
                    var endtime = dateformat(new Date(item.end_time));
                    if (item.colour == '27') {
                        color = "#00c0ef";
                    }
                    if (item.colour == '28') {
                        color = "#f0ad4e";
                    }
                    if (item.colour == '29') {
                        color = "#dd4b39";
                    }
                    events.push({
                        title: item.title,

                        start: starttime,

                        backgroundColor: color,

                        borderColor: color,

                        end: endtime,

                        id: item.id,

                        user: item.nickname,

                        des: item.describe,

                        rctype: item.meetingformatname
                    });
                });
                callback(events);
            }
        })
    }
});

var dateformat = function(a) {
    return a.getFullYear() + "-" + (a.getMonth() + 1) + "-" + a.getDate() + " " + a.getHours() + ":" + a.getMinutes() + ":" + a.getSeconds();
};


//新建日程
function addNew() {
    layui.use(['form','jquery','layer'], function(){
        var layer = layui.layer;
        var form = layui.form;
        var $ = layui.$;
        layer.open({
            type: 2,
            title:"新建日程记录",
            shade: true,//以模态窗口显示
            shade:0.5,//设置透明度
            area: ['800px','550px'],
            anim: 1,//进入的动画效果
            maxmin: false,//不使用最大化，最小化按钮
            content: '/forward/ScheduleList/createCalendar',
            zIndex: layer.zIndex, //重点1
            success: function(layero){

            }
        })
    })
}