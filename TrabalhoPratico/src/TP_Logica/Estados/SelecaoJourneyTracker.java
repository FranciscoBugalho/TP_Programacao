package TP_Logica.Estados;

import TP_Logica.GameData;
import java.io.Serializable;

public class SelecaoJourneyTracker extends StateAdapter implements Serializable{

    public SelecaoJourneyTracker(GameData jogo) {
        super(jogo);
    }

    @Override
    public IEstado comecarJogo() {
        return new EscolheCrewMember(getGame());
    }
    
}
