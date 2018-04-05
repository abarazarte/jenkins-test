package cl.multicaja.test.kong;

import cl.multicaja.utils.http.HttpResponse;
import org.junit.Assert;
import org.junit.Test;

public class Test_20180402152322 extends TestBase {

  @Test
  public void check_users_1_0_0() {

    HttpResponse resp = kongGET("/users/ping", "users-1-0-0");

    Assert.assertEquals("Status 200", 200, resp.getStatus());
    Assert.assertEquals("Servicio users 1.0.0", "UsersController100", resp.getJSON().getString("impl"));
  }

  @Test
  public void check_users_1_0_1() {

    HttpResponse resp = kongGET("/users/ping", "users-1-0-1");

    Assert.assertEquals("Status 200", 200, resp.getStatus());
    Assert.assertEquals("Servicio users 1.0.1", "UsersController101", resp.getJSON().getString("impl"));
  }
}
