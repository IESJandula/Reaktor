package es.reaktor.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * - Class -
 * This class is used to send the information of the computer to the Client Web  
 */
@Data
@AllArgsConstructor
public class SimpleComputerDTO
{
    /** Attribute id*/
    private String id;

    /** Attribute malwareCount*/
    private Long malwareCount;

    /** Attribute location*/
    private String location;

    /** Attribute responsable*/
    private String responsable;

    /** Attribute computerOn*/
    private Boolean computerOn;

    /** Attribute isAdmin*/
    private Boolean isAdmin;
}
