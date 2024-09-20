package com.example.progettoisw.model.type;

import javafx.beans.property.Property;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

public abstract class GeneralType {

    private static final List<String> stringifyClass = List.of(
            String.class.getName()
    );

    public abstract GeneralType copyWithDiffId(int id);

    public abstract int getId();

    public abstract String getStringId();

    /**
     * Used for insert an instance into the database
     *
     * @return Tuple of object property name
     * e.g. (Name1, Name2, ...)
     */
    public String getTupleName() {
        String out = "(";
        for (Property prop : getProperties()) {
            out = out.concat(prop.getName() + ",");
        }

        return out.substring(0, out.length() - 1) + ")";
    }

    /**
     * Used for insert an instance into the database
     *
     * @return Tuple of object property values
     * e.g. (Value1, Value2, ...)
     */
    public String getTupleValues() {
        HashMap<Property, String> propType = getPropertiesType();
        String out = "(";

        for (Property prop : getProperties()) {
            if (stringifyClass.contains(propType.get(prop)))
                out += String.format("%1$s,", "'" + prop.getValue() + "'");
            else if (propType.get(prop).equals(Date.class.getName()))
                out += String.format("%1$s,", ((Date) prop.getValue()).getTime());
            else if (propType.get(prop).equals(Alert.Type.class.getName()))
                out += String.format("%1$s,", ((Alert.Type) prop.getValue()).ordinal());
            else
                out += String.format("%1$s,", prop.getValue());
        }

        return out.substring(0, out.length() - 1) + ")";
    }

    /**
     * Used for update instance on the database
     *
     * @return List of object property name and value associations
     * e.g. Name1=Value1, Name2=Value2, ...
     */
    public String getAssociationNameValues() {
        HashMap<Property, String> propType = getPropertiesType();
        String out = "";

        for (Property prop : getProperties()) {
            if (stringifyClass.contains(propType.get(prop)))
                out += String.format("%1$s='%2$s',", prop.getName(), prop.getValue());
            else if (propType.get(prop).equals(Date.class.getName()))
                out += String.format("%1$s=%2$s,", prop.getName(), ((Date) prop.getValue()).getTime());
            else if (propType.get(prop).equals(Alert.Type.class.getName()))
                out += String.format("%1$s=%2$s,", prop.getName(), ((Alert.Type) prop.getValue()).ordinal());
            else
                out += String.format("%1$s=%2$s,", prop.getName(), prop.getValue());
        }

        return out.substring(0, out.length() - 1);
    }

    public abstract Property[] getProperties();

    public abstract HashMap<Property, String> getPropertiesType();
}
