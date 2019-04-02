package yevhen;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@XmlRootElement(name="tasks")
public class TasksList implements Serializable {

    private List<Task> tasks = new ArrayList<>();

    @XmlElement(name="task")
    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public Map<String, Integer> getMap(){
        return tasks.stream()
                    .collect(Collectors.toMap(Task::getName,
                                              Task::getMemory,
                                              (memory1,memory2)->memory1+memory2));
    }

    @Override
    public String toString() {
        return "TasksList{" +
                "tasks=" + tasks +
                '}';
    }

}
