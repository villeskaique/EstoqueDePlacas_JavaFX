package br.villes.aplicativoplacas.utils;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Validadores {
    public static boolean isTextFieldEmpty(TextField... campos) {
        for (TextField campo : campos) {
            if (campo.getText().trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }
    public static boolean isTextFieldDisabled(TextField... campos) {
        for (TextField campo : campos) {
            if (campo.isDisabled()) {
                return true;
            }
        }
        return false;
    }
    public static boolean foiChamadoPorBotao(Node root) {
        Node source = root.getScene().getFocusOwner();
        return source instanceof Button;
    }

    public static String formatPrice(String valor) {
        String numeroLimpo = valor.replaceAll("[^\\d.,]", "");

        numeroLimpo = numeroLimpo.replace(",", ".");

        try {
            double numero = Double.parseDouble(numeroLimpo);

            DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("pt", "BR"));
            symbols.setDecimalSeparator(',');
            symbols.setGroupingSeparator('.');
            DecimalFormat formato = new DecimalFormat("R$ #,##0.00", symbols);

            return formato.format(numero);
        } catch (NumberFormatException e) {
            return "-";
        }
    }
}
