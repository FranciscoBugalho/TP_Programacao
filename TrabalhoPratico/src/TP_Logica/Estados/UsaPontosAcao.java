package TP_Logica.Estados;

import TP_Logica.GameData;
import java.io.Serializable;

public class UsaPontosAcao extends StateAdapter implements Serializable {
    
    public UsaPontosAcao(GameData jogo) {
        super(jogo);
    }

    @Override
    public IEstado comecarJogo() {
        return new SelecionaCrewMemberJogar(getGame());
    }

    @Override
    public IEstado recomecarJogo() {
        setGame(new GameData());
        return new EsperaInicio(getGame());
    }

    @Override
    public IEstado escolhaSala() {
        return new AguardaEscolhaSala(getGame());
    }
    
    @Override
    public IEstado proximoTurno(String [] turno, int esteTurno) {
        if(Character.isDigit(turno[esteTurno].charAt(0))){
            return new UsaPontosAcao(getGame());
        }
        else{
            if(Character.compare(turno[esteTurno].charAt(0), 'E') != 0){
                return new UsaPontosInspiracao(getGame());
            }
            else{
                return new FimDeJogo(getGame());
            }
        }
    }
    
    @Override
    public IEstado lancamentoDado(){
        return new AguardaLancamentoDado(getGame());
    }
    
    @Override
    public IEstado fimJogo(){
        return new FimDeJogo(getGame());
    }
    
}
