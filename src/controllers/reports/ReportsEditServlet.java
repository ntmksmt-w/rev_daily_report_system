package controllers.reports;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Report;
import utils.DBUtil;

@WebServlet("/reports/edit")
public class ReportsEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        // レポート情報をid(自動採番)で取得
        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));

        em.close();

        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");
        // show.jspの「この日報を編集する」の仕組みだとギリ他人の日報も編集可能なので、念の為再チェック
        // 編集画面でidを自分以外の日報のidに変えれば試せる
        if(r != null && login_employee.getId() == r.getEmployee().getId()) {
            request.setAttribute("report", r);
            request.setAttribute("_token", request.getSession().getId());
            request.getSession().setAttribute("report_id", r.getId());
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/edit.jsp");
        rd.forward(request, response);
    }

}
