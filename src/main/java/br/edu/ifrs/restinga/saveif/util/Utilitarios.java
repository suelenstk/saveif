package br.edu.ifrs.restinga.saveif.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Utilitarios {

    public boolean validaEmail(String email) {
        String padraoDeEmail
                = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(padraoDeEmail);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
