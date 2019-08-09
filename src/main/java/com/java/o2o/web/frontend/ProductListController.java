package com.java.o2o.web.frontend;


import com.java.o2o.entity.Product;
import com.java.o2o.entity.ProductRequireData;
import com.java.o2o.enums.Code;
import com.java.o2o.service.ProductService;
import com.java.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping(value = "/product")
public class ProductListController {

    @Autowired
    ProductService productService;

    @RequestMapping(value = "/list")
    @ResponseBody
    public Map productListShow(HttpServletRequest request/*@RequestParam("limit") int limit, @RequestParam("offset") int offset*/)
    {
        int limit= HttpServletRequestUtil.getInt(request,"limit");
        int offset=HttpServletRequestUtil.getInt(request,"offset");

//        Map modelMap=new HashMap<>();

        HashMap hashMap = new LinkedHashMap<>();
        LinkedList<ProductRequireData> objects = new LinkedList<ProductRequireData>();
        boolean hasMore;

        if((offset<=0 || limit<=0) )
        {
            Integer code = Code.ERROR.getCode();
            hasMore=false;
            hashMap.put("code",code);
            hashMap.put("hasMore",hasMore);
            hashMap.put("list",null);
        }
        else
        {
            List<Product> productListMorePage = productService.getProductListMorePage(limit, offset);
            List<ProductRequireData> productRequireData = exstractionData(productListMorePage);

            if (productListMorePage.size()==0)
            {
                Integer code = Code.ERROR.getCode();
                hashMap.put("code",code);

                hasMore=false;
                hashMap.put("hashMore",hasMore);
                hashMap.put("list",null);
            }else
            {
                Integer code = Code.SUCCESS.getCode();
                hasMore=true;

                hashMap.put("code",code);
                hashMap.put("hasMore",hasMore);

//            for(ProductRequireData productRequireData1: productRequireData)
                hashMap.put("list",productRequireData);

            }


        }

            return hashMap;
    }

    public List<ProductRequireData> exstractionData(List<Product> productList)
    {
        LinkedList<ProductRequireData> dataLinkedList= new LinkedList<>();

//        ProductRequireData[] productRequireData=null;
//        for (int i=0;i<100;i++)
//        {
//            productRequireData[i]=new ProductRequireData();
//        }
        int count=0;

        for (Product product:productList)
        {
//            productRequireData[count]=new ProductRequireData();
            String imgAddr = product.getImgAddr();
            String productName=product.getProductName();
            Double normalPrice=product.getNormalPrice();
            Integer saleNum = product.getSaleNum();
            Long productId = product.getProductId();
//            productRequireData[count]=new ProductRequireData(imgAddr,productName,normalPrice,saleNum,productId);
//            count++;

            dataLinkedList.add(new ProductRequireData(imgAddr,productName,normalPrice,saleNum,productId));
        }

//        return productRequireData;
        return dataLinkedList;
    }

}
