package cl.multicaja.test.kong;

import cl.multicaja.utils.http.HttpResponse;
import org.junit.Assert;
import org.junit.Test;

public class Test_20180405124915 extends TestBase {

  @Test
  public void check_users_1_0_4() {

    HttpResponse resp = kongGET("/users/ping", "users-1-0-4");

    Assert.assertEquals("Status 200", 200, resp.getStatus());
    Assert.assertEquals("Servicio users 1.0.4", "UsersController104", resp.getJSON().getString("impl"));
  }
}
