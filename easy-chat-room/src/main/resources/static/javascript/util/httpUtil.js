
function jsonPost(data, url, succ, fail) {
    $.ajax({
        url: url+'?t='+new Date().getTime(),
        data: JSON.stringify(data),
        dataType: 'json',
        type: 'post',
        contentType: 'application/json; charset=utf-8',
        success: succ,
        error: fail
    });
}


function post(data, url, succ, fail) {
    $.ajax({
        url: url+'?t='+new Date().getTime(),
        data: data,
        dataType: 'json',
        type: 'post',
        success: succ,
        error: fail
    });
}


function get(data, url, succ, fail) {
    $.ajax({
        url: url+'?t='+new Date().getTime(),
        data: data,
        dataType: 'json',
        type: 'get',
        success: succ,
        error: fail
    });
}
