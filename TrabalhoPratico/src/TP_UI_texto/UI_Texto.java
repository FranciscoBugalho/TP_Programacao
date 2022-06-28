package TP_UI_texto;

import TP_Logica.*;
import TP_Logica.CrewMembers.*;
import TP_Logica.Traps.*;
import TP_Logica.Estados.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class UI_Texto implements Serializable {

    private String [] opcoesCrewMember = {  // Array com opções dos Crew Members
        "0 - Voltar atrás.",
        "1 - Doctor.",
        "2 - Comm's Officer.",
        "3 - Red Shirt.",
        "4 - Science Officer.",
        "5 - Engineer.",
        "6 - Captain.",
        "7 - Commander.",
        "8 - Transporter Chief.",
        "9 - Moral Officer.",
        "10 - Security Officer.",
        "11 - Navigation Officer.",
        "12 - Shuttle Pilot."
    };
    
    private DestinationEarth Jogo;          // Jogo
    private boolean fim = false;            // Variável de controlo

    public UI_Texto(DestinationEarth Jogo) {
        this.Jogo = Jogo;
    }

    public void corre() {                   // Verificação do estado atual
        while (!fim) {
            IEstado estado = Jogo.getEstado();
            if (estado instanceof EsperaInicio) {
                System.out.println(imprimeDesign());
                UI_EsperaInicio();
            } else if (estado instanceof EscolheCrewMember) {
                UI_EscolheCrewMember();
            } else if(estado instanceof SelecaoJourneyTracker){
                UI_SelecaoJourneyTracker();
            } else if (estado instanceof ColocaTokens1CrewMember) {
                UI_ColocaTokens1CrewMember();
            } else if (estado instanceof ColocaTokens2CrewMember) {
                UI_ColocaTokens2CrewMember();
            } else if (estado instanceof UsaPontosInspiracao) {
                UI_UsaPontosInspiracao();
            } else if (estado instanceof UsaPontosAcao) {
                UI_UsaPontosAcao();
            } else if (estado instanceof SelecionaCrewMemberJogar) {
                UI_SelecionaCrewMemberJoga();
            } else if (estado instanceof AguardaLancamentoDado) {
                UI_AguardaLancamentoDado();
            } else if(estado instanceof AguardaEscolhaSala) {
                UI_AguardaEscolhaSala();
            } else if(estado instanceof FimDeJogo) {
                UI_FimDeJogo();
            }
        }
    }

    public void UI_EsperaInicio() {
        int opcao;
        
        do{
            System.out.println(imprime(Jogo.getEstado()));
            opcao = opcoesInt();
            switch(opcao){
                case 0:
                    fim = true;
                    return;
                case 1:
                    System.out.println("\nInsira um username: ");
                    Jogo.getGameData().setNome(opcaoLine());  
                    Jogo.comecarJogo();
                    return;
                case 2:
                    System.out.println("\nInsira nome do ficheiro do jogo:");
                    Jogo.loadGame(opcaoLine());
                    return;
            }
        }while(opcao < 0 || opcao > 2);
    }
    
    public void UI_SelecaoJourneyTracker() {
        int opcao;
        do{
            System.out.println(imprime(Jogo.getEstado()));
            opcao = opcoesInt();
            switch(opcao){
                case 0:
                    fim = true;
                    return;
                case 1:
                    System.out.println(mostraJornneyTracker());
                    Jogo.comecarJogo();
                    return;
//                case 2:
//                    Jogo.getGameData().criaNovoJorneyTracker();
//                    return;
            }
        }while(opcao < 0 || opcao > 1);
        
        System.out.println(mostraJornneyTracker());
        
        Jogo.comecarJogo();
    }

    public void UI_EscolheCrewMember() {
        List<CrewMember> escolhas = new ArrayList<>();
        int opcao, segupcao = 0, contador = 0, repetido = -1;
        
        do{
            System.out.println(imprime(Jogo.getEstado()));
            opcao = opcoesInt();
            switch(opcao){
                case 0:
                    fim = true;
                    System.exit(0);
                    return;
                case 1:
                    ++contador;
                    System.out.println(mostraOpcoesCrewMember());
                    do{
                        segupcao = opcoesInt();
                    }while(repetido == segupcao || (segupcao < 0 || segupcao > 12));

                    if(repetido == -1 && segupcao != 0){
                        repetido = segupcao;
                    }
                    if(Jogo.getGameData().adicionaCrewMember(segupcao, contador, escolhas) == 0){
                        --contador;
                    }
                    break;
                case 2:
                    ++contador;
                    Random rd = new Random();
                    do{
                        segupcao = rd.nextInt(12) + 1;
                    }while(segupcao == repetido);
                    if(repetido == -1){
                        repetido = segupcao;
                    }
                    Jogo.getGameData().adicionaCrewMember(segupcao, contador, escolhas);
                    break;
            }
            System.out.println(tripulacaoEscolhida(escolhas));
        }while(contador != Jogo.getGameData().getNCrewsMax());
        
        Jogo.getGameData().guardaCrews(escolhas, contador);
        
        Jogo.comecarJogo();
    }

    public void UI_ColocaTokens1CrewMember() {
        int opcao, op, dice;
        CrewMember people = Jogo.getGameData().getCrew(0);
        
        do{
            System.out.println(imprime(Jogo.getEstado()));
            
            op = opcoesInt();

            switch(op){
                case 1:
                    Dado D = new Dado(12);
                    dice = D.giraDado(1);
                    imprimeDadoD12(dice);
                    Jogo.getGameData().adicionaCrewMember1(dice);
                    Jogo.getGameData().mostraLocalCrew();
                    Jogo.comecarJogo();
                    return;
                case 2:
                    System.out.println("Sala a colocar o " + people.getNome() + ':');
                    System.out.println(Jogo.getGameData().mostraSalas());

                    do{
                        opcao = opcoesInt();
                    }while(opcao < 0 || opcao > Jogo.getGameData().getMax_salas());

                    if(opcao == 0){
                        return;
                    }
                    
                    Jogo.getGameData().adicionaCrewMember1(opcao);
                    Jogo.getGameData().mostraLocalCrew();
                    Jogo.comecarJogo();
                    return;
                }
        } while(op < 0 || op > 2);
    }

    public void UI_ColocaTokens2CrewMember() {
        int opcao, op, dice;
        CrewMember people = Jogo.getGameData().getCrew(1);
        
        do{
            System.out.println(imprime(Jogo.getEstado()));
            
            op = opcoesInt();
            
            switch(op){
                case 1:
                    Dado D = new Dado(12);
                    dice = D.giraDado(1);
                    imprimeDadoD12(dice);
                    Jogo.getGameData().adicionaCrewMember2(dice);
                    Jogo.getGameData().mostraLocalCrew();
                    Jogo.getGameData().setTurno(Jogo.getGameData().getTurno() + 1);
                    Jogo.proximoTurno();
                    return;
                case 2:
                    System.out.println("Sala a colocar o " + people.getNome() + ':');
                    System.out.println(Jogo.getGameData().mostraSalas());

                    do{
                        opcao = opcoesInt();
                    }while(opcao < 1 || opcao > Jogo.getGameData().getMax_salas());

                    Jogo.getGameData().adicionaCrewMember2(opcao);
                    Jogo.getGameData().mostraLocalCrew();
                    Jogo.getGameData().setTurno(Jogo.getGameData().getTurno() + 1);
                    Jogo.proximoTurno();
                    return;
                }
        } while(op < 0 || op > 2);
    }

    public void UI_UsaPontosInspiracao() {
        int opcao;
        
        Jogo.saveEstadoAnterior();
        
        if(Jogo.getGameData().getArmadura() <= 0 || Jogo.getGameData().getVida() <= 0){
            Jogo.fimJogo();
            return;
        }
        
        if(Jogo.getGameData().getPontosInspiracao() == 0){
            Jogo.acabouInspiracao();
            return;
        }
        
        if(!Jogo.getGameData().algumSelecionado()){
            Jogo.comecarJogo();
            return;
        }
        
        do{
            System.out.println(imprime(Jogo.getEstado()));
            opcao = opcoesInt();
            switch(opcao){
                case 0:
                    Jogo.reset();
                    Jogo.recomecarJogo();
                    return;
                case 1:
                    switch(Jogo.getGameData().regeneracao()){
                        case 1:
                            System.out.println(regeneracaoImprime(1));
                            break;
                        case 2:
                            System.out.println(regeneracaoImprime(2));
                            break;
                    }
                    return;
                case 2:
                    if(Jogo.getGameData().reparacao() == -1)
                        System.out.println(reparacaoImprimeErro());                          
                    return;
                case 3:
                    switch(Jogo.getGameData().compraBombaOD()){
                        case 0:
                            break;
                        case 1:
                            System.out.println(compraBombaODImprime(1));
                            break;
                        case 2:
                            System.out.println(compraBombaODImprime(2));
                            break;
                    }
                    return;
                case 4:
                    switch(Jogo.getGameData().compraMovimentos()){
                        case 0:
                            System.out.println(compraMovimentosImprime(0));
                            break;
                        case 1:
                            System.out.println(compraMovimentosImprime(1));
                            break;
                        case 2:
                            System.out.println(compraMovimentosImprime(2));
                            break;
                    }
                    return;
                case 5:
                    switch(Jogo.getGameData().compraBombaPD()){
                        case 1:
                            System.out.println(compraBombaPDImprime(1));
                            break;
                        case 2:
                            System.out.println(compraBombaPDImprime(2));
                            break;
                    }
                    return;
                case 6:
                    switch(Jogo.getGameData().compraSealedTokens()){
                        case 1:
                            System.out.println(compraSealedTokensImprime(1));
                            break;
                        case 2:
                            System.out.println(compraSealedTokensImprime(2));
                            break;
                    }
                    return;
                case 7:
                    switch(Jogo.getGameData().compraDadoAtaque()){
                        case 1:
                            System.out.println(compraDadoAtaqueImprime(1));
                            break;
                        case 2:
                            System.out.println(compraDadoAtaqueImprime(2));
                            break;
                    }
                    return;
                case 8:
                    if(Jogo.getGameData().compraExtraAtaque() == -1)
                        System.out.println(compraExtraAtaqueErro());
                    return;
                case 9:
                    Jogo.selecionarOutroCrewMember();
                    return;
                case 10:
                    Jogo.terminarTurno();
                    return;
                case 11:
                    Jogo.saveGame(opcaoLine());
                    return;
            }
        }while(opcao < 0 || opcao > 11);
    }

    public void UI_UsaPontosAcao() {
        Dado D = new Dado(12);
        int opcao, k;
        
        if(Jogo.getGameData().getArmadura() <= 0 || Jogo.getGameData().getVida() <= 0){
            Jogo.fimJogo();
            return;
        }
        
        Jogo.saveEstadoAnterior();

        if(Jogo.getGameData().isProximoTurno()){
            Jogo.getGameData().setProximoTurno(false);
            
            System.out.println("======== Alien Spawn ========");
            
            for(int i = 0; i < Integer.parseInt(Jogo.proximoTurnoDetecao()); i++){
                k = D.giraDado(1);
                Jogo.getGameData().getEstaSala(k - 1).acrescentaAlien(new Alien());
            }
            
            Jogo.getGameData().resetAliens();
            
            switch(Jogo.getGameData().habilidadesPassivas()){
                case 1:
                    System.out.println(habilidadesPassivasImprime(1));
                    break;
                case 2:
                    System.out.println(habilidadesPassivasImprime(2));
                    break;
            }
        }
        
        if(!Jogo.getGameData().algumSelecionado()){
            Jogo.comecarJogo();
            return;
        }
        
        if(Jogo.getGameData().getPontosAcao() == 0){
            Jogo.getGameData().moveAliens();
            Jogo.acabouPontosAcao();
            return;
        }
        
        System.out.println(Jogo.getGameData().toString()); // Mostra as modificações do jogo
        
        do{
            System.out.println(imprime(Jogo.getEstado()));
            opcao = opcoesInt();
            switch(opcao){
                case 0:
                    Jogo.reset();
                    Jogo.recomecarJogo();
                    return;
                case 1:
                    Jogo.escolhaSala();
                    return;
                case 2:
                    Jogo.getGameData().reduzPontosAcao();
                    Jogo.lancamentoDado();
                    return;
                case 3:
                    //Jogo.getGameData().montarBomba();//rever
                    switch(Jogo.getGameData().detonarBomba()){
                        case -1:
                            break;
                        case 1:
                            do{
                                System.out.println(montarBombaImprime(0));
                                opcao = opcoesInt();

                                switch(opcao){
                                    case 0:
                                        break;
                                    case 1:
                                        System.out.println(montarBombaImprime(Jogo.getGameData().montarBombaOrg()));
                                        break;
                                    case 2:
                                        System.out.println(montarBombaImprime(Jogo.getGameData().montarBombaPart()));
                                        break;
                                    default:
                                        break;
                                }
                            }while(opcao < 0 || opcao > 2);
                            break;
                    }
                    return;
                case 4:
                    //Jogo.getGameData().detonarBomba();//rever
                    switch(Jogo.getGameData().detonarBombaMontada()){
                        case -1:
                            System.out.println(detonarBombaImprime(0));
                            break;
                        default:
                            do{
                                System.out.println(detonarBombaImprime(2));
                                opcao = opcoesInt();
                                if(opcao == 0){
                                    break;
                                }
                            }while(Jogo.getGameData().detonarBombaAqui(opcao) == 0);
                            System.out.println(detonarBombaImprime(1));
                            break;
                    }
                    return;
                case 5:
                    //Jogo.getGameData().selarSala();//rever
                    switch(Jogo.getGameData().selarSala()){
                        case -1:
                            System.out.println(selarImprime(-1));
                            break;
                        default:
                            do{
                                System.out.println(selarImprime(1));
                                opcao = opcoesInt();
                            }while(opcao < 0 || opcao > Jogo.getGameData().getMax_salas());
                            if(opcao == 0){
                                break;
                            }
                            System.out.println(Jogo.getGameData().selarAquiSala(opcao));
                            break;
                    }
                    return;
                case 6:
                    Jogo.getGameData().Selecionado().setSelecionado(false);
                    Jogo.comecarJogo();
                    return;
                case 7:
                    Jogo.getGameData().usaHabilidadesEspeciais(Jogo.getGameData().Selecionado());
                    break;
                case 8:
                    Jogo.getGameData().moveAliens();
                    Jogo.acabouPontosAcao();
                    return;
                case 9:
                    Jogo.saveGame(opcaoLine());
                    return;
            }
        }while(opcao < 0 || opcao > 9);
    }

    public void UI_SelecionaCrewMemberJoga() {
        int opcao;
        System.out.println(imprime(Jogo.getEstado()));
        opcao = opcoesInt();
        Jogo.getGameData().selecionaEsteCrewMember(opcao);
        Jogo.voltarAtras();
    }

    public void UI_AguardaLancamentoDado() {
        int opcao, aqui = -1, ali, valor;

        aqui = Jogo.getGameData().retornaSalaAtaque();
        
        if(aqui == -1 || aqui == 0) {
            System.out.println("\nNão existem Aliens por perto. \n");
            Jogo.getGameData().aumentaPontosAcao();
            Jogo.comecarJogo();
            return;
        }
        
        ali = aqui;
        
        do{
            System.out.println(imprime(Jogo.getEstado()));
            
            opcao = opcoesInt();
            switch(opcao){
                case 0:         // Enganou-se a escolher o Crew Member
                    Jogo.getGameData().aumentaPontosAcao();
                    Jogo.comecarJogo();
                    return; 
                case 1:
                    //rever
                    System.out.println("\nO " + Jogo.getGameData().Selecionado().toString().toUpperCase() + " tem " + Jogo.getGameData().Selecionado().getAtaque() + " pontos de ataque.");//debug
                    valor = Jogo.getGameData().lancaDadoValor();
                    
                    if(valor > 6){
                        if((valor - 6) > 6){
                            imprimeDado(valor - 12);
                            valor -= 6;
                            imprimeDado(valor - 6);
                            valor -= 6;
                            imprimeDado(valor);
                        }
                        else{
                            imprimeDado(valor - 6);
                            valor -= 6;
                            imprimeDado(valor);
                        }
                    }
                    else{
                        imprimeDado(valor);
                    }
                    
                    Jogo.getGameData().lancaDado(valor, ali);
                    
                    Jogo.comecarJogo();
                    return;
                case 2:
                    //rever verificar se está correto
                    System.out.println(selecionaDadoImprime());
                    opcao = opcoesInt();
                    switch(Jogo.getGameData().selecionaDadoOk(opcao)){
                        case -1:
                            break;
                        default:
                            Jogo.getGameData().selecionaDado(opcao, ali);
                            break;
                    }
                    Jogo.comecarJogo();
                    return;
            }
        }while(opcao < 0 || opcao > 2);
    }

    public void UI_AguardaEscolhaSala() { 
        int opcao, contador = 0;
        
        if(Jogo.getGameData().Selecionado() instanceof TransporterChief){
            do{
                System.out.println(imprime(Jogo.getEstado()));
                do{
                    opcao = opcoesInt();
                }while(opcao < 0 || opcao > Jogo.getGameData().nSalasDisponiveis());

                if(opcao == 0){
                    Jogo.comecarJogo();
                    return;
                }
            }while(Jogo.getGameData().getEstaSala(opcao - 1).isSelada());

            System.out.println("###!Passou da sala: " + Jogo.getGameData().getEstaSala(Jogo.getGameData().Selecionado()).getNome() + " para a sala: " + Jogo.getGameData().getEstaSala(opcao - 1).getNome() + "!###");//debug
            Jogo.getGameData().getEstaSala(Jogo.getGameData().Selecionado()).removerCrewEstaSala(Jogo.getGameData().Selecionado());
            Jogo.getGameData().getEstaSala(opcao - 1).acrescentaCrewMember(Jogo.getGameData().Selecionado());
            Jogo.getGameData().reduzPontosAcao();
            Jogo.comecarJogo();
            return;
        }
        
        do{
            List<Integer> locais = new ArrayList<>();
            locais = Jogo.getGameData().direcoes(Jogo.getGameData().getEstaSala(Jogo.getGameData().Selecionado()).getId() - 1);

            System.out.println(Jogo.getGameData().mostraOndeIr(contador));
            opcao = opcoesInt();
            
            if(opcao == 0){
                if(contador > 0){
                    Jogo.getGameData().reduzPontosAcao();
                }
                Jogo.comecarJogo();
                return;
            }
            
            ++contador;
            
            if(opcao > locais.size() || opcao < 0){
                --contador;
            }
            else{
                System.out.println("###!Passou da sala: " + Jogo.getGameData().getEstaSala(Jogo.getGameData().Selecionado()).getNome() + " para a sala: " + Jogo.getGameData().getEstaSala(locais.get(opcao - 1)).getNome() + "!###");//debug
                Jogo.getGameData().getEstaSala(Jogo.getGameData().Selecionado()).removerCrewEstaSala(Jogo.getGameData().Selecionado());
                Jogo.getGameData().getEstaSala(locais.get(opcao - 1)).acrescentaCrewMember(Jogo.getGameData().Selecionado());
            }
        }while(contador < Jogo.getGameData().Selecionado().getMovimentos());
        
        Jogo.getGameData().reduzPontosAcao();
        Jogo.comecarJogo();
    }

    public void UI_FimDeJogo() {
        int opcao;
        
        System.out.println(imprime(Jogo.getEstado()));

        do{
            opcao = opcoesInt();

            switch(opcao){
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    Jogo.reset();
                    Jogo.comecarJogo();
                    return;
            }
        } while(opcao < 0 || opcao > 2);
    }
    
    
    
    
    /**
     *  Retorna uma String para ser usada na função
     * @see regeneracao
     * para saber situação ocorreu.
     * @param i Integer - opcao a realizar
     * @return String - ação que ocorreu
     */
    public String regeneracaoImprime(int i){
        StringBuilder sb = new StringBuilder();
        switch(i){
            case 1:
                sb.append("\nNão é possível adicionar mais vida!");
                break;
            case 2:
                sb.append("\nNão tem pontos de inspiração suficiente!");
                break;
        }
        return sb.toString();
    }
    
    /**
     *  Retorna uma String para ser usada na função
     * @see reparacao
     * para saber que o erro ocorreu.
     * @return String - erro ocorreu
     */
    public String reparacaoImprimeErro(){
        return "\nNão é possível adicionar mais armadura!";
    }
    
    
    /**
     *  Retorna uma String para ser usada na função
     * @see compraBombaOD
     * para saber que erro ocorreu.
     * @param i Integer - opcao a realizar
     * @return String - erro que ocorreu
     */
    public String compraBombaODImprime(int i){
        StringBuilder sb = new StringBuilder();
        switch(i){
            case 1:
                sb.append("\nNão pode ter mais Organic Detonators!");
                break;
            case 2:
                sb.append("\nNão tem pontos de inspiração suficiente!");
                break;
        }
        return sb.toString();
    }
    
    /**
     *  Retorna uma String para ser usada na função
     * @see compraMovimentos
     * para saber que erro ocorreu.
     * @param i Integer - opcao a realizar
     * @return String - ação resultante
     */
    public String compraMovimentosImprime(int i){
        StringBuilder sb = new StringBuilder();
        switch(i){
            case 0:
                sb.append("Não pode ter movimento extra devido à sua habilidade especial!");
                break;
            case 1:
                sb.append("\nO ").append(Jogo.getGameData().Selecionado().toString()).append(" não pode ter mais movimentos!");
                break;
            case 2:
                sb.append("\nNão tem pontos de inspiração suficiente!");
                break;
        }
        return sb.toString();
    }
    
    /**
     *  Retorna uma String para ser usada na função
     * @see compraBombaPD
     * para saber que erro ocorreu.
     * @param i Integer - opcao a realizar
     * @return String - erro que ocorreu
     */
    public String compraBombaPDImprime(int i){
        StringBuilder sb = new StringBuilder();
        switch(i){
            case 1:
                sb.append("\nNão pode ter mais Particle Disperser!");
                break;
            case 2:
                sb.append("\nNão tem pontos de inspiração suficiente!");
                break;
        }
        return sb.toString();
    }
    
    /**
     *  Retorna uma String para ser usada na função
     * @see compraSealedTokens
     * para saber que erro ocorreu.
     * @param i Integer - opcao a realizar
     * @return String - erro que ocorreu
     */
    public String compraSealedTokensImprime(int i){
        StringBuilder sb = new StringBuilder();
        switch(i){
            case 1:
                sb.append("\nNão pode ter mais Sealed Tokens!");
                break;
            case 2:
                sb.append("\nNão tem pontos de inspiração suficiente!");
                break;
        }
        return sb.toString();
    }
    
    /**
     *  Retorna uma String para ser usada na função
     * @see compraDadoAtaque
     * para saber que erro ocorreu.
     * @param i Integer - opcao a realizar
     * @return String - erro que ocorreu
     */
    public String compraDadoAtaqueImprime(int i){
        StringBuilder sb = new StringBuilder();
        switch(i){
            case 1:
                sb.append("\nO ").append(Jogo.getGameData().Selecionado().toString()).append(" não pode ter mais dados de ataque!");
                break;
            case 2:
                sb.append("\nNão tem pontos de inspiração suficiente!");
                break;
        }
        return sb.toString();
    }
    
    /**
     *  Função que retorna a String do erro ocorrido
     * na tentativa de compra de ataque extra.
     * @see compraExtraAtaque
     * @return String - erro ocorrido
     */
    public String compraExtraAtaqueErro(){
        return "\nNão tem pontos de inspiração suficiente!";
    }
    
    /**
     *  Retorna a String do tipo de habilidades passivas que ocorreram.
     * @see habilidadesPassivas
     * @param i Integer - opcao a realizar
     * @return String - que tipo de habilidade passiva foi executada
     */
    public String habilidadesPassivasImprime(int i){
        StringBuilder sb = new StringBuilder();
        switch(i){
            case 1:
                sb.append("====Regeneração de vida====");
                break;
            case 2:
                sb.append("====Regeneração de armadura====");
                break;
        }
        return sb.toString();
    }
    
    
    
    
    /**
     *  Função da interface da função 
     * @see selecionaDado
     * que retorna a String para o mesmo.
     * @return String - interface da função
     */
    public String selecionaDadoImprime(){
        return "\nO " + Jogo.getGameData().Selecionado().toString().toUpperCase() + " tem entre " + (Jogo.getGameData().Selecionado().getAtaque()*1 + Jogo.getGameData().Selecionado().getExtraAtaque()) + " e " + (Jogo.getGameData().Selecionado().getAtaque()*6 + Jogo.getGameData().Selecionado().getExtraAtaque()) + " pontos de ataque." 
                + "\nInsira um valor: ";
    }
    
    
    /**
     *  Função que recebe a lista de crew members que adicionou
     * e retorna uma String a dizer os mesmos.
     * Assim se adicionar um membro de forma aleatória o utilizador
     * saberá que crew member foi.
     * @param escolhas List CrewMember - lista dos crew members adicionados ao Jogo
     * @return String - Tripulação escolhida
     */
    public String tripulacaoEscolhida(List<CrewMember> escolhas){
        StringBuilder sb = new StringBuilder();
        if(!escolhas.isEmpty()){
            sb.append("\nTripulação escolhida:");
            for(CrewMember este : escolhas){
                sb.append("\n\t").append(este.getNome());
            }
        }
        return sb.toString();
    }
    
    public String mostraJornneyTracker(){           // Apresentação do Journey Tracker
        StringBuilder sb = new StringBuilder();
        sb.append("\nJornney Tracker: ");
        for(String ali : Jogo.getGameData().getJornneyTracker()){
            sb.append(ali).append(" | ");
        }
        sb.append("\n");
        return sb.toString();
    }
    
    /**
     *  Ecrã inicial do Jogo
     *  Os códigos apresentados (\033) são apenas para mostrar cor porém apenas
     *    funcionam na consola do IDE (neste caso, NetBeans), logo, não funcionam
     *    na consola (do Windows, por exemplo).
     * @return String - Destination Earth (com um design de uma nave espacial)
     */
    public String imprimeDesign() {
       return     "                                                                        \033[0;32m !               \033[0m \n"
                + "                                                                        \033[0;32m !               \033[0m \n"
                + "                                                                        \033[0;32m ^               \033[0m \n"
                + "                                                                       \033[0;32m / \\             \033[0m \n"
                + "                                                                      \033[0;32m /___\\            \033[0m \n"
                + "                                                                     \033[0;36m |\033[0;33m=   =\033[0;36m|            \033[0m \n"
                + "                                                                     \033[0;36m |     |            \033[0m \n"
                + "                                                                     \033[0;36m |     |           \033[0m \n"
                + "                                                                     \033[0;36m |     |            \033[0m \n"
                + "  _____            _   _             _   _                           \033[0;36m |     |            \033[0m \n"
                + " |  __ \\          | | (_)           | | (_)                          \033[0;36m |     |           \033[0m \n"
                + " | |  | | ___  ___| |_ _ _ __   __ _| |_ _  ___  _ __                \033[0;36m |     |            \033[0m \n"
                + " | |  | |/ _ \\/ __| __| | '_ \\ / _` | __| |/ _ \\| '_ \\               \033[0;36m |     |        \033[0m \n"
                + " | |__| |  __/\\__ \\ |_| | | | | (_| | |_| | (_) | | | |              \033[0;36m |     |          \033[0m \n"
                + " |_____/ \\___||___/\\__|_|_| |_|\\__,_|\\__|_|\\___/|_| |_|             \033[0;32m /\033[0;36m|\033[0;33m##!##\033[0;36m|\033[0;32m\\     \033[0m \n"
                + "              |  ____|         | | | |                             \033[0;32m / \033[0;36m|\033[0;33m##!##\033[0;36m| \033[0;32m\\         \033[0m \n"
                + "              | |__   __ _ _ __| |_| |__                          \033[0;32m /  \033[0;36m|\033[0;33m##!##\033[0;36m|  \033[0;32m\\        \033[0m \n"
                + "              |  __| / _` | '__| __| '_ \\                        \033[0;32m |  / \033[0;36m^ | ^ \033[0;32m\\  |      \033[0m \n"
                + "              | |___| (_| | |  | |_| | | |                       \033[0;32m | /  \033[0;33m( \033[0;36m| \033[0;33m)  \033[0;32m\\ |       \033[0m \n"
                + "              |______\\__,_|_|   \\__|_| |_|                       \033[0;32m |/   \033[0;33m( \033[0;36m| \033[0;33m)   \033[0;32m\\|     \033[0m \n"
                + "                                                                     \033[0;31m (\033[0;33m(   )\033[0;31m)            \033[0m \n"
                + "                                                                    \033[0;31m (\033[0;33m(  :  )\033[0;31m)           \033[0m \n"
                + "                                                                    \033[0;31m (\033[0;33m(  :  )\033[0;31m)           \033[0m \n"
                + "                                                                     \033[0;31m (\033[0;33m(   )\033[0;31m)            \033[0m \n"
                + "                                                                      \033[0;31m (\033[0;33m( )\033[0;31m)             \033[0m \n"
                + "                                                                       \033[0;31m ( )              \033[0m \n"
                + "                                                                        \033[0;33m .               \033[0m \n"
                + "                                                                        \033[0;33m .               \033[0m \n"
                + "                                                                        \033[0;33m .               \033[0m \n";
    }
    

    /**
     *  Retorna uma imagem do dado de 12 faces
     * e com o seu respetivo valor.
     * @param n Integer - valor da face do dado
     */
    public void imprimeDadoD12(int n){
        System.out.print("         /\\  \n");
        System.out.print("        /  \\ \n");
        System.out.print("       /    \\ \n");
        if(n < 10)
            System.out.printf("      /   %d  \\ \n", n);
        else
            System.out.printf("      /  %d  \\ \n", n);
        System.out.print("     /        \\ \n");
        System.out.println("    /__________\\ \n");
    }
    
    /**
     *  Dependendo do estado atual em que se encontra é
     * returnada uma String com as interfaces de cada estado.
     * @return String - interface do estado atual em que o Jogo se escontra
     */
    public String imprime(IEstado estado) {
        StringBuilder sb = new StringBuilder();
        
        if(estado instanceof EsperaInicio){
            sb.append("\n=== BEM-VINDO AO DESTINATION EARTH ===\n").append("\n1 - Comecar Jogo.").append("\n2 - Carregar Jogo.").append("\n0 - Sair.");
        }
        else if(estado instanceof SelecaoJourneyTracker){
            sb.append("\n=== CONFIGURAÇÃO DO JOURNEY TRACKER ===\n").append("\n1 - Usar Journey Tracker default.");
            sb.append("\n2 - Criar Journey Tracker.").append("\n0 - Sair.");
        }
        else if(estado instanceof EscolheCrewMember){
            sb.append("\n=== ESCOLHE OS TEUS MEMBROS DA TRIPULAÇÃO ===\n").append("\n1 - Selecionar membro da tripulação.");
            sb.append("\n2 - Selecionar membro da tripulação random.").append("\n0 - Sair.");
        }
        else if(estado instanceof ColocaTokens1CrewMember){
            sb.append("\n=== COLOCA TOKENS NO ").append(Jogo.getGameData().getCrew(0).getNome().toUpperCase()).append(" ===\n");
            sb.append("\nComo deseja colocar o seu tripulante na sala?\n").append("\n1 - Lançar um dado.");
            sb.append("\n2 - Escolher a sala.");
        }
        else if(estado instanceof ColocaTokens2CrewMember){
            sb.append("\n=== COLOCA TOKENS NO ").append(Jogo.getGameData().getCrew(1).getNome().toUpperCase()).append(" ===\n");
            sb.append("\nComo deseja colocar o seu tripulante na sala?\n").append("\n1 - Lançar um dado.");
            sb.append("\n2 - Escolher a sala.");
        }
        else if(estado instanceof UsaPontosInspiracao){
            for(int i = 0; i < Jogo.getGameData().getMax_salas(); i++){
                Jogo.getGameData().getEstaSala(i).removerAliens();
            }
            sb.append("\n=== USA PONTOS DE INSPIRAÇÃO ===").append("\n=== SELECIONANDO: ").append(Jogo.getGameData().Selecionado().getNome().toUpperCase());
            sb.append(" ===").append("\n=== PONTOS DE VIDA - ").append(Jogo.getGameData().getVida()).append(" ===");
            sb.append("\n=== PONTOS DE ARMADURA - ").append(Jogo.getGameData().getArmadura()).append(" ===");
            sb.append("\n=== PONTOS DE INSPIRAÇÃO - ").append(Jogo.getGameData().getPontosInspiracao()).append(" ===\n");
            if(Jogo.getGameData().Selecionado() instanceof Doctor && Jogo.getGameData().Selecionado().getNome().equalsIgnoreCase("Doctor")){
                sb.append("\n1 - Adiciona 2 de vida. ").append('(').append("1 IP").append(')');
            }
            else{
                sb.append("\n1 - Adiciona 1 de vida. ").append('(').append("1 IP").append(')');
            }
            if(Jogo.getGameData().Selecionado() instanceof Engineer && Jogo.getGameData().Selecionado().getNome().equalsIgnoreCase("Engineer")){
                sb.append("\n2 - Repara 2 de armadura. ").append('(').append("1 IP").append(')');
            }
            else{
                sb.append("\n2 - Repara 1 de armadura. ").append('(').append("1 IP").append(')');
            }
            sb.append("\n3 - Constrói 1 Organic Detonator. ").append('(').append("2 IP").append(')').append("\n4 - Adiciona 1 de movimento. ").append('(').append("4 IP").append(')');
            sb.append("\n5 - Contrói um Particle Disperser. ").append('(').append("5 IP").append(')').append("\n6 - Ganha 1 Token para Selar uma sala. ").append('(').append("5 IP").append(')');
            sb.append("\n7 - Ganha 1 Dado de ataque. ").append('(').append("6 IP").append(')').append("\n8 - Adiciona 1 ao resultado do Dado. ").append('(').append("6 IP").append(')');
            sb.append("\n9 - Selecionar outro membro da tripulação.").append("\n10 - Terminar turno.").append("\n11 - Salvar Jogo.").append("\n0 - Recomeçar o jogo do início.");
        }
        else if(estado instanceof UsaPontosAcao){
            sb.append("\n=== USA PONTOS DE AÇÃO ===").append("\n=== SELECIONANDO: ").append(Jogo.getGameData().Selecionado().getNome().toUpperCase());
            sb.append(" ===").append("\n=== PONTOS DE VIDA - ").append(Jogo.getGameData().getVida()).append(" ===");
            sb.append("\n=== PONTOS DE ARMADURA - ").append(Jogo.getGameData().getArmadura()).append(" ===");
//            //debug
//            sb.append("\n=== ALIENS: ").append(getEstaSala(Selecionado()).exiteAliens());
//            sb.append(" ===");
            sb.append("\n=== TURNO: ").append(Jogo.getGameData().getJornneyTrackerId(Jogo.getGameData().getTurno()));
            sb.append(" ===");
            sb.append("\n=== PONTOS DE AÇÃO - ").append(Jogo.getGameData().getPontosAcao()).append(" ===\n");
            sb.append("\n1 - Mover. ").append('(').append("1 PA").append(')');
            sb.append("\n2 - Atacar. ").append('(').append("1 PA").append(')');
            sb.append("\n3 - Montar Armadilha. ").append('(').append("1 PA + 1 Armadilha").append(')');
            sb.append("\n4 - Detonar Dispersor de Particulas. ").append('(').append("1 PA + 1 Detonador").append(')');
            sb.append("\n5 - Selar Sala. ").append('(').append("1 PA + 1 Token de Selar").append(')');
            sb.append("\n6 - Voltar à seleção do tripulante.").append("\n7 - Usa especial.");
            sb.append("\n8 - Terminar turno.").append("\n9 - Salvar Jogo.").append("\n0 - Recomeçar o jogo do início.");
        }
        else if(estado instanceof SelecionaCrewMemberJogar){
            sb.append("\n=== SELECIONA O MEMBRO DA TRIPULAÇÃO A JOGAR ===\n");
            sb.append(Jogo.getGameData().mostraLocalCrew());
            for(CrewMember este : Jogo.getGameData().getCrews()){
                sb.append("\n").append(este.getId()).append(" - ").append(este.getNome());
            }
            sb.append("\nOpção: ");
        }
        else if(estado instanceof AguardaLancamentoDado){
            sb.append("\n=== USA DADO ===").append("\n=== SELECIONANDO: ").append(Jogo.getGameData().Selecionado().getNome().toUpperCase()).append(" ===");
            sb.append("\n=== PONTOS DE VIDA - ").append(Jogo.getGameData().getVida()).append(" ===");
            sb.append("\n=== PONTOS DE ARMADURA - ").append(Jogo.getGameData().getArmadura()).append(" ===");
            sb.append("\n=== PONTOS DE AÇÃO - ").append(Jogo.getGameData().getPontosAcao()).append(" ===\n");
            sb.append("\n1 - Girar o dado.").append("\n2 - Introduzir um valor.").append("\n0 - Enganou-se no tripulante a selecionar?");
        }
        else if(estado instanceof AguardaEscolhaSala && Jogo.getGameData().Selecionado() instanceof TransporterChief){
            for(int i = 0; i < Jogo.getGameData().getMax_salas(); i++){
                if(!Jogo.getGameData().getEstaSala(i).isSelada()){
                    sb.append("\n").append(i + 1).append(" - ").append(Jogo.getGameData().getEstaSala(i).getNome()).append('.');
                }
            }
            sb.append("\n0 - Voltar atrás.");
        }
        else if(estado instanceof FimDeJogo){
            if(Jogo.getGameData().getArmadura() <= 0 || Jogo.getGameData().getVida() <= 0){
                sb.append(imprimePerdeu());
                if(Jogo.getGameData().getArmadura() <= 0)
                    sb.append("\n\nFicou com 0 de armadura!\n");
                else if(Jogo.getGameData().getVida() <= 0)
                    sb.append("\n\nFicou com 0 de vida!\n");
                else
                    sb.append("\n\nFicou com 0 de armadura e 0 de vida!\n");            
            }
            else{
                sb.append(imprimeGanhou());
                sb.append("\n\nChegou ao planeta Terra!\n");
            }
            sb.append("\nDeseja continuar?").append("\n1 - Sim, quero jogar de novo.").append("\n0 - Não.");
        }
        return sb.toString();
    }

    
    
    
    /**
     *  Retorna uma String de que o utilizador perdeu.
     * @return String - GAMEOVER
     */
    public String imprimePerdeu(){
        return "   ____    _    __  __ _____    _____     _______ ____  \n" +
               "  / ___|  / \\  |  \\/  | ____|  / _ \\ \\   / / ____|  _ \\ \n" +
               " | |  _  / _ \\ | |\\/| |  _|   | | | \\ \\ / /|  _| | |_) |\n" +
               " | |_| |/ ___ \\| |  | | |___  | |_| |\\ V / | |___|  _ < \n" +
               "  \\____/_/   \\_\\_|  |_|_____|  \\___/  \\_/  |_____|_| \\_\\\n" +
               "                                                        ";
    }
    
    /**
     *  Retorna uma String de que o utilizador ganhou.
     * @return String - GANHOU
     */
    public String imprimeGanhou(){
        return "                                           \n" +
               "  ____    _    _   _ _   _  ___  _   _   _ \n" +
               " / ___|  / \\  | \\ | | | | |/ _ \\| | | | | |\n" +
               "| |  _  / _ \\ |  \\| | |_| | | | | | | | | |\n" +
               "| |_| |/ ___ \\| |\\  |  _  | |_| | |_| | |_|\n" +
               " \\____/_/   \\_|_| \\_|_| |_|\\___/ \\___/  (_)\n" +
               "                                           ";
    }
    
    /**
     *  Função para a leitura de nomes.
     * @return String - linha escrita pelo utilizador
     */
    public String opcaoLine(){              // TIRAR DAQUI
        Scanner sc = new Scanner(System.in);
        while(!sc.hasNextLine()){
            sc.next();
        }
        return sc.nextLine();
    }
    
    /**
     *  Leitura de opções introduzidas pelo utilizador e que
     * apenas possam ser números inteiros.
     * @return Integer - inteiro introduzido pelo utilizador
     */
    public int opcoesInt(){
        Scanner sc = new Scanner(System.in);
        while(!sc.hasNextInt()){
            sc.next();
        }
        return sc.nextInt();
    }
    
    /**
     *  Dependendo do valor da opcao recebida ela irá
     * returnar a String evidenciando as fazes da função selarSala
     * em que se encontra.
     * @see selarSala
     * @param opcao Integer - opcao a realizar
     * @return String - Salas que pretende selar ou aviso que não possui Tokens para selar
     */
    public String selarImprime(int opcao){
        StringBuilder sb = new StringBuilder();
        switch(opcao){
            case -1:
                sb.append("Não possui nenhuns Tokens de Selar.");
                break;
            default:
                for(int i = 0; i < Jogo.getGameData().getMax_salas(); i++){
                    sb.append("\n").append(i + 1).append(" - ").append(Jogo.getGameData().getEstaSala(i).getNome()).append('.');
                }
                sb.append("\n0 - Escolher outra opcao.");
                break;
        }
        return sb.toString();
    }
    
    
    
    
    
    /**
     *  Retorna uma String que contem
     * o que o utilizador pode realizar ou 
     * a mensagem de erro.
     * @see montarBomba
     * @param i Integer - opcao a realizar
     * @return String - mensagem a devolver segundo o valor introduzido
     */
    public String montarBombaImprime(int i){
        StringBuilder sb = new StringBuilder();
        switch(i){
            case 0:
                sb.append("1 - Colocar Detonador Organico nesta sala.").append("\n2 - Colocar Dispersor de Particulas nesta sala.");
                sb.append("\n0 - Selecionar uma ação diferente.");
                break;
            case 1:
                sb.append("Não tem na sua posse nenhum Detonador Organico.");
                break;
            case 2:
                sb.append("Não tem na sua posse nenhum Dispersor de Particulas.");
                break;
            case 3:
                sb.append("Montou um Detonador Organico.");
                break;
            case 4:
                sb.append("Montou um Dispersor de Particulas.");
                break;
        }
        return sb.toString();
    }
    
    /**
     *  Retorna uma String com as salas onde existem Dispersores de Particulas
     * e as ações que pode tomar.
     * @see detonarBomba
     * @param i Integer - opcao a realizar
     * @return String - mensagem a apresentar dependendo do valor introduzido
     */
    public String detonarBombaImprime(int i){
        StringBuilder sb = new StringBuilder();
        switch(i){
            case 0:
                sb.append("Não tem Dispersores de Particulas montados.");
                break;
            case 1:
                sb.append("\n").append(Jogo.getGameData().Selecionado().getNome()).append(" detonou um Dispersor de Particulas!");
                break;
            default:
                for(int j = 0; j < Jogo.getGameData().getMax_salas(); j++){
                    if(Jogo.getGameData().getEstaSala(j).contemBombasTipo(new ParticleDisperser())){
                        sb.append((j + 1)).append(" - ").append(Jogo.getGameData().getEstaSala(j).getNome()).append('.').append("\n");
                    }
                }
                sb.append("0 - Voltar atrás.");
                break;
        }
        return sb.toString();
    }
    
    /**
     *  Retorna um desenho do valor do dado que recebeu
     * em i.
     * @param dice Integer - valor do dado
     */
    public void imprimeDado(int dice){
        switch(dice){
            case 1:
                System.out.println("╔═══╗\n" +
                                   "║     ║\n" +
                                   "║  •  ║\n" +
                                   "║     ║\n" +
                                   "╚═══╝\n");
                break;
            case 2:
                System.out.println("╔═══╗\n" +
                                   "║  •  ║\n" +
                                   "║     ║\n" +
                                   "║  •  ║\n" +
                                   "╚═══╝\n");
                break;
            case 3:
                System.out.println("╔═══╗\n" +
                                   "║  •  ║\n" +
                                   "║  •  ║\n" +
                                   "║  •  ║\n" +
                                   "╚═══╝\n");
                break;
            case 4:
                System.out.println("╔═══╗\n" +
                                   "║ • • ║\n" +
                                   "║     ║\n" +
                                   "║ • • ║\n" +
                                   "╚═══╝\n");
                break;
            case 5:
                System.out.println("╔═══╗\n" +
                                   "║ • • ║\n" +
                                   "║  •  ║\n" +
                                   "║ • • ║\n" +
                                   "╚═══╝\n");   
                break;
            case 6:
                System.out.println("╔═══╗\n" +
                                   "║ • • ║\n" +
                                   "║ • • ║\n" +
                                   "║ • • ║\n" +
                                   "╚═══╝\n");
                break;
        }
    }
    
    public String mostraOpcoesCrewMember(){         // Apresenta informação sobre os Crew Members 
        StringBuilder sb = new StringBuilder();
        for(String op : opcoesCrewMember){
            sb.append(op).append("\n");
        }
        return sb.toString();
    }
    
}
