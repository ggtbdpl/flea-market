package com.shumei.servlet.productservlet;

import com.shumei.DAO.Impl.ProductDAOImpl;
import com.shumei.DAO.ProductDAO;
import com.shumei.pojo.Product;
import com.shumei.servlet.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/Mdel")
public class MProductDelServlet extends ViewBaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out=resp.getWriter();
        int pid=Integer.parseInt(req.getParameter("pid"));
        ProductDAO productDAO = new ProductDAOImpl();
        boolean flag=productDAO.delProduct(pid);
        if(flag){
            out.println("<script type='text/javascript'>");
            out.println("alert('删除成功！');");
            out.println("window.location.href = 'Mindex';");
            out.println("</script>");
            System.out.println("删除成功");
            //resp.sendRedirect("Mindex");
        }else{
            out.println("<script type='text/javascript'>");
            out.println("alert('删除失败！');");
            out.println("</script>");
        }
    }
}
