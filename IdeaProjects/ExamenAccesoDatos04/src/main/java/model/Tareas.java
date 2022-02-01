package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Tareas {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    private Users user_id;
    private String title;
    private Date due_on;
    private boolean status;

    public Tareas(int id, Users user_id, String title, boolean status) {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.status = status;
    }

}

