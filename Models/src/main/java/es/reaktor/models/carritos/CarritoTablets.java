package es.reaktor.models.carritos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.stereotype.Service;

@Entity
@Table(name = "carrito_tablets")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Service
public class CarritoTablets
{
    @Id
    @Column(name = "IdCarritoTablets")
    private Long id;

    @Column(length = 3, nullable = false)
    private int numeroTablets;

    @Column(length = 1, nullable = false)
    private int planta;

}
