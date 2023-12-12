package es.reaktor.models.carritos.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AulasInformaticaDTO {

    /**
     * Attribute id of AulaInformatica
     */
    private Long id;

    /**
     * Attribute number of the Aula
     */
    private int numeroAula;

    /**
     * Attribute plant of the Aula
     */
    private int planta;

}
