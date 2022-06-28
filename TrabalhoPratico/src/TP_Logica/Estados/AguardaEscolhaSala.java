package TP_Logica.Estados;

import TP_Logica.GameData;
import java.io.Serializable;

public class AguardaEscolhaSala extends StateAdapter implements Serializable {
    
    public AguardaEscolhaSala(GameData jogo) {
        super(jogo);
    }

    @Override
    public IEstado comecarJogo() {
        return new UsaPontosAcao(getGame());
    }
    
}
