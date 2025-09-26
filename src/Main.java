/*
Java-desafio-2: Sistema de Gestão de tarefas de Estudo.
Objetivo: Criar um programa em java que permita registrar, listar e
gerenciar suas tarefas de estudo diárias, calculando o tempo total
dedicado e identificando  a disciplina que mais consumiu seu tempo.

Funcionalidades principais:
*/

import java.util.Scanner;

public class  Main{
    public static void main(String[] ags){

 /*
1-Adicionar tarefa:
° Nome da disciplina
° Descrição da atividade
° Tempo estimado (em horas ou minutos)
*/
        Scanner userEntrance = new Scanner(System.in);

        System.out.print("Nome da disciplina: ");
        String task = userEntrance.nextLine();

        System.out.print("Descrição da atividade: ");
        String description = userEntrance.nextLine();

        System.out.print("Tempo estimado (horas): ");
        int hour = userEntrance.nextInt();

        System.out.print("Tempo estimado (minutos): ");
        int minute = userEntrance.nextInt();

        System.out.print("Defina um número como identificador: ");
        int id = userEntrance.nextInt();

        Tarefa novaTarefa = new Tarefa(task, description, hour, minute, id);
        novaTarefa.showTasks();





















 /*
2-Listar tarefa:
° Mostrar todas as tarefas registradas
° Exibir total de tempo de estudo do dia

3-Resumo por disciplina
° Mostrar todas as tarefas registradas
° Identificar a disciplina que mais consumiu meu tempo

4-Marcar tarefa como concluída:
° Permitir que eu possa marcar tarefas concluídas
° Atualizar o toal de horas efetivamente estudadas
*/





    }
}