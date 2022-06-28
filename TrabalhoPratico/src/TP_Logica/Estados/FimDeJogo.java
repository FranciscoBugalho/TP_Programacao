package TP_Logica.Estados;

import TP_Logica.GameData;
import java.io.Serializable;

public class FimDeJogo extends StateAdapter implements Serializable {
    
    public FimDeJogo(GameData jogo) {
        super(jogo);
    }

    @Override
    public IEstado comecarJogo() {
        return new EsperaInicio(getGame());
    }
    
}
