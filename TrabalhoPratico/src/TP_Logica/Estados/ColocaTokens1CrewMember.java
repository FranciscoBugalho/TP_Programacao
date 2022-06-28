package TP_Logica.Estados;

import TP_Logica.GameData;
import java.io.Serializable;

public class ColocaTokens1CrewMember extends StateAdapter implements Serializable {

    public ColocaTokens1CrewMember(GameData jogo) {
        super(jogo);
    }

    @Override
    public IEstado comecarJogo() {
        return new ColocaTokens2CrewMember(getGame());
    }
    
}
