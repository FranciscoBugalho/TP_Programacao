/* 
 * File:   pessoa.h
 * Author: José Francisco de Jesus Bugalho
 *
 * Created on 16 de Maio de 2020, 10:00
 */

#ifndef PESSOA_H
#define PESSOA_H

#include "local.h"
#include "utils.h"

#define TAM_ID_PESSOA 100 // Tamanho máximo do identificador de cada pessoa

typedef struct pessoa Pessoa, *ppessoa;

struct pessoa {
    char id[TAM_ID_PESSOA]; // Identificador alfanumérico único
    int idade; // Idade da pessoa
    char estado; // Estado da pessoa (S - Saudável, D - Doente, I - Imune)
    int diasDoente; // Número de dias que está doente
    ppessoa prox; // Ponteiro para a próxima pessoa na lista
    plocal local; // Local onde a pessoa será inserida
};

// Função para ler e adicionar as pessoas à lista.
// Retorna a lista preenchida com as pessoas (NULL em caso de erro).
ppessoa adicionaPessoas(ppessoa lista, char* nomeFich, plocal espaco, int total);

// Função para mostrar a lista de pessoas.
void mostraLista(ppessoa lista);

// Verifica se o id a inserir já existe na lista.
// Retorna 1 se o id já existe, 0 caso contrário.
int verificaIdPessoa(ppessoa lista, char* id);

// Atribui-se um local desde existam locais com capacidade para acolher pessoas (capacidade > 0).
// Retorna um ponteiro para o local onde a pessoa será inserida. NULL caso não seja possível inserir a pessoa num local.
plocal atribuiLocal(plocal espaco, int total);

// Conta o número de pessoas no local idLocal.
// O número de pessoas nessa sala.
int numPessoasLocal(ppessoa lista, int idLocal);

// Forma um array de carateres com id das pessoas doentes.
// Retorna um array com os id's das pessoas doentes, NULL em caso de erro.
char** obtemDoentes(ppessoa lista, int* nDoentes, FILE *f);

// Verifica se o id do doente enviado está no array de id's de doentes, ou seja, esta pessoa estava doente no inicio da itereção.
// Retorna 1 se o id enviado pertencer ao array idDoentes, 0 caso contrários.
int estavaDoente(char** idDoentes, int nDoentes, char* idDoenteAtual);

// Cria um novo doente com os dados introduzidos pelo utilizador. 
//  O utilizador indica: identificador, idade e número de dias doente da pessoa bem como indica o local onde esse doente será inserido.
// Retorna o novo doente.
Pessoa criaNovoDoente(ppessoa lista, plocal espaco, int total);

// Verifica se existem pessoas num determinado local passado como parâmetro.
// Retorna 0 se o local não tiver pessoas, 1 caso contrário.
int localTemPessoas(ppessoa lista, int idLocal, int *erro);

// Obtém o identificador das pessoas num local recebido como parâmetro.
// Retorna um array de ponteiros para ponteiros com o identificadores das pessoas.
char** obtemPessoasNoEspaco(ppessoa lista, int* nPessoas, int idOrigem);

// Função para guardar a lista final da simulalção num ficheiro.
void salvaPopulacaoNoFicheiro(ppessoa lista);

// Retira as pessoas dos espaços.
// Retorna um ponteiro para a lista de pessoas.
ppessoa* retiraPessoasLocal(ppessoa* lista, plocal espaco, int total);

// Altera a capacidade do espaço consoante as pessoas que estão nele.
void atualizaPessoasEspacos(ppessoa lista, plocal espaco, int total);

// Função para guardar a lista de pessoas atual no ficheiro dos reports.
void guardaListaAtual(ppessoa lista, FILE* f);

// Liberta a lista de pessoas.
void libertaListaPessoas(ppessoa lista);

#endif /* PESSOA_H */

