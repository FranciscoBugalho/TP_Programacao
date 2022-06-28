package TP_Logica.CrewMembers;

import java.io.Serializable;

public class NavigationOfficer extends CrewMember implements Serializable{
    
    public NavigationOfficer() {
        super("Navigation Officer", "Pode mover se para dois quartos com 1 ponto de acao.\n", 1, 2);
    }

}
