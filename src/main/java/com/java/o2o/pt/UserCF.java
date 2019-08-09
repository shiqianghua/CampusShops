package com.java.o2o.pt;

import com.java.o2o.entity.Shop;
import com.java.o2o.entity.User;
import com.java.o2o.service.ShopService;
import com.java.o2o.service.impl.ShopServiceImpl;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.*;
import org.apache.mahout.cf.taste.impl.neighborhood.*;
import org.apache.mahout.cf.taste.impl.recommender.*;
import org.apache.mahout.cf.taste.impl.similarity.*;
import org.apache.mahout.cf.taste.model.*;
import org.apache.mahout.cf.taste.recommender.*;
import org.apache.mahout.cf.taste.similarity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;

@Component
public class UserCF {

    public final static int NEIGHBORHOOD_NUM = 2;//临近的用户个数
    public final static int RECOMMENDER_NUM = 4;//推荐物品的最大个数
    public  List<User> listSpidAndImid=new LinkedList<User>();
    public  Long userInfoId;
    @Autowired
    ShopService shopService;

    public List<User> getListSpidAndImid() {
        return listSpidAndImid;
    }

    public void setListSpidAndImid(List<User> listSpidAndImid) {
        this.listSpidAndImid = listSpidAndImid;
    }

    public List<Shop> nearUser(User frontenduser) throws IOException, TasteException {

        userInfoId=1L/*frontenduser.getUserInfo()*/;
        System.out.println("当前用户ID为"+userInfoId);
        String file = "E:\\IDEA\\WorkSpace\\javaWeb2\\o2o\\data.cvs";
        DataModel model = new FileDataModel(new File(file));//数据模型
        UserSimilarity user = new EuclideanDistanceSimilarity(model);//用户相识度算法
        NearestNUserNeighborhood neighbor = new NearestNUserNeighborhood(NEIGHBORHOOD_NUM, user, model);
        //用户近邻算法
        Recommender r = new GenericUserBasedRecommender(model, neighbor, user);//用户推荐算法
        LongPrimitiveIterator iter = model.getUserIDs();///得到用户ID

        while (iter.hasNext()) {

            User user1 = frontenduser;
//已经训练得到结果，然后依次给每个用户推荐结果
            long uid = iter.nextLong();

//            user1.setUser_info(uid);
//给出推荐商铺的id列表
            List<RecommendedItem> list = r.recommend(uid, RECOMMENDER_NUM);
            System.out.printf("uid:%s", uid);
            for (RecommendedItem ritem : list) {
//                给用户推荐的店铺列表可能有多个
                long itemID = ritem.getItemID();
                System.out.printf("(%s,%f)",itemID , ritem.getValue());
//                  给当前用户推荐的店铺id
//                user1.setShopId(itemID);
                User user2 = new User(null,null,uid, itemID,0L,null);
                listSpidAndImid.add(user2);
            }

        System.out.println();

        }

        System.out.println("\n --------------------------------为所有用户列表推荐店铺列表");
        for (User user2:listSpidAndImid)
            System.out.println(user2.toString());
        System.out.println("\n--------------------------------\n");


        System.out.println("\n --------------------------------为当前用户列表推荐店铺列表\n");
        List<Shop> recShopList = getRecShopList(userInfoId);
//        for (Shop shop:recShopList)
//            System.out.println(shop.toString());

        if(recShopList.size()==0)
        {
            return null;
        }
        else
        {
            return recShopList;
        }
    }

    List<Shop> getRecShopList(Long userInfoId)
    {

        LinkedList<Shop> shops = new LinkedList<>();

        for(User user:listSpidAndImid)
        {
            Long userInfo = user.getUserInfo();

            if(userInfoId==userInfo)
            {

//                User user1 = new User();
//                user1.setUser_info(userInfo);
                Long shopId1 = user.getShopId();

                Shop getShopById = shopService.getByShopId(shopId1);

                shops.add(getShopById);
            }
            else
            {

                break;
//                shops=null;
            }

        }

        return shops;
    }


}
