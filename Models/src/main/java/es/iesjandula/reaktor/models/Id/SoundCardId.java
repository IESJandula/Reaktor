package es.iesjandula.reaktor.models.Id;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

import es.iesjandula.reaktor.models.Motherboard;

/**
 * @author Neil Hdez
 * @version 1.0.0
 * @since 04/02/2023
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SoundCardId implements Serializable
{

    /** Attribute serialVersionUID*/
    @Serial
    private static final long serialVersionUID = 3622702765595396409L;

    /** Attribute idSoundCard*/
    private String idSoundCard;

    /** Attribute motherboard*/
    @OneToOne
    @JoinColumn(name = "serial_number_motherboard")
    private Motherboard motherboard;

}
