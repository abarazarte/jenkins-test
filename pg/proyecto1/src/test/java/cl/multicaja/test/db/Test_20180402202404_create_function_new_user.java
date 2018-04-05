package cl.multicaja.test.db;

import org.junit.Assert;
import org.junit.Test;
import java.util.Map;

public class Test_20180402202404_create_function_new_user extends TestBase {

    @Test
    public void checkFunctionNewUser() {

        String name = String.format("user", Math.random());
        String email = String.format("user%s@mail.com", Math.random());

        Map<String, Object> res = dbUtils.executeAndGetFirst("proyecto1.add_user", name, email);
        Assert.assertNotNull("Ha retornado un valor", res);
        Assert.assertTrue("Deberia retornar un id", (Integer)res.get("result") > 0);
    }
}
