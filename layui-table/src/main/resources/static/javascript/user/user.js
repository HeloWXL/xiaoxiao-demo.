var table = null;
var form = null;
var layer = null;

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
        layui.use(['table', 'form', 'layer'], function () {
            table = layui.table;
            form = layui.form;
            layer = layui.layer;
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
                // 新增
                case 'add':
                    layer.open({
                        id: 'add',
                        type: 1,
                        title: ['新增用户'],
                        skin: 'layui-layer-molv',
                        area: '500px',
                        offset: 'auto',
                        content: $('#userDialog'),
                        success: (layero, index) => {
                            layero.find('.layui-layer-btn').css('text-align', 'center');
                        }
                    })
                    break;
                // 修改
                case 'update':
                    layer.open({
                        id: 'add',
                        type: 1,
                        title: ['修改用户'],
                        skin: 'layui-layer-molv',
                        area: '500px',
                        offset: 'auto',
                        content: $('#userDialog'),
                        success: (layero, index) => {
                            layero.find('.layui-layer-btn').css('text-align', 'center');
                            form.val("userInfo", data[0]);
                        }
                    })
                    break;
                // 删除
                case 'delete':
                    if (data.length === 0) {
                        layer.msg('请选择一行', {icon: 1, time: 1500});
                    } else {
                        var length = data.length;
                        var obj = [];
                        for (var i = 0; i < length; i++) {
                            obj.push(data[i].id);
                        }
                        layer.confirm('是否删除？', {title: '提示'}, function (index) {
                            jsonPost(obj, '/user/deleteBatch', res => {
                                if (res.code === 0 && res.data) {
                                    layer.msg("删除成功", {icon: 1, time: 1500}, () => {
                                        // 重新刷新表格
                                        table.reload('userList');
                                    })
                                } else {
                                    layer.msg("删除失败", {icon: 5, time: 1500})
                                }
                            }, error => {
                                console.error(error)
                                layer.msg('服务器内部异常', {icon: 5, time: 1500})
                            })
                        });
                    }
                    break;
                // 默认
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
            , defaultToolbar: []
            , parseData: function (res) { //res 即为原始返回的数据
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
                pageName: 'current' //页码的参数名称，默认：page
                , limitName: 'size' //每页数据量的参数名，默认：limit
            }
            , cols: [[ //表头
                {field: 'number', type: 'numbers'}
                , {field: 'checkbox', type: 'checkbox'}
                , {field: 'userId', title: '用户ID', align: 'center'}
                , {field: 'userName', title: '用户名', align: 'center'}
                , {field: 'age', title: '年龄', align: 'center'}
                , {field: 'address', title: '地址', align: 'center'}
                , {field: 'createTime', title: '创建时间', align: 'center'}
            ]]
        });
    },

    /**
     * 监听提交表单
     */
    saveUser: () => {
        form.on('submit(userInfo)', function (data) {
            var obj = data.field;
            // 向后台发送数据
            jsonPost(obj, '/user/insert', res => {
                // 判断提交成功还是失败
                if (res.code === 0 && res.data) {
                    layer.msg(res.msg, {icon: 1, time: 1500}, () => {
                        layer.closeAll();
                        table.reload('userList');
                    })
                } else {
                    layer.msg(res.msg, {icon: 5, time: 1500}, () => {
                        table.reload('userList');
                    })
                }
                console.log(res)
            }, error => {
                console.error(error)
                layer.msg('服务器内部异常', {icon: 5, time: 1500})
            })
        });
    }
}