package es.reaktor.models.carritos.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarritoPcDTO
{

    /**
     * Attribute id of the Pc Cart
     */
    private Long id;

    /**
     * Attribute Number o pc's
     */
    private int numeroPcs;

    /**
     * Attribute where the cart is
     */
    private int planta;

    /**
     * Attribute name of the SO
     */
    private String sistemaOperativo;
}
