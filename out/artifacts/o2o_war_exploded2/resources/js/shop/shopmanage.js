$(function ()
{
   var shopId=1;
   var shopId=getQueryString('shopId');
   var shopInfoUrl='getshopmanagementinfo?shopId='+shopId;

   $.getJSON(shopInfoUrl,function (data) {
       if(data.redirect)
       {
           window.location.href=data.url;

       }else
        {
            if(data.shopId !=undefined && data.shopId !=null)
            {
                shopId=data.shopId;
            }

            $('#shopInfo').attr('href','shopoperation?shopId='+shopId);
        }
   })

});