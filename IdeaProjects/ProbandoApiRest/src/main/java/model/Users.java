package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@NamedQueries({@NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u")})

public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;
    public String name;
    public String username;
    public String email;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idAddress")
    public Address address;
    public String phone;
    public String website;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idCompany")
    public Company company;

    public Users(String name, String username, String email, String phone, String website) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.website = website;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address +
                ", phone='" + phone + '\'' +
                ", website='" + website + '\'' +
                ", company=" + company +
                '}';
    }
}