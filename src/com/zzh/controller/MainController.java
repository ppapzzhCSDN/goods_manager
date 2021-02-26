package com.zzh.controller;

import com.zzh.entity.Goods;
import com.zzh.entity.Order;
import com.zzh.entity.OrderDetail;
import com.zzh.entity.User;
import com.zzh.service.GoodsService;
import com.zzh.service.OrderService;
import com.zzh.service.UserService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author zzh
 * @description
 * @date
 */
public class MainController {
    public static void main(String[] args) {
        new MainController().login();

    }

    private UserService userService = new UserService();
    private GoodsService goodsService = new GoodsService();
    private OrderService orderService = new OrderService();


    public void login() {
        System.out.println("欢迎使用军事商城系统");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("请输入你的账号：");
            String nameMsg = scanner.next();
            System.out.println("请输入你的密码：");
            String passwordMsg = scanner.next();
            User user = new User();
            user.setName(nameMsg);
            user.setPassword(passwordMsg);
//        验证账号和密码，如果错误则提示账号密码不正确，然后重新跳转到登录页面
            if (userService.checkNameAndPassword(user)) {
                System.out.println("登录成功");
                manager();
            } else {
                System.out.println("登录失败：密码不正确！");
            }
        }

    }

    public void manager() {
        while (true) {
            System.out.println("请选择：");
            Scanner scanner = new Scanner(System.in);
            System.out.println("-------1.商品管理");
            System.out.println("-------2.订单管理");
            System.out.println("-------3.结束使用");

            String index = scanner.next();
            if ("1".equals(index)) {
                goodsManeger();
                //1.商品管理
            } else if ("2".equals(index)) {
                //2.订单管理
                orderManager();
            } else if ("3".equals(index)) {
                //3.结束使用
                System.out.println("欢迎再次使用军事商城系统！再见！");
                return;
            } else {
                System.out.println("请根据要求重新输入！：");
                continue;
            }
        }
    }

    public void goodsManeger() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("商品管理界面");
        while (true) {
            System.out.println("============1.查询商品");
            System.out.println("============2.添加商品");
            System.out.println("============3.删除商品");
            System.out.println("============4.返回上一层");
            String index = scanner.next();
            if ("1".equals(index)) {
                //1.查询商品
                System.out.println("id " + "商品名称 " + "商品单价 " + "商品单位 " + "备注 ");
                ArrayList<Goods> goods = goodsService.listGoods();
                goods.stream().forEach(n -> {
                    System.out.println(n.getId() + " " + n.getName() + " " + n.getPrice() + " " + n.getUnit() + " " + n.getRemake());
                });
            } else if ("2".equals(index)) {
                //2.添加商品(商品名唯一)
                System.out.println("请输入商品信息，用'，'隔开");
                System.out.println("示例：飞机大炮，4.5，门，D:\\img\\a.jpg，70，红警游戏 现在准备开始");
                String addGoodsStr = scanner.next();

                String[] split = addGoodsStr.split("，");
                Goods goods = new Goods();
                goods.setName(split[0]);
                goods.setPrice(BigDecimal.valueOf(Double.valueOf(split[1])));
                goods.setUnit(split[2]);
                goods.setImg(split[3]);
                goods.setStock(Integer.valueOf(split[4]));
                goods.setRemake(split[5]);
                if (goodsService.addGoods(goods)) {
                    System.out.println("添加商品成功！");
                } else {
                    System.out.println("添加商品失败...");
                }
            } else if ("3".equals(index)) {
                //3.删除商品(根据商品名称删除商品)
                System.out.println("请输入要删除的商品名称：");
                String deleteName = scanner.next();
                if (goodsService.deleteByName(deleteName)) {
                    System.out.println("删除成功");
                } else {
                    System.out.println("删除失败！");
                }
                continue;
            } else if ("4".equals(index)) {
                //4.返回上一层
                break;
                //这里break会跳出这个循环并回到上个循环，相当于返回上一层
            } else {
                System.out.println("请根据要求重新输入！：");
            }
        }
    }

    public void orderManager() {
        System.out.println("订单管理界面");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("--1.查询历史订单");
            System.out.println("--2.添加订单");
            System.out.println("--3.退货");
            System.out.println("--4.返回上一层");
            String string = scanner.next();
            if ("1".equals(string)) {
                //查询历史订单   注意拉布达表达式
                ArrayList<Order> orders = orderService.listOrder();
                System.out.println("id  " + "  订单编号 " + "  订单状态 " + "       创建时间 " + "       创建人 ");
                orders.stream().forEach(n -> {
                    System.out.println(" " + n.getId() + "  " + n.getOrderNo() + "  " + n.getStatusName() + "  " + n.getCreateTime() + "  " + n.getCreateName());
                });
            } else if ("2".equals(string)) {
                //添加订单
                ArrayList<OrderDetail> orderList = new ArrayList<>();
                while (true) {
                    System.out.println("请输入商品id和数量,中间用逗号(,)隔开:");
                    String addOrderString = scanner.next();
                    String[] split = addOrderString.split(",");
                    OrderDetail orderDetail = new OrderDetail();
                    //商品id和购买数量
                    orderDetail.setGoodsId(Integer.valueOf(split[0]));
                    orderDetail.setNumber(Integer.valueOf(split[1]));
                    orderList.add(orderDetail);

                    System.out.println("是否继续添加商品订单：(Y/N)");
                    String next = scanner.next();
                    //不等于Y的都结束添加订单。
                    if (!"Y".equals(next)) {
                        break;
                    }
                }
                if (orderService.addOrder(orderList)) {
                    System.out.println("下单成功!");
                } else {
                    System.out.println("下单失败!");
                }
            } else if ("3".equals(string)) {
                //退货
                //通过订单编号order_id退货
                System.out.println("退货系统");
                //第一步：把库存加回去
                System.out.println("请输入要退货的订单编号：");
                String orderNo = scanner.next();
                //第二步：把订单表order的状态改成退货。
                //判断是否退货成功
                if (orderService.ruturnOrder(orderNo)) {
                    System.out.println("退货成功！");
                    return;
                } else {
                    System.out.println("退货失败!请按要求重新输入要退货的订单编号！");
                }
            } else if ("4".equals(string)) {
                //这里是  返回上一层   注意break的位置
                break;
            } else {
                System.out.println("您的输入有误，请重新输入：");
            }
        }
    }
}


