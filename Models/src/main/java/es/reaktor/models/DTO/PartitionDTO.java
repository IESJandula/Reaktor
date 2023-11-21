package es.reaktor.models.DTO;

import es.reaktor.models.Id.PartitionId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartitionDTO
{
    /** Attribute id*/
    private String id;

    /** Attribute size*/
    private Long size;

    /** Attribute letter*/
    private Character letter;

    /** Attribute operatingSystem*/
    private String operatingSystem;
}
