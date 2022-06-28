package TP_Logica.CrewMembers;

import java.io.Serializable;

public class SecurityOfficer extends CrewMember implements Serializable{
    
    public SecurityOfficer() {
        super("Security Officer", "Comeca com dois dados de ataque.\n", 2, 1);
    }

}
