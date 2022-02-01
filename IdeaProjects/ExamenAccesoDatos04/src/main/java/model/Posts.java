package model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int user_id;
    private String title;
    private String body;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Users user;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Comments> comentarios;
}
