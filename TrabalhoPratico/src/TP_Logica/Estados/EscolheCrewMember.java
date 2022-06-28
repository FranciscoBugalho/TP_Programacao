package TP_Logica.Estados;

import TP_Logica.GameData;
import java.io.Serializable;

public class EscolheCrewMember extends StateAdapter implements Serializable {

    public EscolheCrewMember(GameData jogo) {
        super(jogo);
    }

    @Override
    public IEstado comecarJogo() {
        return new ColocaTokens1CrewMember(getGame());
    }
    
}
