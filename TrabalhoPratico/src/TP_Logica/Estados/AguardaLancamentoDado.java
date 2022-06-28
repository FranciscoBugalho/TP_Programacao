package TP_Logica.Estados;

import TP_Logica.GameData;
import java.io.Serializable;

public class AguardaLancamentoDado extends StateAdapter implements Serializable {
    
    public AguardaLancamentoDado(GameData jogo) {
        super(jogo);
    }
    
    @Override
    public IEstado comecarJogo(){
        return new UsaPontosAcao(getGame());
    }
    
}
