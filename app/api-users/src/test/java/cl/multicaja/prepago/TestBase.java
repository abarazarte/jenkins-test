package cl.multicaja.prepago;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

/**
 * @author vutreras
 */
public class TestBase {

  @LocalServerPort
  protected int port;

  @Autowired
  protected TestRestTemplate restTemplate;

  protected String buildUrl(String path) {

    String host = "http://localhost:" + port;

    if (!path.startsWith("/")) {
      path = "/" + path;
    }

    return host + path;
  }

  protected ResponseEntity<String> get(String path) {
    return this.restTemplate.getForEntity(buildUrl(path), String.class);
  }

  protected ResponseEntity<String> post(String path, Object body) {
    return this.restTemplate.postForEntity(buildUrl(path), body, String.class);
  }
}
