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

    @EmbeddedId
    private PartitionId id;

    @Column(name = "size")
    private Long size;

    @Column(name = "letter")
    private Character letter;

    @Column(name = "operating_system")
    private String operatingSystem;

    public Partition()
    {

    }
}
