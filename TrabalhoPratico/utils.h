/* 
 * File:   pessoa.h
 * Author: José Francisco de Jesus Bugalho
 *
 * Created on 16 de Maio de 2020, 10:10
 * 
 * Código disponibilizado pelos professores da cadeira de Programação.
 */

#ifndef UTILS_H
#define UTILS_H

// Inicializa o gerador de numeros aleatorios.
// Esta funcao deve ser chamada apenas uma vez no inicio da execucao do programa
void initRandom();

//Devolve um valor inteiro aleatorio distribuido uniformemente entre [a, b]
int intUniformRnd(int a, int b);

// Devolve o valor 1 com probabilidade prob. Caso contrario, devolve 0
int probEvento(float prob);

#endif /* UTILS_H */
