package TP_Logica.CrewMembers;

import java.io.Serializable;

public class CommsOfficer extends CrewMember implements Serializable{
    
    public CommsOfficer() {
        super("Coom's Officer", "Antes de o alien atacar, este membro da tripulacao lanca um dado,"
                + " se sair 1 ou 2 o alien nao ataca.\n", 1, 1);
    }
    
}
