package com.java.o2o.pt;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class ItemCF {
    final static int RECOMMENDER_NUM = 3;//推荐物品的最大个数

    public void RecItemCF() throws IOException, TasteException {
        String file = "E:\\IDEA\\WorkSpace\\javaWeb2\\o2o\\data.cvs";
        DataModel model = new FileDataModel(new File(file));//数据模型
        ItemSimilarity item=new EuclideanDistanceSimilarity(model);//用户相识度算法
        Recommender r=new GenericItemBasedRecommender(model,item);//物品推荐算法
        LongPrimitiveIterator iter =model.getUserIDs();
        while(iter.hasNext()){
            long uid=iter.nextLong();
//            获取推荐结果
            List<RecommendedItem> list = r.recommend(uid, 3);
            System.out.printf("uid:%s",uid);
//            遍历推荐结果
            for (RecommendedItem ritem : list) {
                System.out.printf("(%s,%f)", ritem.getItemID(), ritem.getValue());
            }
            System.out.println();
        }
    }}

