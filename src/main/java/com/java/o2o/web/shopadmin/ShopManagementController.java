package com.java.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.o2o.dto.ImageHolder;
import com.java.o2o.dto.ShopExecution;
import com.java.o2o.entity.Area;
import com.java.o2o.entity.PersonInfo;
import com.java.o2o.entity.Shop;
import com.java.o2o.entity.ShopCategory;
import com.java.o2o.enums.ShopStateEnum;
import com.java.o2o.service.AreaService;
import com.java.o2o.service.ShopCategoryService;
import com.java.o2o.service.ShopService;
import com.java.o2o.util.CodeUtil;
import com.java.o2o.util.HttpServletRequestUtil;
import com.java.o2o.util.ImageUtil;
import com.java.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* 店家对店铺信息完全管理
* */

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "getshopmanagementinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getShopManagementInfo(HttpServletRequest request)
    {
        Map<String,Object> modelMap=new HashMap<String, Object>();
        long shopId = HttpServletRequestUtil.getLong(request,"shopId");

//        店铺ID为空，重回登录界面
        if(shopId <=0 )
        {
            Object currentShopObj=request.getSession().getAttribute("currentShop");
            if(currentShopObj==null)
            {
                modelMap.put("redirect",true);
                modelMap.put("url","shop/shoplist");
            }
            else
                {
                    Shop currentShop=(Shop) currentShopObj;
                    modelMap.put("redirect",false);
                    modelMap.put("shopId",currentShop.getShopId());
                }
        }
        else
            {
                Shop currentShop=new Shop();
                currentShop.setShopId(shopId);
                request.getSession().setAttribute("currentShop",currentShop);
                modelMap.put("redirect",false);
            }
        return modelMap;
    }


//进入登录界面，查看自己已经登录店铺的界面
    @RequestMapping(value = "/getshoplist",method=RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getShopList(HttpServletRequest request)
    {
        Map<String,Object> modelMap=new HashMap<String, Object>();
        PersonInfo user=new PersonInfo();
        user.setUserId(1L);
        user.setName("test");
        request.getSession().setAttribute("user",user);
        user=(PersonInfo)request.getSession().getAttribute("user");

        try
        {
            Shop shopCondition=new Shop();
            shopCondition.setOwner(user);
            ShopExecution se=shopService.getShopList(shopCondition,0,100);
            modelMap.put("shopList",se.getShopList());
            modelMap.put("user",user);
            modelMap.put("success",true);
        }catch (Exception e)
            {
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
            }

            return modelMap;
    }

//通过ID获取店铺的信息
    @RequestMapping(value = "/getshopbyid",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getShopById(HttpServletRequest request)
    {
        Map<String ,Object> modelMap=new HashMap<String, Object>();
        Long shopId=HttpServletRequestUtil.getLong(request,"shopId");

        if(shopId > -1)
        {
            try
            {
                Shop shop=shopService.getByShopId(shopId);
                List<Area> areaList=areaService.getAreaList();
                modelMap.put("shop",shop);
                modelMap.put("areaList",areaList);
                modelMap.put("success",true);
            }catch (Exception e)
            {
                modelMap.put("success",false);
                modelMap.put("errMsg",e.toString());
            }
        }else
            {
                modelMap.put("success",false);
                modelMap.put("errMsg","empty shopId");
            }

        return modelMap;
    }



    @RequestMapping(value = "/getshopinitinfo")
    @ResponseBody
    private Map<String,Object> getShopInitInfo()
    {
        Map<String,Object> modelMap=new HashMap<String, Object>();
        List<ShopCategory> shopCategoryList=new ArrayList<ShopCategory>() ;
        List<Area> areaList=new ArrayList<Area>();

        try
        {
            //按照parent_id条件，查询tb_shop_category表格中的店铺
            shopCategoryList=shopCategoryService.getShopCategoryList(new ShopCategory());
            areaList=areaService.getAreaList();
            modelMap.put("shopCategoryList",shopCategoryList);
            modelMap.put("areaList",areaList);
            modelMap.put("success",true);
        }
        catch (Exception e)
        {
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
        }
        return modelMap;
    }

    @RequestMapping(value = "/registershop",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> registerShop(HttpServletRequest request)
    {
        Map<String,Object> modelMap=new HashMap<String, Object>();

        if(!CodeUtil.checkVerifyCode(request))
        {
            modelMap.put("success",false);
            modelMap.put("errMsg","输入了错误的验证码！");
            return modelMap;
        }

//        1.接受并转化相应的参数，包括店铺信息以及图片
        String shopStr = HttpServletRequestUtil.getString(request,"shopStr");
        ObjectMapper mapper=new ObjectMapper();
        Shop shop=null;
        try
        {
            shop=mapper.readValue(shopStr,Shop.class);//从前台获取的shopStr对象转换为实体类对象

        }
        catch (Exception e)
        {
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }

        CommonsMultipartFile shopImg=null;
        CommonsMultipartResolver commonsMultipartResolver=new CommonsMultipartResolver(
                request.getSession().getServletContext()
        );

        if(commonsMultipartResolver.isMultipart(request))
        {
            MultipartHttpServletRequest multipartHttpServletRequest=(MultipartHttpServletRequest)request;
            shopImg=(CommonsMultipartFile)multipartHttpServletRequest.getFile("shopImg");
        }
        else
        {
            modelMap.put("sucess",false);
            modelMap.put("errMsg","上传图片不能为空");
            return modelMap;
        }

// 2.注册店铺

        if(shop!=null && shopImg!=null)
        {
            PersonInfo owner=(PersonInfo)request.getSession().getAttribute("user");                 /*new PersonInfo();*/
            /*owner.setUserId(1L);*/
            shop.setOwner(owner);

            ShopExecution se= null;
            try
            {

//                se = shopService.addShop(shop,shopImg.getInputStream(),shopImg.getOriginalFilename());
//                se = shopService.addShop(shop,shopImg.getInputStream(),shopImg.getOriginalFilename());
//                  se = shopService.addShop(shop,shopImg.getInputStream(),shopImg.getOriginalFilename());

                //将传入过来的商铺信息和图片交给service进行添加处理
                ImageHolder imageHolder=new ImageHolder(shopImg.getOriginalFilename(),shopImg.getInputStream());
                se = shopService.addShop(shop,imageHolder);
                if (se.getState()== ShopStateEnum.CHECK.getState())
                {
                    modelMap.put("success",true);
//                    该用户可以操作的店铺列表
                    List<Shop> shopList=(List<Shop>)request.getSession().getAttribute("shopList");

                    if(shopList==null || shopList.size()==0)
                     {
                        shopList =new ArrayList<Shop>();
                     }
                     shopList.add(se.getShop());
                     request.getSession().setAttribute("shopList",shopList);

                } else
                    {
                        modelMap.put("success",false);
                        modelMap.put("errMsg",se.getStateInfo());
                    }

                } catch (IOException e) {
                modelMap.put("success",false);
                modelMap.put("errMsg",se.getStateInfo());
            }

            //        3.返回店铺结果
                return modelMap;
        }
        else
        {
            modelMap.put("sucess",false);
            modelMap.put("errMsg","上传图片不能为空");
            return modelMap;
        }

    }

//修改店铺信息，
    @RequestMapping(value = "/modifyshop",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> modifyShop(HttpServletRequest request)
    {
        Map<String,Object> modelMap=new HashMap<String, Object>();

        if(!CodeUtil.checkVerifyCode(request))
        {
            modelMap.put("success",false);
            modelMap.put("errMsg","输入了错误的验证码！");
            return modelMap;
        }

//        1.接受并转化相应的参数，包括店铺信息以及图片
        String shopStr = HttpServletRequestUtil.getString(request,"shopStr");
        ObjectMapper mapper=new ObjectMapper();
        Shop shop=null;
        try
        {
            shop=mapper.readValue(shopStr,Shop.class);//从前台获取的shopStr对象转换为实体类对象

        }catch (Exception e)
        {
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }

        CommonsMultipartFile shopImg=null;
        CommonsMultipartResolver commonsMultipartResolver=new CommonsMultipartResolver(
                request.getSession().getServletContext()
        );

        if(commonsMultipartResolver.isMultipart(request))
        {
            MultipartHttpServletRequest multipartHttpServletRequest=(MultipartHttpServletRequest)request;
            shopImg=(CommonsMultipartFile)multipartHttpServletRequest.getFile("shopImg");
        }

// 2.修改店铺信息

        if(shop!=null && shop.getShopId()!=null)
        {

            /*无需修改用户的信息
            PersonInfo owner=new PersonInfo();
            owner.setUserId(1L);
            shop.setOwner(owner);
*/

            ShopExecution se= null;
            try
            {
                if (shopImg==null)
                {
                    se=shopService.modifyShop(shop,null);

                }else
                {
//                    se=shopService.modifyShop(shop,shopImg.getInputStream(),shopImg.getOriginalFilename());

                    ImageHolder imageHolder=new ImageHolder(shopImg.getOriginalFilename(),shopImg.getInputStream());
                    se=shopService.modifyShop(shop,imageHolder);

                }

                if (se.getState()== ShopStateEnum.SUCCESS.getState())
                {
                    modelMap.put("success",true);
                }
                else
                {
                    modelMap.put("success",false);
                    modelMap.put("errMsg",se.getStateInfo());
                }
            } catch (IOException e) {
                modelMap.put("success",false);
                modelMap.put("errMsg",se.getStateInfo());
            }

            //        3.返回店铺结果
                return modelMap;
        }
        else
        {
            modelMap.put("sucess",false);
            modelMap.put("errMsg","请输入店铺的id");
            return modelMap;
        }

    }

    /*
             File shopImgFile=new File(PathUtil.getImgBasePath()+ ImageUtil.getRandomFileName());


           try
           {
               shopImgFile.createNewFile();
           }
           catch (IOException e)
           {
               modelMap.put("success",false);
               modelMap.put("errMsg",e.getMessage());
               return modelMap;
           }

         try {
                inputStreamToFile(shopImg.getInputStream(),shopImgFile);
            } catch (IOException e) {
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
                return modelMap;
            }

    private static void inputStreamToFile(InputStream inputStream,File file)
    {
        FileOutputStream os=null;
        try
        {
            os=new FileOutputStream(file);
            int bytesRead=0;
            byte[] buffer=new byte[1024];
            while ((bytesRead=inputStream.read(buffer))!=-1)*//*读取1024个字节后，向指定文件写入*//*
            {
                os.write(buffer,0,bytesRead);
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException("调用inputStreamToFile产生异常"+e.getMessage());
        }
        finally
        {
            try
            {
                if(os!=null)
                    os.close();
                if(inputStream!=null)
                    inputStream.close();
            }
            catch (IOException e)
            {
                throw new RuntimeException("inputStreamToFile关闭Io产生异常:"+e.getMessage());
            }
        }
    }*/
}
