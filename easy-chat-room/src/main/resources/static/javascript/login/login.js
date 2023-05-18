var layer = null;

$(function () {
    Layui.init();
    $('.message a').click(function () {
        $('form').animate({
            height: 'toggle',
            opacity: 'toggle'
        }, 'slow');
    });
});

var Layui = {
    /**
     * 初始化layui
     */
    init: () => {
        layui.use(['layer'], function () {
            layer = layui.layer;
        });
    }
}

/**
 * 登录
 */
function check_login() {
    var name = $("#user_name").val();
    var pass = $("#password").val();
    if (name && pass) {
        let obj = {
            userId: name,
            password: pass
        }
        post(obj, '/doLogin', res => {
            if (res.code === 0) {
                layer.msg(res.data, {icon: 1, time: 1500}, function () {
                    window.location.href = '/room/index';
                })
            } else {
                layer.msg(res.msg, {icon: 5, time: 1500});
                $("#login_form").removeClass('shake_effect');
                setTimeout(function () {
                    $("#login_form").addClass('shake_effect')
                }, 1);
            }
        }, error => {
            layer.msg('服务器内部错误', {icon: 5, time: 1500})
        })
    } else {
        layer.msg('请完善表单', {icon: 5, time: 1500})
    }
}

/**
 * 用户注册
 */
function check_register() {
    var name = $("#r_user_name").val();
    var pass = $("#r_password").val();
    var nickName = $("#r_user_nick_name").val();
    if (name && pass && nickName) {
        let obj = {
            userId: name,
            password: pass,
            userName:nickName
        }
        jsonPost(obj, '/doRegister', res => {
            if (res.data === true) {
                layer.msg("注册成功", {icon: 1, time: 1500}, function () {
                   $('#registerForm .message a').click();
                })
            } else {
                layer.msg("注册失败", {icon: 5, time: 1500});
                $("#login_form").removeClass('shake_effect');
                setTimeout(function () {
                    $("#login_form").addClass('shake_effect')
                }, 1);
            }
        }, error => {
            layer.msg('服务器内部错误', {icon: 5, time: 1500})
        })
    } else {
        layer.msg('请完善表单', {icon: 5, time: 1500});
        $("#login_form").removeClass('shake_effect');
    }
}