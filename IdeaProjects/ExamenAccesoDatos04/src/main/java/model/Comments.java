package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Comments {
    @Id
    private int id;
    private int post_id;
    private String name;
    private String email;
    private String body;
    @ManyToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Posts post;
}
