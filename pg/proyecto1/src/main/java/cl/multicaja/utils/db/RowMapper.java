package cl.multicaja.utils.db;

import java.util.Map;

/**
 * @author vutreras
 */
public interface RowMapper {

  Object process(Map<String, Object> row);
}
