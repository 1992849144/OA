/**
 * Created by Administrator on 2017/11/8.
 */
// 加载树
$(function () {

        $("#tree").tree({
                url:'json/userTree.json',
                onClick:function (node) {
                        var nodes=$(this).tree('isLeaf',node.target);
                        var partId;
                        if(nodes==false){
                                return ;
                        }
                        else{
                                partId=node.domId;
                               $.ajax({
                                       type:'get',
                                       url:"json/user.json"+partId,
                                       dataType:'json',
                                       success:function (data) {
                                               var tableTata=data.data;
                                               $("#table").datagrid('load',tableTata);

                                       }
                               })


                        }

                }
        })
        // 加载表格
        $("#table").datagrid({
                title:"数据列表",
                iconCls:"icon-left02",
                url:'json/user.json',
                fitColumns:true,
                striped:true,
                pagination:true,
                pageSize:10,
                width:'100%',
                rownumbers:true,
                pageList:[10,20],
                pageNumber:1,
                nowrap:true,
                height:'auto',
                sortName:'id',
                checkOnSelect:false,
                sortOrder:'asc',
                toolbar: '#tabelBut',
                columns:[[
                        {
                                checkbox:true,
                                field:'no',
                                width:100,
                                align:'center'
                        },
                        {
                                field:'id',
                                title:'编号',
                                width:100,
                                align:'center'



                        },
                        {
                                field:'name',
                                title:'用户名',
                                width:100,
                                align:'center'



                        },
                        {
                                field:'title',
                                title:'角色',
                                width:100,
                                align:'center'



                        },
                        {
                                field:'sex',
                                title:'姓别',
                                width:100,
                                align:'center',

                                formatter:function (val,row) {
                                        if(val=='男'){
                                                return '<div style="color: green">'+val+'</div>';
                                        }
                                        else{
                                                return '<div style="color: red">'+val+'</div>';
                                        }

                                }


                        },
                        {
                                field:'part',
                                title:'所属部门',
                                width:100,
                                align:'center'



                        },
                        {
                                field:'time',
                                title:'入职时间',
                                width:100,
                                align:'center'



                        },
                        {
                                field:"opr",
                                title:'操作',
                                width:100,
                                align:'center',
                                formatter:function (val,row) {
                                        e = '<a  id="add" data-id="98" class=" operA"  onclick="obj.edit(\'' + row.id + '\')">编辑</a> ';
                                        d = '<a  id="add" data-id="98" class=" operA01"  onclick="obj.delOne(\'' + row.id + '\')">删除</a> ';
                                        return e+d;

                                }

                        }
                ]]
        })

})

// 加载部门下拉框
$("#part").combotree({
        url:'json/userTree.json',
        height:26,
        width:166,
        checkbox:true,
    onLoadSuccess:function(node,data){
        $("#part").combotree('setValue',data[0].id);
    },
    onSelect:function () {
            var t = $("#part").combotree('tree'); // 得到树对象
            var n = t.tree('getSelected');
            $("#part").combotree('setValue',n.text)
        }

})


//加载角色
$('#role').combobox({
        url:'json/userTree.json',
    height:26,
    width:'70%',
        valueField:'id',
        textField:'text'
});
// 加载详情页面部门下拉框
$("#part01").combotree({
    url:'json/userTree.json',
    height:26,
    width:'70%',
    checkbox:true,
    onLoadSuccess:function(node,data){
        $("#part01").combotree('setValue',data[0].id);

    },
    onSelect:function () {
        var t = $("#part01").combotree('tree'); // 得到树对象
        var n = t.tree('getSelected');
        $("#part01").combotree('setValue',n.text)
    }

});
obj={
        // 查询
        find:function () {


                $("#table").datagrid('load',{
                        user:$.trim($("#user").val()),
                        part:$.trim($("#part").val())
                })
                
        },
        // 添加
        addBox:function () {
                $("#addBox").dialog({
                        closed: false

                })
                $("#addForm").form('reset');
                $("#can").hide();
                $("#res").show();


        },
        // 编辑
        edit:function (id) {
                var ID;
                $("#res").hide();
                $("#can").show();
                $("#addBox").dialog({
                        closed: false,
                })
            // $("#addForm").form('load','json/user.json');
                $.ajax({
                        url:'json/user.json',
                        type:'get',
                        dataType:'json',
                        success:function (rows) {
                                var data=rows.rows;
                                if(data){
                                        $.each(data,function (index) {
                                               ID=data[index].id;
                                              if(id==ID){
                                                      $("#no").val(ID);
                                                      $("#name").val(data[index].name);
                                                      $("#pass").val(data[index].pass);
                                                      $('#time').datebox('setValue', data[index].time);
                                                      $('#part01').combotree('setValue', data[index].part);
                                                      $('#role').combobox('setValue', data[index].title);

                                              }

                                        })




                                }
                                else{
                                        return false;
                                }

                        }

                })



        },
        // 提交表单
        sum:function () {
                $('#addForm').form('submit', {
                            // url:'',
                    onSubmit: function(){
                        var lag= $(this).form('validate');
                           if(lag==true){

                           }
                },
                success: function(){
                        $.messager.progress('close');
                }
        });

        },
        // 重置表单
        res:function () {
                $("#addForm").form('reset');

        },
        // 取消表单
        can:function () {
                $("#addBox").dialog({
                        closed: true

                })

        },
        // 删除多个
        del:function () {
                var rows=$("#table").datagrid("getSelections");
               if(rows.length>0){
                       $.messager.confirm('确定删除','你确定要删除你选择的记录吗？',function (flg) {
                               if(flg){
                                       var ids=[];
                                       for(i=0;i<rows.length;i++){
                                               ids.push(rows[i].id);

                                       }
                                       var num=ids.length;
                                      $.ajax({
                                              type:'post',
                                              url:"",
                                              data:{
                                                      ids:ids.join(',')
                                              },
                                              beforesend:function () {
                                                      $("#table").datagrid('loading');
                                                      
                                              },
                                              success:function (data) {
                                                      if(data){

                                                              $("#table").datagrid('loaded');
                                                              $("#table").datagrid('load');
                                                              $("#table").datagrid('unselectAll');
                                                              $.messager.show({
                                                                      title:'提示',
                                                                      msg:num+'个用户被删除'
                                                              })

                                                      }
                                                      else{
                                                              $.messager.show({
                                                                      title:'警示信息',
                                                                      msg:"信息删除失败"
                                                              })

                                                      }

                                              }
                                      })
                               }

                       })

               }
               else{
                       $.messager.alert('提示','请选择要删除的记录','info');
               }

        },

        //删除一个
        delOne:function (id) {
                id=$("#table").datagrid('getSelected').id;
                $.messager.confirm('提示信息','是否删除所选择记录',function (flg) {
                        if(flg){
                                $.ajax({
                                        type:'post',
                                        url:'',
                                        data:{
                                                ID:id
                                        },
                                        beforesend:function () {
                                                $("#table").datagrid('loading');

                                        },
                                        success:function (data) {
                                                if(data){
                                                        $("#table").datagrid("loaded");
                                                        $("#table").datagrid("load");
                                                        $("#table").datagrid("unselectRow");
                                                        $.messager.show({
                                                                title:'提示信息',
                                                                msg:"信息删除成功"
                                                        })
                                                }
                                                else{
                                                        $.messager.show({
                                                                title:'警示信息',
                                                                msg:"信息删除失败"
                                                        })

                                                }

                                        }
                                })

                        }

                })


        }
}

// 弹出框加载
$("#addBox").dialog({
        title:"信息内容",
        width:500,
        height:350,
        closed: true,
        modal:true,
        shadow:true
})

