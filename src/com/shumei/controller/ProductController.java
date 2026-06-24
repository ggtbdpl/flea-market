package com.shumei.controller;

import com.shumei.DAO.Impl.ProductDAOImpl;
import com.shumei.DAO.ProductDAO;
import com.shumei.pojo.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductController {
    ProductDAO productDAO = new ProductDAOImpl();

    public void showProductList() {
        System.out.println("————显示所有商品列表————");
        ArrayList<Product> list = productDAO.getProductList();
        for (Product p : list) {
            System.out.println(p.toString());
        }
    }

    public void delProduct() {
        System.out.println("————删除指定id的商品————");
        System.out.println("请输入要删除的id：");
        Scanner input = null;
        try {
            input = new Scanner(System.in);
            if (!input.hasNextInt()) {
                System.out.println("输入无效，请输入整数！");
                input.next();
                return;
            }
            int id = input.nextInt();
            if (id <= 0) {
                System.out.println("ID必须为正整数！");
                return;
            }
            System.out.println("确定删除吗？1.确定 0.取消");
            String slt = input.next();
            if (slt.equals("1")) {
                boolean flag = productDAO.delProduct(id);
                if (flag) {
                    System.out.println("删除成功");
                } else {
                    System.out.println("删除失败！可能该ID不存在");
                }
            } else {
                System.out.println("取消删除");
            }
        } catch (Exception e) {
            System.out.println("操作异常：" + e.getMessage());
        } finally {
            if (input != null) {
                input.close();
            }
        }
    }

    public void addProduct() {
        System.out.println("————添加商品————");
        Scanner input = null;
        try {
            input = new Scanner(System.in);

            System.out.println("请输入商品名称：");
            String title = input.nextLine();

            System.out.println("请输入商品价格：");
            if (!input.hasNextBigDecimal()) {
                System.out.println("输入无效，请输入数字！");
                input.next();
                return;
            }
            BigDecimal price = input.nextBigDecimal();
            if (price.compareTo(BigDecimal.ZERO) < 0) {
                System.out.println("价格不能为负数！");
                return;
            }
            input.nextLine();

            System.out.println("请输入新旧程度（如：9成新）：");
            String condition = input.nextLine();

            System.out.println("请输入商品描述：");
            String description = input.nextLine();

            System.out.println("请输入产品图片路径：");
            String image = input.nextLine();

            System.out.println("请输入联系方式：");
            String contact = input.nextLine();

            Product product = new Product();
            product.setTitle(title);
            product.setPrice(price);
            product.setCondition(condition);
            product.setDescription(description);
            product.setImage(image);
            product.setContact(contact);
            product.setStatus(1);

            boolean flag = productDAO.addProduct(product);
            if (flag) {
                System.out.println("添加成功");
            } else {
                System.out.println("添加失败");
            }
        } catch (Exception e) {
            System.out.println("操作异常：" + e.getMessage());
        } finally {
            if (input != null) {
                input.close();
            }
        }
    }

    public void getProductById() {
        System.out.println("————查询指定id的商品————");
        Scanner input = null;
        try {
            input = new Scanner(System.in);
            System.out.println("请输入要查询的id：");
            if (!input.hasNextInt()) {
                System.out.println("输入无效，请输入整数！");
                input.next();
                return;
            }
            int id = input.nextInt();
            if (id <= 0) {
                System.out.println("ID必须为正整数！");
                return;
            }
            Product product = productDAO.getProductById(id);
            if (product != null) {
                System.out.println("查询成功：");
                System.out.println(product.toString());
            } else {
                System.out.println("未找到该ID的商品");
            }
        } catch (Exception e) {
            System.out.println("操作异常：" + e.getMessage());
        } finally {
            if (input != null) {
                input.close();
            }
        }
    }

    public void updateProduct() {
        System.out.println("————修改商品信息————");
        Scanner input = null;
        try {
            input = new Scanner(System.in);
            System.out.println("请输入要修改的商品id：");
            if (!input.hasNextInt()) {
                System.out.println("输入无效，请输入整数！");
                input.next();
                return;
            }
            int id = input.nextInt();
            if (id <= 0) {
                System.out.println("ID必须为正整数！");
                return;
            }
            input.nextLine();

            Product existProduct = productDAO.getProductById(id);
            if (existProduct == null) {
                System.out.println("未找到该ID的商品");
                return;
            }

            System.out.println("当前商品信息：" + existProduct.toString());

            System.out.println("请输入新的商品名称（直接回车保持不变）：");
            String title = input.nextLine();
            if (title.isEmpty()) {
                title = existProduct.getTitle();
            }

            System.out.println("请输入新的商品价格（直接回车保持不变）：");
            String priceStr = input.nextLine();
            BigDecimal price = existProduct.getPrice();
            if (!priceStr.isEmpty()) {
                try {
                    price = new BigDecimal(priceStr);
                    if (price.compareTo(BigDecimal.ZERO) < 0) {
                        System.out.println("价格不能为负数，使用原价格");
                        price = existProduct.getPrice();
                    }
                } catch (NumberFormatException e) {
                    System.out.println("价格格式错误，使用原价格");
                }
            }

            System.out.println("请输入新的新旧程度（直接回车保持不变）：");
            String condition = input.nextLine();
            if (condition.isEmpty()) {
                condition = existProduct.getCondition();
            }

            System.out.println("请输入新的商品描述（直接回车保持不变）：");
            String description = input.nextLine();
            if (description.isEmpty()) {
                description = existProduct.getDescription();
            }

            System.out.println("请输入新的图片路径（直接回车保持不变）：");
            String image = input.nextLine();
            if (image.isEmpty()) {
                image = existProduct.getImage();
            }

            System.out.println("请输入新的联系方式（直接回车保持不变）：");
            String contact = input.nextLine();
            if (contact.isEmpty()) {
                contact = existProduct.getContact();
            }

            Product product = new Product();
            product.setId(id);
            product.setTitle(title);
            product.setPrice(price);
            product.setCondition(condition);
            product.setDescription(description);
            product.setImage(image);
            product.setContact(contact);
            product.setStatus(existProduct.getStatus());

            boolean flag = productDAO.updateProduct(product);
            if (flag) {
                System.out.println("修改成功");
            } else {
                System.out.println("修改失败");
            }
        } catch (Exception e) {
            System.out.println("操作异常：" + e.getMessage());
        } finally {
            if (input != null) {
                input.close();
            }
        }
    }
}
