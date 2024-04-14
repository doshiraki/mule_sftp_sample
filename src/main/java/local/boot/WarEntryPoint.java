package local.boot;

import java.net.URL;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

public class WarEntryPoint {
  static Logger logger = Logger.getLogger(WarEntryPoint.class.getName());

  public static void main(String[] args) throws Exception {
    int port = Integer.parseInt(System.getProperty("port", "8888"));
    URL warLocation = WarEntryPoint.class.getProtectionDomain().getCodeSource().getLocation();
    WebAppContext webapp = new WebAppContext();
    webapp.setWar(warLocation.toExternalForm());
    webapp.setContextPath("/");

    Server server = new Server(port);
    server.setHandler(webapp);
    server.start();
    Thread.sleep(3000);
    send(args.length > 0 ? args[0] : "", port);
    server.stop();
  }

  public static void send(String jsonData, int port) {
    try {
      logger.info(jsonData);
      // 127.0.0.1にGETリクエストを送信するためのURLを作成
      URL url = new URL("http://127.0.0.1:" + port);

      // HttpURLConnectionオブジェクトを作成し、POSTメソッドを設定
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("POST");

      // リクエストのボディにJSONデータを書き込む
      connection.setDoOutput(true);
      OutputStream outputStream = connection.getOutputStream();
      outputStream.write(jsonData.getBytes());
      outputStream.flush();
      outputStream.close();

      // レスポンスコードを取得
      int responseCode = connection.getResponseCode();
      System.out.println("Response Code: " + responseCode);

      // 接続を閉じる
      connection.disconnect();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
