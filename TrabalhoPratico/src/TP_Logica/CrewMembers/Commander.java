package TP_Logica.CrewMembers;

import java.io.Serializable;

public class Commander extends CrewMember implements Serializable{
    
    public Commander() {
        super("Commander", "Tem direito a 6 pontos de acao por ronda em vez de 5 pontos de acao.\n", 1, 1);
    }
}
