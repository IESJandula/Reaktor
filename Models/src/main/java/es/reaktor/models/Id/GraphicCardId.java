package es.reaktor.models.Id;

import es.reaktor.models.Motherboard;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Neil Hdez
 * @version 1.0.0
 * @since 03/02/2023
 */
@Embeddable
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GraphicCardId implements Serializable
{

    @Serial
    private static final long serialVersionUID = 8701481126108693094L;

    private String idGraphicCard;

    @OneToOne
    @JoinColumn(name = "serial_number_motherboard")
    private Motherboard motherboard;

}
