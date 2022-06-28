package TP_Logica.Estados;

import TP_Logica.GameData;
import java.io.Serializable;

public class UsaPontosInspiracao extends StateAdapter implements Serializable {
    
    public UsaPontosInspiracao(GameData jogo) {
        super(jogo);
    }
    
    @Override
    public IEstado recomecarJogo() {            // É necessário colocar o contador a 0
        setGame(new GameData());
        return new EsperaInicio(getGame());
    }
    
    @Override
    public IEstado comecarJogo() {
        return new SelecionaCrewMemberJogar(getGame());
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
    
    @Override
    public IEstado fimJogo(){
        return new FimDeJogo(getGame());
    }
    
}
