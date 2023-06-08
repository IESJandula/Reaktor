package es.reaktor.models.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MotherboardDTO
{
    private String serialNumber;

    /**
     * Attribute - model of motherboard
     */
    private String model;

    /**
     * Attribute - classroom of motherboard
     */
    private String classroom;

    /**
     * Attribute - description of motherboard
     */
    private String description;

    /**
     * Attribute - professor of motherboard
     */
    private String professor;

    /**
     * Attribute - last connection of motherboard
     */
    private Date lastConnection;

    /**
     * Attribute - last update computer on
     */
    private Date lastUpdateComputerOn;

    /**
     * Attribute - computer on
     */
    private Boolean computerOn;

}
