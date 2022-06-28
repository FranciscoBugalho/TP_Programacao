package TP_Logica.CrewMembers;

import java.io.Serializable;

public class Captain extends CrewMember implements Serializable{

    public Captain() {
        super("Captain", "Pode atacar quando o valor do" + '(' + "s" + ')' + " dado"
                + '(' + "s" + ')' + " for superior a 3.\n", 1, 1);
    }
    
}
