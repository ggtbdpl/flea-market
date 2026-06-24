package com.shumei.view;

import com.shumei.controller.ProductController;

import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        System.out.println("-----欢迎访问文创产品管理系统-----");
        System.out.println("1.查看所有文创产品");
        System.out.println("2.删除制定id的文创产品");
        System.out.println("3.增加文创产品");
        System.out.println("4.查询指定id的文创产品");
        System.out.println("5.修改指定id的文创产品");
        System.out.println("0.退出程序");

        Scanner input=new Scanner(System.in);
        int slt=input.nextInt();
        ProductController productController=new ProductController();
        switch (slt){
            case 1:
                System.out.println("查看所有文创产品");
                productController.showProductList();
                break;
            case 2:
                System.out.println("删除指定id的文创产品");
                productController.delProduct();
                break;
            case 3:
                System.out.println("增加文创产品");
                productController.addProduct();
                break;
            case 4:
                System.out.println("查询指定id的文创产品");
                productController.getProductById();
                break;
            case 5:
                System.out.println("修改指定id的文创产品");
                productController.updateProduct();
                break;
        }
    }
}
