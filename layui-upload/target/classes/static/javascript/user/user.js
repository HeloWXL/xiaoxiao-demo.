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
    initLayui:()=>{
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
    toolbarListener:()=>{
        table.on('toolbar(userTableFilter)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            //获取选中的数据
            var data = checkStatus.data;
            switch (obj.event) {
                case 'add':
                    layer.open({
                        id: 'add',
                        type: 1,
                        title: ['新增用户'],
                        skin: 'layui-layer-molv',
                        area: '500px',
                        offset: 'auto',
                        content:$('#userDialog')
                    })
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
    queryUserData:()=>{
        table.render({
            id: 'userList',
            elem: '#userTable'
            , url: '/user/selectAll'
            , toolbar: '#toolbar'
            , page: true
            , method: 'get'
            , size: 'sm'
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
                , {field: 'userId', title: '用户名', align: 'center'}
                , {field: 'nickName', title: '用户昵称', align: 'center'}
                , {field: 'mobile', title: '手机号码', align: 'center'}
                , {
                    field: 'createTime', title: '创建时间', align: 'center'
                }
            ]]
        });
    }
}