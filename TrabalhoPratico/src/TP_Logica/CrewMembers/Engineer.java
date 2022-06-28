package TP_Logica.CrewMembers;

import java.io.Serializable;

public class Engineer extends CrewMember implements Serializable{

    public Engineer() {
        super("Engineer", "Pode reparar 2 pontos da armadura por 1 ponto de acao na face de descanso.\n"
                + "Pode reparar 1 pontos da armadura por 1 ponto de acao.\n"
                + "Pode reparar 1 pontos da armadura por ronda por 0 pontos de acao quando se encontrar no Engineering.\n", 1, 1);
    }

}
