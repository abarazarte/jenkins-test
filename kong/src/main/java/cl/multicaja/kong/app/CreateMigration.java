package cl.multicaja.kong.app;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateMigration extends BaseMigrations {

    public static void main(String[] args) {

        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

        File file = new File(DIR_MIGRATIONS, String.format("%s.kong", df.format(new Date())));

        if (file.exists()) {
            Utils.println("La migración " + file.getAbsolutePath() + " ya existe, intenta nuevamente", Utils.ANSI_RED);
            return;
        }

        try {

            file.createNewFile();

            String text = IOUtils.toString(CreateMigration.class.getResourceAsStream("/template.kong"), "UTF-8");

            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            writer.write(text);
            writer.flush();
            writer.close();

            Utils.println("Migracion creada: " + file.getAbsolutePath(), Utils.ANSI_GREEN);

        } catch(Exception ex) {
            Utils.println("Error al crear migracion: " + file.getAbsolutePath(), Utils.ANSI_RED);
            ex.printStackTrace();
        }
    }

}
