/**
 * 操作相关
 */
var OPT = {
    /**
     * 注册
     */
    register: () => {
        layer.open({
            id: 'registerLayer',
            type: 1,
            title: ['注册'],
            skin: 'layui-layer-molv',
            area: '400px',
            offset: 'auto',
            content: $('#registerDialog'),
            btn: ['提交', '取消'],
            // 弹窗加载完成后回调
            success: (layero) => {
                layero.find('.layui-layer-btn').css('text-align', 'center');
            },
            yes: (layero, index) => {
                SOCKET.start();
            },
            cancel: (index, layero) => {
                layer.close(index)
            }
        })
    }
}