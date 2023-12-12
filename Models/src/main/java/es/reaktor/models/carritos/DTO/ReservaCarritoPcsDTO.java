package es.reaktor.models.carritos.DTO;

import es.reaktor.models.carritos.CarritoPc;
import es.reaktor.models.carritos.Id.ReservaCarritoPcsId;
import es.reaktor.models.carritos.Profesor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaCarritoPcsDTO
{

    /**
     * Attribute id of the reserva Carrito Pc
     */
    private ReservaCarritoPcsId reservaCarrtitoPcsId;

    /**
     * Attribute id of the profesor
     */
    private Profesor idProfesor;

    /**
     * Attribute id of the carrito pcs
     */
    private CarritoPc idCarritoPcs;

    /**
     * Attribute ubication of the prestamo
     */
    private String ubicacionPrestamo;
}
