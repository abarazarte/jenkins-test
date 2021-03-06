package cl.multicaja.utils.db;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

/**
 * Informacion de una columna de una tabla de base de datos
 * @author vutreras
 */
public class ColumnInfo {

  private String name;
  private String type;
  private Integer size;

  public ColumnInfo(String name, String type, Integer size) {
    this.name = name;
    this.type = type;
    this.size = size;
  }

  public String getName() {
    return name;
  }

  public Integer getSize() {
    return size;
  }

  public String getType() {
    return type;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ColumnInfo)) return false;
    ColumnInfo that = (ColumnInfo) o;
    return Objects.equals(String.valueOf(getName()).toLowerCase(), String.valueOf(that.getName()).toLowerCase()) &&
      Objects.equals(String.valueOf(getType()).toLowerCase(), String.valueOf(that.getType()).toLowerCase()) &&
      Objects.equals(getSize(), that.getSize());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getType(), getSize());
  }
}
