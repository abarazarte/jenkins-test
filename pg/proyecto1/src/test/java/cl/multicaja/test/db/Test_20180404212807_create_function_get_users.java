package cl.multicaja.test.db;

import org.junit.Assert;
import org.junit.Test;
import java.util.List;
import java.util.Map;

public class Test_20180404212807_create_function_get_users extends TestBase {

    @Test
    public void checkGetUsers() {

        String name = String.format("user%s", Math.random());
        String email = String.format("user%s@mail.com", Math.random());

        Map<String, Object> res = dbUtils.executeAndGetFirst("proyecto1.add_user", name, email);
        Assert.assertNotNull("Ha retornado un valor", res);
        Assert.assertTrue("Deberia retornar un id", (Integer)res.get("result") > 0);

        List<Map<String, Object>> res2 = dbUtils.executeAndGetList("proyecto1.get_users");

        Assert.assertTrue("Deberia existir el usuario por nombre", res2.toString().contains(name));
        Assert.assertTrue("Deberia existir el usuario por email", res2.toString().contains(email));
    }

    @Test
    public void checkGetUserByName() {

        String name = String.format("user%s", Math.random());
        String email = String.format("user%s@mail.com", Math.random());

        Map<String, Object> res = dbUtils.executeAndGetFirst("proyecto1.add_user", name, email);
        Assert.assertNotNull("Ha retornado un valor", res);
        Assert.assertTrue("Deberia retornar un id", (Integer)res.get("result") > 0);

        Map<String, Object> res2 = dbUtils.executeAndGetFirst("proyecto1.get_user_by_name", name);

        Assert.assertTrue("Deberia existir el usuario por nombre", res2.toString().contains(name));
        Assert.assertTrue("Deberia existir el usuario por email", res2.toString().contains(email));
    }
}
