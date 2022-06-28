package TP_Logica.Salas;

import TP_Logica.CrewMembers.*;
import TP_Logica.Traps.*;
import TP_Logica.Alien;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Sala implements Serializable{
    
    private String nome;                    // Nome da sala
    private int id;                         // Identificador da sala
    private boolean selavel;                // Pode ou não selada (true - sim || false - não)
    private boolean selada;                 // Está ou não selada (true - sim || false - não)
    private List<Alien> aliens;             // Lista dos Aliens na sala
    private List<CrewMember> crews;         // Lista dos Crew Members na sala
    private List<Traps> armadilhas;         // Lista das Traps na sala

    public Sala(String nome, int id, boolean selavel, boolean selada) {
        this.nome = nome;
        this.id = id;
        this.selavel = selavel;
        this.selada = selada;
        this.crews = new ArrayList<>();
        this.aliens = new ArrayList<>();
        this.armadilhas = new ArrayList<>();
    }

    ///////////////////////// Getter do nome /////////////////////////
    public String getNome() {
        return nome;
    }

    ///////////////////////// Getter do Id /////////////////////////
    public int getId() {
        return id;
    }

    ///////////////////////// Getter do selavel /////////////////////////
    public boolean isSelavel() {
        return selavel;
    }

    ///////////////////////// Getter e Setter do selada /////////////////////////
    public boolean isSelada() {
        return selada;
    }

    public void setSelada(boolean selada) {
        this.selada = selada;
    }
    
    ///////////////////////// Getter da lista de Traps /////////////////////////
    public List<Traps> getArmadilhas() {
        return armadilhas;
    }
    
    ///////////////////////// Adição de um Crew Member /////////////////////////
    public void acrescentaCrewMember(CrewMember este){
        crews.add(este);
    }
    
    ///////////////////////// Verificação da existência de um Crew Member /////////////////////////
    public boolean existeCrew(CrewMember este){     // Dado um Crew Member
        if(!crews.isEmpty()){
            return crews.contains(este);
        }
        return false;
    }
    
    public boolean existeCrew(){
        return !crews.isEmpty();
    }
    
    public int nAliens(){                           // Conta número de Aliens
        int i = 0;
        for(Alien ali : aliens){
            i++;
        }
        return i;
    }
    
    public List<Alien> getAliens() {                // Lista dos Aliens
        return aliens;
    }
    
    public void acrescentaAlien(Alien alien){                  // Adiciona um Alien
        if(!isSelada()){
            aliens.add(alien);
        }
    }
    
    public void removerAlien(Alien alien){                     // Remove um Alien
        int i = 0;
        for(Alien ali : aliens){
            if(alien.getId() == ali.getId()){
                aliens.remove(aliens.get(i));
                return;
            }
            i++;
        }
    }
    
    public void removerAliens(){                    // Remove todos os Aliens
        if(!aliens.isEmpty()){
            aliens.clear();
        }
    }

    public boolean exiteArmadilhas() {              // Verifica se existe Armadilhas
        return (!armadilhas.isEmpty());
    }
    
    public boolean exiteAliens() {                  // Verifica se existe Aliens
        return (!aliens.isEmpty());
    }
    
    public boolean existeAquiComms(){               // Verifica se existe o Crew Member Comm's Officer
        return crews.contains(new CommsOfficer());
    }

    public void removerArmadilhaAutomatica() {      // Remove uma Armadilha
        for(int i = 0; i < armadilhas.size(); i++){
            if(armadilhas.get(i) instanceof OrganicDetonator){
                armadilhas.remove(armadilhas.get(i));
                break;
            }
        }
    }
 
    public void removerCrewEstaSala(CrewMember ele){// Remover um Crew Member da Sala
        for(CrewMember ali : crews){
            if(ele instanceof Doctor && ali instanceof Doctor){
                crews.remove(ali);
                return;
            }
            else if(ele instanceof Captain && ali instanceof Captain){
                crews.remove(ali);
                return;
            }
            else if(ele instanceof Commander && ali instanceof Commander){
                crews.remove(ali);
                return;
            }
            else if(ele instanceof CommsOfficer && ali instanceof CommsOfficer){
                crews.remove(ali);
                return;
            }
            else if(ele instanceof Engineer && ali instanceof Engineer){
                crews.remove(ali);
                return;
            }
            else if(ele instanceof MoralOfficer && ali instanceof MoralOfficer){
                crews.remove(ali);
                return;
            }
            else if(ele instanceof NavigationOfficer && ali instanceof NavigationOfficer){
                crews.remove(ali);
                return;
            }
            else if(ele instanceof RedShirt && ali instanceof RedShirt){
                crews.remove(ali);
                return;
            }
            else if(ele instanceof SecurityOfficer && ali instanceof SecurityOfficer){
                crews.remove(ali);
                return;
            }
            else if(ele instanceof ShuttlePilot && ali instanceof ShuttlePilot){
                crews.remove(ali);
                return;
            }
            else if(ele instanceof TransporterChief && ali instanceof TransporterChief){
                crews.remove(ali);
                return;
            }
        }
    }
    
    public boolean contemBombasTipo(Traps tr){              // Verifica se existe Traps de qualquer tipo
        if(armadilhas.isEmpty()){
            return false;
        }
       
        for(Traps ali : armadilhas){
            if(tr instanceof OrganicDetonator && ali instanceof OrganicDetonator){
                return true;
            }
            else if(tr instanceof ParticleDisperser && ali instanceof ParticleDisperser){
                return true;
            }
        }
        return false;
    }
    
    public boolean removeBombasTipo(Traps tr){              // Remove Traps de qualquer tipo
        if(armadilhas.isEmpty()){
            return false;
        }
        for(Traps ali : armadilhas){
            if(tr instanceof OrganicDetonator && ali instanceof OrganicDetonator){
                return armadilhas.remove(ali);
            }
            else if(tr instanceof ParticleDisperser && ali instanceof ParticleDisperser){
                return armadilhas.remove(ali);
            }
        }
        return false;
    }
    
    @Override
    public boolean equals (Object x) {

        if(x == null) {
            return false;
        }

        if(getClass() != x.getClass()) {
            return false;
        }

        if(this == x) {
            return true;
        }

        Sala outra = (Sala) x;

        return id == outra.id;
    }
    
    @Override
    public int hashCode () {
        return id;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n\nSala: ").append(getNome());
        if(aliens.isEmpty()){
            sb.append("\nAliens: vazio.");
        }
        else{
            sb.append("\nAliens: ");
            int i = 0;
            for(Alien ali : aliens){
                i++;
            }
            sb.append(i);
        }
        if(crews.isEmpty()){
            sb.append("\nCrew Members: vazio.");
        }
        else{
            sb.append("\nCrew Members:");
            for(CrewMember ali : crews){
                sb.append(ali.toString());
            }
        }
        if(armadilhas.isEmpty()){
            sb.append("\nArmadilhas: vazio.");
        }
        else{
            sb.append("\nArmadilhas: ");
            int i = 0;
            for(Traps ali : armadilhas){
                i++;
            }
            sb.append(i);
        }
        return sb.toString();
    }

    public void removerAlienSala() {
        for(Alien ali : aliens){
            aliens.remove(ali);
            return;
        }
    }
    
    
}
