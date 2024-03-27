package org.but.feec.javafx.exceptions;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionHandler {

    public static void handleException(Exception ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setWidth(600);
        alert.setHeight(600);
        alert.setTitle("Error Dialog");

        TextArea area = new TextArea("Error message: " + ex.getMessage() + System.lineSeparator() + System.lineSeparator() + "The full stack trace:" + System.lineSeparator() + getFullStackTrace(ex));
        area.setWrapText(true);
        area.setEditable(false);

        alert.getDialogPane().setContent(area);
        alert.setResizable(true);
//        alert.setHeaderText(ex.getMessage());
//        alert.setContentText(getFullStackTrace(ex));
        alert.showAndWait();
    }

    private static String getFullStackTrace(Exception exception) {
        try (StringWriter sw = new StringWriter();
             PrintWriter pw = new PrintWriter(sw)) {
            exception.printStackTrace(pw);
            String fullStackTrace = sw.toString();
            return fullStackTrace;
        } catch (IOException e) {
            System.out.println("It was not possible to get the stack trace for that exception: ");
        }
        return "It was not possible to get the stack trace for that exception.";
    }


}