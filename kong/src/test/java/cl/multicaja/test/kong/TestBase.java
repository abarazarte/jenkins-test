package cl.multicaja.test.kong;

import cl.multicaja.utils.http.HttpHeader;
import cl.multicaja.utils.http.HttpResponse;
import cl.multicaja.utils.http.HttpUtils;

import java.net.URL;

/**
 * Clase base de tests
 * @author vutreras
 */
public class TestBase {

  protected static HttpUtils httpUtils = HttpUtils.getInstance();

  /**
   *
   * @return
   */
  protected String getKongHost() {
    String kong_host = System.getProperty("kong_host", "http://localhost:8000");
    System.out.println("kong_host: " + kong_host);
    return kong_host;
  }

  /**
   *
   * @param host
   * @param path
   * @return
   */
  protected String buildUrl(String host, String path) {

    if (host.endsWith("/")) {
      host = host.substring(0, host.length() - 1);
    }

    if (!path.startsWith("/")) {
      path = "/" + path;
    }

    return host + path;
  }

  /**
   *
   * @param path
   * @param headerHost
   * @return
   */
  protected HttpResponse kongGET(String path, String headerHost) {
    return httpUtils.get(buildUrl(getKongHost(), path), new HttpHeader("Host", headerHost));
  }

  /**
   *
   * @param path
   * @param body
   * @param headerHost
   * @return
   */
  protected HttpResponse kongPOST(String path, String body, String headerHost) {
    return httpUtils.post(buildUrl(getKongHost(), path), body, new HttpHeader("Host", headerHost));
  }
}
