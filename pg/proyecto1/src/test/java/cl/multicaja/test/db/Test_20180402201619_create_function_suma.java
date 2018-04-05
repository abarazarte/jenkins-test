package cl.multicaja.test.db;

import org.junit.Assert;
import org.junit.Test;
import java.util.Map;

public class Test_20180402201619_create_function_suma extends TestBase {

    @Test
    public void checkFunctionSuma() {
        Map<String, Object> res = dbUtils.executeAndGetFirst("proyecto1.suma", 1, 2);
        Assert.assertNotNull("Ha retornado un valor", res);
        Assert.assertEquals("Deberia retornar 3", 3, res.get("result"));
    }
}
