package es.monitoringserver.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GraphicCardDTO
{
    /** Attribute id*/
    private String id;

    /** Attribute model*/
    private String model;

}
