package TP_Logica.Estados;

public interface IEstado {
    IEstado comecarJogo();
    IEstado proximoTurno(String [] turno, int esteTurno);
    IEstado voltarAtras(IEstado estado);
    IEstado recomecarJogo();
    IEstado lancamentoDado();
    IEstado escolhaSala();
    IEstado fimJogo();
}
