package TP_Logica;

import TP_Logica.CrewMembers.*;
import TP_Logica.Salas.*;
import TP_Logica.Traps.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameData implements Constantes, Serializable{
    
    private int custoMax;                   // Maior caminho possível a percorrer pelos Aliens
    private List<Sala> caminho;             // Variável auxiliar para o movimento dos Aliens
    private List<Sala> percurso;            // Variável auxiliar para o movimento dos Aliens
    
    private Sala [] Salas;                  // Array de Salas
    private int armadura;                   // Valor da armadura (atual)
    private int vida;                       // Valor da vida (atual)
    private int pontosInspiracao;           // Valor dos pontos de inspitação (atual)
    private int pontosAcao;                 // Valor dos pontos de ação (atual)
    private int turno;                      // Indica o turno em que estamos (posição do Journey Tracker)
    private int qntOD;                      // Quantidade de Organic Detonators (atual)
    private int qntPD;                      // Quantidade de Particle Dispersors (atual)
    private int qntMontadoOD;               // Quantidade de Organic Detonators montandos (atual)
    private int qntMontadoPD;               // Quantidade de Particle Dispersors montados (atual)
    private int qntSealedTokens;            // Quantidade de Sealead Tokens (atual)
    private String nomeJogador;             // Nome do jogador
    private CrewMember [] Crews;            // Array de Crew Members
    private static List<Traps> trap;        // Lista de Traps (pertence a todos, estática)
    private String [] JornneyTracker;       // Array do Journey Tracker
    private int max_pontosAcao = 5;         // Quantidade máxima de pontos de ação (encontra-se aqui pois o seu valor pode ser modificado)
    private int max_vida = 12;              // Quantidade máxima de vida (encontra-se aqui pois o seu valor pode ser modificado)
    private int max_crew = 2;               // Quantidade máxima de Crew Members (encontra-se aqui pois o seu valor pode ser modificado)
    private boolean proximoTurno;           // Verificação se é o próximo turno
    
    private int [][] movimentos = {         // Array dos movimentos possíveis
        {0,0,0,0,1,0,0,1,0,0,0,0},  //Sala Bridge
        {0,0,0,0,0,1,1,1,0,0,0,0},  //Sala SickBay
        {0,0,0,0,1,0,0,0,1,0,0,0},  //Sala Brig
        {0,0,0,0,0,0,0,1,0,0,1,0},  //Sala CrewQuarters
        {1,0,1,0,0,0,0,1,0,1,0,0},  //Sala ConferenceRoom
        {0,1,0,0,0,0,0,0,0,1,0,0},  //Sala ShuttleBay
        {0,1,0,0,0,0,0,0,0,0,1,0},  //Sala WeaponsBay
        {1,1,0,1,1,0,0,0,0,0,0,0},  //Sala MessHall
        {0,0,1,0,0,0,0,0,0,0,0,1},  //Sala Engineering
        {0,0,0,0,1,1,0,0,0,0,0,1},  //Sala Astrometrics
        {0,0,0,1,0,0,1,0,0,0,0,0},  //Sala Holodeck
        {0,0,0,0,0,0,0,0,1,1,0,0}   //Sala Hydroponics
    };
    
    
    
    public GameData() {                 // Inicialização dos variáveis do jogo
        
        this.Salas = new Sala[max_salas];
        this.Salas[0] = new Bridge();
        this.Salas[1] = new SickBay();
        this.Salas[2] = new Brig();
        this.Salas[3] = new CrewQuarters();
        this.Salas[4] = new ConferenceRoom();
        this.Salas[5] = new ShuttleBay();
        this.Salas[6] = new WeaponsBay();
        this.Salas[7] = new MessHall();
        this.Salas[8] = new Engineering();
        this.Salas[9] = new Astrometrics();
        this.Salas[10] = new Holodeck();
        this.Salas[11] = new Hydroponics();
        
        this.JornneyTracker = new String[max_turnos];
        this.JornneyTracker[0] = "S";
        this.JornneyTracker[1] = "2A";
        this.JornneyTracker[2] = "3A";
        this.JornneyTracker[3] = "4A";
        this.JornneyTracker[4] = "5A*";
        this.JornneyTracker[5] = "R";
        this.JornneyTracker[6] = "4A";
        this.JornneyTracker[7] = "5A";
        this.JornneyTracker[8] = "6A*";
        this.JornneyTracker[9] = "R";
        this.JornneyTracker[10] = "6A";
        this.JornneyTracker[11] = "7A*";
        this.JornneyTracker[12] = "R";
        this.JornneyTracker[13] = "8A";
        this.JornneyTracker[14] = "E";
        
        this.custoMax = 5;
        
        this.caminho = new ArrayList<>();
        this.percurso = new ArrayList<>();

        this.trap = new ArrayList<>();
        this.qntSealedTokens = 0;
        this.qntOD = 0;
        this.qntPD = 0;
        this.qntMontadoOD = 0;
        this.qntMontadoPD = 0;
        this.proximoTurno = true;
        this.Crews = new CrewMember[max_crew];
        this.turno = 0;
        this.armadura = max_armadura;
        this.vida = max_vida;
        this.pontosInspiracao = 0;
        this.pontosAcao = max_pontosAcao;
    }

    ///////////////////////// Getter's e Setter's da quantidade de OD / PD montados /////////////////////////
    public int getQntMontadoOD() {
        return qntMontadoOD;
    }

    private void setQntMontadoOD(int qntMontadoOD) {
        this.qntMontadoOD = qntMontadoOD;
    }

    public int getQntMontadoPD() {
        return qntMontadoPD;
    }

    private  void setQntMontadoPD(int qntMontadoPD) {
        this.qntMontadoPD = qntMontadoPD;
    }
    
    ///////////////////////// Getter do número máximo de Aliens /////////////////////////
    public int getMax_aliens() {
        return max_aliens;
    }
    
    ///////////////////////// Getter e Setter do máximo de pontos de ação /////////////////////////
    public int getMax_pontosAcao() {
        return max_pontosAcao;
    }
    
    private  void setMax_pontosAcao(int max_pontosAcao) {
        this.max_pontosAcao = max_pontosAcao;
    }
    
    ///////////////////////// Getter e Setter do máximo de vida /////////////////////////
    public int getMax_vida() {
        return max_vida;
    }
    
    private  void setMax_vida(int max_vida) {
        this.max_vida = max_vida;
    }
    
    ///////////////////////// Getter do máximo de armadura /////////////////////////
    public int getMax_armadura() {
        return max_armadura;
    }
    
    ///////////////////////// Getter do máximo de pontos de inspiração /////////////////////////
    public int getMax_pontosInspiracao() {
        return max_pontosInspiracao;
    }
    
    ///////////////////////// Getter do máximo das salas /////////////////////////
    public int getMax_salas() {
        return max_salas;
    }
    
    ///////////////////////// Getter e Setter do máximo de Crew Members /////////////////////////
    public int getNCrewsMax() {
        return max_crew;
    }

    private  void setMax_crew(int max_crew) {
        this.max_crew = max_crew;
    }
    
    ///////////////////////// Getter do tamanho máximo do Journey Tracker /////////////////////////
    public int getJourneyTackerTAM(){
        return max_turnos;
    }

    ///////////////////////// Getter do máximo de OD / PD /////////////////////////
    public int getMax_arm_OD() {
        return max_arm_OD;
    }

    public int getMax_arm_PD() {
        return max_arm_PD;
    }
    
    ///////////////////////// Getter do máximo de SealedTokens
    public int getMax_sealedTokens() {
        return max_sealedTokens;
    }
    
    
    ///////////////////////// Getter e Setter da armadura /////////////////////////
    public int getArmadura() {
        return armadura;
    }

    private  void setArmadura(int armadura) {
        this.armadura = armadura;
    }


    ///////////////////////// Getter e Setter da vida /////////////////////////
    public int getVida() {
        return vida;
    }

    private  void setVida(int vida) {
        this.vida = vida;
    } 
    
    ///////////////////////// Getter e Setter dos pontos de inspiração /////////////////////////
    public int getPontosInspiracao() {
        return pontosInspiracao;
    }

    private  void setPontosInspiracao(int pontosInspiracao) {
        this.pontosInspiracao = pontosInspiracao;
    }

    ///////////////////////// Getter e Setter dos pontos de ação /////////////////////////
    public int getPontosAcao() {
        return pontosAcao;
    }

    private  void setPontosAcao(int pontosAcao) {
        this.pontosAcao = pontosAcao;
    }

    ///////////////////////// Getter e Setter das Salas /////////////////////////
    public Sala getEstaSala(int i){             // Dado o seu id
        return Salas[i];
    }
    
    public Sala getEstaSala(CrewMember ele){    // Dado um Crew Member
        for(Sala ali : Salas){
            if(ali.existeCrew(ele)){
                return ali;
            }
        }
        return null;    // Em caso de erro
    }
    
    public Sala getEstaSala(Sala s){            // Dada uma sala
         for(Sala sala : Salas){
             if(s.equals(sala)){
                 return sala;
             }
         }
         return null;   // Em caso de erro         
     }
    
    private void setSalas(Sala[] Salas) {
        this.Salas = Salas;
    }
    
    ///////////////////////// Getter e Setter do turno /////////////////////////
    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }
    
    ///////////////////////// Getter e Setter do nome do jogador /////////////////////////
    public String getNome() {
        return nomeJogador;
    }
        
    public void setNome(String name){
        this.nomeJogador = name;
    }

    ///////////////////////// Getter e Setter do Journey Tarcker /////////////////////////
    public String[] getJornneyTracker() {           // Todo o Journey Tracker
        return JornneyTracker;
    }
    
    public String getJornneyTrackerId(int i) {      // Apenas um turno 
        return JornneyTracker[i];
    }

    private void setJornneyTracker(String[] JornneyTracker) {
        this.JornneyTracker = JornneyTracker;
    }
        
    ///////////////////////// Getter's e Setter dos Crew Members /////////////////////////
    public CrewMember getCrew(int i) {          // Dado uma posição 
        return Crews[i];
    }

    public CrewMember[] getCrews() {            // Todos os Crew Members
        return Crews;
    }
    
    private void setCrews(CrewMember[] Crews) {
        this.Crews = Crews;
    }
    
    ///////////////////////// Getter's e Setter da quantidade de OD / PD /////////////////////////
    public int getQntOD() {
        return qntOD;
    }

    private void setQntOD(int qntOD) {
        this.qntOD = qntOD;
    }


    public int getQntPD() {
        return qntPD;
    }

    private void setQntPD(int qntPD) {
        this.qntPD = qntPD;
    }


    ///////////////////////// Getter e Setter da quantidade de Sealed Tokens /////////////////////////
    public int getQntSealedTokens() {
        return qntSealedTokens;
    }

    private void setQntSealedTokens(int qntSealedTokens) {
        this.qntSealedTokens = qntSealedTokens;
    }
    
    ///////////////////////// Getter e Setter do próximo turno /////////////////////////
    public boolean isProximoTurno() {
        return proximoTurno;
    }

    public void setProximoTurno(boolean proximoTurno) {
        this.proximoTurno = proximoTurno;
    }
    
    ///////////////////////// Getter e Setter das Traps /////////////////////////
    public List<Traps> getTrap() {
        return trap;
    }
    
    public Traps getTrap(int i) {
        return trap.get(i);
    }

    ///////////////////////// Adição de um Crew Member a uma sala /////////////////////////
    public void adicionaCrewMember1(int i){
        Salas[--i].acrescentaCrewMember(Crews[0]); 
    }
    
    public void adicionaCrewMember2(int i){
        Salas[--i].acrescentaCrewMember(Crews[1]); 
    }
       
    public String mostraSalas(){                    // Apresentação das Salas
        int i = 0;
        StringBuilder sb = new StringBuilder();
        for(Sala ali : Salas){
            ++i;
            sb.append("\n").append(i).append(" - ").append(ali.getNome());
        }
        return sb.toString();
    }
    
    public boolean algumSelecionado(){              // Verifica se existe Crew Member selecionado
        for(int i = 0; i < getNCrewsMax(); i++){
            if(getCrew(i).isSelecionado()){
                return true;
            }
        }
        return false;
    }
    
    public CrewMember Selecionado(){                // Indica qual o Crew Member selecionado
        for(int i = 0; i < getNCrewsMax(); i++){
            if(Crews[i].isSelecionado())
                return Crews[i];
        }   
        return null;
    }

    public boolean nTraps(Traps tr){                // Calcular o número de Traps
        if(getQntOD() + getQntMontadoOD() < getMax_arm_OD() && tr instanceof OrganicDetonator){
            getTrap().add(tr);
            return true;
        }
        else if(getQntPD() + getQntMontadoPD() < getMax_arm_PD() && tr instanceof ParticleDisperser){
            getTrap().add(tr);
            return true;
        }
        return false;
    }

    public boolean acrescentaTrap(Traps tr) {
        return nTraps(tr);                          // Adiciona um Trap
    }
    
    public boolean removeTrap(Traps tr) {           // Remove uma Trap
        for(Traps ali : trap){
            if(tr instanceof OrganicDetonator && ali instanceof OrganicDetonator){
                return true;
            }
            else if(tr instanceof ParticleDisperser && ali instanceof ParticleDisperser){
                return true;
            }
        }
        return false;
    }
    
    public void mataRedShirt(){                     // Matar o Crew Member Red Shirt
        CrewMember[] novo = new CrewMember[1];
        if(getCrew(0) instanceof RedShirt){
            getCrew(0).setSelecionado(false);
            getCrew(1).setSelecionado(true);
            novo[0] = getCrew(1);
            setCrews(novo);
            setMax_crew(getNCrewsMax() - 1);
            setCrews(novo);
        }
        else{
            if(getCrew(1) instanceof RedShirt){
                getCrew(1).setSelecionado(false);
                getCrew(0).setSelecionado(true);
                novo[0] = getCrew(0);
                setCrews(novo);
                setMax_crew(getNCrewsMax() - 1);
                setCrews(novo);
            }
        }
    }
        
    

    public int[][] getMovimentos() {
        return movimentos;
    }
    
    ///////////////////////// Getter e Setter do caminho /////////////////////////
    public List<Sala> getCaminho() {
        return caminho;
    }

    private void setCaminho(List<Sala> caminho) {
        this.caminho = caminho;
    }

    ///////////////////////// Getter e Setter do percurso /////////////////////////
    public List<Sala> getPercurso() {
        return percurso;
    }

    private void setPercurso(List<Sala> percurso) {
        this.percurso = percurso;
    }

    ///////////////////////// Getter e Setter do custo máximo /////////////////////////
    public int getCustoMax() {
        return custoMax;
    }

    private void setCustoMax(int custoMax) {
        this.custoMax = custoMax;
    }
    
    
    
      /**
     *  Função para as habilidades especiais do CrewMember que ela recebe.
     * @param atual CrewMember - crew member intrudozido
     */
    public void usaHabilidadesEspeciais(CrewMember atual){
        if(atual instanceof Doctor){
            if(getVida() + 1 < getMax_vida()){
                setPontosAcao(getPontosAcao() - 1);
                setVida(getVida() + 1);
            }
            else{
                setVida(getMax_vida());
            }
        }
        else if(atual instanceof RedShirt){
            if(getVida() + 5 <= getMax_vida()){
                getEstaSala(atual).removerCrewEstaSala(atual);
                mataRedShirt();
                setVida(getVida() + 5);
            }
            else{
                mataRedShirt();
                getEstaSala(atual).removerCrewEstaSala(atual);
                setVida(getMax_vida());
            }
        }
        else if(atual instanceof Engineer){
            if(getArmadura() + 1 <= getMax_armadura()){                
                setPontosAcao(getPontosAcao() - 1);
                setArmadura(getArmadura() + 1);
            }
        }
    }
    
    /**
     *  Função para apresentar os locais para onde o Crew Member pode ir
     * recebendo o contador do número de vezes que ele andou é
     * returnado uma string com as opções das salas onde se pode 
     * movimentar e dependendo desse contador as opcões finais
     * são modificadas, sabendo assim se ele se movimentou pelo 
     * menos uma vez ou não.
     * @param contador Integer - número de vezes que o movimento foi realizado
     * @return  String - Opções para onde o utilizador se pode movimentar e as suas ações
     */
    public String mostraOndeIr(int contador){
        int k = 1;
        StringBuilder sb = new StringBuilder();
        List<Integer> boys = new ArrayList<>();
        
        boys = direcoes(getEstaSala(Selecionado()).getId() - 1);
        
        sb.append("Mover o ").append(Selecionado().getNome()).append(" para:\n");
        
        for(int i : boys){
            sb.append("\n").append(k).append(" - ").append(getEstaSala(i).getNome()).append('.');
            k++;
        }
        
        if(contador == 0){
            sb.append("\n0 - Voltar atrás.");
        }
        else{
            sb.append("\n0 - Parar aqui.");
        }
        
        return sb.toString();
    }
    
    /**
     *  Função que mostra os Crew Members e a sua respetiva sala atual,
     * caso ele não tenha ainda sala selecionada, o mesmo irá evidenciá-lo.
     * @return String - Nome do CrewMember = nome da sala onde se encontra
     */
    public String mostraLocalCrew(){
        int i, k, very;
        StringBuilder sb = new StringBuilder();
        CrewMember [] people = new CrewMember[getNCrewsMax()];
        
        for(i = 0; i < getNCrewsMax(); i++){
            people[i] = getCrew(i);
        }
        
        for(k = 0; k < getNCrewsMax(); k++){
            for(very = 0, i = 0; i < getMax_salas(); i++){
                if(getEstaSala(i).existeCrew(people[k])){
                    sb.append("\n").append(people[k].getNome()).append(" = ").append(getEstaSala(i).getNome());
                    very = 1;
                    break;
                }
            }
            if(very == 0){
                sb.append("\n").append(people[k].getNome()).append(" = não selecionado.");
            }
        }
        
        return sb.toString();
    }

    /**
     *  Função que sela a sala escolhida pelo parâmetro que recebe
     * e em caso de sucesso ou de impossibilidade é returnada uma String
     * com a informação sobre o mesmo.
     * @param opcao Integer - opcao a realizar
     * @return String - Sucesso a selar a sala ou razão pela qual não consegue selar
     */
    private String selaEstaSala(int opcao) {
         if(!getEstaSala(opcao - 1).isSelavel()){
             return getEstaSala(opcao - 1).getNome() + " não é selável!";
         }
         
         if(!getEstaSala(opcao - 1).existeCrew() && !getEstaSala(opcao - 1).exiteAliens()){
             getEstaSala(opcao - 1).setSelada(true);
             setQntSealedTokens(getQntSealedTokens() - 1);
             setPontosAcao(getPontosAcao() - 1);
             return getEstaSala(opcao - 1).getNome() + " selada!";
         }
         else{
             return getEstaSala(opcao - 1).getNome() + " não se escontra vazia!";
         }
    }
    
    
    /**
     *  Função que devolve o número de salas que se encontram disponíveis.
     * @return Integer - número de salas disponíveis
     */
    public int nSalasDisponiveis() {
        int i, contador;
        for(i = 0, contador = 0; i < getMax_salas(); i++){
            if(!getEstaSala(i).isSelada()){
                ++contador;
            }
        }
        return contador;
    }
    
     /**
     *  Função para regenerar vida se o jogador tiver um Doctor
     * como um dos seus crew members.
     */
    public int regeneracao() {
        if(getPontosInspiracao() - 1 >= 0){
            if(Selecionado() instanceof Doctor && Selecionado().getNome().equalsIgnoreCase("Doctor")){
               if(getVida() + 2 <= getMax_vida()){
                    setPontosInspiracao(getPontosInspiracao() - 1);
                    setVida(getVida() + 2);
                }
                else{
                    setVida(getMax_vida());
                } 
            }
            else{
                if(getVida() + 1 <= getMax_vida()){
                    setPontosInspiracao(getPontosInspiracao() - 1);
                    setVida(getVida() + 1);
                }
                else{
                    return 1;
                }
            }
        }
        else{
            return 2;
        }
        return 0;
    }
    
    /**
     *  Função para regenerar armadura se o jogador tiver um Engineer
     * como um dos seus crew members.
     */
    public int reparacao() {
        if(getPontosInspiracao() - 1 >= 0){
            if(Selecionado() instanceof Engineer && Selecionado().getNome().equalsIgnoreCase("Engineer")){
                if(getArmadura() + 2 <= getMax_armadura()){
                   setPontosInspiracao(getPontosInspiracao() - 1);
                   setArmadura(getArmadura() + 2);
               }
               else{
                    if(getArmadura() + 1 <= getMax_armadura()){
                        setPontosInspiracao(getPontosInspiracao() - 1);
                        setArmadura(getArmadura() + 1);
                    }
                }  
            } 
            else{
                if(getArmadura() + 1 <= getMax_armadura()){
                    setPontosInspiracao(getPontosInspiracao() - 1);
                    setArmadura(getArmadura() + 1);
                }
                else{
                    return -1;
                }
            } 
        }
        return 0;
    }
    
     /**
     *  Função para comprar Organic Detonators.
     */
    public int compraBombaOD() {
        if(getPontosInspiracao() - 2 >= 0){
            if(getQntOD() + getQntMontadoOD() + 1 <= getMax_arm_OD()){
                setPontosInspiracao(getPontosInspiracao() - 2);
                acrescentaTrap(new OrganicDetonator());
                setQntOD(getQntOD() + 1);
                return 0;
            }
            else{
                return 1;
            }
        }
        return 2;
    }
    
    /**
     *  Função para comprar movimentos extra.
     */
    public int compraMovimentos() {
        if(getPontosInspiracao() - 4 >= 0){
            if(Selecionado() instanceof TransporterChief){
                return 0;
            }
            else{
                if(Selecionado().getMovimentos() + 1 <= Selecionado().getMovs()){
                    setPontosInspiracao(getPontosInspiracao() - 4);
                    Selecionado().setMovimentos(Selecionado().getMovimentos() + 1);
                }
                else{
                    return 1;
                }
            }
        }
        else{
            return 2;
        }
        return -1;
    }

    /**
     *  Função para comprar Particle Dispersors.
     */
    public int compraBombaPD() {                                               
        if(getPontosInspiracao() - 5 >= 0){
            if(getQntPD() + getQntMontadoPD() + 1 <= getMax_arm_PD()){
                setPontosInspiracao(getPontosInspiracao() - 5);
                acrescentaTrap(new ParticleDisperser());
                setQntPD(getQntPD() + 1);
            }
            else{
                return 1;
            }
        }
        else{
            return 2;
        }
        return 0;
    }
    
    /**
     *  Função para comprar Sealed Tokens.
     */
    public int compraSealedTokens() {
        if(getPontosInspiracao() - 5 >= 0){
            if(getQntSealedTokens() + 1 <= getMax_sealedTokens()){
                setPontosInspiracao(getPontosInspiracao() - 5);
                setQntSealedTokens(getQntSealedTokens() + 1);
            }
            else{
                return 1;
            }
        }
        else{
            return 2;       
        }
        return 0;
    }
    
    /**
     *  Função para comprar dados para melhorar o ataque.
     */
    public int compraDadoAtaque() {
        if(getPontosInspiracao() - 6 >= 0){
            if(Selecionado().getAtaque() + 1 <= Selecionado().getMax_dados()){
                setPontosInspiracao(getPontosInspiracao() - 6);
                Selecionado().setAtaque(Selecionado().getAtaque() + 1);
            }
            else{
                return 1;
            }
        }
        else{
            return 2;
        }
        return 0;
    }
    
    /**
     *  Função para comprar extra ataque.
     */
    public int compraExtraAtaque() {
        if(getPontosInspiracao() - 6 >= 0){
            setPontosInspiracao(getPontosInspiracao() - 6);
            Selecionado().setExtraAtaque(Selecionado().getExtraAtaque() + 1);
        }
        else{
            return -1;
        }
        return 0;
    }
    
    /**
     *  Função das habilidades passívas.
     */
    public int habilidadesPassivas() {
        for(CrewMember ali : getCrews()){
            if(getEstaSala(ali).getNome().equalsIgnoreCase("Sick Bay") && ali instanceof Doctor){     // Regeneração de vida se tiver um Doctor
                if(getVida() + 1 <= getMax_vida()){
                    setVida(getVida() + 1);
                    return 1;
                }
            }
            if(getEstaSala(ali).getNome().equalsIgnoreCase("Engineering") && ali instanceof Engineer){// Regeneração de armadura se tiver um Engineering
                if(getArmadura() + 1 <= getMax_armadura()){
                    setArmadura(getArmadura() + 1);
                    return 2;
                }
            }
        }
        return 0;
    }
    
    /**
     *  Função de selar sala.
     */
    public int selarSala() {
        int opcao = -1;
        if(getQntSealedTokens() <= 0){
            return opcao;
        }
        return 1;
    }
    
    /**
     *  Função para montar as Traps.
     * @return 
     */
    public int montarBomba() {
        int opcao = -1;
        if(getTrap().isEmpty()){
            return opcao;
        }
        return 1;
    }
    
    /**
     *  Função para montar um Particle Dispersor e um Organic Detonator.
     */
    public int detonarBomba() {
        if(getQntPD() == 0 && getQntOD() == 0){
            return -1;
        }
        return 1;
    }
    
    /**
     *  Função para detonar um Particle Dispersor.
     */
    public int detonarBombaMontada() {
        if(getQntMontadoPD() == 0){
            return -1;
        }
        return 1;
    }
    
    public int detonarBombaAqui(int opcao){
        if(opcao < 0 || opcao > getMax_salas()){
            return 0;
        }
        if(!getEstaSala(opcao - 1).contemBombasTipo(new ParticleDisperser())){
            return 0;
        }
        
        if(getEstaSala(opcao - 1).existeCrew()){
            setVida(0);
            return 1;
        }
        else if(getEstaSala(opcao - 1).exiteAliens()){
            getEstaSala(opcao - 1).removerAliens();   
        }
        
        getEstaSala(opcao - 1).removeBombasTipo(new ParticleDisperser());
        removeTrap(new ParticleDisperser());
        setQntMontadoPD(getQntMontadoPD() - 1);
        setPontosAcao(getPontosAcao() - 1);
        
        return 1;
    }
    
    /**
     *  Retorna um inteiro que representa o id da sala
     * no seu array dependendo do tipo de crew member selecionado.
     * @return Integer - sala a atacar
     */
    public int retornaSalaAtaque() {
        if(Selecionado() instanceof ScienceOfficer){
            if(getEstaSala(Selecionado()).exiteAliens()){
                return getEstaSala(Selecionado()).getId() - 1;
            }
            else{
                List<Integer> alas = new ArrayList<>(direcoes(getEstaSala(Selecionado()).getId() - 1));
                for(int alias : alas){
                    if(getEstaSala(alias).exiteAliens()){
                        return alias;
                    }
                }
            }
        }
        else if(!getEstaSala(Selecionado()).exiteAliens()){
            return -1;
        }
        else if(getEstaSala(Selecionado()).exiteAliens()){
            return (getEstaSala(Selecionado()).getId() - 1);
        }
        return -1;
    }
    
    /**
     *  Função para o lançamento do dado do crew member selecionado e ataque ao alien
     * na sala onde esse crew member se encontra.
     * @param nDice
     * @param ali Integer - número da sala onde o alien está a atacar
     */
    public void lancaDado(int nDice, int ali) {
        if((nDice + Selecionado().getExtraAtaque()) > 3 && Selecionado() instanceof Captain){
            getEstaSala(ali).removerAlienSala();
            if(getPontosInspiracao() < getMax_pontosInspiracao()){
                setPontosInspiracao(getPontosInspiracao() + 1);
            }
        }
        else if((nDice + Selecionado().getExtraAtaque()) > 4){
            getEstaSala(ali).removerAlienSala();
            if(getPontosInspiracao() < getMax_pontosInspiracao()){
                setPontosInspiracao(getPontosInspiracao() + 1);
            }
        }
    }
    
    public int lancaDadoValor(){
        int aux, nDice = 0;
        
        Dado D = new Dado(6);
        
        for(int i = 0; i < Selecionado().getAtaque(); i++){
            aux = D.giraDado(1);
            nDice += aux ;
        }
        return nDice;
    }
    
    /**
     *  Função para a seleção do valor que o ataque do utilizador provocará num alien.
     * @param aux
     * @param ali Integer - sala onde o crew member selecionado se encontra
     */
    public void selecionaDado(int aux, int ali) {
        if(aux > 3 && Selecionado() instanceof Captain){
            getEstaSala(ali).removerAlienSala();
            if(getPontosInspiracao() < getMax_pontosInspiracao()){
                setPontosInspiracao(getPontosInspiracao() + 1);
            }
        }
        else if(aux > 4){
            getEstaSala(ali).removerAlienSala();
            if(getPontosInspiracao() < getMax_pontosInspiracao()){
                setPontosInspiracao(getPontosInspiracao() + 1);
            }
        }
    }
    
     /**
     *  Movimentos dos Aliens.
     */
    public void moveAliens() {
        int i, very = 0;
        
        // Verificação se existem Traps e Aliens na mesma Sala
        for(i = 0; i < getMax_salas(); i++){
            if(getEstaSala(i).exiteArmadilhas() && getEstaSala(i).exiteAliens()){
                List<Alien> alienisnas = new ArrayList<>(getEstaSala(i).getAliens());
                for(Alien alien : alienisnas){
                    explodeTrap(i, alien);
                }
            }
        }
        
        for(i = 0; i < getMax_salas(); i++){
            // Verificação se existem Crew Members e Aliens na mesma Sala
            if(getEstaSala(i).exiteAliens() && getEstaSala(i).existeCrew()){
                for(Alien alien : getEstaSala(i).getAliens()){
                    if(!alien.isAtacou()){
                        Dado D = new Dado(6);
                        Dado C = new Dado(6);

                        if(C.giraDado(1) < 3 && getEstaSala(i).existeAquiComms()){
                            continue;
                        }
                        else if(D.giraDado(1) > 4){
                            setVida(getVida() - 1);
                            System.out.println("__!-Dano na Sala: " + getEstaSala(i).getNome() + "-!__"); //debug
                        }
                        else{//debug
                            System.out.println("__!-Tentativa de Dano na Sala: " + getEstaSala(i).getNome() + "-!__");//debug
                        }//debug
                        alien.setAtacou(true);
                        alien.setMovimentado(true);
                    }
                }
            }
            else{
                for(int ali : direcoes(i)){
                    // Verificação para onde o Alien se pode movimentar
                    // Se tiver um Crew Member na sala ao lado
                    if(getEstaSala(ali).existeCrew() && getEstaSala(i).exiteAliens()){
                        List<Alien> alienisnas = new ArrayList<>(getEstaSala(i).getAliens());
                        for(Alien alien : alienisnas){
                            if(!alien.isMovimentado()){
                                getEstaSala(i).removerAlien(alien);
                                getEstaSala(ali).acrescentaAlien(alien);
                                System.out.println("====!-Sai da Sala: " + getEstaSala(i).getNome() + "-!====\n====!-Fui para a Sala: " + getEstaSala(ali).getNome() + "-!===="); // INFORMAÇÃO

                                alien.setMovimentado(true);
                                
                                if(getEstaSala(ali).exiteArmadilhas() && getEstaSala(ali).exiteAliens()){   // Verificação se existe uma Trap e um Alien na mesma Sala
                                    very = explodeTrap(i, alien);
                                }
                                //rever
                                if(!alien.isAtacou() && very != 1){
                                    // Ataque dos Aliens
                                    Dado D = new Dado(6);
                                    Dado C = new Dado(6);
                                    if(C.giraDado(1) < 3 && getEstaSala(ali).existeAquiComms()){
                                        continue;
                                    }
                                    else if(D.giraDado(1) > 4){
                                        setVida(getVida() - 1);
                                        System.out.println("====!-Dano na Sala Nova: " + getEstaSala(ali).getNome() + "-!===="); // debug
                                    }
                                    else{//debug
                                        System.out.println("====!-Tentativa de Dano na Sala Nova: " + getEstaSala(ali).getNome() + "-!====");//debug
                                    }//debug1
                                    alien.setAtacou(true);
                                }
                                //rever
                            }
                        }
                        break;
                    }
                    else{
                       very = 0;
                       // Movimentos para perto dos Crew Members
                       if(!getEstaSala(ali).existeCrew() && getEstaSala(i).exiteAliens()) {
                           List<Alien> alienisnas = new ArrayList<>(getEstaSala(i).getAliens());
                           for(Alien alien : alienisnas){
                               if(!alien.isMovimentado()){
                                   getPercurso().add(getEstaSala(i));
                                   calculaMenorCaminho(1, getEstaSala(i));

                                   if(!(getCaminho().isEmpty())){
                                       System.out.println("====!- 1 Sai da Sala: " + getCaminho().get(0).getNome() + "-!====\n====!-Fui para a Sala: " + getCaminho().get(1).getNome() + "-!===="); // INFORMAÇÃO

                                       getEstaSala(getCaminho().get(0)).removerAlien(alien);
                                       getEstaSala(getCaminho().get(1)).acrescentaAlien(alien);
                                       alien.setMovimentado(true);
                                       alien.setAtacou(true);

                                       very = explodeTrap(i, alien);
                                   }
                                   if(very != 1){
                                       setArmadura(getArmadura() - 1);
                                       System.out.println("###DANO ARMADURA###");//debug
                                   }
                                   getCaminho().clear();
                                   getPercurso().clear();
                                   setCustoMax(5);
                               }
                           }
                       }
                   }
                }
            }
        }
    }

    public int explodeTrap(int i, Alien alien){
        // Verificação se existe uma Trap e um Alien na mesma Sala
        for(Traps tr : getEstaSala(i).getArmadilhas()){
            if(tr instanceof OrganicDetonator && getEstaSala(i).exiteAliens()){
                getEstaSala(i).removerAlien(alien);
                getEstaSala(i).removerArmadilhaAutomatica();
                setQntMontadoOD(getQntMontadoOD() - 1);
                setPontosInspiracao(getPontosInspiracao() + 1);
                return 1;
            }
        }
        return 0;
    }
    
    /**
     *  Cálculo do menor caminho a percorrer pelos Aliens.
     * Recebe o custo e a sala onde se encontra.
     * @param custo Integer - custo do deslocamento
     * @param s Sala - sala em que se encontra
     */
    public void calculaMenorCaminho(int custo, Sala s){
        if(custo <= getCustoMax()){
            // Se tinha Red Shirt e este usou o especial
            if(getNCrewsMax() == 1){
                if(getEstaSala(getCrew(0)).equals(s) ){ 
                    setCustoMax(custo);
                    getCaminho().clear();
                    getCaminho().addAll(getPercurso());
                    return;
                }
                else{
                    for(int sal : direcoes(s.getId() - 1)){
                        if(getPercurso().isEmpty()){
                            getPercurso().add(getEstaSala(sal));
                            calculaMenorCaminho(++custo, getEstaSala(sal));
                            getPercurso().remove(getPercurso().size() - 1);
                        }
                        else{
                            int aux = 0;
                            for(Sala p : getPercurso()){
                                if(p.equals(getEstaSala(sal))){
                                    aux = 1;
                                    break;
                                }
                            }
                            if(aux == 0){
                                getPercurso().add(getEstaSala(sal));
                                calculaMenorCaminho(++custo, getEstaSala(sal));
                                getPercurso().remove(getPercurso().size() - 1);
                            }
                        }
                    }
                }
            }
            else{
                if(getEstaSala(getCrew(0)).equals(s) || getEstaSala(getCrew(1)).equals(s) ){
                    setCustoMax(custo);
                    getCaminho().clear();
                    getCaminho().addAll(getPercurso());
                    return;
                }
                else{
                    for(int sal : direcoes(s.getId() - 1)){
                        if(getPercurso().isEmpty()){
                            getPercurso().add(getEstaSala(sal));
                            calculaMenorCaminho(++custo, getEstaSala(sal));
                            getPercurso().remove(getPercurso().size() - 1);
                        }
                        else{
                            int aux = 0;
                            for(Sala p : getPercurso()){
                                if(p.equals(getEstaSala(sal))){
                                    aux = 1;
                                    break;
                                }
                            }
                            if(aux == 0){
                                getPercurso().add(getEstaSala(sal));
                                calculaMenorCaminho(++custo, getEstaSala(sal));
                                getPercurso().remove(getPercurso().size() - 1);
                            }
                        }
                    }
                }
            }
        }
    }
    
    /**
     *  Recebe o id da sala em que se encontra e retorna
     * uma lista de inteiros dos ids das salas que estão
     * ligadas a essa sala e que não se encontram seladas.
     * @param i Integer - id da sala onde se encontra
     * @return List Integer  - ids das salas ligadas a esta
     */
    public List<Integer> direcoes(int i) {
        int k;
        List<Integer> elas = new ArrayList<>();
        
        for(k = 0; k < getMovimentos().length; k++){
            if(getMovimentos()[k][i] == 1 && !getEstaSala(k).isSelada()){
                elas.add(k);
            }
        }
        return elas;
    }
    
    /**
     *  Função que faz reset a todos os parametros dos aliens
     * no fim da sua fase de ataque.
     */
    public void resetAliens() {
        for(int i = 0; i < getMax_salas(); i++){
            for(Alien ali : getEstaSala(i).getAliens()){
                ali.setAtacou(false);
                ali.setMovimentado(false);
            }
        }
    }
    
    
//    /**
//     *  Função de criação do novo Jorney Tracker do Jogo.
//     */
//    public void criaNovoJorneyTracker() {     // INCOMPLETO
//        Scanner sc = new Scanner(System.in);
//        boolean rest = false;
//        int opt, i = 1, num, max_al = getMax_aliens();
//        String [] JT_aux = new String[getJourneyTackerTAM()];
//        System.out.println(criaNovoJorneyTrackerImprime(0,0,0));
//
//        JT_aux[0] = "S";
//        JT_aux[getJourneyTackerTAM() - 1] = "E";
//
//        do{
//            System.out.println(criaNovoJorneyTrackerImprime(i,0,0));
//            while(!sc.hasNextInt()){
//                sc.next();
//            }
//            opt = sc.nextInt();
//
//            switch(opt){
//                case 0:
//                    return;
//                case 1:
//                    if(!rest){
//                        if(JT_aux[i - 1].length() == 2){
//                            StringBuilder estes = new StringBuilder();
//                            estes.append(JT_aux[i - 1]).append("*");
//                            JT_aux[i - 1] = estes.toString();
//                        }
//                        if(JT_aux[i - 1].length() == 3 && JT_aux[i - 1].charAt(2) != '*'){
//                            StringBuilder estes = new StringBuilder();
//                            estes.append(JT_aux[i - 1]).append("*");
//                            JT_aux[i - 1] = estes.toString();
//                        }
//                        JT_aux[i] = "R";
//                        rest = true;
//                        max_al = getMax_aliens();
//                    }
//                    else{
//                        System.out.println(criaNovoJorneyTrackerImprime(0,opt,0));
//                        i--;
//                    }
//                    break;
//                case 2:
//                    if(max_al == 0){
//                        i--;
//                        break;
//                    }
//                    do{
//                        System.out.println(criaNovoJorneyTrackerImprime(0,opt,max_al));
//                        while(!sc.hasNextInt()){
//                            sc.next();
//                        }
//                        num = sc.nextInt();
//                    }while(num > max_al || num < 1);
//                    max_al -= num;
//                    StringBuilder cons = new StringBuilder();
//                    cons.append(num).append("A");
//                    JT_aux[i] = cons.toString();
//                    rest = false;
//                    break;
//                case 3:
//                    if(max_al == 0){
//                        i--;
//                        break;
//                    }
//                    do{
//                        System.out.println(criaNovoJorneyTrackerImprime(0,opt,max_al));
//                        while(!sc.hasNextInt()){
//                            sc.next();
//                        }
//                        num = sc.nextInt();
//                    }while(num > max_al || num < 1);
//                    max_al -= num;
//                    StringBuilder record = new StringBuilder();
//                    record.append(num).append("A").append("*");
//                    JT_aux[i] = record.toString();
//                    rest = false;
//                    break;
//                default:
//                    i--;
//                    break;
//            }
//            i++;
//        }while(i < getJourneyTackerTAM() - 1 || opt < 0 || opt > 3);
//        
//        System.out.println(criaNovoJorneyTrackerImprime(i,0,0));
//        
//        setJornneyTracker(JT_aux);
//        
//    }
    
    /**
     *  Função que retorna uma String dependendo dos parâmetros
     * introduzidos.
     * Se opcao == 0, max_al == 0 e i == 0:
     * Devolve a intrudoção da função e como o Jorney Tracker começa;
     * Se opcao == 0 e i == 14:
     * Devolve a indicação do final da construção do Jorney Tracker;
     * Se opcao == 0 e i != 0 e i != 14:
     * Devolve as opções de construção do Jorney Tracker;
     * Se opcao == 1:
     * Devolve um aviso que não pode existir rest phases seguidas no Jorney Tracker;
     * Se opcao != 0 e opcao != 1:
     * Devolve um aviso de quantos Aliens o utilizador ainda pode introduzir na crew phase no Jorney Tracker;
     * @see criaNovoJorneyTracker
     * @param i Integer - opcao a realizar
     * @param opcao Integer - opcao a realizar
     * @param max_al Integer - numero máximo de aliens que pode criar
     * @return String
     */
    public String criaNovoJorneyTrackerImprime(int i,int opcao,int max_al){
        StringBuilder sb = new StringBuilder();
        if(opcao == 0){
            switch(i){
                case 0:
                    sb.append("Crie o seu próprio Journey Tracker!\n").append("A primeira jornada é S.\n");
                    break;
                case 14:
                    sb.append("A última jornada é terminada em E.\n");
                    break;
                default:
                    sb.append("1 - R.").append("\n2 - A.").append("\n3 - A*.").append("\n0 - Sair.").append("\n\nJornada ").append(i).append(": ");
                    break;
            }
        }
        else{
            switch(opcao){
                case 1:
                    sb.append("Não pode ter Rest Phases seguidas.");
                    break;
                default:
                    sb.append("Insira a quantidade de Aliens (entre 1 e ").append(max_al).append("): ");
                    break;
            }
        }
        return sb.toString();
    }
    
     /**
     *  Função para a seleção de um crew member que o 
     * utilizador possui.
     * @param opcao Integer - id do crew member a selecionar
     */
    public void selecionaEsteCrewMember(int opcao) {
        for(int i = 0; i < getCrews().length; i++){
            if(opcao == getCrew(i).getId()){
                getCrew(i).setSelecionado(true);
                break;
            }
        }
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        for(Sala sl : Salas){
            sb.append(sl.toString());
        }
        return sb.toString();
    }

    public void reduzPontosAcao() {
        setPontosAcao(getPontosAcao() - 1);
    }

    public void aumentaPontosAcao() {
        setPontosAcao(getPontosAcao() + 1);
    }

    public void resetPontosAcao() {
        setPontosAcao(getMax_pontosAcao());
    }
    
    public String selarAquiSala(int opcao){
        return selaEstaSala(opcao);
    }

    public int montarBombaOrg() {
        if(getQntOD() > 0 && getQntMontadoOD() < getMax_arm_OD()){
            removeTrap(new OrganicDetonator());
            getEstaSala(Selecionado()).getArmadilhas().add(new OrganicDetonator());
            setQntOD(getQntOD() - 1);
            setQntMontadoOD(getQntMontadoOD() + 1);
            setPontosAcao(getPontosAcao() - 1);
            return 3;
        }
        return 1;
    }
    
    public int montarBombaPart() {
        if(getQntPD() > 0 && getQntMontadoPD() < getMax_arm_PD()){
            getTrap().remove(new ParticleDisperser());
            getEstaSala(Selecionado()).getArmadilhas().add(new ParticleDisperser());
            setQntPD(getQntPD() - 1);
            setQntMontadoPD(getQntMontadoPD() + 1);
            setPontosAcao(getPontosAcao() - 1);
            return 4;
        }
        return 2;
    }

    public int selecionaDadoOk(int opcao) {
        if(opcao < (Selecionado().getAtaque()*1 + Selecionado().getExtraAtaque()) || opcao > (Selecionado().getAtaque()*6 + Selecionado().getExtraAtaque())){
            return -1;
        }
        return 1;
    }

    public void especialShuttlePilot() {
        setMax_vida(getMax_vida() + 4);
        setVida(getMax_vida());
    }

    public void especialMoralOfficer() {
        setPontosInspiracao(5);
    }

    public void especialCommander() {
        setMax_pontosAcao(6);
        setPontosAcao(getMax_pontosAcao());
    }

    public int adicionaCrewMember(int segupcao,int contador, List<CrewMember> escolhas) {
        switch(segupcao){
            case 0:
                return 0;
            case 1:
                escolhas.add(new Doctor());
                escolhas.get(contador - 1).setId(contador);
                return 1;
            case 2:
                escolhas.add(new CommsOfficer());
                escolhas.get(contador - 1).setId(contador);
                return 1;
            case 3:
                escolhas.add(new RedShirt());
                escolhas.get(contador - 1).setId(contador);
                return 1;
            case 4:
                escolhas.add(new ScienceOfficer());
                escolhas.get(contador - 1).setId(contador);
                return 1;
            case 5:
                escolhas.add(new Engineer());
                escolhas.get(contador - 1).setId(contador);
                return 1;
            case 6:
                escolhas.add(new Captain());
                escolhas.get(contador - 1).setId(contador);
                return 1;
            case 7:
                escolhas.add(new Commander());
                escolhas.get(contador - 1).setId(contador);
                especialCommander();
                return 1;
            case 8:
                escolhas.add(new TransporterChief());
                escolhas.get(contador - 1).setId(contador);
                return 1;
            case 9:
                escolhas.add(new MoralOfficer());
                escolhas.get(contador - 1).setId(contador);
                especialMoralOfficer();
                return 1;
            case 10:
                escolhas.add(new SecurityOfficer());
                escolhas.get(contador - 1).setId(contador);
                return 1;
            case 11:
                escolhas.add(new NavigationOfficer());
                escolhas.get(contador - 1).setId(contador);
                return 1;
            case 12:
                escolhas.add(new ShuttlePilot());
                escolhas.get(contador - 1).setId(contador);
                especialShuttlePilot();
                return 1;
        }
        return -1;//rever , em caso de erro manda isto
    }

    public void guardaCrews(List<CrewMember> escolhas, int contador) {
        if(contador == getNCrewsMax()){
            CrewMember [] people = new CrewMember[2];
            for(int i = 0; i < escolhas.size(); i++){
                people[i] = escolhas.get(i);
            }
            setCrews(people);
        }
    }
    
}
