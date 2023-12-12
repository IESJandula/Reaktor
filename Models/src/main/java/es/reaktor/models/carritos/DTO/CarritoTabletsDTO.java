package es.reaktor.models.carritos.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarritoTabletsDTO
{

    /**
     * Attribute id of the tablets cart
     */
    private Long id;

    /**
     * Attribute number of tablets
     */
    private int numeroTablets;

    /**
     * Attribute number of the plant that the cart is
     */
    private int planta;
}
