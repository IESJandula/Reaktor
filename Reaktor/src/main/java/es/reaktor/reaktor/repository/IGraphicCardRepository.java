package es.reaktor.reaktor.repository;

import es.reaktor.models.GraphicCard;
import es.reaktor.models.Id.GraphicCardId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGraphicCardRepository extends JpaRepository<GraphicCard, GraphicCardId>
{
}
