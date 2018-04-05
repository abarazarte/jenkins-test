package cl.multicaja.utils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Utils {

    /**
     * Lee un archivo y lo retorna como un string
     * @param file
     * @return
     */
    public static String readFile(File file) {
        String text = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            text = IOUtils.toString(fis, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(fis);
        }
        return text;
    }

    /**
     * Transforma un string de comandos :curl -i -X POST --url http://kong/apis --data 'name=api-users-1-0-1' --data 'upstream_url=http://localhost:3200/api/users-1.0.1' --data 'hosts=api-users-1-0-1'
     * a un array de comandos: [curl, -i, -X, POST, --url, http://kong/apis, --data, 'name=api-users-1-0-1', --data, 'upstream_url=http://localhost:3200/api/users-1.0.1', --data, 'hosts=api-users-1-0-1']
     * @param cmd
     * @return
     */
    public static String[] commandToArrayParams(String cmd) {

        String[] commandParts = cmd.split(" ");

        Map<String, List> paramsMap = new LinkedHashMap<String, List>();
        String currentParam = null;

        for (int j = 0;  j < commandParts.length; j++) {
            String param = commandParts[j];
            if (StringUtils.isNotBlank(param)) {
                param = param + "####" + j;
                if (param.startsWith("-")) {
                    currentParam = param;
                    paramsMap.put(param, new ArrayList<String>());
                } else {
                    List l = paramsMap.get(currentParam);
                    if (l == null) {
                        paramsMap.put(param, new ArrayList<String>());
                    } else {
                        l.add(param);
                    }
                }
            }
        }

        List<String> params = new ArrayList<String>();

        for (String k : paramsMap.keySet()) {
            List<String> list = paramsMap.get(k);
            params.add(k.split("####")[0]);
            if (!list.isEmpty()) {
                String s1 = "";
                for (String l : list) {
                    s1+= l.split("##")[0] + " ";
                }
                params.add(s1);
            }
        }

        String[] array = new String[params.size()];
        params.toArray(array);

        return array;
    }

    public static String clearText(String text) {
        String partialFiltered = text.replaceAll("/\\*.*\\*/", "");
        String fullFiltered = partialFiltered.replaceAll("//.*(?=\\n)", "");
        return fullFiltered.trim();
    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";


    public static void println(String text, String ansi) {
        System.out.println(ansi + text + ANSI_RESET);
    }
}
