$(function () {
    // $.addtabs.set({iframe:false})
    console.log('欢迎使用LanaiUI')
})
var table;
layui.use('table', function(){
    table = layui.table;

    //第一个实例
    table.render({
        elem: '#fileExplorer'
        ,height: 'full'
        ,skin:'line'
        ,data:[{"fileName":"<div style='z-index:1;position:absolute;'>dsadadsa</div><i onclick='f()' title='分享' class='fa fa-share-alt layui-table-cell actionIcon'></i>","fileSize":"dsadadsa","changeTime":"dsadadsa","path":"000"},{"fileName":"dsadadsa","fileSize":"dsadadsa","changeTime":"dsadadsa","path":"000"},{"fileName":"dsadadsa","fileSize":"dsadadsa","changeTime":"dsadadsa","path":"000"}]
        ,page: true //开启分页
        ,text: {
            none: '文件夹为空' //默认：无数据。注：该属性为 layui 2.2.5 开始新增
        }
        ,cols: [ //表头
            [
                {type:'checkbox', width:'5%'}
                ,{field: 'fileName', title: '文件名', width:'50%', sort: true}
                ,{field: 'fileSize', title: '大小',width:'20%', sort: true}
                ,{field: 'changeTime', title: '修改日期', sort: true,width:'25%'}
                ,{field: 'path', sort: true,hide:true}
                ,{field: 'fileType', sort: true,hide:true}
            ]]
    });
    //监听表格复选框选择
    table.on('checkbox(demo)', function(obj){
        console.log(obj)
    });
    //监听工具条
    table.on('tool(demo)', function(obj){
        var data = obj.data;
        if(obj.event === 'detail'){
            layer.msg('ID：'+ data.id + ' 的查看操作');
        } else if(obj.event === 'del'){
            layer.confirm('真的删除行么', function(index){
                obj.del();
                layer.close(index);
            });
        } else if(obj.event === 'edit'){
            layer.alert('编辑行：<br>'+ JSON.stringify(data))
        }
    });

    var $ = layui.$, active = {
        getCheckData: function(){ //获取选中数据
            var checkStatus = table.checkStatus('fileExplorer')
                ,data = checkStatus.data;
            layer.alert(JSON.stringify(data));
        }
        ,getCheckLength: function(){ //获取选中数目
            var checkStatus = table.checkStatus('fileExplorer')
                ,data = checkStatus.data;
            layer.msg('选中了：'+ data.length + ' 个');
        }
        ,isAll: function(){ //验证是否全选
            var checkStatus = table.checkStatus('fileExplorer');
            layer.msg(checkStatus.isAll ? '全选': '未全选')
        }
    };

    $('.demoTable .layui-btn').on('click', function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
});

function f() {
    $.ajax( {
            type: "POST",
            contentType: "application/json",
            url:"/fileExplorerController/queryUserPath",
            data: JSON.stringify(getQueryCondition()),
            success:function(response) {
               if (response.code == 200){
                    var dat = response.data;
                    var data =new Array();
                    for (var i = 0;i<dat.length;i++){
                        var params={"fileName":"","fileSize":"","changeTime":"","path":"","fileType":""};
                        var file = dat[i].filePath.split('/');
                        params.fileName = file[file.length-1];
                        params.fileSize = "100K";
                        params.changeTime = "2019-12-12";
                        params.path = dat[i].filePath;
                        params.fileType = dat[i].fileType;
                        data.push(params);
                    }

                   table.reload('fileExplorer', {
                       data:data
                        //,height: 300
                    });
               } else {
                   console(response)
               }
            },
            dataSrc:function(data)
            {
                return data.data;
            }
        }
    );


}

//查询参数封装
function getQueryCondition() {
    var params={'filePath':''};
    params.filePath = "/test/";
    return params;
}