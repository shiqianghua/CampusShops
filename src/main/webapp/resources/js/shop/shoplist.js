// var shopId;
// var phoneNum;
// var count=0;
$(function ()
{


    getlist();

    // permissionValidate();
    //
    // function ()
    // {
    //
    // }



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
                      data
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
            phoneNum=item.phone;
            // alert("电话号码为："+phoneNum);
            shopId=item.shopId;
            // count++;
            html+='<div class="row row-shop"><div class="col-40">'
                +item.shopName+'</div><div class="col-40"> '
            +shopStatus(item.enableStatus)
            +'</div><div class="col-20">'
            +goShop(item.enableStatus,item.shopId)+'</div></div>';
        });

        $('.shop-wrap').html(html);
    }




    // document.getElementById("click").onclick=function(data)
    // {
    //
    // }

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

    function goShop(status,shopId) {
        if(status ==1)
        {

            return '<a id="goshop" href="shopmanage?shopId='+shopId+'">进入</a>';

            /*'<input type="button" value="进入" onclick="dataCheck();" />';*/

                // '<form action="/shopmanage" enctype="multipart/form-data" method="post">\n' +
                // '    shopId: <input type="text" name="username">\n' +
                // '    phone: <input type="password" name="password">\n' +
                // '    <input type="submit">\n' +
                // '</form>';
            // return '<form name="goshop" method="post">\n' +
            //     // '  <div>Email:<input type="text" name="email" id="email"/></div>\n' +
            //     '  <div>电话号码:<input type="password" name="password" id="password"/></div>\n' +

            //     // '  <div>重输密码:<input type="password" name="repassword" id="repassword"/></div>\n' +
            //     // '  <div>姓名:<input type="text" name="name" id="name"/></div>\n' +
            //     // '  <div><input id="bt" type="submit" value="注册"/></div>\n' +
            //     '</form>';
        }else
            {
                return '';
            }
    }


})

// function dataCheck()
// {
//     var phone=prompt("请输入您的电话号码：","");
//
//     if(phone==phoneNum)
//     {
//         var  checkUrl='shopmanage?shopId='+shopId+'phone='+phoneNum;
//         $.getJSON(checkUrl,
//             function (data) {});
//     }else
//     {
//         alert("电话号码不正确");
//     }

// }