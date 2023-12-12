package es.reaktor.models.carritos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.stereotype.Service;

@Entity
@Table(name = "aulas_informatica")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Service
public class AulasInformatica {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(length = 3, nullable = false)
    private int numeroAula;

    @Column(length = 1, nullable = false)
    private int planta;

}
