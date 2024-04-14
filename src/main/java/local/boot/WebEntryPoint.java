package local.boot;

import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import local.mule.MuleTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WebEntryPoint extends HttpServlet {
  static Logger logger = Logger.getLogger(MuleTest.class.getName());
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    MuleTest mt = new MuleTest();
    // mt.exec(new String[] {"mule-send-receive-large-file-test-config-flow.xml"});
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // リクエストのボディを読み取る
    BufferedReader reader = request.getReader();
    StringBuilder stringBuilder = new StringBuilder();
    String line;
    while ((line = reader.readLine()) != null) {
      stringBuilder.append(line);
    }
    reader.close();

    // リクエストのボディからJSONデータを取得
    String jsonData = stringBuilder.toString();

    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode = objectMapper.readTree(jsonData);

    // 処理結果を生成
    String responseData = "Received JSON data: " + jsonNode + "\n";
    MuleTest mt = new MuleTest();
    mt.setFlowName(jsonNode.get("name").asText());
    mt.setStartUpProperties(jsonNode.get("conf"));
    mt.exec(new String[] {jsonNode.get("file").asText()});

    // レスポンスをクライアントに送信
    response.setContentType("text/html");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write("ok");
  }
}
