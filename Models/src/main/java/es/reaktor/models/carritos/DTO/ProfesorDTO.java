package es.reaktor.models.carritos.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfesorDTO {

    /**
     * Attribute profesor id
     */
    private Long id;

    /**
     * Attribute name of the profesor
     */
    private String nombre;

    /**
     * Attribute last name of the profesor
     */
    private String apellidos;
}
