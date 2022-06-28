package TP_Logica.Salas;

import java.io.Serializable;

public class Bridge extends Sala implements Serializable{
    
    public Bridge() {
        super("Bridge", 1, false, false);
    }
}
