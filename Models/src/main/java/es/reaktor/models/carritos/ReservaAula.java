package es.reaktor.models.carritos;

import es.reaktor.models.carritos.Id.ReservaAulaId;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Getter
@Entity
@Table(name = "reserva_aula")
@NoArgsConstructor
@Component
public class ReservaAula
{

    @EmbeddedId
    private ReservaAulaId reservaAulaId;

    @ManyToOne
    @JoinColumn(name = "id_profesor")
    @MapsId("idProfesor")
    private Profesor idProfesor;

    public void setReservaAulaId(ReservaAulaId reservaAulaId)
    {
        this.reservaAulaId = reservaAulaId;
    }

    public void setIdProfesor(Profesor idProfesor)
    {
        this.idProfesor = idProfesor;
    }

}
