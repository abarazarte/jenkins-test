package cl.multicaja.test.kong;

import cl.multicaja.utils.http.HttpResponse;
import org.junit.Assert;
import org.junit.Test;

public class Test_20180404143938 extends TestBase {

  @Test
  public void check_users_1_0_2() {

    HttpResponse resp = kongGET("/users/ping", "users-1-0-2");

    Assert.assertEquals("Status 200", 200, resp.getStatus());
    Assert.assertEquals("Servicio users 1.0.2", "UsersController102", resp.getJSON().getString("impl"));
  }

  @Test
  public void check_users_1_0_3() {

    HttpResponse resp = kongGET("/users/ping", "users-1-0-3");

    Assert.assertEquals("Status 200", 200, resp.getStatus());
    Assert.assertEquals("Servicio users 1.0.3", "UsersController103", resp.getJSON().getString("impl"));
  }
}
