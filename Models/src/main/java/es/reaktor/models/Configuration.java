package es.reaktor.models;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * - CLASS -
 * This class is used to create the configuration of the classroom
 */
@Data
@AllArgsConstructor
public class Configuration
{

    /**
     * - ATTRIBUTES -
     * This attributes are used to create the configuration of the classroom
     */
    private String classroom;

    /**
     * - ATTRIBUTES -
     * This attributes are used to create the configuration of the teacher
     */
    private String teacher;

    /**
     * - ATTRIBUTES -
     * This attributes are used to create the configuration of the trolley
     */
    private String trolley;
    
    /** Attribute andaluciaId*/
    private String andaluciaId;
    
    /** Attribute computerNumber*/
    private String computerNumber;
    
    /** Attribute computerSerialNumber*/
    private String computerSerialNumber;

    /**
     * - ATTRIBUTES -
     * This attributes are used to create the configuration of the admin
     */
    private Boolean isAdmin;
    
    /**
     * - CONSTRUCTOR BY DEFAULT -
     * This constructor is used to create the configuration of the classroom by default
     */
    public Configuration()
    {
    }
}
