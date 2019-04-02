package yevhen;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class Task implements Serializable {

    private String name;
    private int memory;

    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public int getMemory() {
        return memory;
    }

    @XmlElement
    public void setMemory(int memory) {
        this.memory = memory;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", memory=" + memory +
                '}';
    }

}
