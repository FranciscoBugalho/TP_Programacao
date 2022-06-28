package TP_Logica.CrewMembers;

import TP_Logica.Constantes;
import java.io.Serializable;
import java.util.Objects;


public abstract class CrewMember implements Constantes, Serializable{
    
    private String nome;        // Nome do Crew Member
    private String descricao;   // Descrição do Crew Member
    private int id;             // Identificador do Crew Member
    private int ataque;         // Ataque do Crew Member
    private int movimentos;     // Número de movimentos do Crew Member
    private boolean selecionado;// Demonstra se o membro da tripulação é o que se encontra selecionado
    private int extraAtaque;    // Ataque extra do Crew Member

    public CrewMember(String nome, String descricao, int ataque, int movimentos) {
        this.nome = nome;
        this.descricao = descricao;
        this.id = 0;
        this.ataque = ataque;
        this.movimentos = movimentos;
        this.selecionado = false;
        this.extraAtaque = 0;
    }
    
    ///////////////////////// Getter e Setter do ataque extra /////////////////////////
    public int getExtraAtaque() {
        return extraAtaque;
    }

    public void setExtraAtaque(int extraAtaque) {
        this.extraAtaque = extraAtaque;
    }

    ///////////////////////// Getter da descrição /////////////////////////
    public String getDescricao() {
        return descricao;
    }

    ///////////////////////// Getter e Setter do nome /////////////////////////
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }

    ///////////////////////// Getter e Setter do Id /////////////////////////
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    ///////////////////////// Getter e Setter do ataque /////////////////////////
    public int getAtaque() {
        return ataque;
    }
    
    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    ///////////////////////// Getter e Setter do movimento /////////////////////////
    public int getMovimentos() {
        return movimentos;
    }
    
    public void setMovimentos(int movimentos) {
        this.movimentos = movimentos;
    }

    ///////////////////////// Getter e Setter do selecionado /////////////////////////
    public boolean isSelecionado() {
        return selecionado;
    }

    public void setSelecionado(boolean selecionado) {
        this.selecionado = selecionado;
    }

    ///////////////////////// Getter dos movimentos máximos /////////////////////////
    public int getMovs() {
        return movs;
    }

    ///////////////////////// Getter do máximo de dados /////////////////////////
    public int getMax_dados() {
        return max_dados;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n").append(getNome()).append("\nDescricao: ").append(getDescricao()).append("Ataque: ");
        sb.append(getAtaque()).append("\nMovimento: ").append(getMovimentos());
        return sb.toString();
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

        CrewMember outro = (CrewMember) x;

        return id == outro.id;
    }
    
    @Override
    public int hashCode() {
        return id;
    }
       
}
