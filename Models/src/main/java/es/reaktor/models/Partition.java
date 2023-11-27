package es.reaktor.models;

import es.reaktor.models.Id.PartitionId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Neil Hdez
 * @version 1.0.0
 * @since 03/02/2023
 */
@Entity
@Table(name = "partition_hard_disk")
@Getter
@Setter
public class Partition
{

    /** Attribute id*/
    @EmbeddedId
    private PartitionId id;

    /** Attribute size*/
    @Column(name = "size")
    private Long size;

    /** Attribute letter*/
    @Column(name = "letter")
    private Character letter;

    /** Attribute operatingSystem*/
    @Column(name = "operating_system")
    private String operatingSystem;

    /**
     * Constructor for create new Partition
     */
    public Partition()
    {

    }
}
