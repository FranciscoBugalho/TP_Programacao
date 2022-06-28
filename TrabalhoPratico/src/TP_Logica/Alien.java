package TP_Logica;

import java.io.Serializable;

public class Alien implements Serializable {

    private static int contador = 0;        // Contador, auxiliar ao id dos Aliens
    private boolean movimentado;            // Controla o movimento (TRUE - Movimentou-se | FALSE - Não se movimentou)
    private boolean atacou;                 // Controla o ataque (TRUE - Atacou | FALSE - Não atacou)
    private int id;                         // Identificador de cada Alien
    
    public Alien() {            
        this.movimentado = false;
        this.atacou = false;
        this.id = contador++;
    }

    ///////////////////////// Getter e Setter do movimento /////////////////////////
    public boolean isMovimentado() {
        return movimentado;
    }

    public void setMovimentado(boolean movimentado) {
        this.movimentado = movimentado;
    }

    ///////////////////////// Getter e Setter do ataque /////////////////////////
    public boolean isAtacou() {
        return atacou;
    }

    public void setAtacou(boolean atacou) {
        this.atacou = atacou;
    }

    ///////////////////////// Getter do id /////////////////////////
    public int getId() {
        return id;
    }
    
}
