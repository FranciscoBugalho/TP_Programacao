package TP_Logica;


import TP_Logica.Estados.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DestinationEarth implements Serializable {
    private IEstado estado;                             // Variável que guarda o estado atual
    private IEstado estadoAnterior;                     // Variável que guarda o estado anterior
    private GameData gameData;                          // Dados do jogo
    
    static final long serialVersionUID = 1L;
    
    /**
     *  Construtor do Jogo.
     */
    public DestinationEarth() {
        this.gameData = new GameData();
        setEstado(new EsperaInicio(gameData));
        this.estadoAnterior = new EsperaInicio(gameData);
    }

    ///////////////////////// Getter e Setter do estado atual ///////////////////////// 

    /**
     *  Retorna o estado atual.
     * @return IEstado - Estado Atual
     */
    public IEstado getEstado() {
        return estado;
    }

    /**
     *  Recebe um estado e guarda-o no parâmetro do estado atual do Jogo. 
     * @param estado IEstado - estado que queremos guardar
     */
    public void setEstado(IEstado estado) {
        this.estado = estado;
    }

    ///////////////////////// Getter e Setter do gameData ///////////////////////// 

    /**
     *  Retorna as informações atuais do jogo.
     * @return  GameData - Game Data atual
     */
    public GameData getGameData() {
        return gameData;
    }

    /**
     *  Recebe as informações do jogo modificadas e guarda-as nas atuais.
     * @param gameData GameData - informações do jogo
     */
    public void setGameData(GameData gameData) {
        this.gameData = gameData;
    }
    
    ///////////////////////// Getter e Setter do estado anterior ///////////////////////// 

    /**
     *  Guarda o estado atual no estado anterior para usar mais tarde.
     */
    public void saveEstadoAnterior() {
        this.estadoAnterior = this.estado;
    }
    
    /**
     *  Retorna o estado anterior para ser usado.
     * @return  IEstado - estado anterior
     */
    public IEstado loadEstadoAnterior() {
        return estadoAnterior;
    }
    
    ///////////////////////// Funções para a mudança de estado ///////////////////////// 

    /**
     *  Passagem para estados que se encontrem seguidos.
     */
    public void comecarJogo() {
        setEstado(getEstado().comecarJogo());
    }
    
    /**
     *  Passagem para o estado anterior em que se encontrava.
     */
    public void voltarAtras() {
        setEstado(loadEstadoAnterior());
    }

    /**
     *  Passagem para o estado seguinte, segundo o turno em que se encontra.
     */
    public void proximoTurno() {
        setEstado(getEstado().proximoTurno(getGameData().getJornneyTracker(), getGameData().getTurno()));
    }
    
    /**
     *  Passagem para o estado inicial do Jogo.
     */
    public void recomecarJogo() {
        setEstado(getEstado().recomecarJogo());
    }   
    
    /**
     *  Passagem para o estado de lançamento de dado onde o mesmo é usado para debug do Jogo.
     */
    public void lancamentoDado() {
        setEstado(getEstado().lancamentoDado());
    }
    
    /**
     *  Passagem para o estado de escolha da sala para onde o CrewMember selecionado se irá deslocar.
     */
    public void escolhaSala() {
        setEstado(getEstado().escolhaSala());
    }
    
    /**
     *  Passagem para o estado de final de Jogo.
     */
    public void fimJogo(){
        setEstado(getEstado().fimJogo());
    }
    
    /**
     *  Passagem para o estado de selecionar o CrewMember com que o utilizador irá fazer as suas ações.
     */
    public void selecionarOutroCrewMember() {
        getGameData().Selecionado().setSelecionado(false);
        comecarJogo();
    }
    
    /**
     *  Reset das informações do Jogo para recomeçar o Jogo do inicio.
     */
    public void reset() {
        this.gameData = new GameData();
    }
    
  
    
 
    /**
     *  Função para verificar se o turno terminou.
     * E passar para o próximo turno.
     */
    public void terminarTurno() {
        getGameData().Selecionado().setSelecionado(false);
        getGameData().setTurno(getGameData().getTurno() + 1);
        getGameData().setProximoTurno(true);
        proximoTurno();
    }

    /**
     *  Função para verificar se os pontos de inspiração acabaram.
     * E passar para o próximo turno.
     */
    public void acabouInspiracao() {
        if(getGameData().algumSelecionado()){
            getGameData().Selecionado().setSelecionado(false);
        }
        getGameData().setTurno(getGameData().getTurno() + 1);
        getGameData().setProximoTurno(true);
        proximoTurno();
    }
    
    /**
     *  Função para verificar se os pontos de ação acabaram.
     * E passar para o próximo turno.
     */
    public void acabouPontosAcao() {
        getGameData().setProximoTurno(true);
        getGameData().Selecionado().setSelecionado(false);
        gameData.resetPontosAcao();
        getGameData().setTurno(getGameData().getTurno() + 1);
        proximoTurno();
    }

    /**
     *  Retorna a String do próximo turno
     * detetando de que tipo o mesmo é.
     * @return String - proximo turno
     */
    public String proximoTurnoDetecao() {
        StringBuilder n = new StringBuilder();
        n.append(getGameData().getJornneyTrackerId(getGameData().getTurno()).charAt(0));
        if(Character.isDigit(getGameData().getJornneyTrackerId(getGameData().getTurno()).charAt(1))){
            n.append(getGameData().getJornneyTrackerId(getGameData().getTurno()).charAt(1));
        }
        return n.toString();
    }

    
    
    /**
     *  Função para guardar o Jogo num ficheiro de texto.
     * false: não foi guardado nenhum Jogo.
     * true: foi guardado o Jogo atual com sucesso.
     * @return Boolean - sucesso da função
     */
    public boolean saveGame(String name) {
        ObjectOutputStream out = null;
        StringBuilder filename = new StringBuilder();
        filename.append(name).append(".txt");
        
        try {
            out = new ObjectOutputStream(new FileOutputStream(filename.toString()));
            out.writeObject(gameData);
            out.writeObject(estado);
            out.close();
            return true;
        }
        catch (IOException ex) {
            Logger.getLogger(DestinationEarth.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    /**
     *  Função para carregar o Jogo a partir do nome do
     * ficheiro de texto introduzido.
     * false: não foi carregado nenhum Jogo.
     * true: foi carregado o Jogo com sucesso.
     * @return Boolean - sucesso da função
     */
    public boolean loadGame(String name){
        ObjectInputStream in = null;
        StringBuilder filename = new StringBuilder();
        filename.append(name).append(".txt");
        
        try{
            in = new ObjectInputStream(new FileInputStream(filename.toString()));
            gameData = (GameData) in.readObject();
            estado = (IEstado) in.readObject();
            in.close();
            return true;
        }
        catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(DestinationEarth.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }

    
}
