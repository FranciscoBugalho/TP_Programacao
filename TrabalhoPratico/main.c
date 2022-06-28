/* 
 * File:   main.c
 * Author: José Francisco de Jesus Bugalho
 *
 * Created on 16 de Maio de 2020, 9:30
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "header.h"
#include "backupPessoas.h"

/*
 * Função main.
 */
int main(int argc, char** argv) {
    plocal espaco = NULL;
    int totalEspacos = 0;
    ppessoa lista = NULL;
    pbackup listaBackups = NULL;
    int opt, isErro = 0;
    char ficheiro[TAM_NOME_FICHS];

    initRandom();

    while ((opt = menuPreparacao())) {
        switch (opt) {
            case 1:
                if (espaco != NULL) {
                    printf("\n Ja tem um espaco criado se quiser alterar reinicie o programa!\n");
                    break;
                }

                do {
                    if (isErro)
                        printf(" Esse ficheiro (\"%s\") nao existe. Experimente: \"%s\" ou \"%s\" ou \"%s\".\n", ficheiro, FICH_ESPACO_1, FICH_ESPACO_2, FICH_ESPACO_3);
                    printf("\n Indique o nome do ficheiro (.bin) de criacao do espaco da simulacao (ou \"voltar\" para voltar ao menu): ");
                    scanf(" %49[^\n]", ficheiro);
                } while (!ficheiroEspacoExiste(ficheiro, &isErro) && strcmp(ficheiro, VOLTAR) != 0);

                // Se escrever voltar volta ao menu
                if (strcmp(ficheiro, VOLTAR) == 0) {
                    fprintf(stderr, "\n A voltar ao menu...\n");
                    isErro = 0;
                    break;
                }

                espaco = criaEspaco(espaco, &totalEspacos, ficheiro);

                // Ocorreu um erro
                if (espaco == NULL) {
                    fprintf(stderr, "\n A sair...\n");
                    libertaLista(lista);
                    return 0;
                }
                isErro = 0;
                break;
            case 2:
                // Verifica se já existe um espaço criado
                if (espaco == NULL) {
                    // Caso não exista avisa o utilizador que tem de ter um espaço antes de adicionar as pessoas
                    fprintf(stderr, "\n Devera criar um espaco antes de adicionar as pessoas.\n");
                    break;
                }

                do {
                    if (isErro)
                        printf(" Esse ficheiro (\"%s\") nao existe. Experimente: \"%s\" ou \"%s\".\n", ficheiro, FICH_PESSOAS_1, FICH_PESSOAS_2);
                    printf("\n Indique o nome do ficheiro (.txt) de pessoas (ou \"voltar\" para voltar ao menu): ");
                    scanf(" %49[^\n]", ficheiro);
                } while (!ficheiroPessoasExiste(ficheiro, &isErro) && strcmp(ficheiro, VOLTAR) != 0);

                // Se escrever voltar volta ao menu
                if (strcmp(ficheiro, VOLTAR) == 0) {
                    fprintf(stderr, "\n A voltar ao menu...\n");
                    isErro = 0;
                    break;
                }

                lista = adicionaPessoas(lista, ficheiro, espaco, totalEspacos);

                // Ocorreu um erro
                if (lista == NULL && espaco != NULL) {
                    fprintf(stderr, "\n A sair...\n");
                    free(espaco);
                    totalEspacos = 0;
                    return 0;
                }
                isErro = 0;
                break;
            case 3:
                mostraEspaco(espaco, totalEspacos);
                break;
            case 4:
                mostraLista(lista);
                break;
            case 5:
                if (espaco == NULL || lista == NULL) {
                    printf("\n Devera criar um espaco e adicionar pessoas a simulacao antes de a comecar!\n");
                    break;
                }
                puts("");

                FILE* f = fopen(FICH_REPORT, "wt");
                if (!f) {
                    fprintf(stderr, "[ERRO]: A criar o ficheiro para guardar os logs da simulacao!\n");
                    break;
                }
                fprintf(f, "Inicio da simulacao: \n");

                while ((opt = menuSimulacao())) {
                    switch (opt) {
                        case 1:
                            // Guarda-se a lista anterior à lista de backups antes de se avançar
                            listaBackups = adicionaIteracao(listaBackups, lista);
                            guardaListaBackups(listaBackups, f);
                            // Avança 1 iteração
                            lista = avancaSimulacao(lista, f);
                            guardaListaAtual(lista, f);
                            //mostraListaBackups(listaBackups);
                            break;
                        case 2:
                            calculaEstatistica(lista, espaco, totalEspacos, f);
                            break;
                        case 3:
                            lista = adicionaDoente(lista, criaNovoDoente(lista, espaco, totalEspacos), f);
                            break;
                        case 4:
                            lista = transferePessoas(lista, espaco, totalEspacos, f);
                            break;
                        case 5:
                            listaBackups = recuarIteracoes(listaBackups, &lista, espaco, totalEspacos, f);
                            guardaListaBackups(listaBackups, f);
                            //mostraListaBackups(listaBackups);
                            //mostraLista(lista);
                            break;
                        case 6:
                            mostraEspaco(espaco, totalEspacos);
                            break;
                        case 7:
                            mostraLista(lista);
                            break;
                    }
                }
                fprintf(f, "Fim da simulacao. \n");
                fclose(f);

                // Salva no ficheiro
                salvaPopulacaoNoFicheiro(lista);

                libertaLista(lista);
                libertaListaBackups(listaBackups);
                free(espaco);

                return 0;
        }
    }

    libertaLista(lista);
    libertaListaBackups(listaBackups);
    free(espaco);

    return (EXIT_SUCCESS);
}

int menuPreparacao() {
    int opcao;

    puts("");
    printf("  ---- Fase de Preparacao ---- \n\n");
    printf(" 1 - Selecionar o espaco.\n");
    printf(" 2 - Adicionar as pessoas.\n");
    printf(" 3 - Listar espaco.\n");
    printf(" 4 - Listar pessoas.\n");
    printf(" 5 - Iniciar Fase de Simulacao.\n");
    puts("");
    printf(" 0 - Sair\n\n");
    do {
        opcao = -1;
        printf("Opcao: ");
        if (scanf(" %d", &opcao) != 1) {
            scanf("%*[^\n]");
        }
    } while (opcao < 0 || opcao > 7);
    return opcao;
}

int menuSimulacao() {
    int opcao;

    puts("");
    printf("  ---- Fase de Simulacao ---- \n\n");
    printf(" 1 - Avancar 1 iteracao.\n");
    printf(" 2 - Apresentar estatistica.\n");
    printf(" 3 - Adicionar doente.\n");
    printf(" 4 - Transferir pessoas.\n");
    printf(" 5 - Recuar X iteracoes.\n");
    printf(" 6 - Listar espaco.\n");
    printf(" 7 - Listar pessoas.\n");
    puts("");
    printf(" 0 - Terminar a simulacao.\n\n");
    do {
        opcao = -1;
        printf("Opcao: ");
        if (scanf(" %d", &opcao) != 1) {
            scanf("%*[^\n]");
        }
    } while (opcao < 0 || opcao > 7);
    return opcao;
}

int ficheiroEspacoExiste(char* nomeFicheiro, int* isErro) {
    if (strcmp(nomeFicheiro, FICH_ESPACO_1) != 0
            && strcmp(nomeFicheiro, FICH_ESPACO_2) != 0
            && strcmp(nomeFicheiro, FICH_ESPACO_3) != 0) {
        *isErro = 1;
        return 0;
    }
    return 1;
}

int ficheiroPessoasExiste(char* nomeFicheiro, int* isErro) {
    if (strcmp(nomeFicheiro, FICH_PESSOAS_1) != 0
            && strcmp(nomeFicheiro, FICH_PESSOAS_2) != 0) {
        *isErro = 1;
        return 0;
    }
    return 1;
}

void libertaLista(ppessoa lista) {
    ppessoa aux;
    while (lista != NULL) {
        aux = lista;
        lista = lista->prox;
        free(aux->local);
        free(aux);
    }
}

void libertaListaBackups(pbackup listaBackups) {
    pbackup aux;
    ppessoa auxP;
    while (listaBackups != NULL) {
        while (listaBackups->proxP != NULL) {
            auxP = listaBackups->proxP;
            listaBackups->proxP = listaBackups->proxP->prox;
            free(auxP->local);
            free(auxP);
        }
        aux = listaBackups;
        listaBackups = listaBackups->prox;
        free(aux);
    }
}