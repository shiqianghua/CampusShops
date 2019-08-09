package com.java.o2o.service.impl;

import com.java.o2o.dao.ProductDao;
import com.java.o2o.dto.ProductCategoryExecution;
import com.java.o2o.dao.ProductCategoryDao;
import com.java.o2o.entity.ProductCategory;
import com.java.o2o.enums.ProductCategoryStateEnum;
import com.java.o2o.exceptions.ProductCategoryOperationException;
import com.java.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryDao productCategoryDao;
    @Autowired
    private ProductDao productDao;

    @Override
    public List<ProductCategory> getProductCategoryListAll() {
        return productCategoryDao.queryProductCategoryListAll();
    }

    @Override
    public List<ProductCategory> getProductCategoryList(long shopId) {
        return productCategoryDao.queryProductCategoryList(shopId);
    }

//    添加商品失败则抛出异常
    @Override
    @Transactional
    public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationException {
        if(productCategoryList !=null && productCategoryList.size() >0)
        {
            try
            {
                int effectedNum=productCategoryDao.batchInsertProductCategory(productCategoryList);
                if(effectedNum <=0)
                {
                    throw new ProductCategoryOperationException("店铺类别创建失败");
                }
                else
                {
                    return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
                }
            }
            catch (Exception e)
            {
                throw new ProductCategoryOperationException("batchAddProductCategory error:"+e.getMessage());
            }
        }
        else
        {
            return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
        }
    }

//    删除指定店铺下面的商品
    @Override
    @Transactional
    public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId) throws ProductCategoryOperationException {
//        将此商品类别下的商品类别ID置为空

//        解除tb_product里的商品与该productCategoryId的关联
        try
        {
            int effectedNum=productDao.updateProductCategoryToNull(productCategoryId);
            if (effectedNum <0)
            {
                throw new RuntimeException("商品类别更新失败！");
            }
        }catch (Exception e)
        {
            throw new RuntimeException("deleteProductCategory error:"+e.getMessage());
        }

//        删除该productCategory
        try
        {
            int effectedNum=productCategoryDao.deleteProductCategory(productCategoryId,shopId);
            if (effectedNum <=0)
            {
                throw  new ProductCategoryOperationException("商品类别删除失败");
            }
            else
            {
                return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
            }
        }
        catch (Exception e)
        {
            throw new ProductCategoryOperationException("delectProductCategory error"+e.getMessage());
        }
    }


}
