package es.reaktor.reaktor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.reaktor.models.GraphicCard;
import es.reaktor.models.Id.GraphicCardId;

public interface IGraphicCardRepository extends JpaRepository<GraphicCard, GraphicCardId>
{
}
