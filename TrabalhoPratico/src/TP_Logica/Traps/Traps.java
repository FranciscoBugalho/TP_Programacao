package TP_Logica.Traps;

import TP_Logica.Salas.Sala;
import java.io.Serializable;

public abstract class Traps implements Serializable {
    String nome;            // Nome da Trap
    Sala sala;              // Sala onde a trap está

    public Traps(String nome) {
        this.nome = nome;
        this.sala = null;
    }

    ///////////////////////// Getter do nome da Trap /////////////////////////
    public String getNome() {
        return nome;
    }

    ///////////////////////// Getter e Setter da Sala /////////////////////////
    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }
    
}
