package cl.multicaja.prepago;

import cl.multicaja.prepago.model.User;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Test_UsersController100 extends TestBase {

  @Test
  public void ping_users_1_0_0() throws JSONException {

    ResponseEntity<String> resp = get("/users-1.0.0/ping");

    Assert.assertEquals("status 200", 200, resp.getStatusCodeValue());

    JSONObject jsonResp = new JSONObject(resp.getBody());

    Assert.assertEquals("invocacion de ping", "UsersController100", jsonResp.getString("impl"));
  }

  @Test
  public void new_user_1_0_0() throws JSONException {

    String body = new Gson().toJson(new User(1, "jack"));

    ResponseEntity<String> resp = post("/users-1.0.0/", body);

    Assert.assertEquals("status 200", 200, resp.getStatusCodeValue());

    JSONObject jsonResp = new JSONObject(resp.getBody());

    Assert.assertEquals("invocacion de new_user", true, jsonResp.getBoolean("success"));
    Assert.assertEquals("user id", 1, jsonResp.getJSONObject("user").getInt("id"));
    Assert.assertEquals("user name", "jack", jsonResp.getJSONObject("user").getString("name"));
  }
}
