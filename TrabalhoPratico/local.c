#include <stdio.h>
#include <stdlib.h>

#include "local.h"

plocal criaEspaco(plocal espaco, int* total, char* nomeFich) {
    FILE* f;
    local localTemp;
    plocal novoLocal;

    // Abre o ficheiro para leitura
    f = fopen(nomeFich, "rb");
    if (!f) {
        fprintf(stderr, " [ERRO]: a abrir o ficheiro binario \"%s\" para leitura.\n", nomeFich);
        return espaco;
    }

    // Lê do ficheiro enquanto houverem estruturas
    while (fread(&localTemp, sizeof (local), 1, f) == 1) {

        if (verificaIdLocal(novoLocal, *total, localTemp) == 0) {
            fprintf(stderr, " [ERRO]: Local lido nao esta corretamente contruido.\n");
            fclose(f);
            free(espaco);
            *total = 0;
            return NULL;
        }

        // Realoca espaço para um novo local
        novoLocal = realloc(espaco, (*total + 1) * sizeof (local));
        if (novoLocal == NULL) {
            fprintf(stderr, "[ERRO]: Realocar memoria para um novo local.\n");
            fclose(f);
            free(espaco);
            *total = 0;
            return NULL;
        }

        novoLocal[*total] = localTemp;

        // Aloca espaço para os novos locais
        espaco = malloc(*total * sizeof (local));
        if (novoLocal == NULL) {
            fprintf(stderr, "[ERRO]: Alocar memoria para os locais.\n");
            fclose(f);
            free(espaco);
            *total = 0;
            return NULL;
        }

        espaco = novoLocal;
        *total += 1;
    }

    // Após estar o array todo contruído
    // Verificam-se as ligações
    if (verificaLigacoes(novoLocal, *total) == 0) {
        fprintf(stderr, " [ERRO]: Ligacoes entre locais estao incorretas.\n");
        fclose(f);
        free(espaco);
        *total = 0;
        return NULL;
    }

    printf("-Espaco criado com sucesso!\n");

    fclose(f);

    return novoLocal;
}

void mostraEspaco(plocal espaco, int total) {
    puts("");
    if (espaco == NULL) {
        printf(" Nao existem espacos associados!\n");
        return;
    }

    for (int i = 0; i < total; i++) {
        printf(" Local %d: \n   Capacidade: %d\n   Ligacoes: ",
                espaco[i].id, espaco[i].capacidade);

        for (int j = 0; j < N_LIGACOES; j++)
            printf("%d  ", espaco[i].liga[j]);

        puts("\n");
    }
}

int verificaIdLocal(plocal espaco, int total, local novoLocal) {
    for (int i = 0; i < total; i++)
        if (espaco[i].id == novoLocal.id || espaco[i].id <= 0)
            return 0;
    return 1;
}

int verificaLigacoes(plocal espaco, int total) {
    int verif = 0, id;

    for (int i = 0; i < total; i++) {
        for (int j = 0; j < N_LIGACOES; j++) {
            // Obtém o indice do local no array de espaços
            id = getIdLocal(espaco, total, espaco[i].liga[j]);
            // Caso não exista sai
            if (id == -1 && espaco[i].liga[j] != -1)
                return 0;
            if (espaco[i].liga[j] == espaco[id].id)
                verif = 1;
            else
                if (verif != 1)
                verif = 0;
        }
        if (verif == 0)
            return 0;
    }
    return verif;
}

int getIdLocal(plocal espaco, int total, int idLocal) {
    for (int i = 0; i < total; i++)
        if (espaco[i].id == idLocal)
            return i;
    return -1;
}

int verificaCapacidade(plocal espaco, int total) {
    for (int i = 0; i < total; i++)
        if (espaco[i].capacidade > 0)
            return 1;
    return 0;
}

void mostraLocaisDisponiveis(plocal espaco, int total) {
    for (int i = 0; i < total; i++) {
        if (espaco[i].capacidade > 0) {
            printf(" Local %d: \n   Capacidade: %d\n   Ligacoes: ",
                    espaco[i].id, espaco[i].capacidade);

            for (int j = 0; j < N_LIGACOES; j++)
                printf("%d  ", espaco[i].liga[j]);

            puts("\n");
        }
    }
}

int isEspacoCheio(plocal espaco, int total, int localDestino, int *erro) {
    for (int i = 0; i < total; i++)
        if (espaco[i].id == localDestino && espaco[i].capacidade > 0)
            return 0;
    *erro = 2;
    return 1;
}

int temLigacaoDireta(plocal espaco, int total, int idOrigem, int idDestino, int *erro) {
    for(int i = 0; i < N_LIGACOES; i++) 
        if(espaco[getIdLocal(espaco, total, idOrigem)].liga[i] == idDestino)
            return 1;
    *erro = 1;
    return 0;
}