#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "backupPessoas.h"
#include "header.h"

pbackup adicionaIteracao(pbackup listaBackups, ppessoa lista) {
    pbackup novoBackup;
    ppessoa novaPessoa;

    // Aloca-se memória para o novo nó
    novoBackup = malloc(sizeof (BackupPessoas));
    if (novoBackup == NULL) {
        fprintf(stderr, "[ERRO]: A alocar memoria para um novo no da lista de backups!\n");
        return listaBackups;
    }

    // Atualizam-se o ponteiro para o próximo elemento
    novoBackup->prox = NULL;
    novoBackup->proxP = NULL;

    while (lista != NULL) {
        novaPessoa = malloc(sizeof (Pessoa));
        if (novaPessoa == NULL) {
            fprintf(stderr, "[ERRO]: A alocar memoria para um novo no da lista de pessoas.\n");
            // Liberta a lista
            libertaListaPessoas(novoBackup->proxP);
            novoBackup->proxP = NULL;
            return listaBackups;
        }

        // Preenche a nova pessoa
        strcpy(novaPessoa->id, lista->id);
        novaPessoa->idade = lista->idade;
        novaPessoa->estado = lista->estado;
        novaPessoa->diasDoente = lista->diasDoente;
        novaPessoa->prox = NULL;
        // Aloca memória para o local
        novaPessoa->local = malloc(sizeof (local));
        if (novaPessoa->local == NULL) {
            fprintf(stderr, "[ERRO]: A alocar memoria para um local do novo no da lista de pessoas.\n");
            // Liberta a lista
            libertaListaPessoas(novoBackup->proxP);
            novoBackup->proxP = NULL;
            return listaBackups;
        }
        novaPessoa->local = lista->local;

        // Insere no início da lista
        if (novoBackup->proxP == NULL)
            novoBackup->proxP = novaPessoa;
            // Insere no fim da lista
        else {
            ppessoa aux = novoBackup->proxP;

            while (aux->prox != NULL)
                aux = aux->prox;

            aux->prox = novaPessoa;
        }
        lista = lista->prox;
    }

    // Preenche-se o contador
    if (listaBackups == NULL) {
        // Se for o primeiro fica a 1
        novoBackup->iteracao = 1;
    } else {
        // Caso contrátio incrementa-se 1 ao anterior
        novoBackup->iteracao = listaBackups->iteracao + 1;
    }

    // Insere-se a lista no início
    novoBackup->prox = listaBackups;
    listaBackups = novoBackup;

    return listaBackups;
}

pbackup recuarIteracoes(pbackup listaBackups, ppessoa* lista, plocal espaco, int total, FILE* f) {
    int nIteracoes = -1, iteracoesMax, iteracao;
    pbackup aux, apagador, anterior = NULL;
    ppessoa novaPessoa, auxP;

    if (listaBackups == NULL) {
        printf(" Ainda nao avancou nenhuma iteracao! So podera recuar quando avancar pelo menos 1 iteracao.\n");
        fprintf(f, "So podera recuar quando avancar pelo menos 1 iteracao.\n");
        return NULL;
    }

    iteracoesMax = listaBackups->iteracao;
    // 3 é o número máximo de iterações possivel de recuar (indicado no enunciado)
    if (iteracoesMax > 3)
        iteracoesMax = 3;

    do {
        if (iteracoesMax == 1)
            printf(" Indique o numero iteracoes a recuar (so avancou 1 iteracao, logo, so podera recuar 1 iteracao): ");
        else
            printf(" Indique o numero iteracoes a recuar (1 a %d): ", iteracoesMax);
        if (scanf(" %d", &nIteracoes) != 1) {
            scanf("%*[^\n]");
            nIteracoes = -1;
        }
    } while (nIteracoes == -1 || nIteracoes > iteracoesMax);

    // Caso só tenha feito 1 iteração apaga tudo
    if (listaBackups->iteracao == 1) {
        aux = listaBackups;
        iteracao = 0;
    } else {
        // Se não obtém a iteração até à qual vai eliminar
        aux = listaBackups;
        for (int i = 0; i < nIteracoes; i++) {
            if (aux->prox == NULL) {
                iteracao = 0;
                break;
            } else
                iteracao = aux->iteracao - 1;
            aux = aux->prox;
        }
    }
    auxP = aux->proxP;

    // Remove pessoas do local
    lista = retiraPessoasLocal(lista, espaco, total);
    // Liberta a lista
    libertaListaPessoas(*lista);

    // Cria a lista
    *lista = malloc(sizeof (Pessoa));
    if (*lista == NULL) {
        fprintf(stderr, "[ERRO]: A alocar memoria para um novo no da lista de pessoas.\n");
        fprintf(f, "[ERRO]: A alocar memoria para um novo no da lista de pessoas.\n");
        return listaBackups;
    }
    (*lista)->prox = NULL;
    (*lista)->local = NULL;

    int auxiliar = 0;
    while (auxP != NULL) {
        novaPessoa = malloc(sizeof (Pessoa));
        if (novaPessoa == NULL) {
            fprintf(stderr, "[ERRO]: A alocar memoria para um novo no da lista de pessoas.\n");
            fprintf(f, "[ERRO]: A alocar memoria para um novo no da lista de pessoas.\n");
            return listaBackups;
        }

        // Preenche a nova pessoa
        strcpy(novaPessoa->id, auxP->id);
        novaPessoa->idade = auxP->idade;
        novaPessoa->estado = auxP->estado;
        novaPessoa->diasDoente = auxP->diasDoente;
        novaPessoa->prox = NULL;
        // Aloca memória para o local
        novaPessoa->local = malloc(sizeof (local));
        if (novaPessoa->local == NULL) {
            fprintf(stderr, "[ERRO]: A alocar memoria para um local do novo no da lista de pessoas.\n");
            fprintf(f, "[ERRO]: A alocar memoria para um local do novo no da lista de pessoas.\n");
            return listaBackups;
        }
        novaPessoa->local = auxP->local;

        // Insere no início da lista
        if ((*lista) == NULL || auxiliar == 0)
            *lista = novaPessoa;
            // Insere no fim da lista
        else {
            ppessoa aux = (*lista);

            while (aux->prox != NULL)
                aux = aux->prox;

            aux->prox = novaPessoa;
        }
        auxP = auxP->prox;
        auxiliar++;
    }

    // Caso tenha de remover o primeiro
    if (iteracao == 0) {
        libertaListaPessoas(listaBackups->proxP);
        free(listaBackups);

        // Atualiza as pessoas nos espaços
        atualizaPessoasEspacos(*lista, espaco, total);

        printf("\n-Recuou %d iteracoes com sucesso!\n", nIteracoes);
        fprintf(f, "Recuou %d iteracoes com sucesso!\n", nIteracoes);
        return NULL;
    } else {
        // Se não apaga até à iteração calculada a cima
        apagador = listaBackups;

        while (apagador->iteracao != iteracao) {
            libertaListaPessoas(apagador->proxP);
            anterior = apagador;
            apagador = apagador->prox;
        }
        free(anterior);

        // Atualiza as pessoas nos espaços
        atualizaPessoasEspacos(*lista, espaco, total);

        printf("\n-Recuou %d iteracoes com sucesso!\n", nIteracoes);
        fprintf(f, "Recuou %d iteracoes com sucesso!\n", nIteracoes);
        return apagador;
    }
}

void mostraListaBackups(pbackup listaBackups) {
    if (listaBackups == NULL) {
        printf(" Nao tem backups!\n");
        return;
    }
    ppessoa auxP;
    while (listaBackups) {
        printf("\n Iteracao %d: \n", listaBackups->iteracao);
        for (auxP = listaBackups->proxP; auxP; auxP = auxP->prox) {
            printf("  %s %d %c ", auxP->id, auxP->idade, auxP->estado);
            if (auxP->estado == 'D')
                printf("%d ", auxP->diasDoente);
            printf(" - Local: %d \n", auxP->local->id);
        }
        listaBackups = listaBackups->prox;
    }
}

void guardaListaBackups(pbackup listaBackups, FILE* f) {
    if (listaBackups == NULL) {
        fprintf(f, "Nao tem backups!\n");
        return;
    }

    ppessoa auxP;
    fprintf(f, "Lista de backups: \n");
    while (listaBackups) {
        fprintf(f, " Iteracao %d: \n", listaBackups->iteracao);
        for (auxP = listaBackups->proxP; auxP; auxP = auxP->prox) {
            fprintf(f, "   %s %d %c ", auxP->id, auxP->idade, auxP->estado);
            if (auxP->estado == 'D')
                fprintf(f, "%d ", auxP->diasDoente);
            fprintf(f, " - Local: %d \n", auxP->local->id);
        }
        listaBackups = listaBackups->prox;
    }
}