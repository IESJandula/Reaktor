package es.reaktor.models.Id;

import es.reaktor.models.Motherboard;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Neil Hdez
 */
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CpuId implements Serializable
{

    @Serial
    private static final long serialVersionUID = -8958993707778577542L;

    private String idCpu;

    @OneToOne
    @JoinColumn(name = "serial_number_motherboard")
    private Motherboard motherboard;

    public String getIdCpu()
    {
        return this.idCpu;
    }

    public Motherboard getMotherboard() {
        return motherboard;
    }

    public void setMotherboard(Motherboard motherboard) {
        this.motherboard = motherboard;
    }
}
