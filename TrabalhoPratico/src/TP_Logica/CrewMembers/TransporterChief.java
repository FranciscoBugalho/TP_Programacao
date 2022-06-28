package TP_Logica.CrewMembers;

import java.io.Serializable;

public class TransporterChief extends CrewMember implements Serializable{
    
    public TransporterChief() {
        super("Transporter Chief", "Pode teletransportar se para qualquer quarto na nave em troca de 1 ponto de acao.\n", 1, 0);
    }

}
