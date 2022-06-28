package TP_Logica.Estados;

import TP_Logica.GameData;
import java.io.Serializable;

public class StateAdapter implements IEstado, Serializable {
    private GameData jogo;              // Dados do jogo

    public StateAdapter(GameData jogo) {
        this.jogo = jogo;
    }

    ///////////////////////// Getter e Setter dos dados do jogo /////////////////////////
    public GameData getGame() {
        return jogo;
    }
    
    public void setGame(GameData jogo) {
        this.jogo = jogo;
    }

    @Override
    public IEstado comecarJogo() {
        return this;
    }

    @Override
    public IEstado proximoTurno(String [] turno, int esteTurno) {
        return this;
    }

    @Override
    public IEstado voltarAtras(IEstado estado) {
        return this;
    }

    @Override
    public IEstado recomecarJogo() {
        return this;
    }
    
    @Override
    public IEstado lancamentoDado(){
        return this;
    }

    @Override
    public IEstado escolhaSala() {
        return this;
    }

    @Override
    public IEstado fimJogo() {
        return this;
    }
    
}