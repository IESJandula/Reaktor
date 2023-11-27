package es.reaktor.models;

import es.reaktor.models.Id.CpuId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.stereotype.Component;

/**
 * @author Neil Hdez
 *
 * Class - CPU of computer
 */
@Entity
@Table(name = "cpu")
@Data
@NoArgsConstructor
@Component
public class Cpu
{
    /** Attribute id*/
    @EmbeddedId
    private CpuId id;

    /** Attribute cores*/
    @Column(name = "cores")
    private Integer cores;

    /** Attribute frequency*/
    @Column(name = "frequency")
    private Long frequency;

    /** Attribute threads*/
    @Column(name = "threads")
    private Integer threads;

    /**
     * Constructor for create new Cpu
     * @param id
     * @param cores
     * @param frequency
     * @param threads
     */
    public Cpu(CpuId id, Integer cores, Long frequency, Integer threads) {
        this.id = id;
        this.cores = cores;
        this.frequency = frequency;
        this.threads = threads;
    }
}
