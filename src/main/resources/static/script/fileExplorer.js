var pwd=[];
var pwdStr="";
var upPath="";
var upIniFlag=false;
var uploader;

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
        //,data:[{"fileName":"<div style='z-index:1;position:absolute;'>dsadadsa</div><i onclick='f()' title='分享' class='fa fa-share-alt layui-table-cell actionIcon'></i>","fileSize":"dsadadsa","changeTime":"dsadadsa","path":"000"},{"fileName":"dsadadsa","fileSize":"dsadadsa","changeTime":"dsadadsa","path":"000"},{"fileName":"dsadadsa","fileSize":"dsadadsa","changeTime":"dsadadsa","path":"000"}]
        ,page: true //开启分页
        ,text: {
            none: '文件夹为空' //默认：无数据。注：该属性为 layui 2.2.5 开始新增
        }
        ,cols: [ //表头
            [
                {type:'checkbox', width:'5%'}
                ,{field: 'fileName', title: '文件名', width:'50%', sort: true}
                ,{field: 'fileSize', title: '大小',width:'18%', sort: true}
                ,{field: 'changeTime', title: '修改日期',width:'22%', sort: true  }
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

    f("./");
});

function re(){
    f(upPath);
}

function downFile(fileurl) {

    window.open("http://127.0.0.1:8080/downloadCacheFile/"+fileurl)

}

function f(path) {
    $.ajax( {
            type: "POST",
            contentType: "application/json",
            url:"/fileExplorerController/queryUserPath",
            data: JSON.stringify(getQueryCondition(path)),
            success:function(response) {
               if (response.code == 200){
                    var dat = response.data;
                    var data =new Array();
                   pwd = path.split("/");
                   upPath = "";
                   for (var i=0;i<pwd.length-2;i++){
                       upPath+=pwd[i]+"/";
                   }
                    if (path=="./"){
                        $("#return").hide();
                        $('#homeTag').show();
                    }else {
                        $("#return").show();
                        $('#homeTag').hide();
                    }
                    pwdStr='';
                    var pStr = '';
                    var urlStr='| ';
                    for (var i=0;i<pwd.length-1;i++){
                        if (i<pwd.length-2){
                            var pName = '';
                            if (pwd[i]=='.'){
                                pName = '全部文件';
                            }else {
                                pName = pwd[i];
                            }
                            pStr+=pwd[i]+'/';
                            urlStr+="<span style='color: #00a2d4;cursor:pointer;' onclick='f(\""+pStr+"\")'>"+pName+"</span><span> > </span>";
                        }else {
                            urlStr+="<span>"+pwd[i]+"</span>";
                        }
                        pwdStr+=pwd[i]+'/';
                    }
                    if (pwd.length>2){
                        $("#pathUrl").html(urlStr);
                    }else {
                        $("#pathUrl").html('');
                    }
                    for (var i = 0;i<dat.length;i++){
                        var params={"fileName":"","fileSize":"","changeTime":"","path":"","fileType":""};
                        var file = dat[i].fileRelativePath.split('./');
                        var serverPath = dat[i].parentFileRelativePath.replace("./","").replace("/","-");
                        var fileName = dat[i].fileName;
                        if (dat[i].fileType.indexOf('folder')==0){
                            fileName = "<div class='fileName' onclick='f(\""+dat[i].fileRelativePath+"/\")'><i class='fa fa-folder'></i> "+fileName+"</div>";
                        }else if (dat[i].fileType.indexOf('mov')==0) {
                            fileName = "<div class='fileName' onclick='alert(\"下载\")'><i class='fa fa-file-video-o'></i> "+fileName+"</div>";
                        }else {
                            fileName = "<div class='fileName')'><i class='fa  fa-file-o'></i> <a href=\"./downloadCacheFile?path="+serverPath+"&fileName="+fileName+"\" download=\""+fileName+"\">"+fileName+"</a></div>";
                        }
                        params.fileName = fileName;
                        params.fileSize = dat[i].displayFileSize;
                        params.changeTime = dat[i].changeTime;
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
function getQueryCondition(path) {
    var params={'filePath':''};
    params.filePath = path;
    return params;
}

function upload() {
    if (!upIniFlag){//防止多次初始化上传控件
        var $list = $("#thelist");
        var $btn = $("#ctlBtn");
        var state = 'pending'; // 上传文件初始化
        var timer;
        var fileArray = [];

        uploader = WebUploader.create({

            // 文件接收服务端。
            server: '/upload',

            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick: '#picker',

            // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
            resize: false,
            duplicate: true,
            chunked: true,
            threads: 5,
            chunkSize: 10485760,
            formData: {
                guid: "",
                path:pwdStr
            },
            accept: {
                extensions:'*' ,
                mimeTypes: '*'
            }
        });

//点击上传之前调用的方法
        uploader.on("uploadStart", function (file) {
            var guid = WebUploader.Base.guid();
            var paramOb = {"guid": guid, "filedId": file.source.ruid}
            uploader.options.formData.guid = guid;
            fileArray.push(paramOb);
        });

// 当有文件被添加进队列的时候
        uploader.on('fileQueued', function (file) {
            var fileName = file.name;
            if (fileName.length>=25){
                fileName = fileName.substring(0,22)+'...';
            }
            $list.append('<div id="' + file.id + '" class="item">' +
                '<h4 class="info">' + fileName + '</h4>' +
                '<p class="state">等待上传...</p>' +
                '</div>');
        });

// 文件上传过程中创建进度条实时显示。
        uploader.on('uploadProgress', function (file, percentage) {
            var $li = $('#' + file.id),
                $percent = $li.find('.progress .progress-bar');
            // 避免重复创建
            if (!$percent.length) {
                $percent = $('<div class="progress progress-striped active">' +
                    '<div class="progress-bar" role="progressbar" style="width: 0%">' +
                    '</div>' +
                    '</div>').appendTo($li).find('.progress-bar');
            }
            $li.find('p.state').text('上传中');
            $percent.css('width', percentage * 100 + '%');
        });

//文件成功、失败处理
        uploader.on('uploadSuccess', function (file) {
            var successFileId = file.source.ruid;
            var successDuid;
            if (fileArray.length > 0) {
                for (var i = 0; i < fileArray.length; i++) {
                    if (fileArray[i].filedId === successFileId) {
                        successDuid=fileArray[i].guid;
                        fileArray.splice(i, 1);
                    }
                }
            }
            clearInterval(timer);
            $('#' + file.id).find('p.state').text('已上传');
            $.get("/merge", {"guid": successDuid}, function (data, status) {
                layer.msg("合并完成");
            });

        });

//文件上传出错
        uploader.on('uploadError', function (file) {
            $('#' + file.id).find('p.state').text('上传出错');
        });

//文件上传完成
        uploader.on('uploadComplete', function (file) {
            $('#' + file.id).find('.progress').fadeOut();
        });

//点击上传时间
        $btn.on('click', function () {
            if (state === 'uploading') {
                uploader.stop();
            } else {
                uploader.upload();
                timer = setInterval(function () {
                    var useTime = parseInt($("#useTime").html());
                    useTime = useTime + 1;
                    $("#useTime").html(useTime);
                }, 1000);
            }
        });
        upIniFlag =true;
        $("#buttag").remove();
    }else {
        uploader.options.formData.path=pwdStr;
    }

}


