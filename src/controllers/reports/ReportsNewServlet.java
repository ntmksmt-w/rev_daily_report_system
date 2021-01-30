package controllers.reports;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Report;

/**
 * Servlet implementation class ReportsNewServlet
 */
@WebServlet("/reports/new")
public class ReportsNewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // CSRF対策
        request.setAttribute("_token", request.getSession().getId());

        // _form.jspエラー対策
        Report r = new Report();
        // 本日の日付を取得して格納
        // 1970年元旦0時を起点とした経過時刻(ミリ秒)
        System.out.println(System.currentTimeMillis());
        // 2020-11-15の形に変換
        System.out.println(new Date(System.currentTimeMillis()));
        r.setReport_date(new Date(System.currentTimeMillis()));
        request.setAttribute("report", r);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/new.jsp");
        rd.forward(request, response);
    }

}
