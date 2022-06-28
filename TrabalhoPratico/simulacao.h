/* 
 * File:   simulacao.h
 * Author: José Francisco de Jesus Bugalho
 *
 * Created on 23 de Maio de 2020, 10:15
 */

#ifndef SIMULACAO_H
#define SIMULACAO_H

#include "config.h"
#include "backupPessoas.h"

// Avança 1 iteração na simulação, aplica o modelo de propagação.
// Retorna a lista de pessoas atualizada. 
ppessoa avancaSimulacao(ppessoa lista, FILE* f);

// Transmite a doença às pessoas na mesma sala.
void transmiteDoenca(ppessoa lista, int idLocal, char* idPessoa, FILE* f);

/* Verifica se um doente recuperou ou não (aumenta o dias de doente). 
 *  Formas de recuperação:
 *      - Probabilidade de recuperação (1 / idade);
 *      - Duração máxima da infeção (5 dias + 1 por cada dezena de anos).
 *  É também verificado se a pessoa ficou imune ao vírus após recuperar.
 */
void verificaRecuperacao(ppessoa lista, char* idPessoa, FILE* f);

 /* Calculo das estatísticas da simulacao.
 *   - Percentagem de pessoas por local;
 *   - Percentagem de pessoas saudáveis, infetados e imunes;
 *   - Total de pessoas na simulacao.
 */
void calculaEstatistica(ppessoa lista, plocal espaco, int total, FILE* f);

// Adiciona um novo doente à lista da simulação.
// Retorna a lista alterada ou inalterada em caso de erro.
ppessoa adicionaDoente(ppessoa lista, Pessoa doente, FILE* f);

// Transfere N pessoas de um local de origem para um destino.
// Retorna a lista alterada em caso de sucesso ou a lista como estava em caso de erro.
ppessoa transferePessoas(ppessoa lista, plocal espaco, int total, FILE* f);


#endif /* SIMULACAO_H */

