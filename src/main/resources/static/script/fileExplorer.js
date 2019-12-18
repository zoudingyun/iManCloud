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
        ,data:[{"fileName":"<div style='z-index:1;position:absolute;'>dsadadsa</div><i onclick='f()' title='分享' class='fa fa-share-alt layui-table-cell actionIcon'></i>","fileSize":"dsadadsa","changeTime":"dsadadsa"},{"fileName":"dsadadsa","fileSize":"dsadadsa","changeTime":"dsadadsa"},{"fileName":"dsadadsa","fileSize":"dsadadsa","changeTime":"dsadadsa"},]
        ,page: true //开启分页
        ,cols: [ //表头
            [
                {type:'checkbox', width:'5%',}
                ,{field: 'fileName', title: '文件名', width:'55%', sort: true}
                ,{field: 'fileSize', title: '大小',width:'20%', sort: true}
                ,{field: 'changeTime', title: '修改日期', sort: true,width:'20%'}
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
    table.reload('fileExplorer', {
        data:[{"fileName":"<div style='z-index:1;position:absolute;'>11111111111</div><i title='分享' class='fa fa-share-alt layui-table-cell actionIcon'></i>","fileSize":"dsadadsa","changeTime":"dsadadsa"},{"fileName":"dsadadsa","fileSize":"dsadadsa","changeTime":"dsadadsa"},{"fileName":"dsadadsa","fileSize":"dsadadsa","changeTime":"dsadadsa"},]
        //,height: 300
    });
}