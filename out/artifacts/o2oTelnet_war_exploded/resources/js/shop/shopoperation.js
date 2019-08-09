$(function ()
{
    var shopId=getQueryString('shopId');
    var isEdit=shopId?true:false;

    var initUrl='getshopinitinfo';
    var registerShopUrl='registershop';

    var shopInfoUrl="getshopbyid?shopId="+shopId;
    var editShopUrl='modifyshop';

    if(!isEdit)
    {
        getShopInitInfo();
    }
    else
    {
        getShopInfo(shopId);
    }

    //数据传到前台
    function getShopInfo(shopId)//返回shop对象，对应data
    {
        $.getJSON(shopInfoUrl,function (data) //通过get请求得到json数据,url用于提供json数据的地址页
        {
            if(data.success)
            {
                var shop=data.shop;
                $('#shop-name').val(shop.shopName);
                $('#shop-addr').val(shop.shopAddr);
                $('#shop-phone').val(shop.phone);
                $('#shop-desc').val(shop.shopDesc);

                alert("执行步骤1");

                var shopCategory='<option data-id="'+shop.shopCategory.shopCategoryId+'">'+shop.shopCategory.shopCategoryName+'</option>';

                alert("执行步骤2");

                var tempAreaHtml='';
                data.areaList.map(function (item,index)
                {
                    tempAreaHtml+='<option data-id="'+item.areaId+'">'
                    +item.areaName+'</option>';
                });

                //alert("执行步骤3");

                $('#shop-category').html(shopCategory);
                $('#shop-category').attr('disabled','disabled');//店铺不能修改
                $('#area').html(tempAreaHtml);
                $("#area option[data-id='"+shop.area.areaId+"']").attr('selected','selected');

            }
        });
    }




   /* getShopInitInfo();
    alert(initUrl);*/

   //数据传给后台
    function getShopInitInfo() {

        $.getJSON(initUrl,function (data) {

            if(data.success)
            {
                var tempHtml='';
                var tempAreaHtml='';/*获取区域信息*/
                data.shopCategoryList.map(function (item, index)/*遍历列表*/ {
                    tempHtml+='<option data-id="'+item.shopCategoryId
                        +'">'+item.shopCategoryName+'</option>';
                });
                data.areaList.map(function (item,index)
                {
                    tempAreaHtml+='<option data-id="'+item.areaId+'">'
                        +item.areaName+'</option>;'
                });
                $('#shop-category').html(tempHtml);/*获取到信息放入到id=shop-category元素中*/
                $('#area').html(tempAreaHtml);
            }
        });
    }

        $('#submit').click(function () {
            var shop={};

            if(isEdit)
            {
                shop.shopId=shopId;
            }

            shop.shopName=$('#shop-name').val();
            shop.shopAddr=$('#shop-addr').val();
            shop.phone=$('#shop-phone').val();
            shop.shopDesc=$('#shop-desc').val();
            shop.shopCategory={
                shopCategoryId:$('#shop-category').find('option').not(function () {
                    return !this.selected;
                }).data('id')
            };
            shop.area={
                areaId:$('#area').find('option').not(function () {
                    return !this.selected;
                }).data('id')
            };

            var shopImg=$('#shop-img')[0].files[0];/*获取上传的缩略图文件*/

            var formData=new FormData();
            formData.append('shopImg',shopImg);
            formData.append('shopStr',JSON.stringify(shop));/*JSON.stringify（）将对象转化为字符串*/

            var verifyCodeActual=$('#j_captcha').val();/*获取验证码填写框填写的值*/
            if(!verifyCodeActual)
            {
                //$.toast('请输入验证码！');
                alert("验证码为空，请输入验证码");
                return;
            }

            formData.append('verifyCodeActual',verifyCodeActual);

            $.ajax({
                url:(isEdit ? editShopUrl:registerShopUrl),
                type:'POST',
                data:formData,
                contentType:false,
                processData:false,
                cache:false,
                success:function (data) {
                    if(data.success)
                    {
                        alert('提交成功');
                        // console.log(data);
                    }
                    else
                    {

                        alert('提交失败！'+data.errMsg);
                        //$.toast('提交失败！');
                    }

                    $('#captcha_img').click();
                }
            });

        });




    //alert("程序快要结束！");
});