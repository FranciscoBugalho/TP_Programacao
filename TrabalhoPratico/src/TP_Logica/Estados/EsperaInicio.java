package TP_Logica.Estados;

import TP_Logica.GameData;
import java.io.Serializable;

public class EsperaInicio extends StateAdapter implements Serializable {

    public EsperaInicio(GameData jogo) {
        super(jogo);
    }

    @Override
    public IEstado comecarJogo() {
        return new SelecaoJourneyTracker(getGame());
    }
    
}
