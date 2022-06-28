/* 
 * File:   local.h
 * Author: José Francisco de Jesus Bugalho
 *
 * Created on 16 de Maio de 2020, 10:00
 */

#ifndef LOCAL_H
#define LOCAL_H

#define N_LIGACOES 3 // Número máximo de localizações por local

typedef struct sala local, *plocal;

struct sala {
    int id; // Id numérico do local
    int capacidade; // Capacidade máxima
    int liga[N_LIGACOES]; // Id das ligações (-1 nos casos não usados)
};

// Função para inicializar o espaço.
// Retorna o array dinâmico de espaços preenchido (NULL em caso de erro).
plocal criaEspaco(plocal espaco, int* total, char* nomeFich);

// Função de apresentação do array de espaços no ecrã.
void mostraEspaco(plocal espaco, int total);

// Verifica se o local lido do ficheiro é único (id diferente de todos os outros já lidos) e positivo (ou seja, maior que 0).
// Retorna 0 se o identificador do local não for único, 1 caso contrário.
int verificaIdLocal(plocal espaco, int total, local novoLocal);

// Verifica todas se todas as ligações dos locais estão corretas.
// Retorna 1 se todas as ligações estiverem corretas ou 0 caso contrário.
int verificaLigacoes(plocal espaco, int total);

// Obtém o índice do local no array de espaços.
// Retorna o identificador do local (-1 caso não exista).
int getIdLocal(plocal espaco, int total, int idLocal);

// Verifica se existe capacidade para mais pessoas no espaço da simulação.
// Retorna 1 se houver capacidade nos locais, 0 caso contrário.
int verificaCapacidade(plocal espaco, int total);

// Mostra os locais disponíveis, ou seja, com capacidade maio que 0 (zero).
void mostraLocaisDisponiveis(plocal espaco, int total);

// Verifica se um espaço está ou não cheio para depois se poderem adicionar mais pessoas.
// Retorna 0 se o espaço não está cheio, 1 caso contrário.
int isEspacoCheio(plocal espaco, int total, int localDestino, int *erro);

// Verifica se um local tem ligação direta com outro local.
// Retorna 0 se não tiverem ligação direta, 1 caso contrário.
int temLigacaoDireta(plocal espaco, int total, int idOrigem, int idDestino, int *erro);

#endif /* LOCAL_H */

