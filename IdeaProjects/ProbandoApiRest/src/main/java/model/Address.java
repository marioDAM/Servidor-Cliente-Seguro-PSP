package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@NamedQueries({@NamedQuery(name = "Address.findAll", query = "SELECT u FROM Address u")})

public class Address {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAddress;
    public String street;
    public String suite;
    public String city;
    public String zipcode;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idGeo")
    public Geo geo;

    public Long getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(Long idAddress) {
        this.idAddress = idAddress;
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", suite='" + suite + '\'' +
                ", city='" + city + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", geo=" + geo +
                '}';
    }
}