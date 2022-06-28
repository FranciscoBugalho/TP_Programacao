/* 
 * File:   backupPessoas.h
 * Author: José Francisco de Jesus Bugalho
 *
 * Created on 30 de Maio de 2020, 10:00
 */

#ifndef BACKUPPESSOAS_H
#define BACKUPPESSOAS_H

#include "pessoa.h"

typedef struct backupPessoas BackupPessoas, *pbackup;

struct backupPessoas {
    int iteracao; // Número da iteração
    ppessoa proxP; // Ponteiro para a próxima pessoa na lista
    pbackup prox; // Ponteiro para a próxima iteração
};

// Adiciona à lista de backups a lista após se avançar uma iteração na simulação.
// Retorna a lista de backups atualizada ou não em caso de erro.
pbackup adicionaIteracao(pbackup listaBackups, ppessoa lista);

// Recua entre 1 e 3 iterações na simulação.
// Retorna a lista de backups atualizada (NULL se a lista ficar vazia)
pbackup recuarIteracoes(pbackup listaBackup, ppessoa* lista, plocal espaco, int total, FILE* f);

// Função auxiliar para mostrar a lista de backups.
void mostraListaBackups(pbackup listaBackups);

// Função para guardar a lista de backups no ficheiro de reports
void guardaListaBackups(pbackup listaBackups, FILE* f);

#endif /* BACKUPPESSOAS_H */

