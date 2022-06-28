/* 
 * File:   config.h
 * Author: José Francisco de Jesus Bugalho
 *
 * Created on 17 de Maio de 2020, 11:36
 */

#ifndef CONFIG_H
#define CONFIG_H

// Número de pessoas que um doente infeta em cada iteração da simulação.
// Neste modelo, o valor corresponde a 5% do número de pessoas que se
//   encontram no mesmo local (valor arredondado para baixo).
#define PROB_DISSEMINACAO 0.5

// Probabilidade que um doente tem de recuperar em cada iteração da simulação.
// Neste modelo, a probabilidade é 1/idade.
#define PROB_RECUPERACAO 1

// Número de dias em que uma pessoa está doente. No final deste período,
//  qualquer pessoa volta a ficar saudável. 
// Neste modelo, o valor é de 5 + 1 dia por cada dezena de anos de vida (arredondado para baixo).
#define TEMPO_DEFAULT_INFECAO 5

// Quando uma pessoa infetada fica curada (por recuperação ou
//  por ter ultrapassado a duração máxima da infeção), existe a probabilidade 
//  de ficar imune à doença. Se isto acontecer, mesmo que volte a ser infetado 
//  no futuro, não contrai novamente a doença. 
// Neste modelo, a taxa de imunidade é de 20%.
#define PROB_IMUNIDADE 0.20

#endif /* CONFIG_H */

