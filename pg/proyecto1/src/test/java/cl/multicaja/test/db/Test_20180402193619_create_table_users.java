package cl.multicaja.test.db;

import cl.multicaja.utils.db.ColumnInfo;
import org.junit.Assert;
import org.junit.Test;

public class Test_20180402193619_create_table_users extends TestBase {

    @Test
    public void checkIfTableUserExists() {
        boolean exists = dbUtils.tableExists("proyecto1", "users", true, new ColumnInfo("id", "serial", 10),
                                                                                                    new ColumnInfo("name", "varchar", 100),
                                                                                                    new ColumnInfo("email", "varchar", 100));
        Assert.assertTrue("Verificar si tabla users existe", exists);
    }
}
