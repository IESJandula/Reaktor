package es.monitoringserver.models;

import es.monitoringserver.models.Id.RamId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Neil Hdez
 * @version 1.0.0
 * @since 04/02/2023
 */
@Entity
@Table(name = "ram")
@Getter
@Setter
public class Ram
{
    /**
     * Attribute - id
     * @see RamId
     */
    @EmbeddedId
    private RamId id;

    /**
     * Attribute - size
     */
    @Column(name = "size")
    private Long size;

    /**
     * Attribute - Slots occupied
     */
    @Column(name = "occupied_slots")
    private String occupiedSlots;

    /**
     * Attribute - model
     */
    @Column(name = "model")
    private String model;

    /**
     * Attribute - type
     */
    @Column(name = "type")
    private String type;

    /**
     * Attribute - speed
     */
    @Column(name = "speed")
    private Long speed;


    public Ram()
    {
    }
}
