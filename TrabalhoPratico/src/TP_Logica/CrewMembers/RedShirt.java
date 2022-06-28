package TP_Logica.CrewMembers;

import java.io.Serializable;

public class RedShirt extends CrewMember implements Serializable{

    public RedShirt() {
        super("Red Shirt", "Pode ser sacrificado para obter 5 pontos de vida."
                + " Se for sacrificado fica a jogar com apenas o outro membro da tripulacao.\n", 1, 1);
    }

}
