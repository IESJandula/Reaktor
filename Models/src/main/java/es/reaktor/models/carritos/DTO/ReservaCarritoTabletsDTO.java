package es.reaktor.models.carritos.DTO;

import es.reaktor.models.carritos.CarritoTablets;
import es.reaktor.models.carritos.Id.ReservaCarritoTabletsId;
import es.reaktor.models.carritos.Profesor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaCarritoTabletsDTO
{

    /**
     * Attribute id Reserva Carrito Tablets
     */
    private ReservaCarritoTabletsId reservaCarritoTabletsId;

    /**
     * Attribute id profesor
     */
    private Profesor idProfesor;

    /**
     * Attribute id carrito tablets
     */
    private CarritoTablets idCarritoTablets;

    /**
     * Attribute ubicacion prestamo c arrito tablets
     */
    private String ubicacionPrestamo;

}
