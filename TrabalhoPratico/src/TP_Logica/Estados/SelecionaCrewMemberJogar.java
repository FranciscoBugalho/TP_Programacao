package TP_Logica.Estados;

import TP_Logica.GameData;
import java.io.Serializable;

public class SelecionaCrewMemberJogar extends StateAdapter implements Serializable {
    
    public SelecionaCrewMemberJogar(GameData jogo) {
        super(jogo);
    }

    @Override
    public IEstado voltarAtras(IEstado estado) {
        if(estado instanceof UsaPontosAcao){
            return new UsaPontosAcao(getGame());
        }
        if(estado instanceof UsaPontosInspiracao){
            return new UsaPontosInspiracao(getGame());
        }
        return estado;
    }

    @Override
    public void setGame(GameData jogo) {
        super.setGame(jogo);
    }
    
}
