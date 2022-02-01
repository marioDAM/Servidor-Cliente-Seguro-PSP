package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String email;
    private String gender;
    private boolean status;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Posts> posts;
    @OneToMany(mappedBy = "user_id", cascade = CascadeType.ALL)
    private Set<Tareas> tareas;

    public Users(int id) {
        this.id = id;
    }

}
