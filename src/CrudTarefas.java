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
        saveTasksToFile(); 
    }

   
    private static int readInt(String prompt, int min, int max) {
        int value;
        while (true) {
            try {
                System.out.println(prompt);
                value = userEntrance.nextInt();
                userEntrance.nextLine();
                if (value < min || value > max) {
                    System.out.println("[ERRO] Digite um valor entre " + min + " e " + max + ".");
                } else {
                    return value;
                }
            } catch (Exception e) {
                System.out.println("[ERRO] Entrada inválida. Digite um número inteiro.");
                userEntrance.nextLine();
            }
        }
    }

    public static void addTask() {
        System.out.print("Nome da disciplina: ");
        String task = userEntrance.nextLine();

        System.out.print("Descrição da atividade: ");
        String description = userEntrance.nextLine();

        
        int hour = readInt("Tempo estimado (horas: ", 0, 24);
        int minute = readInt("Tempo estimado (minutos): ", 0, 59);

       
        System.out.print("Deseja definir um ID manual? (s/N): ");
        String resposta = userEntrance.nextLine().trim().toLowerCase();
        int id;
        if (resposta.equals("s") || resposta.equals("sim")) {
            
            id = readInt("Defina um número como identificador: ", 0, 59);
            if (findTaskById(id) != null) {
                System.out.println("[ERRO] Já existe uma tarefa com esse ID. Escolha outro ID.");
                return;
            }
        } else {
            
            int maxId = 0;
            for (Tarefa t : tarefas) if (t.getId() > maxId) maxId = t.getId();
            id = maxId + 1;
            System.out.println("ID gerado automaticamente: " + id);
        }

        Tarefa novaTarefa = new Tarefa(task, description, hour, minute, id);
        tarefas.add(novaTarefa);
        novaTarefa.showTasks();
        System.out.println("[CHECK] Tarefa adicionada corretamente ✔");

        
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
            System.out.println("5 - Remover tarefa");
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
                case 5:
                    deleteTask();
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

    public static void deleteTask(){
        if(tarefas.isEmpty()){
            System.out.println("Não há tarefas cadastradas para remover");
            return;
        }

        System.out.println("Tarefas cadastradas:");
        for (Tarefa t: tarefas){
            System.out.println("id: " + t.getId() + " | Disciplina: " + t.getTask() + " | Concluída: " + (t.isConcluded() ? "sim" : "não"));
        }
        System.out.println("Digite o ID da tarefa que deseja remover: ");
        int id = userEntrance.nextInt();
        userEntrance.nextLine();

        Tarefa tarefaParaRemover = findTaskById(id);
        if (tarefaParaRemover != null) {
            tarefas.remove(tarefaParaRemover);
            System.out.println("[CHECK] Tarefa removida com sucesso ✔");
            saveTasksToFile();
        }else {
            System.out.println("[ERRO] Nenhuma tarefa encontrada com esse ID.");
        }
    }

   

    public static void saveTasksToFile() {
        File original = new File(TASKS_FILE);
        if (original.exists()) {
            File backup = new File(TASKS_FILE + ".bak");
            try (InputStream in = new FileInputStream(original);
                 OutputStream out = new FileOutputStream(backup)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
            } catch (IOException e) {
                System.out.println("[ERRO] Falha ao criar backup: " + e.getMessage());
            }
        }

        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TASKS_FILE))) {
            for (Tarefa t : tarefas) {
                String safeTask = t.getTask().replace(";", ",").replace("\n", " ");
                String safeDesc = t.getDescription().replace(";", ",").replace("\n", " ");
                String line = String.format("%s;%s;%d;%d;%d;%b", safeTask, safeDesc, t.getHour(), t.getMinute(), t.getId(), t.isConcluded());
                String encryptedLine = xorEncryptDecrypt(line);
                writer.write(encryptedLine);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("[ERRO] Falha ao salvar tarefas: " + e.getMessage());
        }
    }

    public static void loadTasksFromFile() {
        File f = new File(TASKS_FILE);
        if (!f.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String decryptedLine = xorEncryptDecrypt(line);
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


    private static String xorEncryptDecrypt(String input) {
        char key = 'K';
        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] =(char)(chars[i] ^ key);
        }
        return new String(chars);
    }
}
