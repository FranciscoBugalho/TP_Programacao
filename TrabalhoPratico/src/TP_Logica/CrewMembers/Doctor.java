package TP_Logica.CrewMembers;

import java.io.Serializable;

public class Doctor extends CrewMember implements Serializable {

    public Doctor() {
        super("Doctor","Pode regenerar 2 pontos de vida por 1 ponto de acao na face de descanso.\n"
                + "Pode regenerar 1 pontos de vida por 1 ponto de acao.\n"
                + "Pode regenerar 1 pontos de vida por ronda por 0 pontos de acao quando se encontrar no Sickbay.\n", 1, 1);
    }
}
