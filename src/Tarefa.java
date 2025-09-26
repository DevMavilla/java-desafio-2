/*
1-Adicionar tarefa:
° Nome da disciplina
° Descrição da atividade
° Tempo estimado (em horas e minutos)
*/

public class Tarefa {
    private String task;
    private String description;
    private int hour;
    private int minute;
    private int id;

    //Constructor
    public Tarefa(String task, String description, int hour, int minute, int id) {
        this.task = task;
        this.hour = hour;
        this.description = description;
        this.minute = minute;
        this.id = id;
    }


    //Getters task
    public String getTask() {
        return task;
    }
    //Getters description
    public String getDescription() {
        return description;
    }
    //Getters hour
    public int getHour() {
        return hour;
    }
    //Getters minute
    public int getMinute() {
        return minute;
    }
    //Getter id
    public int getId() {
        return id;
    }


    //Método para exibir tarefa formatada
    public void showTasks(){
        System.out.println("Tarefa adicionada:");
        System.out.println("- Disciplina: " + task);
        System.out.println("- Descição: " + description);
        System.out.println("- Tempo estimado: " + hour + ":" + minute);
    }

}
