package listeners;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class PropertiesListener
 *
 */
@WebListener
public class PropertiesListener implements ServletContextListener {

    /**
     * Default constructor.
     */
    public PropertiesListener() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }

    /**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    // Webアプリケーションが開始した時に呼ばれる
    public void contextInitialized(ServletContextEvent arg0) {
        ServletContext context = arg0.getServletContext();

        String path = context.getRealPath("/META-INF/application.properties");
        try {

            // FileInputStreamのインスタンスを生成
            InputStream is = new FileInputStream(path);

            // プロパティ(設定)ファイル用のクラスとして、Propertiesクラスがある
            // キーと値(どちらもString型)のペアで値を扱い、ファイルに読み書きができる(つまり、File I/O ストリームと併用する)
            Properties properties = new Properties();
            // propertiesの中身 -  ("pepper", "6Ab3mtmG")
            properties.load(is);
            is.close();

            // iterator - 集合の要素に順番にアクセスするインターフェース(ArrayList, Set<E>など実装)
            // プロパティ名の一覧を取得、イテレータを取得(stringPropertyNamesの戻り値がSet<String> -> [pepper, ...])
            Iterator<String> pit = properties.stringPropertyNames().iterator();
            while (pit.hasNext()) {
                // pepper
                String pname = pit.next();
                // アプリケーションスコープに、("pepper", "6Ab3mtmG")を保存
                context.setAttribute(pname, properties.getProperty(pname));
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

}
