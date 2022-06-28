/* 
 * Game:    Destination Earth
 * Authors: José Francisco de Jesus Bugalho          No. 21270489
 *          Luís António Moreira Ferreira da Silva   No. 21270503
 * Licenciatura em Engenharia Informática (Diurno)
 */

package TP_Main;

import TP_Logica.DestinationEarth;
import TP_UI_texto.UI_Texto;

public class Main {
    public static void main(String [] args) {
        DestinationEarth de = new DestinationEarth();
        UI_Texto ui_text = new UI_Texto(de);
        ui_text.corre();
    }
}