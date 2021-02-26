package com.zzh.service;

import com.zzh.dao.GoodsDao;
import com.zzh.dao.OrderDao;
import com.zzh.dao.OrderDetailDao;
import com.zzh.entity.Goods;
import com.zzh.entity.Order;
import com.zzh.entity.OrderDetail;
import com.zzh.enums.StatusEnum;
import com.zzh.utils.DateUtil;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * @author zzh
 * @description
 * @date
 */
public class OrderService {
    private OrderDao orderDao = new OrderDao();
    private GoodsDao goodsDao = new GoodsDao();
    private OrderDetailDao orderDetailDao = new OrderDetailDao();

    public ArrayList<Order> listOrder() {
        ArrayList<Order> orderList = orderDao.listOrder();
        return orderList;
    }

    public boolean addOrder(ArrayList<OrderDetail> orderDetailList) {
        //第一步：查询库存是否足够
        //只要有一个商品库存不够，下单失败
        for (OrderDetail orderDetail : orderDetailList) {
            Goods goods = goodsDao.getGoodById(orderDetail.getGoodsId());
            //判断商品id不存在
            if (goods.getId() != orderDetail.getGoodsId()) {
                System.out.println("商品id不正确!");
                return false;
            }

            if (orderDetail.getNumber() > goods.getStock()) {
                //库存不够
                System.out.println("库存不足");
                return false;
            }
        }

        //第二步：添加数据到Order表
        Order order = new Order();
        //订单编号
        order.setOrderNo(DateUtil.getOrderNo());
        //订单状态(1-已支付，2-已退货)
        order.setStatus(StatusEnum.STATUSFINISH.getName());
        //下单时间
        order.setCreateTime(DateUtil.getDateString());
        //下单用户
        order.setCreateBy(1);
        Integer orderId = orderDao.addOrder(order);
        if (orderId == null) {
            //添加订单失败
            return false;
        }

        //第三步：添加数据到OrderDetail表
        //orderList中只有商品id和数量
        orderDetailList.stream().forEach(n -> {
            OrderDetail orderDetail = new OrderDetail();
            //订单id
            orderDetail.setOrderId(orderId);
            orderDetail.setGoodsId(n.getGoodsId());
            orderDetail.setNumber(n.getNumber());
            //orderList中只有商品id和数量
            Goods goods = goodsDao.getGoodById(orderDetail.getGoodsId());
            orderDetail.setPrice(goods.getPrice());
            BigDecimal total = goods.getPrice().multiply(new BigDecimal(n.getNumber()));
            orderDetail.setTotal(total);

            orderDetailDao.addOrderDetail(orderDetail);
        });
        //第四步；减去库存。
        orderDetailList.stream().forEach(n -> {
            Goods goods = new Goods();
            goods.setId(n.getGoodsId());
            goods.setStock(n.getNumber());
            goodsDao.updateStock(goods);
        });

        return true;
    }

    /*
     *功能描述 将一个number传递过来
     * @author zzh
     * @date 2021/2/5
     * @param orderNo
     * @return boolean
     */
    public boolean ruturnOrder(String orderNo) {
        OrderDetail orderDetialNumber = orderDao.getOrderNumber(orderNo);
        //通过订单id查询不到number，返回false
        if (orderDetialNumber.getNumber() == null) {
            return false;
        }

        // //第一步：把库存加回去
        Integer goodsId = orderDetialNumber.getGoodsId();
        Goods goods = new Goods();
        goods.setId(goodsId);
        goods.setStock(orderDetialNumber.getNumber());
        goodsDao.updateGoodsStock(goods);

        //第二步：把订单表order的状态改成退货。
        //订单状态(1-已支付，2-已退货)
        Order order = new Order();
        order.setStatus(StatusEnum.STATUSRETURN.getName());
        orderDao.updateOrderStatus(order);

        return true;
    }

}
