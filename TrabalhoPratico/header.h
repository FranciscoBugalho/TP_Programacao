/* 
 * File:   header.h
 * Author: José Francisco de Jesus Bugalho
 *
 * Created on 17 de Maio de 2020, 11:32
 */

#ifndef HEADER_H
#define HEADER_H

#include "simulacao.h"

#define FICH_ESPACO_1 "E1.bin" // Ficheiro de espaços - E1
#define FICH_ESPACO_2 "E2.bin" // Ficheiro de espaços - E2
#define FICH_ESPACO_3 "E3.bin" // Ficheiro de espaços - E3

#define FICH_PESSOAS_1 "pessoasA.txt" // Ficheiro de pessoas - pessoasA
#define FICH_PESSOAS_2 "pessoasB.txt" // Ficheiro de pessoas - pessoasB

#define FICH_REPORT "report.txt" // Ficheiro com o report da simulação

#define TAM_NOME_FICHS 50 // Tamanho máximo para os nomes dos ficheiros

#define VOLTAR "voltar" // Palavra para voltar ao menu

// Função de apresentação do menu da fase de preparação.
int menuPreparacao();

// Função de apresentação do menu da fase de simulação.
int menuSimulacao();

// Verifica se o nome do ficheiro introduzido pelo utilizador é um dos ficheiros de espaços existentes.
// Retorna 0 se não existe e 1 caso exista.
int ficheiroEspacoExiste(char* nomeFicheiro, int* isErro);

// Verifica se o nome do ficheiro introduzido pelo utilizador é um dos ficheiros de pessoas existentes.
// Retorna 0 se não existe e 1 caso exista.
int ficheiroPessoasExiste(char* nomeFicheiro, int* isErro);

// Função para libertar a lista das pessoas de memória.
void libertaLista(ppessoa lista);

// Liberta a lista de backups e as suas sublistas.
void libertaListaBackups(pbackup listaBackups);

#endif /* HEADER_H */

