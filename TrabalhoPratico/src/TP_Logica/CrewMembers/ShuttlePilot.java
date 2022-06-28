package TP_Logica.CrewMembers;

import java.io.Serializable;

public class ShuttlePilot extends CrewMember implements Serializable{
    
    public ShuttlePilot() {
        super("Shuttle Pilot", "Comeca com 4 pontos extra de vida.\n", 1, 1);        
    }
}
