package com.java.o2o.service.impl;

import com.java.o2o.entity.ProductImg;
import com.java.o2o.service.impl.ShopServiceImpl;

import com.java.o2o.dao.ProductDao;
import com.java.o2o.dao.ProductImgDao;
import com.java.o2o.dto.ImageHolder;
import com.java.o2o.dto.ProductExecution;
import com.java.o2o.entity.Product;
import com.java.o2o.enums.ProductStateEnum;
import com.java.o2o.exceptions.ProductOperationException;
import com.java.o2o.service.ProductService;
import com.java.o2o.util.ImageUtil;
import com.java.o2o.util.PageCalculate;
import com.java.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductImgDao productImgDao;

    @Override
    public List<Product> getProductListMorePage(int limit, int offset) {
        List<Product> products = productDao.queryProductListMorePage(limit, offset);
        return products;
    }

    //    获取商品列表
    @Override
    public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
//        页码转换为数据库的行码，并调用dao层取回指定的商品类表
        int rowIndex= PageCalculate.calculateRowIndex(pageIndex,pageSize);
        List<Product> productList=productDao.queryProductList(productCondition,rowIndex,pageSize);
//        商品总数
        int count=productDao.queryProductCount(productCondition);
        ProductExecution pe=new ProductExecution();
        pe.setProductList(productList);
        pe.setCount(count);
        return pe;
    }

    @Override
    @Transactional
    /*1.处理缩略图，获取缩略图相对路径并赋值给product
    * 2.往tb_product写入商品信息，获取productId
    * 3.结合product批量处理商品详情图
    * 4.将商品详情图列表批量插入tb_product_img中*/
    public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList) throws ProductOperationException {
//       空值判断
        if(product !=null && product.getShop().getShopId() !=null)
        {
//            给商品设置默认属性
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
//            默认上架状态
            product.setEnableStatus(1);

//            若商品缩略图不为空则添加
            if (thumbnail != null)
            {
                addThumbnail(product,thumbnail);
            }

            try
            {
                //创建商品信息
                int effctedNum=productDao.insertProduct(product);
                if (effctedNum <=0)
                {
                    throw new ProductOperationException("创建商品失败！");

                }

            }
            catch (Exception e)
            {
                throw new ProductOperationException("创建商品失败！"+e.toString());
            }

//            商品图片详情图不为空则添加
            if (productImgHolderList !=null && productImgHolderList.size() >0)
            {
                addProductImgList(product,productImgHolderList);
            }
            return new ProductExecution(ProductStateEnum.SUCCESS,product);

        }
        else
        {
//            传参为空则返回空值错误信息
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    @Override
    public Product getProductById(long productId) {
        return productDao.queryProductById(productId);
    }


    /*1.若缩略图参数有值，则处理缩略图，
    * 2.若原先存在缩略图则删除缩略图再添加新图，之后获取缩略图相对路径并赋值给product
    * 3.若商品详情图列表参数有值，对商品详情图片列表进行同样的操作
    * 4.将tb_product_img下面的该商品原先的商品详情图记录全部删除
    * 5.更新tb_product以及tb_product_img的信息*/
    @Override
    public ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImageHolderList) throws ProductOperationException {
        if(product !=null && product.getShop().getShopId() !=null && product.getShop().getShopId() !=null)
        {
//            给商品设置默认属性
            product.setLastEditTime(new Date());

//            若商品缩略图不为空则添加
            if (thumbnail != null)
            {
//                先获取一遍原有信息，因为原来的信息里有原图片地址
                Product tempProduct=productDao.queryProductById(product.getProductId());
                if (tempProduct.getImgAddr() !=null)
                {
                    ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
                }

                addThumbnail(product,thumbnail);
            }

//            如果有新存入的商品详情图，则将原先的删除，并添加新的图片
            if (productImageHolderList !=null &&productImageHolderList.size()>0)
            {
                deleteProductImgList(product.getProductId());
                addProductImgList(product,productImageHolderList);
            }

            try
            {
                //更新商品信息
                int effctedNum=productDao.updateProduct(product);
                if (effctedNum <=0)
                {
                    throw new ProductOperationException("更新商品信息失败！");

                }
                return new ProductExecution(ProductStateEnum.SUCCESS,product);

            }
            catch (Exception e)
            {
                throw new ProductOperationException("更新商品失败！"+e.toString());
            }


        }
        else
        {
//            传参为空则返回空值错误信息
            return new ProductExecution(ProductStateEnum.EMPTY);
        }


    }

//    删除某个商品下的所有详情图
    private void deleteProductImgList(long productId)
    {
//        获取原来的图片
        List<ProductImg> productImgList=productImgDao.queryProductImgList(productId);
//        删除掉原来的图片
        for (ProductImg productImg:productImgList)
        {
            ImageUtil.deleteFileOrPath(productImg.getImgAddr());
        }

//        删除数据库里原有的图片信息
        productImgDao.deleteProductImgByProductId(productId);
    }

    /*批量的添加缩略图*/
    private void addThumbnail(Product product,ImageHolder thumbnail)
    {
        String dest= PathUtil.getShopImagePath(product.getShop().getShopId());
        String thumbnailAddr= ImageUtil.generateThumbnail(thumbnail,dest);
        product.setImgAddr(thumbnailAddr);
    }

    private void addProductImgList(Product product,List<ImageHolder> productImgHolderList)
    {
//        获取图片存储路径，这里直接存放到相应店铺的文件夹底下
        String dest=PathUtil.getShopImagePath(product.getShop().getShopId());
        List<ProductImg> productImgList=new ArrayList<ProductImg>();

//        遍历图片一次去处理，并添加进productImg实体类里
        for (ImageHolder productImgHolder:productImgHolderList)
        {
            String imgAddr= ImageUtil.generateNormalImg(productImgHolder,dest);

            ProductImg productImg=new ProductImg();
            productImg.setImgAddr(imgAddr);
            productImg.setProductId(product.getProductId());
            productImg.setCreateTime(new Date());
            productImgList.add(productImg);
        }
//        如果确实有图片需要添加的，就执行批量添加操作
        if (productImgHolderList.size() >0)
        {
            try
            {
                int effectedNum=productImgDao.batchInsertProductImg(productImgList);

                if (effectedNum <=0)
                {
                    throw new ProductOperationException("创建商品详情图片失败");
                }

            }
            catch (Exception e)
            {
                throw new ProductOperationException("创建商品详情图片失败"+e.toString());
            }
        }

    }



}
