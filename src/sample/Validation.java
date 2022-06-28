package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

import java.util.List;

public final class Validation {

    private Validation() {
    }

    // force the field to be numeric only
    public static void numericField(TextField textField){
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    textField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    /** Returns true only if the field passes the test, and is NOT null. */
    public static boolean ensureNotNull(Object field, String errorMsg, List<String> errors) {
        boolean result = true;
        if (field == null) {
            errors.add(errorMsg);
            result = false;
        }
        return result;
    }

    public static boolean ensureNotNull(Object field) {
        boolean result = true;
        if (field == null) {

            result = false;
        }
        return result;
    }

    public static int getNumber(String s){
        int n;
        try{
            n = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            n = 0;
        }
        if(n<0)
            n = 0;
        return n;
    }

    /**
     WARNING: This method is extremely common, and should be in a utility class.
     (It really should be in the JDK, as a static method of the String class.)
     */
    public static boolean hasContent(String string) {
        return (string != null && string.trim().length() > 0);
    }
}
