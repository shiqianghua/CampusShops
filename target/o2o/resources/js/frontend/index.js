$(function() {
    //一级列表URL
    var url = 'listmainpageinfo';

    var pageIndex=0;
    var pageSize=10;

    //获取首页列表信息
    $.getJSON(url, function (data) {
        if (data.success) {
            var headLineList = data.headLineList;
            var swiperHtml = '';
            //拼接轮播图
            headLineList.map(function (item, index) {
                swiperHtml += ''
                            + '<div class="swiper-slide img-wrap">'
                            +'<img class="banner-img" src="'+ item.lineImg +'" alt="'+ item.lineName +'">'
                            + '</div>';
            });
            $('.swiper-wrapper').html(swiperHtml);
            //轮播图轮换时间为3秒
            $(".swiper-container").swiper({
                autoplay: 3000,
                //是否自动停止轮换
                autoplayDisableOnInteraction: false
            });

            //页面内容展示商铺以及图片
            var shopCategoryList = data.shopCategoryList;
            var categoryHtml = '';
            shopCategoryList.map(function (item, index) {
                categoryHtml += ''
                             +  '<div class="col-50 shop-classify" data-category='+ item.shopCategoryId +'>'
                             +      '<div class="word">'
                             +          '<p class="shop-title">'+ item.shopCategoryName +'</p>'
                             +          '<p class="shop-desc">'+ item.shopCategoryDesc +'</p>'
                             +      '</div>'
                             +      '<div class="shop-classify-img-warp">'
                             +          '<img class="shop-img" src="'+ item.shopCategoryImg +'">'
                             +      '</div>'
                             +  '</div>';
            });
            $('.row').html(categoryHtml);
        }
    });

    $('#me').click(function () {
        $.openPanel('#panel-left-demo');
    });

    $('.row').on('click', '.shop-classify', function (e) {
        var shopCategoryId = e.currentTarget.dataset.category;
        var newUrl = 'shoplist?parentId=' + shopCategoryId;
        window.location.href = newUrl;

        /*$.getJSON(newUrl,function (data)
        {
            if(data.success)
            {
                  var shopList=data.shopList;

            }
        })*/
    });

});
