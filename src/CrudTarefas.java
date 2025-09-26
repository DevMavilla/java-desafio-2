import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CrudTarefas {

    static ArrayList<Tarefa> tarefas = new ArrayList<>();
    static Scanner userEntrance = new Scanner(System.in);

    public static void main(String[] args) {
        menu();
    }

    public static void addTask() {
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
        userEntrance.nextLine(); // consumir a quebra de linha

        Tarefa novaTarefa = new Tarefa(task, description, hour, minute, id);
        tarefas.add(novaTarefa);
        novaTarefa.showTasks();
        System.out.println("[CHECK] Tarefa adicionada corretamente ✔");
    }

    public static void menu() {
        int option;

        do {
            System.out.println("=== MENU DE TAREFAS ===");
            System.out.println("1 - Adicionar tarefa");
            System.out.println("2 - Listar tarefas");
            System.out.println("3 - Marcar tarefa concluída");
            System.out.println("4 - Resumo por disciplina");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            option = userEntrance.nextInt();
            userEntrance.nextLine(); // consumir quebra de linha

            switch (option) {
                case 1:
                    addTask();
                    break;
                case 2:
                    listTasks();
                    break;
                case 3:
                    taskConcluded();
                    break;
                case 4:
                    summaryByDiscipline();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }

            System.out.println("\n----------------------------------\n");

        } while (option != 0);
    }

    public static void addTask() {
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
        userEntrance.nextLine(); // consumir a quebra de linha

        Tarefa novaTarefa = new Tarefa(task, description, hour, minute, id);
        tarefas.add(novaTarefa);
        novaTarefa.showTasks();
        System.out.println("[CHECK] Tarefa adicionada corretamente ✔");
    }


    public  static void listTasks() {
            if (tarefas.size() == 0){
                System.out.println("Não há tarefas cadastradas.");
            } else {
                for (Tarefa t : tarefas) {
                t.showTasks();
            }
        }
    }

    public  static void taskConcluded() {

            for (Tarefa t: tarefas){
                System.out.println("id: " + t.getId()
                + "| Disciplina: " + t.getTask()
                + "| Concluída: " + (t.isConcluded() ? "sim" : "não"));
            }

            System.out.println("Digite o ID da Tarefa que deseja marcar como concluída: ");
            int id = userEntrance.nextInt();
            userEntrance.nextLine();

            boolean found = false;

                for (Tarefa t: tarefas){
                    if (t.getId() == id){
                        t.setConcluded(true);
                        System.out.println("[CHECK] Tarefa marcada como concluída ✔");
                        found = true;
                        break;
                   }
                }
        if (!found) {
            System.out.println("Nenhuma tarefa encontrada com esse ID.");
        } else {
            // só lista se realmente encontrou
            listTasks();
        }



    }




    public static void summaryByDiscipline() {
            if (tarefas.size() == 0) {
                System.out.println("Não há tarefas cadastradas.");
                return;
            }

                Map<String, Integer> timeByDiscipline = new HashMap<>();

                for (Tarefa t : tarefas) {
                int totalMinutos = t.getHour() * 60 + t.getMinute();
                timeByDiscipline.put(
                        t.getTask(),
                        timeByDiscipline.getOrDefault(t.getTask(), 0) + totalMinutos
                );
                /*
                Explicando :

                t.getHour() * 60 + t.getMinute() → converte o tempo da tarefa todo para minutos para facilitar a soma.

                getOrDefault(t.getTask(), 0) → pega o valor atual acumulado da disciplina, ou 0 se ainda não existir.

                put() → atualiza o valor total acumulado
                */
                }

                    String mostStudiesDiscipline = "";
                    int maxTimeMinutes = 0;

                    for (Map.Entry<String, Integer> entry : timeByDiscipline.entrySet()) {
                    String discipline = entry.getKey();
                    int time = entry.getValue();

                    System.out.println("Disciplina: " + discipline + "| Total de tempo: " + (time/ 60) + "h " + (time % 60) + "min");

                        if (time > maxTimeMinutes) {
                        maxTimeMinutes = time;
                        mostStudiesDiscipline = discipline;
                        }

                    }

                    System.out.println("\nDisciplina que mais consumiu tempo: " + mostStudiesDiscipline
                    + " | Total: " + (maxTimeMinutes / 60) + "h " + (maxTimeMinutes % 60) + "min");

    }


}


