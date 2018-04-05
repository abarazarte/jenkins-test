package cl.multicaja.utils.http;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.json.JSONObject;

/**
 * Representa una respuesta http
 * @author vutreras
 */
public class HttpResponse {

  private int status;
  private String resp;
  private String url;

  public HttpResponse() {
    super();
  }

  public HttpResponse(int status, String resp) {
    this.status = status;
    this.resp = resp;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getResp() {
    return resp;
  }

  public void setResp(String resp) {
    this.resp = resp;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public JSONObject getJSON() {
    return new JSONObject(this.getResp());
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }
}
