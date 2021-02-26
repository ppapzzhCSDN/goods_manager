package com.zzh.service;

import com.zzh.dao.GoodsDao;
import com.zzh.entity.Goods;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * @author zzh
 * @description
 * @date
 */
public class GoodsService {
    private GoodsDao goodsDao = new GoodsDao();

    public ArrayList<Goods> listGoods() {
        ArrayList<Goods> list = goodsDao.listGoods();
        return list;
    }

    public boolean deleteByName(String name) {
        int result = goodsDao.deleteByName(name);
        if (result > 0) {
            return true;
        }
        return false;
    }

    public boolean addGoods(Goods goods) {
        //第一步：验证商品名称是否重复
        ArrayList<Integer> listId = goodsDao.checkName(goods.getName());
        if (listId.size() > 0) {
            return false;
        }
        //第二步：上传图片到服务器，上传完毕后把路径保存到数据库
        String url = upload(goods.getImg());
        goods.setImg(url);

        //第三步：添加到数据库
        goodsDao.addGoods(goods);

        return true;
    }

    public String upload(String src) {
        try {
            FileInputStream fis=new FileInputStream("D:\\20210203.jpg");
            OutputStream os=new FileOutputStream("D:\\img\\20210203.jpg");
            byte[] bytes=new byte[1024];
            int len=0;
            while ((len=fis.read(bytes))!=-1){
                os.write(bytes,0,len);
            }
            os.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "D:\\img\\20210203.jpg";
    }
}
