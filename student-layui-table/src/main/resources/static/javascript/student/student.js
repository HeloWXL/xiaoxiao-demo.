var table = null;
var form = null;
var layer = null;

var updateId = null;

$(function () {

    student.initLayui();

    /**
     * 查询
     */
    $("#query").click(function (e) {

    });
});


var student = {
    /**
     * 初始化layui
     */
    initLayui: () => {
        layui.use(['table', 'form', 'layer'], function () {
            table = layui.table;
            form = layui.form;
            layer = layui.layer;
            // 监听按钮点击
            student.toolbarListener();
            // 加载表格
            student.queryUserData();
        });
    },
    /**
     * 监听按钮点击
     */
    toolbarListener: () => {
        table.on('toolbar(studentTableFilter)', function (obj) {
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
                        }
                    })
                    break;
                // 修改
                case 'update':
                    if (data.length === 0) {
                        layer.msg('请选择一行', {icon: 3, time: 1500})
                    } else if (data.length > 1) {
                        layer.msg('只能选择一行', {icon: 3, time: 1500})
                    } else {
                        layer.open({
                            id: 'update',
                            type: 1,
                            title: ['修改用户'],
                            skin: 'layui-layer-molv',
                            area: '500px',
                            offset: 'auto',
                            content: $('#userDialog'),
                            success: (layero, index) => {
                                updateId = data[0].id;
                                form.val("userInfo",  {
                                    "userId":data[0].userId,
                                    "userName":data[0].userName
                                });
                                console.log(form.val("userInfo"))
                            }
                        })
                    }
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
            id: 'studentList',
            elem: '#studentTable'
            , url: '/student/queryStudentByPage'
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
                , {field: 'stuNo', title: '用户ID', align: 'center'}
                , {field: 'stuName', title: '用户名', align: 'center'}
                , {field: 'gender', title: '年龄', align: 'center'}
                , {field: 'age', title: '地址', align: 'center'}
                , {field: 'createTime', title: '创建时间', align: 'center'}
            ]]
        });
    },

    /**
     * 修改
     */
    updateUser:()=>{

    },
    /**
     * 监听提交表单
     */
    saveUser: () => {
        form.on('submit(userInfo)', function (data) {
            var obj = data.field;
            // 向后台发送数据
            jsonPost(obj, '/student/saveStudent', res => {
                // 判断提交成功还是失败
                if (res && res > 0 ) {
                    layer.msg('保存成功', {icon: 1, time: 1500}, () => {
                        $("#userForm")[0].reset();
                        form.render();
                        layer.closeAll();
                        table.reload('studentList');
                    })
                } else {
                    layer.msg(res.msg, {icon: 5, time: 1500}, () => {
                        table.reload('studentList');
                    })
                }
                console.log(res)
            }, error => {
                console.error(error)
                layer.msg('服务器内部异常', {icon: 5, time: 1500})
            })
        });
    },

    /**
     * 取消
     */
    cancleDialog:()=>{
        layer.closeAll();
    }
}