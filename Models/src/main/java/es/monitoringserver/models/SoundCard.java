package es.monitoringserver.models;

import es.monitoringserver.models.Id.SoundCardId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sound_card")
@Getter
@Setter
public class SoundCard
{

    /** Attribute id*/
    @EmbeddedId
    private SoundCardId id;

    /** Attribute model*/
    @Column(name = "model")
    private String model;

    /** Attribute driver*/
    @Column(name = "driver")
    private String driver;

    /**
     * Constructor for create new SoundCard
     */
    public SoundCard()
    {

    }

}
