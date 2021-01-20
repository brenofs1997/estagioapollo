/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apollo.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;

/**
 *
 * @author Paulo
 */
public class converteDataPicker {
    
    public void converter(Date data, DatePicker dt){
        int ano = data.getYear() + 1900;
            int mes = data.getMonth() + 1;
            int dia = data.getDate();
            dt.setValue(LocalDate.of(ano, mes, dia));
            dt.setShowWeekNumbers(true);
            StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
                DateTimeFormatter dateFormatter
                        = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                @Override
                public String toString(LocalDate date) {
                    if (date != null) {
                        return dateFormatter.format(date);
                    } else {
                        return "";
                    }//To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public LocalDate fromString(String string) {
                    if (string != null && !string.isEmpty()) {
                        return LocalDate.parse(string, dateFormatter);
                    } else {
                        return null;
                    } //To change body of generated methods, choose Tools | Templates.
                }
            };
            dt.setConverter(converter);
        
    }
    
}
