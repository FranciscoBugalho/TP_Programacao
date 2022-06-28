#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "simulacao.h"

ppessoa avancaSimulacao(ppessoa lista, FILE *f) {
    ppessoa aux = lista;
    int nDoentes = 0;
    char **idDoentes = obtemDoentes(lista, &nDoentes, f);

    while (aux) {
        if (aux->estado == 'D' && estavaDoente(idDoentes, nDoentes, aux->id)) {
            transmiteDoenca(lista, aux->local->id, aux->id, f);
            verificaRecuperacao(lista, aux->id, f);
        }
        aux = aux->prox;
    }

    return lista;
}

void transmiteDoenca(ppessoa lista, int idLocal, char* idPessoa, FILE* f) {
    float podeContaminar_X = (numPessoasLocal(lista, idLocal) - 1) * PROB_DISSEMINACAO;

    while (lista && podeContaminar_X > 0) {
        if (lista->local->id == idLocal && strcmp(lista->id, idPessoa) != 0) {
            if (probEvento(podeContaminar_X)) {

                fprintf(f, "A pessoa %s vai contaminar a pessoa %s (local: %d).", idPessoa, lista->id, lista->local->id);

                switch (lista->estado) {
                    case 'S':
                        lista->estado = 'D';
                        lista->diasDoente = 0;
                        podeContaminar_X--;
                        printf("-Contaminacao: %s -> %s.\n", idPessoa, lista->id);
                        fprintf(f, "A pessoa ficou doente.\n", lista->id);
                        break;
                    case 'D':
                        podeContaminar_X--;
                        fprintf(f, "A pessoa %s ja estava doente.\n", lista->id);
                        break;
                    case 'I':
                        podeContaminar_X--;
                        fprintf(f, "A pessoa %s esta imune a esta doenca.\n", lista->id);
                        break;
                }
            }
        }
        lista = lista->prox;
    }
}

void verificaRecuperacao(ppessoa lista, char* idPessoa, FILE* f) {
    while (strcmp(lista->id, idPessoa) != 0)
        lista = lista->prox;

    float probRecuperar = PROB_RECUPERACAO / lista->idade;

    // Verifica a probabilidade de recuperar (1 / idade)
    if (probEvento(probRecuperar)) {
        // Tem a probabilidade de ficar imune
        if (probEvento(PROB_IMUNIDADE)) {
            lista->estado = 'I';
            printf("-%s ganhou imunidade.\n", lista->id);
            fprintf(f, "A pessoa %s ficou imune a esta doenca.\n", idPessoa);
        } else {
            lista->estado = 'S';
            printf("-%s recuperou, ficou saudavel.\n", lista->id);
            fprintf(f, "A pessoa %s recuperou, ficou saudavel.\n", idPessoa);
        }
        lista->diasDoente = 0;
    }// Caso não recupere verifica-se se está no número de dias máximo de infeção
        //  5 dias + 1 dia por cada dezena de anos
    else if (lista->diasDoente > (TEMPO_DEFAULT_INFECAO + (lista->idade / 10))) {
        // Tem a probabilidade de ficar imune
        if (probEvento(PROB_IMUNIDADE)) {
            lista->estado = 'I';
            printf("-%s ganhou imunidade.\n", lista->id);
            fprintf(f, "A pessoa %s ficou imune a esta doenca.\n", idPessoa);
        } else {
            lista->estado = 'S';
            printf("-%s recuperou, ficou saudavel.\n", lista->id);
            fprintf(f, "A pessoa %s recuperou, ficou saudavel.\n", idPessoa);
        }
        lista->diasDoente = 0;
    }// Se não recuperar aumenta o número de dias da doença
    else {
        lista->diasDoente += 1;
        fprintf(f, "A pessoa %s nao recuperou (dias doente: %d).\n", idPessoa, lista->diasDoente);
    }
}

void calculaEstatistica(ppessoa lista, plocal espaco, int total, FILE* f) {
    ppessoa aux;
    int contaPessoasSala, totalPessoas = 0;
    int podeContarPessoas = 1;
    float saudaveis = 0, infetados = 0, imunes = 0;
    float percentagemPessoas;

    printf("\n Estatisticas da simulacao: \n");
    fprintf(f, "Estatisticas da simulacao: \n");
    for (int i = 0; i < total; i++) {
        contaPessoasSala = 0;
        aux = lista;
        while (aux != NULL) {
            if (espaco[i].id == aux->local->id)
                contaPessoasSala++;

            if (podeContarPessoas) {
                switch (aux->estado) {
                    case 'S':
                        saudaveis++;
                        break;
                    case 'D':
                        infetados++;
                        break;
                    case 'I':
                        imunes++;
                        break;
                }
                totalPessoas++;
            }
            aux = aux->prox;
        }
        percentagemPessoas = (contaPessoasSala / (float) (espaco[i].capacidade + contaPessoasSala)) * 100;
        printf("   Local %d: %.2f%% pessoas (capacidade maxima: %d)\n", espaco[i].id, percentagemPessoas, (espaco[i].capacidade + contaPessoasSala));
        fprintf(f, " Local %d: %.2f%% pessoas (capacidade maxima: %d)\n", espaco[i].id, percentagemPessoas, (espaco[i].capacidade + contaPessoasSala));
        podeContarPessoas = 0;
    }

    saudaveis = (saudaveis / (float) totalPessoas) * 100;
    infetados = (infetados / (float) totalPessoas) * 100;
    imunes = (imunes / (float) totalPessoas) * 100;

    puts("");
    printf("   Taxa de Saudaveis: %.2f%%\n", saudaveis);
    printf("   Taxa de Infetados: %.2f%%\n", infetados);
    printf("   Taxa de Imunes: %.2f%%\n", imunes);
    printf("   Total de pessoas na simulacao: %d\n", totalPessoas);
    // Guarda no ficheiro
    fprintf(f, " Taxa de Saudaveis: %.2f%%\n", saudaveis);
    fprintf(f, " Taxa de Infetados: %.2f%%\n", infetados);
    fprintf(f, " Taxa de Imunes: %.2f%%\n", imunes);
    fprintf(f, " Total de pessoas na simulacao: %d\n", totalPessoas);
}

ppessoa adicionaDoente(ppessoa lista, Pessoa doente, FILE* f) {
    // Caso haja erro na criação do doente este não é adicionado à lista
    if (doente.local == NULL) {
        fprintf(stderr, "[ERRO]: Erro na criacao do doente, nao vai ser adicionado!\n");
        fprintf(f, "[ERRO]: Erro na criacao do doente, nao vai ser adicionado!\n");
        return lista;
    }

    // Aloca-se memória para a nova pessoa
    ppessoa novaPessoa = malloc(sizeof (Pessoa));
    if (novaPessoa == NULL) {
        fprintf(stderr, "[ERRO]: A alocar memoria para um novo no doente.\n");
        fprintf(f, "[ERRO]: A alocar memoria para um novo no doente.\n");
        return lista;
    }
    *novaPessoa = doente;
    novaPessoa->prox = NULL;
    ppessoa aux = lista;

    // Adiciona-se ao fim da lista
    while (aux->prox != NULL)
        aux = aux->prox;

    aux->prox = novaPessoa;

    printf("-Doente adicionado com sucesso!\n");
    fprintf(f, "Doente %s, idade: %d, estado %c (dias doente: %d), local: %d. Adicionado com sucesso\n", novaPessoa->id, novaPessoa->idade,
            novaPessoa->estado, novaPessoa->diasDoente, novaPessoa->local->id);

    return lista;
}

ppessoa transferePessoas(ppessoa lista, plocal espaco, int total, FILE* f) {
    puts("");
    mostraEspaco(espaco, total);
    int erro = 0;

    // Obtém-se o local de origem
    int localOrigem = -1;
    do {
        if (erro == 1) {
            printf("\n[WARNING]: O local selecionado nao tem pessoas!\n");
            erro = 0;
        }
        printf("\n Local origem: ");
        if (scanf(" %d", &localOrigem) != 1) {
            scanf("%*[^\n]");
            localOrigem = -1;
        }
    } while (localOrigem < 0 || !localTemPessoas(lista, localOrigem, &erro));

    // Obtém-se o local de destino
    int localDestino = -2;
    do {
        if (erro == 1) {
            printf("\n[WARNING]: O local selecionado nao tem ligacao direta com o local de origem!\n");
            erro = 0;
        } else if (erro == 2) {
            printf("\n[WARNING]: O local selecionado esta cheio!\n");
            erro = 0;
        }
        printf("\n Local destino (-1 para voltar ao menu): ");
        if (scanf(" %d", &localDestino) != 1) {
            scanf("%*[^\n]");
            localDestino = -2;
        }

        // Se introduzir -1 sai
        if (localDestino == -1) {
            printf("\n A retornar ao menu...\n");
            return lista;
        }
    } while (localDestino < -1
            || !temLigacaoDireta(espaco, total, localOrigem, localDestino, &erro)
            || isEspacoCheio(espaco, total, localDestino, &erro));


    // Obtém-se o número de pessoas a mover do localOrigem para o localDestino
    int nPessoas = -1, resposta = -1;
    do {
        printf("\n Quantas pessoas deseja mover do local %d para o local %d: ", localOrigem, localDestino);
        if (scanf(" %d", &nPessoas) != 1) {
            scanf("%*[^\n]");
            nPessoas = -1;
        }

        int totalPessoas = numPessoasLocal(lista, localOrigem);
        if (nPessoas > totalPessoas) {
            do {
                printf("\n O numero de pessoas inserido e maior do que o numero de pessoas no local de origem (id: %d).", localOrigem);
                printf("\n Deseja mover todas as pessoas do local %d para o local %d (0 - Nao; 1 - Sim)? ", localOrigem, localDestino);
                if (scanf(" %d", &resposta) != 1) {
                    scanf("%*[^\n]");
                    resposta = -1;
                }
            } while (resposta < 0 || resposta > 1);

            if (resposta == 0) {
                printf("\n Tera de indicar o numero de pessoas a mover de novo.");
                nPessoas = -1;
            } else
                nPessoas = totalPessoas;
        }
    } while (nPessoas < 0);

    int j;
    // Faz-se a transferência das pessoas
    for (int i = nPessoas; i > 0; i--) {
        int nPessoasNoEspaco = 0;
        char** idNoEspaco = obtemPessoasNoEspaco(lista, &nPessoasNoEspaco, localOrigem);

        if (idNoEspaco == NULL) {
            fprintf(stderr, "[ERRO]: Na tranferencia de pessoas!\n");
            fprintf(f, "[ERRO]: Na tranferencia de pessoas!\n");
            return lista;
        }

        int posPessoaRand = intUniformRnd(0, (nPessoasNoEspaco - 1));
        ppessoa aux = lista;

        while (strcmp(aux->id, idNoEspaco[posPessoaRand]) != 0)
            aux = aux->prox;

        for (j = 0; j < total; j++) {
            if (espaco[j].id == aux->local->id) {
                espaco[j].capacidade += 1;
                break;
            }
        }

        for (int k = 0; k < total; k++) {
            if (espaco[k].id == localDestino) {
                if (espaco[k].capacidade > 0) {
                    espaco[k].capacidade -= 1;
                    aux->local = &espaco[k];
                    printf("\n A pessoa %s foi para o local %d.", aux->id, espaco[k].id);
                    fprintf(f, "A pessoa %s foi para o local %d.\n", aux->id, espaco[k].id);
                    break;
                } else {
                    espaco[j].capacidade -= 1;
                    printf("\n O espaco %d nao tem mais capacidade! A pessoa %s nao foi movida.\n", localDestino, aux->id);
                    fprintf(f, "O espaco %d nao tem mais capacidade! A pessoa %s nao foi movida.\n", localDestino, aux->id);
                    break;
                }
            }
        }
    }

    if (nPessoas == 0) {
        printf("\n Nao foram movidas pessoas!");
        fprintf(f, "Nao foram movidas pessoas!");
    }
    puts("");

    return lista;
}