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
    @EmbeddedId
    private CpuId id;

    @Column(name = "cores")
    private Integer cores;

    @Column(name = "frequency")
    private Long frequency;

    @Column(name = "threads")
    private Integer threads;

    public Cpu(CpuId id, Integer cores, Long frequency, Integer threads) {
        this.id = id;
        this.cores = cores;
        this.frequency = frequency;
        this.threads = threads;
    }
}
