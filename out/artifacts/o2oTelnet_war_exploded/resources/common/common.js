function changeVerifyCode(img) {
    img.src="../Kaptcha?"+Math.floor(Math.random()*100);
}

function getQueryString(name) {//url传过来的key，name对应shopId，获取shopId的值

    var reg=new RegExp("(^|&)"+name+"=([^&]*)(&|$)");//匹配URL
    var r=window.location.search.substr(1).match(reg);

    if (r != null)
    {
        return decodeURIComponent(r[2]);//取出参数名的值
    }

    return '';
}