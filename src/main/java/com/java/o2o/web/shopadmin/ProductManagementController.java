package com.java.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.o2o.dto.ImageHolder;
import com.java.o2o.dto.ProductExecution;
import com.java.o2o.entity.Product;
import com.java.o2o.entity.ProductCategory;
import com.java.o2o.entity.Shop;
import com.java.o2o.enums.ProductStateEnum;
import com.java.o2o.exceptions.ProductOperationException;
import com.java.o2o.service.ProductCategoryService;
import com.java.o2o.service.ProductService;
import com.java.o2o.service.ShopService;
import com.java.o2o.util.CodeUtil;
import com.java.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
* 商品详情页管理
* */
@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ShopService shopService;

//    支持上传商品详情图的最大数量
    private static final int IMAGEMAXCOUNT=6;

    /*通过商品Id获取商品信息*/
    @RequestMapping(value = "/getproductbyid",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getProductById(@RequestParam Long productId)
    {
        Map<String,Object> modelMap=new HashMap<String, Object>();

        if(productId> -1)
        {


//            获取商品信息
            Product product=productService.getProductById(productId);

//            店铺信息
            Long shopId=product.getProductId();
            product.setShop(shopService.getByShopId(shopId));

//            获取该店铺下的商品类别列表
            List<ProductCategory> productCategoryList=productCategoryService.getProductCategoryList(product.getShop().getShopId());
            modelMap.put("product",product);
            modelMap.put("productCategoryList",productCategoryList);
            modelMap.put("success",true);

        }
        else
        {
            modelMap.put("success",true);
            modelMap.put("errMsg","empty productId");
        }

        return modelMap;
    }

    /*商品编辑*/
    @RequestMapping(value = "modifyproduct",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> modifyProduct(HttpServletRequest request)
    {
        Map<String,Object> modelMap=new HashMap<String, Object>();

//        商品编辑时调用还是上下架操作调用，statusChange改变上下架状态
//        若为前者则进行验证码判断，后者则跳过

        boolean statusChange=HttpServletRequestUtil.getBoolean(request,"statusChange");

        if (!statusChange && !CodeUtil.checkVerifyCode(request))
        {
            modelMap.put("success",false);
            modelMap.put("errMsg","输入了错误的验证码");
            return modelMap;
        }
//        接收前端参数的变量的初始值化，包括商品缩略图，详情图列表实体类
        ObjectMapper mapper=new ObjectMapper();
        Product product=null ;
        ImageHolder thumbnail =null;
        List<ImageHolder> productImgList=new ArrayList<ImageHolder>();
        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(request.getSession().getServletContext());

//        若请求中存在文件流，则取出相关的文件（包括缩略图和详情图）
        try
        {
            if (multipartResolver.isMultipart(request))
            {
                thumbnail = HandleImage((MultipartHttpServletRequest)request, productImgList);
            }
        }
        catch (Exception e )
        {
            modelMap.put("success",false);
            modelMap.put("errMag",e.toString());
        }

        try
        {
            String productStr=HttpServletRequestUtil.getString(request,"productStr");
//            尝试获取前端传过来的表单string流并将其转换为Product实体类
            product=mapper.readValue(productStr,Product.class);
        }catch (Exception e)
        {
            modelMap.put("success",false);
            modelMap.put("errMsg",e.toString());
            return modelMap;
        }

        if (product != null)
        {
            try
            {
//                从前端获取当前店铺的product，减少对前端依赖
//                获取店铺信息
               /* String shopStr= HttpServletRequestUtil.getString(request,"currentShop");
                ObjectMapper mapper2=new ObjectMapper();
                Shop shop=new Shop();
                try {
                    shop=mapper2.readValue(shopStr,Shop.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }


//           */ Shop currentShop=(Shop) request.getSession().getAttribute("currentShop");
                product.setShop(currentShop);

//                开始进行商品信息变更操作
                ProductExecution pe=productService.modifyProduct(product,thumbnail,productImgList);

                if(pe.getState() == ProductStateEnum.SUCCESS.getState())
                {
                    modelMap.put("success",true);

                }else
                {
                    modelMap.put("success",false);
                    modelMap.put("errMsg",pe.getStateInfo());
                }
            }catch (RuntimeException e)
            {
                modelMap.put("success",false);
                modelMap.put("errMsg",e.toString());
            }
        }else
        {
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入商品信息");

        }

        return modelMap;
    }

    @RequestMapping(value = "/getproductlistbyshop",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getProductListByShop(HttpServletRequest request)
    {
        Map<String,Object> modelMap=new HashMap<String, Object>();
        int pageIndex=HttpServletRequestUtil.getInt(request,"pageIndex");
        int pageSize=HttpServletRequestUtil.getInt(request,"pageSize");
        Shop currentShop=(Shop) request.getSession().getAttribute("currentShop");
        if ((pageIndex > -1) && (pageSize > -1) && (currentShop !=null) && (currentShop.getShopId() !=null))
        {
            long productCategoryId=HttpServletRequestUtil.getLong(request,"productCategoryId");
            String productName=HttpServletRequestUtil.getString(request,"productName");
            Product productCondition=compactProductCondition(currentShop.getShopId(),productCategoryId,productName);
            ProductExecution pe=productService.getProductList(productCondition,pageIndex,pageSize);
            modelMap.put("productList",pe.getProductList());
            modelMap.put("count",pe.getCount());
            modelMap.put("success",true);

        }
        else
        {
            modelMap.put("success",false);
            modelMap.put("errMsg","alert Empty");
        }
        return modelMap;
    }

    private Product compactProductCondition(Long shopId, long productCategoryId ,String productName) {
        Product productCondition=new Product();
        Shop shop=new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);
//      若有指定类别的则要求添加进去
        if (productCategoryId != -1L)
        {
            ProductCategory productCategory=new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }
//        若有商品名模糊查询的要求则添加进去
        if (productName !=null )
        {
            productCondition.setProductName(productName);
        }

        return productCondition;
    }


    @RequestMapping(value = "/addproduct",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> addProduct(HttpServletRequest request)
    {
        Map<String,Object> modelMap=new HashMap<String, Object>();

//        验证码校正
        if (!CodeUtil.checkVerifyCode(request))
        {
            modelMap.put("success",false);
            modelMap.put("errMsg","输入了错误的验证码");
            return modelMap;
        }

//      前端参数的变量的初始化，包括商品，缩略图，详情图列表实体类
        ObjectMapper mapper=new ObjectMapper();
        Product product=null;
        String productStr= HttpServletRequestUtil.getString(request,"productStr");
        MultipartHttpServletRequest multipartRequest=null;
        ImageHolder thumbnail=null;
        List<ImageHolder> productImgList=new ArrayList<ImageHolder>();
        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(request.getSession().getServletContext());
        try
        {
//            若请求中存在文件流，则取出相关的文件（包括缩略图和详情图）
            if (multipartResolver.isMultipart(request))
            {
                thumbnail = HandleImage((MultipartHttpServletRequest) request, productImgList);

            }
            else
                {
                    modelMap.put("success",false);
                    modelMap.put("errMsg","上传图片不能为空");
                    return modelMap;
                }
        }catch (Exception e)
        {
            modelMap.put("success",false);
            modelMap.put("errMsg",e.toString());
            return modelMap;
        }

        try
        {
            product=mapper.readValue(productStr,Product.class);

        }catch (Exception e)
        {
            modelMap.put("success",false);
            modelMap.put("errMsg",e.toString());
            return modelMap;
        }

        if (product !=null && thumbnail !=null &&productImgList.size() >0)
        {
            try
            {
//                从session中获取当前的Id并赋值给product，减少对前端数据的依赖
                Shop currentShop=(Shop) request.getSession().getAttribute("currentShop");
                /*Shop shop=new Shop();
                shop.setShopId(currentShop.getShopId());*/
                product.setShop(currentShop);

//                执行添加操作
                ProductExecution pe=productService.addProduct(product,thumbnail,productImgList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState())
                {
                    modelMap.put("success",true);

                }
                else
                {
                    modelMap.put("success",false);
                    modelMap.put("success",pe.getState());
                }

            }catch (ProductOperationException e)
            {
                modelMap.put("success",false);
                modelMap.put("errMag",e.toString());
                return modelMap;
            }
        }

        return modelMap;
    }

    private ImageHolder HandleImage(MultipartHttpServletRequest request, List<ImageHolder> productImgList) throws IOException {
        MultipartHttpServletRequest multipartRequest;
        ImageHolder thumbnail;
        multipartRequest= request;
//                取出缩略图并构建ImageHolder对象
        CommonsMultipartFile thumbnailFile=(CommonsMultipartFile) multipartRequest.getFile("thumbnail");
        thumbnail=new ImageHolder(thumbnailFile.getOriginalFilename(),thumbnailFile.getInputStream());
//取出详情图列表并构建List<ImageHolder>列表对象，最多支持六张图片上传
        for (int i=0;i<IMAGEMAXCOUNT;i++)
        {
            CommonsMultipartFile productImgFile=(CommonsMultipartFile) multipartRequest.getFile("productImg"+i);
            if (productImgFile !=null)
            {
//                      若取出的第i个详情图片文件流不为空，则将其加入详情图列表
                ImageHolder productImg=new ImageHolder(productImgFile.getOriginalFilename(),productImgFile.getInputStream());
                productImgList.add(productImg);
            }
            else
            {
//                        若取出的第i个详情图片文件流为空，则终止循环
                break;
            }
        }
        return thumbnail;
    }
}
