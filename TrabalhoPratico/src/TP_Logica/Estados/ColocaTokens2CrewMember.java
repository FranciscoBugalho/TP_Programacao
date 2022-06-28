package TP_Logica.Estados;

import TP_Logica.GameData;
import java.io.Serializable;

public class ColocaTokens2CrewMember extends StateAdapter implements Serializable {
    
    public ColocaTokens2CrewMember(GameData jogo) {
        super(jogo);
    }
    
    @Override
    public IEstado proximoTurno(String [] turno, int esteTurno) {
        if(Character.isDigit(turno[esteTurno].charAt(0))){
            return new UsaPontosAcao(getGame());
        }
        else{
            if(Character.compare(turno[esteTurno].charAt(0), 'E')!=0){
                return new UsaPontosInspiracao(getGame());
            }
            else{
                return new FimDeJogo(getGame());
            }
        }
    }
    
}