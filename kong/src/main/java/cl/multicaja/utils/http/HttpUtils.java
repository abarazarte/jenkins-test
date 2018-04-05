package cl.multicaja.utils.http;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Utilidad para procesos http
 *
 * @author vutreras (victor.utreras@continuum.cl)
 *
 */
public final class HttpUtils {

	private static final Log log = LogFactory.getLog(HttpUtils.class);

	private static HttpUtils instance;

	private static final String USER_AGENT = "Mozilla/5.0 (Ubuntu; X11; Linux i686; rv:8.0) Gecko/20100101 Firefox/8.0";

	/**
	 * retorn al instancia unica
	 * @return
	 */
	public static HttpUtils getInstance() {
		if (instance == null) {
			instance = new HttpUtils();
		}
		return instance;
	}

	/**
	 *
	 */
	public HttpUtils() {
		super();
	}

	/**
	 * Realiza un post a un recurso web
	 *
	 * @param url url del recurso
	 * @param body contenido que se desea enviar en el post
	 * @param headers headers de la peticion
	 * @return
	 */
	public HttpResponse post(String url, String body, HttpHeader... headers) {

	  //necesario para poder reescribir el header Host necesario por kong
    System.setProperty("sun.net.http.allowRestrictedHeaders", "true");

		OutputStream out = null;
		HttpURLConnection conn = null;
		HttpResponse retorno = null;

		try {

      conn = (HttpURLConnection)new URL(url).openConnection();

      if (body != null) {

        conn.setReadTimeout(60000);
        conn.setConnectTimeout(60000);
        conn.setRequestMethod("POST");
        conn.addRequestProperty("Content-type", "application/x-www-form-urlencoded");
        conn.addRequestProperty("Content-lenght", String.valueOf(body.length()));

        if(headers != null && headers.length > 0) {
          for (HttpHeader h : headers) {
            log.debug("Add header: " + h.getName() + ": " + h.getValue());
            conn.setRequestProperty(h.getName(), h.getValue());
          }
        }

        conn.setDoInput(true);
        conn.setDoOutput(true);

        out = conn.getOutputStream();
        out.write(body.getBytes());
        out.flush();

      } else {

        if(headers != null && headers.length > 0) {
          for (HttpHeader h : headers) {
            log.debug("Add header: " + h.getName() + ": " + h.getValue());
            conn.setRequestProperty(h.getName(), h.getValue());
          }
        }

        conn.setReadTimeout(60000);
        conn.setConnectTimeout(60000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.setDoOutput(true);
      }

		} catch (Exception e) {
			log.error("Error al enviar peticion: " + url, e);
		} finally {
			IOUtils.closeQuietly(out);
		}
		if (conn != null) {
		  retorno = new HttpResponse();
		  retorno.setUrl(url);
			InputStream in = null;
			try {
			  retorno.setStatus(conn.getResponseCode());
			  try {
          in = conn.getInputStream();
        }catch(IOException ioe1) {
        }
				if (in == null) {
			    in = conn.getErrorStream();
        }
				String resp = IOUtils.toString(in, "UTF-8");
			  retorno.setResp(resp);
			} catch (Exception e) {
				log.error("Error al recibir respuesta: " + url, e);
			} finally {
				IOUtils.closeQuietly(in);
			}
		}

		return retorno;
	}

  /**
   *
   * @param url
   * @param body
   * @return
   */
	public HttpResponse post(String url, String body) {
		return post(url, body, null);
	}


  /**
   *
   * @param url
   * @return
   */
	public HttpResponse get(String url) {
		return post(url, (String)null, null);
	}

  /**
   *
   * @param url
   * @param headers
   * @return
   */
  public HttpResponse get(String url, HttpHeader... headers) {
    return post(url, (String)null, headers);
  }

}
