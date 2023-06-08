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
    private String id;

    private Long size;

    private Character letter;

    private String operatingSystem;
}
