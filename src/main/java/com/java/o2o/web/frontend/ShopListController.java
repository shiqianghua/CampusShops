package com.java.o2o.web.frontend;


import com.java.o2o.entity.*;
import com.java.o2o.enums.Code;
import com.java.o2o.pt.UserCF;
import com.java.o2o.service.UserService;
import com.java.o2o.util.GetUserInfo;
import com.java.o2o.dto.ShopExecution;
import com.java.o2o.service.AreaService;
import com.java.o2o.service.ShopCategoryService;
import com.java.o2o.service.ShopService;
import com.java.o2o.util.HttpServletRequestUtil;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/frontend")
public class ShopListController {
    @Autowired
    private AreaService areaService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserCF userCF;
    @Autowired
    private GetUserInfo getUserInfo;
    @Autowired
    ProductListController productList;


    /*返回商店列表的ShopCategory列表以及区域信息（二级或者一级）*/
    @RequestMapping(value = "/listshopspageinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> listShopsPageInfo(HttpServletRequest request)
    {
        Map<String,Object> modelMap=new HashMap<String, Object>();
//        从前端获取parentId
        long parentId= HttpServletRequestUtil.getLong(request,"parentId");
        List<ShopCategory> shopCategoryList=null;

        if(parentId != -1)
        {
//        如果parentId存在，则取出一级商店铺类别的二级店铺类别列表
            try
            {
                ShopCategory shopCategoryCondition=new ShopCategory();
                ShopCategory parent=new ShopCategory();
                parent.setShopCategoryId(parentId);
                shopCategoryCondition.setParent(parent);
                shopCategoryList =shopCategoryService.getShopCategoryList(shopCategoryCondition);
            }catch (Exception e)
            {
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
            }
        }else
        {
            try
            {
//       如果parentId不存在，
//         取出一级店铺列表
                shopCategoryList=shopCategoryService.getShopCategoryList(null);

            }catch (Exception e)
            {
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
            }
        }

            modelMap.put("shopCategoryList",shopCategoryList);

            List<Area> areaList=null;
            try
            {
//                获取区域列表信息
                areaList=areaService.getAreaList();
                modelMap.put("areaList",areaList);
                modelMap.put("success",true);
            }catch (Exception e)
            {
//
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
            }

        return modelMap;
    }

    /*获取指定查询条件下的店铺列表*/
    @RequestMapping(value = "/listshops",method =RequestMethod.GET )
    @ResponseBody
    private Map<String,Object> listShops(HttpServletRequest request)
    {

//        hasmore,shoplist,limit,offset

        Map<String,Object> modelMap=new HashMap<String, Object>();
        int pageIndex=HttpServletRequestUtil.getInt(request,"pageIndex");
//        偏移量
        int pageSize=HttpServletRequestUtil.getInt(request,"pageSize");

        if ((pageIndex > -1) && (pageSize>-1))
        {
//            获取一级类别Id
            long parentId=HttpServletRequestUtil.getLong(request,"parentId");
//            获取二级商品类别Id
            long shopCategoryId=HttpServletRequestUtil.getLong(request,"shopCategoryId");
            int areaId=HttpServletRequestUtil.getInt(request,"areaId");

            String shopName=HttpServletRequestUtil.getString(request,"shopName");
            Shop shopCondition =compactProductCondition4Search(parentId,shopCategoryId,areaId,shopName);
//            获取商品列表
            ShopExecution se=shopService.getShopList(shopCondition,pageIndex,pageSize);

            modelMap.put("shopList",se.getShopList());
            modelMap.put("count",se.getCount());
            modelMap.put("success",true);
        }
        else
        {
            modelMap.put("success",false);
            modelMap.put("errMsg","Empty pageSize or pageIndex");
        }
        return modelMap;
    }

    private Shop compactProductCondition4Search(long parentId,long shopCategoryId,int areaId,String shopNme) {
        Shop shopCondition=new Shop();

        if(parentId != -1)
        {
//            查询某个一级店铺类别下面的所有二级店铺的店铺列表
            ShopCategory childCategory=new ShopCategory();
            ShopCategory parentCategory=new ShopCategory();
            parentCategory.setShopCategoryId(parentId);
            childCategory.setParent(parentCategory);
            shopCondition.setShopCategory(childCategory);
        }

        if (shopCategoryId != -1L)
        {
//            查询某个二级店铺下面的店铺列表
            ShopCategory shopCategory=new ShopCategory();
            shopCategory.setShopCategoryId(shopCategoryId);
            shopCondition.setShopCategory(shopCategory);
        }

        if (areaId != -1L)
        {
//            查询位于区域Id下的店铺列表
            Area area=new Area();
            area.setAreaId(areaId);
            shopCondition.setArea(area);
        }

        if (shopNme !=null)
        {
//查询名字里包含shopName的店铺列表
            shopCondition.setShopName(shopNme);
        }

//        展示审核通过的店铺
        shopCondition.setEnableStatus(1);

        return  shopCondition;
    }



//    登录
    @RequestMapping(value = "/user",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> recommendedShops(HttpServletRequest request)
    {
//        request.getSession()

        Map<String,Object> modelMap=new HashMap<String, Object>();



        User user = new User();
//        将用户信息插入数据库
        userService.insertUser(user);
//        更新用户数据集
        getUserInfo.getUserInfoData();
//   数据集训练
        try {
            userCF.nearUser(user);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TasteException e) {
            e.printStackTrace();
        }

//        获取用户数据集信息推荐列表
        List<User> listSpidAndImid = userCF.getListSpidAndImid();

        List<Shop> shopList=new LinkedList<Shop>();

        for (User user1:listSpidAndImid)
        {
            Long shopId = user1.getShopId();
            Shop byShopId = shopService.getByShopId(shopId);
            shopList.add(byShopId);
        }

        modelMap.put("shopList",shopList);
        modelMap.put("shopCounts",shopList.size());

        return modelMap;
    }



}
