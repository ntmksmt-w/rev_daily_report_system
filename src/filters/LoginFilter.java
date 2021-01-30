package filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Employee;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter("/*")
public class LoginFilter implements Filter {

    /**
     * Default constructor.
     */
    public LoginFilter() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
        // TODO Auto-generated method stub
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // rev_daily_report_system
        String context_path = ((HttpServletRequest)request).getContextPath();
        // /employees/indexとか
        String servlet_path = ((HttpServletRequest)request).getServletPath();

        if(!servlet_path.matches("/css.*")) {       // CSSフォルダ内は認証処理から除外する
            HttpSession session = ((HttpServletRequest)request).getSession();

            // セッションスコープに保存された従業員（ログインユーザ）情報を取得
            Employee e = (Employee)session.getAttribute("login_employee");

            // ログイン画面以外について
            if(!servlet_path.equals("/login")) {
                // ログアウトしている状態であれば
                // ログイン画面にリダイレクト
                if(e == null) {
                    ((HttpServletResponse)response).sendRedirect(context_path + "/login");
                    return;
                }

                // 従業員管理について
                // 管理者のみが閲覧できるようにする(0: 一般、1: 管理者)
                // 一般でログインした状態で、従業員管理のURL(http://localhost:8080/rev_daily_report_system/employees/index)を
                // URLに打ち込んでアクセスすると、遷移せずトップにリダイレクトする
                if(servlet_path.matches("/employees.*") && e.getAdmin_flag() == 0) {
                    ((HttpServletResponse)response).sendRedirect(context_path + "/");
                    return;
                }
            } else {
                // ログイン画面について
                // ログインしているのにログイン画面を表示させようとした場合はトップにリダイレクト
                if(e != null) {
                    ((HttpServletResponse)response).sendRedirect(context_path + "/");
                    return;
                }
            }
        }

        chain.doFilter(request, response);
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
        // TODO Auto-generated method stub
    }

}
