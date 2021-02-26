package com.zzh.dao;

import com.zzh.entity.OrderDetail;
import com.zzh.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author zzh
 * @description
 * @date
 */
public class OrderDetailDao {
    /*
     *功能描述  添加商品信息添加进商品订单详情表
     * @author zzh
     * @date 2021/2/5
     * @param orderDetail
     * @return void
     */
    public void addOrderDetail(OrderDetail orderDetail) {
        Connection conn = null;
        PreparedStatement prep = null;

        try {
            conn = DbUtil.getConn();
            String sql = "insert into order_detail(order_id,goods_id,number,price,total) values(?,?,?,?,?)";
            prep = conn.prepareStatement(sql);
            prep.setInt(1, orderDetail.getOrderId());
            prep.setInt(2, orderDetail.getGoodsId());
            prep.setInt(3, orderDetail.getNumber());
            prep.setBigDecimal(4, orderDetail.getPrice());
            prep.setBigDecimal(5, orderDetail.getTotal());
            prep.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(prep, conn);
        }
    }
}