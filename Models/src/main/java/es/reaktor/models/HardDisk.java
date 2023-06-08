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

    @EmbeddedId
    private HardDiskId id;

    @Column(name = "size")
    private Long size;

    @Column(name = "model")
    private String model;

    public HardDisk()
    {
    }
}
