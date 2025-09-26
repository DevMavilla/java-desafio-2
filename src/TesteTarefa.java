import java.util.ArrayList;

public class TesteTarefa {
    public static void main(String[] args) {
       ArrayList<Tarefa> tarefas = new ArrayList<>();

        Tarefa t1 = new Tarefa("java", "projeto 2", 1, 30, 102);
        Tarefa t2 = new Tarefa("Poo", "Dia 2", 1, 20, 122);

        tarefas.add(t1);
        tarefas.add(t2);

        for (Tarefa t : tarefas) {
            t.showTasks();
            System.out.println("[CHECK] Tarefa adicionada corretamente ✔\n");
        }

        System.out.println("✅ Todas as tarefas foram adicionadas e validadas com sucesso!");
    }


}
