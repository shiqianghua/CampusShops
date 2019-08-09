package com.java.o2o.web.frontend;

import com.java.o2o.entity.ProductCategory;
import com.java.o2o.entity.ProductTags;
import com.java.o2o.entity.ShopCategory;
import com.java.o2o.enums.Code;
import com.java.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value = "/frontend")
public class ProductCategoryController {

    @Autowired
    ProductCategoryService productCategoryService;

    @RequestMapping(value = "/tags")
    @ResponseBody
    public Map shopListTags()
    {
        Map<String,Object> modelMap=new HashMap<String, Object>();

        List<ProductCategory> productCategoryListAll = productCategoryService.getProductCategoryListAll();
        List<ProductTags> productTags = exstractionData(productCategoryListAll);

        if(productCategoryListAll.size()==0)
        {
            Integer code = Code.ERROR.getCode();
            modelMap.put("code",code);
            modelMap.put("list",null);
            return modelMap;
        }

        Integer code = Code.ERROR.getCode();
        modelMap.put("code",code);
        modelMap.put("list",productTags);

        return modelMap;
    }

    public List<ProductTags> exstractionData(List<ProductCategory> productCategoryList)
    {
        LinkedList<ProductTags> dataLinkedList= new LinkedList<>();
        for (ProductCategory productCategory:productCategoryList)
        {
            Long productCategoryId = productCategory.getProductCategoryId();
            String productCategoryName = productCategory.getProductCategoryName();

            dataLinkedList.add(new ProductTags(productCategoryName,productCategoryId));
        }

        return dataLinkedList;
    }

}
