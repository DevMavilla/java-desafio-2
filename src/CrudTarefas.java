import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.*;

public class CrudTarefas {

    static ArrayList<Tarefa> tarefas = new ArrayList<>();
    static Scanner userEntrance = new Scanner(System.in);

    static final String TASKS_FILE = "tasks.csv";

    public static void main(String[] args) {
        loadTasksFromFile();
        menu();
        saveTasksToFile(); // salva ao sair como redundância
    }

    // utilitário para ler um inteiro com validação e prompt
    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = userEntrance.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("[ERRO] Entrada inválida. Digite apenas números (ex.: 1, 42). Tente novamente.");
            }
        }
    }

    public static void addTask() {
        System.out.print("Nome da disciplina: ");
        String task = userEntrance.nextLine();

        System.out.print("Descrição da atividade: ");
        String description = userEntrance.nextLine();

        // Ler horas/minutos com validação
        int hour = readInt("Tempo estimado (horas): ");
        int minute = readInt("Tempo estimado (minutos): ");

        // Opção: permitir ID manual ou gerar automaticamente
        System.out.print("Deseja definir um ID manual? (s/N): ");
        String resposta = userEntrance.nextLine().trim().toLowerCase();
        int id;
        if (resposta.equals("s") || resposta.equals("sim")) {
            // ler id manual validado
            id = readInt("Defina um número como identificador: ");
            if (findTaskById(id) != null) {
                System.out.println("[ERRO] Já existe uma tarefa com esse ID. Escolha outro ID.");
                return;
            }
        } else {
            // gerar ID automático simples: achar maior id e +1
            int maxId = 0;
            for (Tarefa t : tarefas) if (t.getId() > maxId) maxId = t.getId();
            id = maxId + 1;
            System.out.println("ID gerado automaticamente: " + id);
        }

        Tarefa novaTarefa = new Tarefa(task, description, hour, minute, id);
        tarefas.add(novaTarefa);
        novaTarefa.showTasks();
        System.out.println("[CHECK] Tarefa adicionada corretamente ✔");

        // salva no arquivo após adicionar
        saveTasksToFile();
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
            userEntrance.nextLine();

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

    public static void listTasks() {
        if (tarefas.isEmpty()) {
            System.out.println("Não há tarefas cadastradas.");
        } else {
            for (Tarefa t : tarefas) {
                t.showTasks();
            }
        }
    }

    public static void taskConcluded() {
        if (tarefas.isEmpty()) {
            System.out.println("Não há tarefas cadastradas.");
            return;
        }

        // mostra resumo curto para o usuário escolher
        for (Tarefa t : tarefas) {
            System.out.println("id: " + t.getId()
                    + " | Disciplina: " + t.getTask()
                    + " | Concluída: " + (t.isConcluded() ? "sim" : "não"));
        }

        System.out.print("Digite o ID da tarefa que deseja marcar como concluída: ");
        int id = userEntrance.nextInt();
        userEntrance.nextLine();

        Tarefa encontrada = findTaskById(id);
        if (encontrada == null) {
            System.out.println("Nenhuma tarefa encontrada com esse ID.");
            return;
        }

        if (encontrada.isConcluded()) {
            System.out.println("[INFO] Essa tarefa já estava marcada como concluída.");
        } else {
            encontrada.setConcluded(true);
            System.out.println("[CHECK] Tarefa marcada como concluída ✔");
            // mostra lista atualizada e salva
            listTasks();
            saveTasksToFile();
        }
    }

    public static void summaryByDiscipline() {
        if (tarefas.isEmpty()) {
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
        }

        String mostStudiesDiscipline = "";
        int maxTimeMinutes = 0;

        for (Map.Entry<String, Integer> entry : timeByDiscipline.entrySet()) {
            String discipline = entry.getKey();
            int time = entry.getValue();

            System.out.println("Disciplina: " + discipline + " | Total de tempo: " + (time / 60) + "h " + (time % 60) + "min");

            if (time > maxTimeMinutes) {
                maxTimeMinutes = time;
                mostStudiesDiscipline = discipline;
            }
        }

        System.out.println("\nDisciplina que mais consumiu tempo: " + mostStudiesDiscipline
                + " | Total: " + (maxTimeMinutes / 60) + "h " + (maxTimeMinutes % 60) + "min");
    }

    // ---------------------------
    // PERSISTÊNCIA SIMPLES (CSV com ';' como separador)
    // ---------------------------

    public static void saveTasksToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TASKS_FILE))) {
            for (Tarefa t : tarefas) {
                String safeTask = t.getTask().replace(";", ",").replace("\n", " ");
                String safeDesc = t.getDescription().replace(";", ",").replace("\n", " ");
                String line = String.format("%s;%s;%d;%d;%d;%b",
                        safeTask, safeDesc, t.getHour(), t.getMinute(), t.getId(), t.isConcluded());
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("[ERRO] Falha ao salvar tarefas: " + e.getMessage());
        }
    }

    public static void loadTasksFromFile() {
        File f = new File(TASKS_FILE);
        if (!f.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";", -1);
                if (parts.length < 6) continue;
                String task = parts[0];
                String description = parts[1];
                int hour = Integer.parseInt(parts[2]);
                int minute = Integer.parseInt(parts[3]);
                int id = Integer.parseInt(parts[4]);
                boolean concluded = Boolean.parseBoolean(parts[5]);

                Tarefa t = new Tarefa(task, description, hour, minute, id);
                if (concluded) t.setConcluded(true);
                tarefas.add(t);
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("[ERRO] Falha ao carregar tarefas: " + e.getMessage());
        }
    }

    private static Tarefa findTaskById(int id) {
        for (Tarefa t : tarefas) {
            if (t.getId() == id) return t;
        }
        return null;
    }
}
