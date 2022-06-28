#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

#include "pessoa.h"
#include "header.h"

ppessoa adicionaPessoas(ppessoa lista, char* nomeFich, plocal espaco, int total) {
    FILE* f;
    Pessoa pessoaTemp;
    ppessoa novaPessoa, aux;
    plocal nLocal;
    int podeAdicionar, nPessoasAdicionadas = 0;

    // Abre o ficheiro para leitura
    f = fopen(nomeFich, "rt");
    if (!f) {
        fprintf(stderr, " [ERRO]: A abrir o ficheiro de texto \"%s\" para leitura.\n", nomeFich);
        return lista;
    }

    // Enquanto não atinge o fim do ficheiro
    while (fscanf(f, " %s", pessoaTemp.id) == 1) {
        podeAdicionar = 1;

        // Verifica se o id já existe
        if (verificaIdPessoa(lista, pessoaTemp.id) == 1) {
            fprintf(stderr, " [WARNING]: Ja existe uma pessoa com o id \"%s\", logo, esta nao sera adicionada a lista.\n", pessoaTemp.id);
            // Se existir lê-se do ficheiro mas não se adiciona à lista
            podeAdicionar = 0;
        }

        // Tenta ler a idade
        if (fscanf(f, " %d", &pessoaTemp.idade) != 1) {
            fprintf(stderr, "[ERRO]: Na leitura da idade da pessoa.\n");
            fclose(f);
            return lista;
        }

        // Verifica-se a idade da pessoa
        if (pessoaTemp.idade <= 0 || pessoaTemp.idade > 100) {
            fprintf(stderr, " [WARNING]: A idade da pessoa e' invalida.\n");
            // Se a idade for inválida, lê-se do ficheiro mas não se adiciona à lista
            podeAdicionar = 0;
        }

        // Tenta ler a idade
        if (fscanf(f, " %c", &pessoaTemp.estado) != 1) {
            fprintf(stderr, "[ERRO]: Na leitura do estado da pessoa.\n");
            fclose(f);
            return lista;
        }

        // Verifica-se o carater do estado lido (S, D, I)
        if (pessoaTemp.estado != 'S' && pessoaTemp.estado != 'D' && pessoaTemp.estado != 'I') {
            fprintf(stderr, " [ERRO]: A o estado da pessoa e' invalido.\n");
            // Se o carater for inválido, lê-se do ficheiro mas não se adiciona à lista
            podeAdicionar = 0;
        }

        // Se o estado for Doente (D)
        if (pessoaTemp.estado == 'D') {
            // Tenta ler o número de dias de doente dessa pessoa
            if (fscanf(f, " %d", &pessoaTemp.diasDoente) != 1) {
                fprintf(stderr, "[ERRO]: Na leitura do numero de dias de doente de uma pessoa.\n");
                fclose(f);
                return lista;
            }

            // Verifica-se o número de dias doente
            if (pessoaTemp.diasDoente < 0) {
                fprintf(stderr, " [WARNING]: O numero de dias de doente e invalido (menor que 0).\n");
                // Se o número de dias doente for inválido, lê-se do ficheiro mas não se adiciona à lista
                podeAdicionar = 0;
            }
        }// Senão o número de dias doesnte fica a 0
        else
            pessoaTemp.diasDoente = 0;


        // Só adiciona se a pessoa lida cumprir todos os parâmetros
        if (podeAdicionar == 1) {
            // Aloca-se memória para o local onde a pessoa será colocada
            nLocal = malloc(sizeof (local));
            if (nLocal == NULL) {
                fprintf(stderr, "[ERRO]: A alocar memoria para o local onde iria ser colocada a pessoa %s.\n", pessoaTemp.id);
                fclose(f);
                return lista;
            }

            // Coloca a pessoa num espaco
            nLocal = atribuiLocal(espaco, total);
            if (nLocal == NULL) {
                // Não foi possível adicionar essa pessoa a nenhum local
                // Então não se adicionam mais pessoas à lista de participantes na simulação
                fprintf(stderr, " [WARNING]: Nao ha mais capacidade nos locais, a pessoa %s (e as seguintes) irao ser excluidas da simulacao.\n", pessoaTemp.id);
                break;
            }

            // Insere a pessoa na lista
            novaPessoa = malloc(sizeof (Pessoa));
            if (novaPessoa == NULL) {
                fprintf(stderr, "[ERRO]: A alocar memoria para um novo no da lista de pessoas.\n");
                fclose(f);
                return lista;
            }

            *novaPessoa = pessoaTemp;
            novaPessoa->prox = NULL;
            novaPessoa->local = nLocal;

            // Insere no início da lista
            if (lista == NULL)
                lista = novaPessoa;
                // Insere no fim da lista
            else {
                aux = lista;

                while (aux->prox != NULL)
                    aux = aux->prox;

                aux->prox = novaPessoa;
            }
            nPessoasAdicionadas++;
        }
    }

    if (nPessoasAdicionadas == 0)
        printf("\n-Nenhuma pessoa do ficheiro foi adicionada!\n");
    else
        printf("-Pessoas adicionadas com sucesso!\n");

    fclose(f);
    return lista;
}

void mostraLista(ppessoa lista) {
    if (lista == NULL) {
        printf("\n Nao existem pessoas!\n");
        return;
    }

    ppessoa aux = lista;
    printf("\n-Lista de pessoas: \n");

    while (aux) {
        printf(" Identificador: %-25s\n   Idade: %d\n   Estado: ", aux->id, aux->idade);

        switch (aux->estado) {
            case 'S':
                printf("Saudavel (%c)", aux->estado);
                break;
            case 'D':
                printf("Doente (%c) - Numero de dias doente: %d", aux->estado, aux->diasDoente);
                break;
            case 'I':
                printf("Imune (%c)", aux->estado);
                break;
        }
        printf("\n   Local: %d", aux->local->id);
        puts("\n");
        aux = aux->prox;
    }
}

int verificaIdPessoa(ppessoa lista, char* id) {
    if (lista == NULL)
        return 0;

    ppessoa aux = lista;
    while (aux != NULL) {
        if (strcmp(aux->id, id) == 0)
            return 1;
        aux = aux->prox;
    }
    return 0;
}

plocal atribuiLocal(plocal espaco, int total) {
    int idLocal = intUniformRnd(0, total - 1);

    if (verificaCapacidade(espaco, total) == 0)
        return NULL;

    for (int i = 0; i < total; i++) {
        if (espaco[i].id == espaco[idLocal].id) {
            espaco[i].capacidade -= 1;

            if (espaco[i].capacidade < 0) {
                espaco[i].capacidade = 0;
                return atribuiLocal(espaco, total);
            }
            return &espaco[i];
        }
    }
    return NULL;
}

int numPessoasLocal(ppessoa lista, int idLocal) {
    int contador = 0;
    while (lista) {
        if (lista->local->id == idLocal)
            contador++;
        lista = lista->prox;
    }
    return contador;
}

char** obtemDoentes(ppessoa lista, int* nDoentes, FILE* f) {
    char** idDoentes = NULL, **aux = NULL;
    while (lista) {
        // Se estiver doente
        if (lista->estado == 'D') {

            // Realoca espaço para um novo id
            aux = realloc(idDoentes, (*nDoentes + 1) * sizeof (char*));
            if (aux == NULL) {
                fprintf(stderr, "[ERRO]: A alocar memoria para os id dos doentes!\n");
                fprintf(f, "Erro a alocar memoria para os id dos doentes!\n");
                free(idDoentes);
                return NULL;
            }

            aux[*nDoentes] = lista->id;

            // Aloca espaço para os novos id's
            idDoentes = malloc(*nDoentes * sizeof (char*));
            if (idDoentes == NULL) {
                fprintf(stderr, "[ERRO]: A alocar memoria para o array dos id's dos doentes!\n");
                fprintf(f, "Erro a alocar memoria para o array dos id's dos doentes!\n");
                free(aux);
                return NULL;
            }

            idDoentes = aux;
            *nDoentes += 1;
        }
        lista = lista->prox;
    }
    return idDoentes;
}

int estavaDoente(char** idDoentes, int nDoentes, char* idDoenteAtual) {
    for (int i = 0; i < nDoentes; i++)
        if (strcmp(idDoentes[i], idDoenteAtual) == 0)
            return 1;
    return 0;
}

Pessoa criaNovoDoente(ppessoa lista, plocal espaco, int total) {
    Pessoa novaPessoa;
    int erro = 0;

    // Id da pessoa
    do {
        printf("\n Identificador: ");
        scanf(" %99s", novaPessoa.id);
    } while (verificaIdPessoa(lista, novaPessoa.id));

    // Idade da pessoa
    do {
        printf("\n Idade: ");
        if (scanf(" %d", &novaPessoa.idade) != 1)
            scanf("%*[^\n]");
    } while (novaPessoa.idade < 0 || novaPessoa.idade > 100);

    // Número de dias doente da pessoa
    do {
        printf("\n Dias doente: ");
        if (scanf(" %d", &novaPessoa.diasDoente) != 1)
            scanf("%*[^\n]");
    } while (novaPessoa.diasDoente < 0);

    printf("\n Espacos disponiveis: \n");
    mostraLocaisDisponiveis(espaco, total);

    int idEspaco = -1;
    // Seleção do espaço para onde a pessoa vai
    do {
        if (erro == 2) {
            printf("\n[WARNING]: O local selecionado esta cheio!\n");
            erro = 0;
        }
        printf("\n Indique o id do espaco: ");
        if (scanf(" %d", &idEspaco) != 1) {
            scanf("%*[^\n]");
            idEspaco = -1;
        }
    } while (idEspaco == -1 || getIdLocal(espaco, total, idEspaco) == -1 || isEspacoCheio(espaco, total, idEspaco, &erro));

    // Estado da pessoa (Doente, 'D')
    novaPessoa.estado = 'D';

    // Aloca memória para o novo local
    plocal nLocal = malloc(sizeof (local));
    if (nLocal == NULL) {
        fprintf(stderr, "[ERRO]: A alocar memoria para o local onde iria ser colocada o novo doente %s.\n", novaPessoa.id);
        novaPessoa.local = NULL;
        return novaPessoa;
    }

    int id = getIdLocal(espaco, total, idEspaco);

    if (id == -1) {
        fprintf(stderr, "[ERRO]: A obter o id do local onde o doente sera inserido.\n");
        novaPessoa.local = NULL;
        return novaPessoa;
    }

    nLocal = &espaco[id];
    espaco[id].capacidade -= 1;

    novaPessoa.local = nLocal;

    return novaPessoa;
}

int localTemPessoas(ppessoa lista, int idLocal, int *erro) {
    while (lista) {
        if (lista->local->id == idLocal)
            return 1;
        lista = lista->prox;
    }
    *erro = 1;
    return 0;
}

char** obtemPessoasNoEspaco(ppessoa lista, int* nPessoas, int idOrigem) {
    char** idPessoas = NULL, **aux = NULL;
    while (lista) {
        // Se estiver no local recebido como parâmetro
        if (lista->local->id == idOrigem) {

            // Realoca espaço para um novo id
            aux = realloc(idPessoas, (*nPessoas + 1) * sizeof (char*));
            if (aux == NULL) {
                fprintf(stderr, "Erro a alocar memoria para os id das pessoas no local %d!\n", idOrigem);
                free(idPessoas);
                return NULL;
            }

            aux[*nPessoas] = lista->id;

            // Aloca espaço para os novos id's
            idPessoas = malloc(*nPessoas * sizeof (char*));
            if (idPessoas == NULL) {
                fprintf(stderr, "Erro a alocar memoria para o array dos id's das pessoas no local %d!\n", idOrigem);
                free(aux);
                return NULL;
            }

            idPessoas = aux;
            *nPessoas += 1;
        }
        lista = lista->prox;
    }
    return idPessoas;
}

void salvaPopulacaoNoFicheiro(ppessoa lista) {
    int resposta = -1;
    char nomeFich[TAM_NOME_FICHS];

    puts("");
    // Obtém-se o nome do ficheiro
    do {
        printf(" Insira o nome do ficheiro para salvar a populacao final da simulacao (.txt): ");
        scanf(" %49s", nomeFich);

        // Caso o ficheiro seja um dos já existentes pergunta-se se o utilizador pertende sobrepor o nome conteúdo ao conteúdo já existente
        if (strcmp(FICH_PESSOAS_1, nomeFich) == 0 || strcmp(FICH_PESSOAS_2, nomeFich) == 0) {
            do {
                printf("\n Deseja sobrepor o conteudo do novo ficheiro ao ja existente? (0 - Nao; 1 - Sim) ");
                if (scanf(" %d", &resposta) != 1) {
                    resposta = -1;
                    scanf("%*[^\n]");
                }
            } while (resposta < 0 || resposta > 1);

            if (resposta == 0) {
                printf("\n Tera de introduzir um nome novo para o ficheiro!\n");
                resposta = -1;
            }

        } else
            resposta = 1;
    } while (resposta == -1 || strcmp(FICH_REPORT, nomeFich) == 0);

    // Abre-se o ficheiro para escrita
    FILE* f = fopen(nomeFich, "wt");
    if (!f) {
        fprintf(stderr, "[ERRO]: Ao criar o ficheiro de texto %s para escrita!\n");
        return;
    }

    if (lista != NULL) {
        // Escreve-se no ficheiro
        while (lista) {
            if (lista->estado == 'D')
                fprintf(f, "%s %d %c %d\n", lista->id, lista->idade, lista->estado, lista->diasDoente);
            else
                fprintf(f, "%s %d %c\n", lista->id, lista->idade, lista->estado);

            lista = lista->prox;
        }
    }

    fclose(f);

    printf("\n Conteudo salvo com sucesso!\n");
}

ppessoa* retiraPessoasLocal(ppessoa* lista, plocal espaco, int total) {
    ppessoa* aux = lista;
    while ((*aux)) {
        for (int i = 0; i < total; i++) {
            if ((*aux)->local->id == espaco[i].id) {
                espaco[i].capacidade++;
                (*aux)->local = NULL;
                break;
            }
        }
        (*aux) = (*aux)->prox;
    }
    return lista;
}

void atualizaPessoasEspacos(ppessoa lista, plocal espaco, int total) {
    while (lista) {
        for (int i = 0; i < total; i++) {
            if (lista->local->id == espaco[i].id) {
                espaco[i].capacidade--;
                break;
            }
        }
        lista = lista->prox;
    }
}

void guardaListaAtual(ppessoa lista, FILE* f) {
    fprintf(f, "Lista de pessoas: \n");
    while (lista) {
        if (lista->estado == 'D')
            fprintf(f, " %s %d %c %d\n", lista->id, lista->idade, lista->estado, lista->diasDoente);
        else
            fprintf(f, " %s %d %c\n", lista->id, lista->idade, lista->estado);

        lista = lista->prox;
    }
}

void libertaListaPessoas(ppessoa lista) {
    ppessoa aux;
    while (lista != NULL) {
        aux = lista;
        lista = lista->prox;
        free(aux);
    }
}