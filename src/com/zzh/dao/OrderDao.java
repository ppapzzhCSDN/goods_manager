package com.zzh.dao;

import com.zzh.entity.Order;
import com.zzh.entity.OrderDetail;
import com.mysql.jdbc.Statement;
import com.zzh.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * @author zzh
 * @description
 * @date
 */
public class OrderDao {
    public ArrayList<Order> listOrder() {
        ArrayList<Order> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement prep = null;
        ResultSet rs = null;
        try {
            conn = DbUtil.getConn();
            String sql = "SELECT " +
                    "o.id id, " +
                    "o.order_no orderNo, " +
                    "CASE " + "o.STATUS  " +
                    "WHEN 1 THEN " + "'已支付'  " +
                    "WHEN 2 THEN " + "'已退货' ELSE ''  " +
                    "END stuatsName, " +
                    "o.create_time createTime, " +
                    "u.NAME createName  " +
                    "FROM " + "`order` o " +
                    "LEFT JOIN `user` u ON o.create_by = u.id  " +
                    "ORDER BY o.create_time desc";
            prep = conn.prepareStatement(sql);
            rs = prep.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String orderNo = rs.getString("orderNo");
                String stuatsName = rs.getString("stuatsName");
                String createTime = rs.getString("createTime");
                String createName = rs.getString("createName");
                Order order = new Order();
                order.setId(id);
                order.setOrderNo(orderNo);
                order.setStatusName(stuatsName);
                order.setCreateTime(createTime);
                order.setCreateName(createName);
                list.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(rs, prep, conn);
        }
        return list;
    }

    /*
     *功能描述  检查库存
     * @author zzh
     * @date 2021/2/5
     * @param order
     * @return java.lang.Integer
     */
    public Integer addOrder(Order order){
        Integer id=null;
        Connection conn = null;
        PreparedStatement prep = null;

        try {
            conn = DbUtil.getConn();
            String sql = "insert into `order`(order_no,status,create_time,create_by) values(?,?,?,?)";
            // 指定返回生成的主键
            prep = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            prep.setString(1, order.getOrderNo());
            prep.setString(2, order.getStatus());
            prep.setString(3, order.getCreateTime());
            prep.setInt(4, order.getCreateBy());
            prep.executeUpdate();
            // 检索由于执行此 Statement 对象而创建的所有自动生成的键
            ResultSet rs = prep.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(prep, conn);
        }
        return id;
    }


    /*
     *功能描述  根据订单编号获得number
     * @author zzh
     * @date 2021/2/5
     * @param orderNo
     * @return com.entity.OrderDetail
     */
    public OrderDetail getOrderNumber(String orderNo) {
        OrderDetail orderDetail = new OrderDetail();
        PreparedStatement prep = null;
        ResultSet rs = null;
        Connection conn = DbUtil.getConn();
        try {
            String sql = "SELECT " + " b.number number,  " + " b.goods_id goodsId  " +
                    "FROM " + " `order` a " +
                    " LEFT JOIN order_detail b ON a.id = b.order_id  " +
                    "WHERE " +
                    " a.order_no=? ";

            prep = conn.prepareStatement(sql);
            prep.setString(1, orderNo);
            rs = prep.executeQuery();
            while (rs.next()) {
                int number = rs.getInt("number");
                int goodsId = rs.getInt("goodsId");
                orderDetail.setNumber(number);
                orderDetail.setGoodsId(goodsId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(rs, prep, conn);
        }
        return orderDetail;
    }


    public void updateOrderStatus(Order order) {
        Connection conn = null;
        PreparedStatement prep = null;
        try {
            conn = DbUtil.getConn();
            String sql = "update `order` set status = ? ";
            prep = conn.prepareStatement(sql);
            prep.setString(1, order.getStatus());
            prep.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(prep, conn);
        }
    }
}
