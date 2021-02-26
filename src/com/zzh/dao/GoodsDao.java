package com.zzh.dao;

import com.zzh.entity.Goods;
import com.zzh.utils.DbUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author zzh
 * @description
 * @date
 */
public class GoodsDao {
    public ArrayList<Goods> listGoods() {
        ArrayList<Goods> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement prep = null;
        ResultSet rs = null;
        try {
            conn = DbUtil.getConn();
            String sql = "select id,name,price,unit,img,stock,remark from goods";
            prep = conn.prepareStatement(sql);
            rs = prep.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                BigDecimal price = rs.getBigDecimal("price");
                String unit = rs.getString("unit");
                String remake = rs.getString("remark");
                Goods goods0 = new Goods();
                goods0.setId(id);
                goods0.setName(name);
                goods0.setPrice(price);
                goods0.setUnit(unit);
                goods0.setRemake(remake);
                list.add(goods0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(rs, prep, conn);
            return list;
        }
    }

    public ArrayList<Integer> checkName(String name) {
        ArrayList<Integer> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement prep = null;
        ResultSet rs = null;
        try {
            conn = DbUtil.getConn();
            String sql = "select id,name,price,unit,img,stock,remake from goods where name = ?";
            prep = conn.prepareStatement(sql);
            prep.setString(1, name);
            rs = prep.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                list.add(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(rs, prep, conn);
            return list;
        }
    }

    public int addGoods(Goods goods) {
        int result = 0;
        Connection conn = null;
        PreparedStatement prep = null;
        try {
            conn = DbUtil.getConn();
            String sql = "insert into goods(name,price,unit,img,stock,remake) values (?,?,?,?,?,?)";
            prep = conn.prepareStatement(sql);
            prep.setString(1, goods.getName());
            prep.setBigDecimal(2, goods.getPrice());
            prep.setString(3, goods.getUnit());
            prep.setString(4, goods.getImg());
            prep.setInt(5, goods.getStock());
            prep.setString(6, goods.getRemake());
            result = prep.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(prep, conn);
        }
        return result;
    }

    public int deleteByName(String name) {
        int result = 0;
        Connection conn = null;
        PreparedStatement prep = null;
        try {
            conn = DbUtil.getConn();
            String sql = "delete from goods where name=?";
            prep = conn.prepareStatement(sql);
            prep.setString(1, name);
            result = prep.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(prep, conn);
        }
        return result;
    }

    /*
     *功能描述  获得购买订单商品的Id,用来检查库存
     * @author zzh
     * @date 2021/2/5
     * @param goodsId
     * @return com.entity.Goods
     */
    public Goods getGoodById(Integer goodsId) {
        Goods goods = new Goods();
        PreparedStatement prep = null;
        ResultSet rs = null;
        Connection conn = DbUtil.getConn();
        try {
            String sql = "select id,name,price,unit,img,stock,remark from goods where id = ?";
            prep = conn.prepareStatement(sql);
            prep.setInt(1, goodsId);
            rs = prep.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int stock = rs.getInt("stock");
                BigDecimal price = rs.getBigDecimal("price");
                goods.setId(id);
                goods.setStock(stock);
                goods.setPrice(price);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(rs, prep, conn);
        }
        return goods;
    }


    /*
     *功能描述  更新库存信息
     * @author zzh
     * @date 2021/2/5
     * @param goods
     * @return void
     */
    public void updateStock(Goods goods) {
        Connection conn = null;
        PreparedStatement prep = null;
        try {
            conn = DbUtil.getConn();
            String sql = "update goods set stock=stock-? where id=?";
            prep = conn.prepareStatement(sql);
            prep.setInt(1, goods.getStock());
            prep.setInt(2, goods.getId());
            prep.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(prep, conn);
        }
    }


    /*
     *功能描述  根据订单id更新库存信息
     * @author zzh
     * @date 2021/2/5
     * @param goods
     * @return void
     */
    public void updateGoodsStock(Goods goods) {
        Connection conn = null;
        PreparedStatement prep = null;
        try {
            conn = DbUtil.getConn();
            String sql = "update goods set stock=stock+? where id=?";
            prep = conn.prepareStatement(sql);
            prep.setInt(1, goods.getStock());
            prep.setInt(2, goods.getId());
            prep.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(prep, conn);
        }
    }
}
