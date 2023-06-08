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
    private String id;

    private Long malwareCount;

    private String location;

    private String responsable;

    private Boolean computerOn;

    private Boolean isAdmin;
}
