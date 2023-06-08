package es.reaktor.models.DTO;

import es.reaktor.models.Id.RamId;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RamDTO
{
    private String id;

    /**
     * Attribute - size
     */
    private Long size;

    /**
     * Attribute - Slots occupied
     */
    private String occupiedSlots;

    /**
     * Attribute - model
     */
    private String model;

    /**
     * Attribute - type
     */
    private String type;

    /**
     * Attribute - speed
     */
    private Long speed;

}
