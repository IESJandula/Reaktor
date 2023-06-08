package es.reaktor.models.Id;

import es.reaktor.models.Motherboard;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Neil Hdez
 * @version 1.0.0
 * @since 04/02/2023
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HardDiskId implements Serializable
{

    @Serial
    private static final long serialVersionUID = -5976371824092890181L;

    private String serialNumberHardDisk;

    @OneToOne
    @JoinColumn(name = "serial_number_motherboard")
    private Motherboard motherboard;

}
