package es.reaktor.models;

import es.reaktor.models.Id.GraphicCardId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

/**
 * @author Neil Hdez
 * @version 1.0.0
 * @since 03/02/2023
 */
@Entity
@Table(name = "graphic_card")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GraphicCard
{
    @EmbeddedId
    private GraphicCardId id;

    @Column(name = "model")
    private String model;

}
