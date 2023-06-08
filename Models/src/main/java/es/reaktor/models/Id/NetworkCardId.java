package es.reaktor.models.Id;

import es.reaktor.models.Motherboard;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NetworkCardId implements Serializable
{
    @Serial
    private static final long serialVersionUID = 2064496915866351678L;

    private String idNetworkCard;

    @OneToOne
    @JoinColumn(name = "serial_number_motherboard")
    private Motherboard motherboard;


}
