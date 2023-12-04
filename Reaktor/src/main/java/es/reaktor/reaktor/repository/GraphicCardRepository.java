package es.reaktor.reaktor.repository;

import es.reaktor.models.GraphicCard;
import es.reaktor.models.Id.GraphicCardId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GraphicCardRepository extends JpaRepository<GraphicCard, GraphicCardId>
{
}
