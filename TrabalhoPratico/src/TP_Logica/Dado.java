package TP_Logica;

import java.io.Serializable;
import java.util.Random;

public class Dado implements Serializable {
    int faces;                                  // Faces do dado (ou 6 ou 12)

    public Dado(int faces) {
        this.faces = faces;
    }

    ///////////////////////// Getter e Setter do valor das faces ///////////////////////// 
    public int getFaces() {
        return faces;
    }

    public void setFaces(int faces) {
        this.faces = faces;
    }
    
    
    public int giraDado(int vezes){             // Função para girar o dado
        int sum = 0;
        Random dado = new Random();
        
        for(int i = 0; i < vezes; i++){
            sum += 1 + dado.nextInt(faces);
        }
        
        return sum;
    }
    
}
