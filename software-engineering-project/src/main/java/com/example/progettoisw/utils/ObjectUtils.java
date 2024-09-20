package com.example.progettoisw.utils;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ObjectUtils {

    public static boolean allObjectsFull(Object... objects) throws RuntimeException {
        for(Object object : objects) {
            if(object instanceof TextField) {
                if(((TextField) object).getText().equals(""))
                    return false;
            }
            else if(object instanceof DatePicker) {
                if(((DatePicker) object).getValue() == null)
                    return false;
            }
            else if(object instanceof ChoiceBox<?>) {
                if(((ChoiceBox<?>) object).getValue() == null)
                    return false;
            }
            else {
                throw new RuntimeException("Class not implemented in the code");
            }
        }

        return true;
    }

    public static boolean oneObjectFull(Object... objects) throws RuntimeException {
        for(Object object : objects) {
            if(object instanceof TextField) {
                if(!((TextField) object).getText().equals(""))
                    return true;
            }
            else if(object instanceof DatePicker) {
                if(((DatePicker) object).getValue() != null)
                    return true;
            }
            else if(object instanceof ChoiceBox<?>) {
                if(((ChoiceBox<?>) object).getValue() != null)
                    return true;
            }
            else {
                throw new RuntimeException("Class not implemented in the code");
            }
        }

        return false;
    }

    public static void setOnlyInteger(TextField... textFields) {
        for(TextField textField : textFields) {
            textField.textProperty().addListener((observableValue, oldVal, newVal) -> {
                if (!newVal.matches("\\d*"))
                    textField.setText(newVal.replaceAll("[^\\d]", ""));
            });
        }
    }

    public static void setOnlyIntegerWithMax(int max, TextField... textFields) {
        for(TextField textField : textFields) {
            textField.textProperty().addListener((observableValue, oldVal, newVal) -> {
                if (!newVal.matches("\\d*")) {
                    textField.setText(newVal.replaceAll("[^\\d]", ""));
                    return;
                }

                if (!newVal.equals("") && Integer.valueOf(newVal) > max) {
                    textField.setText(String.valueOf(max));
                    return;
                }

                if (newVal.length() > String.valueOf(max).length())
                    textField.setText(newVal.substring(0, String.valueOf(max).length()));
            });
        }
    }

    public static void setOnlyFloat(TextField... textFields) {
        for(TextField textField : textFields) {
            textField.textProperty().addListener((observableValue, oldVal, newVal) -> {

                Matcher m = Pattern.compile("[^0-9.]").matcher(newVal);
                if (m.find()) {
                    textField.setText(m.replaceAll(""));
                    return;
                }

                if (newVal.contains(" ")) {
                    textField.setText(newVal.replaceAll(" ", ""));
                    return;
                }

                // If multiple point
                Matcher matcher = Pattern.compile("\\.").matcher(newVal);
                StringBuffer buffer = new StringBuffer();

                int count = 0;
                boolean isFirst = true;
                while (matcher.find()) {
                    if (isFirst) isFirst = false;
                    else matcher.appendReplacement(buffer, "");

                    count++;
                }

                if (count > 1)
                    textField.setText(matcher.appendTail(buffer).toString());
            });
        }
    }

}
