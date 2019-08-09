package com.java.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/shopadmin",method={RequestMethod.GET})
public class ShopAdminController {

    @RequestMapping(value = "login")
    public String login()
    {
        return "login/login";
    }

    @RequestMapping(value = "/shopoperation")
    public String shopOperation()
    {
        return "shop/shopoperation";
    }

    @RequestMapping(value = "/shoplist")
    public String shopList(/*@RequestParam("shopId") Long productId*//*,@RequestParam("phone")*/)
    {
//        HttpSession session = request.getSession();
//        Long product=productId;
        return "shop/shoplist";
    }

    @RequestMapping(value = "/shopmanage")
    public String shopManage()
    {
        return "shop/shopmanage";
    }

    @RequestMapping(value = "/productcategorymanage",method = RequestMethod.GET)
    public String ProductCategoryManage()
    {
        return "shop/productcategorymanage";
    }

    @RequestMapping(value = "/productoperation")
    public String productOperation()
    {
//        转发至商品添加和编辑页面
        return "shop/productoperation";
    }

    @RequestMapping(value = "/productmanage")
    public String productManage()
    {
        return "shop/productmanage";
    }

}
