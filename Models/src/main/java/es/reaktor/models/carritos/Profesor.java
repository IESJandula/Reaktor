package es.reaktor.models.carritos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.stereotype.Service;

@Entity
@Table(name = "profesor")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Service
public class Profesor {

    @Id
    @Column(name = "IdProfesor")
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellidos;

}
