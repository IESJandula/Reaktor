package es.iesjandula.reaktor.models.Id;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

import es.iesjandula.reaktor.models.HardDisk;

/**
 * @author Neil Hdez
 * @version 1.0.0
 * @since 03/02/2023
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartitionId implements Serializable
{

    /** Attribute serialVersionUID*/
    @Serial
    private static final long serialVersionUID = 2841246474444094495L;

    /** Attribute idPartition*/
    private String idPartition;

    /** Attribute hardDisk*/
    @ManyToOne
    private HardDisk hardDisk;

}
