var table = null;
var form = null;
var layer = null;
var upload = null;

$(function () {

    user.initLayui();

    /**
     * 查询
     */
    $("#query").click(function (e) {

    });
});


var user = {
    /**
     * 初始化layui
     */
    initLayui: () => {
        layui.use(['table', 'form', 'layer','upload'], function () {
            table = layui.table;
            form = layui.form;
            layer = layui.layer;
            upload = layui.upload
            // 监听按钮点击
            user.toolbarListener();
            // 加载表格
            user.queryUserData();
        });
    },

    /**
     * 监听按钮点击
     */
    toolbarListener: () => {
        table.on('toolbar(userTableFilter)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            //获取选中的数据
            var data = checkStatus.data;
            switch (obj.event) {
                case 'import':
                    layer.open({
                        id: 'add',
                        type: 1,
                        title: ['导入'],
                        skin: 'layui-layer-molv',
                        area: '500px',
                        offset: 'auto',
                        content: $('#importDialog'),
                        success: (layero, index) => {
                            upload.render({
                                elem: '#importExcel'
                                , accept:'file'
                                , exts:'xlsx'
                                , url: '/user/import'
                                , done: function (res) {
                                    if (res.flag === true && res.data === true) {
                                        layer.msg('上传成功', {icon: 1, time: 1500},function (){
                                            table.reload('userList')
                                            layer.close(index)
                                        })
                                    }else{
                                        layer.msg('上传失败', {icon: 5, time: 1500},function (){
                                            layer.close(index)
                                        })
                                    }
                                }
                            });
                        }
                    })
                    break;
                case 'export':
                    window.location.href = "/user/export";
                    break;
                default:
                    console.log('无对应按钮事件')
                    break;
            }
        })
    },
    /**
     * 查询用户列表
     */
    queryUserData: () => {
        table.render({
            id: 'userList',
            elem: '#userTable'
            , url: '/user/selectAll'
            , toolbar: '#toolbar'
            , page: true
            , method: 'get'
            , size: 'sm'
            , defaultToolbar: []
            //res 即为原始返回的数据
            , parseData: function (res) {
                return {
                    //解析接口状态
                    "code": res.code,
                    //解析提示文本
                    "msg": res.msg,
                    //解析数据长度
                    "count": res.data.total,
                    //解析数据列表
                    "data": res.data.records
                };
            }
            , request: {
                //页码的参数名称，默认：page
                pageName: 'current'
                //每页数据量的参数名，默认：limit
                , limitName: 'size'
            }
            , cols: [[ //表头
                {field: 'number', type: 'numbers'}
                , {field: 'checkbox', type: 'checkbox'}
                , {field: 'userId', title: '用户名', align: 'center'}
                , {field: 'userName', title: '用户昵称', align: 'center'}
                , {field: 'age', title: '年龄', align: 'center'}
                , {field: 'address', title: '地址', align: 'center'}
                , {
                    field: 'createTime', title: '创建时间', align: 'center'
                }
            ]]
        });
    }
}