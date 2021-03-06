$(function ()
{


    getlist();

    function getlist()
    {
      $.ajax
      ({
              url:"getshoplist",
              type:"get",
              dataType:"json",
              success:function(data)
              {
                  if(data.success)
                  {
                      handleList(data.shopList);
                      //alert("执行步骤1");
                      handleUser(data.user);
                      //alert("执行步骤2");
                  }
              }
      });
    }

    function handleUser(data) {
        //alert("执行handleUser函数：");
        $('#user-name').text(data.name);
        //alert("执行handleUser函数结束！");
    }

    function handleList(data) {
        var html='';
        data.map(function (item,index) {
            html+='<div class="row row-shop"><div class="col-40">'
                +item.shopName+'</div><div class="col-40"> '
            +shopStatus(item.enableStatus)
            +'</div><div class="col-20">'
            +goShop(item.enableStatus,item.shopId)+'</div></div>';
        });

        $('.shop-wrap').html(html);
    }

    function shopStatus(status) {
        if(status ==0 )
        {
            return '审核中';
        }
        else if(status== -1)
            {
                return '非法店铺';
            }
        else if(status == 1)
            {
                return '审核通过';
            }
    }

    function goShop(status ,id) {
        if(status ==1)
        {
            return '<a href="shopmanage?shopId='+id+'">进入</a>';

        }else
            {
                return '';
            }
    }


})