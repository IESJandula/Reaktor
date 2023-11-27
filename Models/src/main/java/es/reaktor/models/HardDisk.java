package es.reaktor.models;

import es.reaktor.models.Id.HardDiskId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Neil Hdez
 * @version 1.0.0
 * @since 04/02/2023
 */
@Entity
@Table(name = "hard_disk")
@Getter
@Setter
public class HardDisk
{

    /** Attribute id*/
    @EmbeddedId
    private HardDiskId id;

    /** Attribute size*/
    @Column(name = "size")
    private Long size;

    /** Attribute model*/
    @Column(name = "model")
    private String model;

    /**
     * Constructor for create new HardDisk
     */
    public HardDisk()
    {
    }
}
