package com.sonsure.dumper.core.command.sql;

import net.sf.jsqlparser.schema.Column;
import org.apache.commons.lang3.StringUtils;

public class ColumnMapping {

    private Column column;

    private String mappingName;

    public String getSmartMappingName() {
        if (StringUtils.isBlank(this.mappingName)) {
            return column.getColumnName();
        }
        return mappingName;
    }

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public String getMappingName() {
        return mappingName;
    }

    public void setMappingName(String mappingName) {
        this.mappingName = mappingName;
    }
}
