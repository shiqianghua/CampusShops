package com.java.o2o.util;


import com.java.o2o.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ImageUtil {
   static Logger logger= LoggerFactory.getLogger(ImageUtil.class);

    private static String basePath =Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static final SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random r=new Random();


//    public static String generateThumbnail(InputStream thumbnailInputStream, String fileName, String targetAddr)
    public static String generateThumbnail(ImageHolder thumbnail,  String targetAddr)
    {
        String realFileName=getRandomFileName();//生成随机文件名
//        String extension=getFileExtension(fileName);//获取输入文件流的扩展名,一般为图片
        String extension=getFileExtension(thumbnail.getImageName());//获取输入文件流的扩展名,一般为图片
        makeDirPath(targetAddr);//创建图片存储的相对路径
        String relativeAddr=targetAddr+realFileName+extension;//图片的绝对路径

        logger.debug("current relativeAddr is"+relativeAddr);

        File dest=new File(PathUtil.getImgBasePath()+relativeAddr);//图片存储的最终绝对路径

        logger.debug("current complete addr is "+PathUtil.getImgBasePath());

        try {
//            Thumbnails.of(thumbnailInputStream).size(200,200)
            Thumbnails.of(thumbnail.getImage()).size(200,200)
                    .watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(basePath+"//Books.png")),0.25f)
                    .outputQuality(0.8f).toFile(dest);//将缩略图片放入指定的目录
        }catch (IOException e){
            e.printStackTrace();
        }
        return relativeAddr;
    }

    public static String generateNormalImg(ImageHolder thumbnail,  String targetAddr)
    {
        String realFileName=getRandomFileName();//生成随机文件名
//        String extension=getFileExtension(fileName);//获取输入文件流的扩展名,一般为图片
        String extension=getFileExtension(thumbnail.getImageName());//获取输入文件流的扩展名,一般为图片
        makeDirPath(targetAddr);//创建图片存储的相对路径
        String relativeAddr=targetAddr+realFileName+extension;//图片的绝对路径

        logger.debug("current relativeAddr is"+relativeAddr);

        File dest=new File(PathUtil.getImgBasePath()+relativeAddr);//图片存储的最终绝对路径

        logger.debug("current complete addr is "+PathUtil.getImgBasePath());

        try {
//            Thumbnails.of(thumbnailInputStream).size(200,200)
            Thumbnails.of(thumbnail.getImage()).size(337,640)
                    .watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(basePath+"//Books.png")),0.25f)
                    .outputQuality(0.9f).toFile(dest);//将缩略图片放入指定的目录
        }catch (IOException e){
            e.printStackTrace();
        }
        return relativeAddr;
    }



//创建目标路径所涉及到的目录,即/home/work/xiangze/xxx.jpg,
//    就会自动创建hone work xiangze这三个文件夹
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath=PathUtil.getImgBasePath()+targetAddr;
        File dirPath=new File(realFileParentPath);
        if(!dirPath.exists())//如果文件不存在则创建
        {
            dirPath.mkdirs();
        }else if(dirPath.exists()){
            System.out.println("该文件夹已经存在");
        }
    }

    //    获取输入文件流的扩展名
    private static String getFileExtension(String fileName)
    {

        return fileName.substring(fileName.lastIndexOf("."));

        /*String fileName=file.getName();
        String[] strArray=fileName.split("\\.");
        int suffixIndex=strArray.length-1;
        return strArray[suffixIndex];*/

        /*String originalFileName=cFile.getOriginalFilename();
        return originalFileName.substring(originalFileName.lastIndexOf("."));*/
    }

//    生成随机文件名，当前年月日小时分钟秒钟+五位随机数
    public static String getRandomFileName()
    {
//        获取随机五位数
        int rannum=r.nextInt(89999)+10000;
        String nowTimeStr=sDateFormat.format(new Date());
        return nowTimeStr+rannum;//字符串+整型变量，自动转换为字符串
    }




    public static void  main(String args[])
    {

        try {
            Thumbnails.of(new File("E:\\IDEA\\WorkSpace\\javaWeb2\\RequiredPictures\\images\\item\\shop\\15\\2017060522042982266.png")).
                    size(200,200).watermark(Positions.BOTTOM_LEFT,
                    ImageIO.read(new File(basePath+"/Books.png")),0.25f).outputQuality(0.02f).toFile("E:\\IDEA\\WorkSpace\\javaWeb2\\RequiredPictures\\images\\item\\shop\\118.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*storePath是文件的路径还是目录的路径，
    * 如果storePath是文件路径则删除该文件，
    * 如果storePath是目录路径则删除该目录文件下的所有文件*/
    public static void deleteFileOrPath(String storePath)
    {
        File fileOrPath=new File(PathUtil.getImgBasePath()+storePath);
        if(fileOrPath.exists())
        {
            if (fileOrPath.isDirectory())
            {
//                将目录里面的文件删除掉
                File files[]=fileOrPath.listFiles();
                for (int i=0;i<files.length;i++)
                {
                    files[i].delete();
                }
            }
//            最后将这个目录也删除掉
            fileOrPath.delete();
        }
    }




}
