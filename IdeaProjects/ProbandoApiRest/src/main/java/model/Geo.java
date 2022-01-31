package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@NamedQueries({@NamedQuery(name = "Geo.findAll", query = "SELECT u FROM Geo u")})

public class Geo {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGeo;

    public String lat;
    public String lng;

    public Long getIdGeo() {
        return idGeo;
    }

    public void setIdGeo(Long idGeo) {
        this.idGeo = idGeo;
    }

    @Override
    public String toString() {
        return "Geo{" +
                "lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                '}';
    }
}
