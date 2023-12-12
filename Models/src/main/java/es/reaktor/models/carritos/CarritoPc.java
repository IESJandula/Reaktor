package es.reaktor.models.carritos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.stereotype.Service;

@Entity
@Table(name = "carrito_pc")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Service
public class CarritoPc
{
    @Id
    @Column(name = "IdCarritoPc")
    private Long id;

    @Column(length = 3, nullable = false)
    private int numeroPcs;

    @Column(length = 1, nullable = false)
    private int planta;

    @Column(length = 50, nullable = false)
    private String sistemaOperativo;
}
