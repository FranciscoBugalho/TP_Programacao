package TP_Logica.CrewMembers;

import java.io.Serializable;

public class ScienceOfficer extends CrewMember implements Serializable{

    public ScienceOfficer() {
        super("Science Officer", "Pode atacar qualquer alien selecionado adjacente a sua sala e que tenha ligacao a esta.\n", 1, 1);
    }

}
