package es.reaktor.models.carritos.DTO;

import es.reaktor.models.carritos.Id.ReservaAulaId;
import es.reaktor.models.carritos.Profesor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaAulaDTO
{

    /**
     * Attribute id of the reserva aula
     */
    private ReservaAulaId reservaAulaId;

    /**
     * Attribute id of the profesor
     */
    private Profesor idProfesor;

}
