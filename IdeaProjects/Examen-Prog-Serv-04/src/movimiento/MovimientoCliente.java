import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoCliente implements Serializable {

    private int UUIDron;
    private String UUID;
    private Instant produccion;
    int nDosYemas;

    public MovimientoCliente(int UUIDron) {
        this.UUIDron = UUIDron++;
        this.UUID = String.valueOf(Math.random());
        this.produccion = Instant.now();
        this.nDosYemas = numeroHuevosDosYemas();

    }

    public int numeroHuevosDosYemas() {
        int huevos = 6;
        int huevosDosYemas = huevos * 30 / 100;
        return huevosDosYemas;
    }

    @Override
    public String toString() {
        return "MovimientoCliente{" +
                "UUIDron=" + UUIDron +
                ", UUID='" + UUID + '\'' +
                ", produccion=" + produccion +
                ", huevos de dos yemas=" + nDosYemas +
                '}';
    }
}
