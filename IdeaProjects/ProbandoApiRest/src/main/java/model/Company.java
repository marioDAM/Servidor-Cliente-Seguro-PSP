package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@NamedQueries({@NamedQuery(name = "Company.findAll", query = "SELECT u FROM Company u")})

public class Company {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCompany;

    public String name;
    public String catchPhrase;
    public String bs;

    public Long getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(Long idCompany) {
        this.idCompany = idCompany;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", catchPhrase='" + catchPhrase + '\'' +
                ", bs='" + bs + '\'' +
                '}';
    }
}