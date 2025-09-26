/*
Java-desafio-2: Sistema de Gestão de tarefas de Estudo.
Objetivo: Criar um programa em java que permita registrar, listar e
gerenciar suas tarefas de estudo diárias, calculando o tempo total
dedicado e identificando  a disciplina que mais consumiu seu tempo.
*/

public class  Main{
    public static void main(String[] ags){

    CrudTarefas crud = new CrudTarefas();

    crud.menu();

    }
}