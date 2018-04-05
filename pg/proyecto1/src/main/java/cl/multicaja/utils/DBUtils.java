package cl.multicaja.utils;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Utilidades para operar con la base de datos, puede operar como instancia comun o singleton
 * @author vutreras
 */
public final class DBUtils {

  private static DBUtils instance;

  /**
   * retorna la instancia unica como singleton
   * @return
   */
  public static DBUtils getInstance() {
      if (instance == null) {

        //el ambiente por defecto es development
        String env = System.getProperty("env", "development");

        File dir = new File("./environments");
        File fileConfig = new File(dir, String.format("/%s.properties", env));

        Properties p = null;
        InputStream in = null;
        try {
          //busca en el sistema de archivos
          if (fileConfig.exists()) {
            in = new FileInputStream(fileConfig);
          }
          //busca en el classpath
          if (in == null) {
            in = DBUtils.class.getResourceAsStream(String.format("/%s.properties", env));
          }
          if (in != null) {
            p = new Properties();
            p.load(in);
          }
        } catch (IOException e) {
          e.printStackTrace();
        } finally {
          try {
            if (in != null) {
              in.close();
            }
          } catch(Exception ex) {
          }
        }
        if (p == null) {
          throw new RuntimeException(String.format("No fue posible cargar las configuraciones desde classpath /%s.properties o desde el archivo %s", env, fileConfig.getAbsolutePath()));
        }
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(p.getProperty("driver"));
        dataSource.setUrl(p.getProperty("url"));
        dataSource.setUsername(p.getProperty("username"));
        dataSource.setPassword(p.getProperty("password"));
        instance = new DBUtils(dataSource);
      }
      return instance;
  }

  private DataSource dataSource;
  private JdbcTemplate jdbcTemplate;

  /**
   *
   * @param dataSource
   */
  public DBUtils(DataSource dataSource) {
      this.dataSource = dataSource;
  }

  /**
   *
   * @return
   */
  public DataSource getDataSource() {
      return this.dataSource;
  }

  /**
   *
   * @return
   */
  public JdbcTemplate getJdbcTemplate() {
      if (this.jdbcTemplate == null) {
          this.jdbcTemplate = new JdbcTemplate(this.getDataSource());
      }
      return this.jdbcTemplate;
  }

  /**
   * verifica la conexion a la base de datos
   * @return
   */
  public boolean checkConnection() {
      Map<String,Object> map = this.getJdbcTemplate().queryForMap("SELECT 1");
      return map != null;
  }

  /**
   * Ejecuta una funcion SQL y retorna el primer resultado como un map, donde el map es la fila del resultado
   * @param name
   * @param params
   * @return
   */
  public Map<String, Object> executeAndGetFirst(String name, Object... params) {
      List<Map<String, Object>> lstMap = execute(name, 1, params);
      return lstMap != null && !lstMap.isEmpty() ? lstMap.get(0) : null;
  }

  /**
   * Ejecuta una funcion SQL y retorna el resultado como una lista de map, donde cada map es una fila del resultado
   * @param name
   * @param params
   * @return
   */
  public List<Map<String, Object>> executeAndGetList(String name, Object... params) {
      return execute(name, -1, params);
  }

  /**
   * Ejecuta una funcion SQL
   * @param name
   * @param fetchCount
   * @param params
   * @return
   */
  public List<Map<String, Object>> execute(String name, int fetchCount, Object... params) {

    List<Map<String, Object>> lstMap = null;
    CallableStatement stm = null;
    ResultSet rs = null;

    try {

      String paramsDef = StringUtils.join(StringUtils.rightPad("", params.length, "?").split(""), ", ");
      String spCall = String.format("{ call %s( %s ) }", name, paramsDef);

      System.out.println("--------------------------------------------");
      System.out.println(spCall);
      System.out.println(Arrays.asList(params));
      System.out.println("--------------------------------------------");

      stm = getDataSource().getConnection().prepareCall(spCall);

      for( int i = 0; i < params.length; i++) {

        Object p = params[i];
        int index = i + 1;

        if (p instanceof Integer) {
            stm.setInt(index, (Integer)p);
        } else if(p instanceof String) {
            stm.setString(index, (String)p);
        } else if(p instanceof Byte) {
            stm.setByte(index, (Byte)p);
        } else if(p instanceof BigDecimal) {
            stm.setBigDecimal(index, (BigDecimal)p);
        } else if(p instanceof Long) {
            stm.setLong(index, (Long)p);
        } else if(p instanceof Long) {
            stm.setLong(index, (Long)p);
        } else if(p instanceof Float) {
            stm.setFloat(index, (Float)p);
        } else if(p instanceof Double) {
            stm.setDouble(index, (Double)p);
        } else if(p instanceof Boolean) {
            stm.setBoolean(index, (Boolean) p);
        } else if(p instanceof Timestamp) {
            stm.setTimestamp(index, (Timestamp)p);
        } else if(p instanceof Time) {
            stm.setTime(index, (Time)p);
        } else if(p instanceof Date) {
            stm.setDate(index, (Date)p);
        } else if(p instanceof Blob) {
            stm.setBlob(index, (Blob)p);
        } else if(p instanceof Clob) {
            stm.setClob(index, (Clob) p);
        } else {
            throw new IllegalArgumentException("Parametro con valor " + p + " del indice " + i + " no es soportado");
        }
      }

      lstMap = new ArrayList<>();

      rs = stm.executeQuery();
      List<String> lstNames = null;
      int row = 0;
      int niveles = 0;

      while(rs.next()) {

        if (lstNames == null) {
          ResultSetMetaData rsmd = rs.getMetaData();
          niveles = rsmd.getColumnCount();
          lstNames = new ArrayList<>();
          for (int i = 1; i <= niveles; i++) {
            String columnName = rsmd.getColumnName(i).toLowerCase();
            lstNames.add(columnName);
          }
        }

        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < niveles; i++) {
          String key = lstNames.get(i);
          Object value = rs.getObject(i+1);
          map.put(key, value);
        }

        lstMap.add(map);

        row++;

        if (fetchCount > 0) {
          if (row == fetchCount) {
            break;
          }
        }
      }

    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
          if (rs != null) {
            rs.close();
          }
        } catch(Exception ex) {
        }
        try {
          if (stm != null) {
            stm.close();
          }
        } catch(Exception ex) {
        }
    }

    return lstMap;
  }

  /**
   * Verifica si la tabla existe con las columnas especificadas como parametros
   * @param schema nombre del esquema
   * @param tableName nombre de la tabla
   * @param strict true: verifica que las columnas pasadas como parametros existan en las columnas de la tabla y sean exactamante las mismas en cantidad
   *               false: verifica que las columnas pasadas como parametros existan en las columnas de la tabla
   * @param columns
   * @return
   */
  public boolean tableExists(String schema, String tableName, boolean strict, ColumnInfo... columns) {

    if (StringUtils.isBlank(schema)) {
      throw new IllegalArgumentException("Paramaetro [schame] es requerido");
    }

    if (StringUtils.isBlank(tableName)) {
      throw new IllegalArgumentException("Paramaetro [tableName] es requerido");
    }

    boolean exists = false;
    try (ResultSet rs = this.getDataSource().getConnection().getMetaData().getTables(null, schema, tableName, null)) {
      while (rs.next()) {
        String tName = rs.getString("TABLE_NAME");
        String tSchema = rs.getString("TABLE_SCHEM");
        if (tName != null && tName.equalsIgnoreCase(tableName) && tSchema != null && tSchema.equalsIgnoreCase(schema)) {
          exists = true;
          break;
        }
      }
    } catch(Exception ex) {
      ex.printStackTrace();
    }
    if (exists && columns != null && columns.length > 0) {

      List<ColumnInfo> list = new ArrayList<>();

      try(ResultSet rs = this.getDataSource().getConnection().getMetaData().getColumns(null, schema, tableName, null)) {

        while (rs.next()) {
          String name = rs.getString("COLUMN_NAME");
          String type = rs.getString("TYPE_NAME");
          int size = rs.getInt("COLUMN_SIZE");
          ColumnInfo ci = new ColumnInfo(name, type, size);
          list.add(ci);
        }

      } catch(Exception ex) {
        ex.printStackTrace();
      }

      if (strict && list.size() != columns.length) {
        System.err.println("EL numero de columnas [" + list.size() + "] de la tabla [" + schema + "." + tableName + "] no coinciden con el numero de columnas [" + columns.length + "] de parametros");
        exists = false;
      }

      if (exists) {
        List<ColumnInfo> params = Arrays.asList(columns);
        exists = list.containsAll(params);
        if (!exists) {
          System.err.println("La lista de parametros [" + params + "] no coincide con los datos [" + list + "] de la tabla [" + schema + "." + tableName + "]");
        }
      }
    }
    return exists;
  }

  /**
   * Retorna una lista de ColumnInfo con cada informacion de cada columna de la tabla
   * @param schema
   * @param tableName
   * @return
   */
  public List<ColumnInfo> getColumnsFromTable(String schema, String tableName) {

    if (StringUtils.isBlank(schema)) {
      throw new IllegalArgumentException("Paramaetro [schame] es requerido");
    }

    if (StringUtils.isBlank(tableName)) {
      throw new IllegalArgumentException("Paramaetro [tableName] es requerido");
    }

    boolean exists = false;
    try (ResultSet rs = this.getDataSource().getConnection().getMetaData().getTables(null, schema, tableName, null)) {
      while (rs.next()) {
        String tName = rs.getString("TABLE_NAME");
        String tSchema = rs.getString("TABLE_SCHEM");
        if (tName != null && tName.equalsIgnoreCase(tableName) && tSchema != null && tSchema.equalsIgnoreCase(schema)) {
          exists = true;
          break;
        }
      }
    } catch(Exception ex) {
      ex.printStackTrace();
    }
    if (exists) {

      List<ColumnInfo> list = new ArrayList<>();

      try(ResultSet rs = this.getDataSource().getConnection().getMetaData().getColumns(null, schema, tableName, null)) {

        while (rs.next()) {
          String name = rs.getString("COLUMN_NAME");
          String type = rs.getString("TYPE_NAME");
          int size = rs.getInt("COLUMN_SIZE");
          ColumnInfo ci = new ColumnInfo(name, type, size);
          list.add(ci);
        }

      } catch(Exception ex) {
        ex.printStackTrace();
      }

      return list;
    }

    return null;
  }
}
