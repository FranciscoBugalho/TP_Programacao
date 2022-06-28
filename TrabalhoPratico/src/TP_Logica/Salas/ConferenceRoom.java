package TP_Logica.Salas;

import java.io.Serializable;

public class ConferenceRoom extends Sala implements Serializable{
    
    public ConferenceRoom() {
        super("Conference Room", 5, false, false);
    }
    
}
