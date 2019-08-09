package com.java.o2o.web.frontend;

import com.java.o2o.entity.HeadLine;
import com.java.o2o.entity.HeadLineInfo;
import com.java.o2o.entity.ShopCategory;
import com.java.o2o.enums.Code;
import com.java.o2o.service.HeadLineService;
import com.java.o2o.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/frontend")
public class MainPageController {
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private HeadLineService headLineService;

//    初始化主页信息
    @RequestMapping(value = "/listmainpageinfo",method = RequestMethod.GET)
    @ResponseBody
   private Map<String,Object> listMainPageInfo()
    {
        Map<String,Object> modelMap=new HashMap<String, Object>();
        List<ShopCategory> shopCategoryList=new ArrayList<ShopCategory>();

        try
        {
//            获取一级店铺类别列表（即parentId空的ShopCategory）
            shopCategoryList=shopCategoryService.getShopCategoryList(null);
            modelMap.put("shopCategoryList",shopCategoryList);
        }
        catch(Exception e)
        {
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }
        List<HeadLine> headLineList=new ArrayList<HeadLine>();

        try
        {
//            获取状态可用（1）的头条列表
            HeadLine headLineCondition=new HeadLine();
            headLineCondition.setEnableStatus(1);
            headLineList=headLineService.getHeadLineList(headLineCondition);
            modelMap.put("headLineList",headLineList);
        }catch (Exception e)
        {
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }
        modelMap.put("success",true);
        return modelMap;
    }


    /*
    * 获取轮播图
    * */

    @RequestMapping(value = "/banner")
    @ResponseBody
    public Map<String,Object> getCarouselPage(HttpServletRequest request) throws IOException {
        Map<String,Object> modelMap=new HashMap<String, Object>();
        LinkedList<String> strings = new LinkedList<>();

        HeadLine headLineCondition=new HeadLine();
        headLineCondition.setEnableStatus(1);
        List<HeadLine> headLineList = headLineService.getHeadLineList(headLineCondition);

        List<HeadLineInfo> headLineInfos = exstractionData(headLineList);

        if(headLineList.size()==0)
        {
            Integer code = Code.ERROR.getCode();
            modelMap.put("code",code);
            modelMap.put("list",null);
            return modelMap;
        }else
        {
            Integer code = Code.SUCCESS.getCode();
            modelMap.put("code",code);
            modelMap.put("list",headLineInfos);
        }

        return modelMap;
    }

    List<HeadLineInfo> exstractionData(List<HeadLine> headLineList)
    {
        LinkedList<HeadLineInfo> headLineInfos = new LinkedList<>();

        for(HeadLine headLine:headLineList)
        {
            String lineImg = headLine.getLineImg();
            Long lineId = headLine.getLineId();

            headLineInfos.add(new HeadLineInfo(lineImg,lineId,lineImg));
        }

        return headLineInfos;
    }


}
