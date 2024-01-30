package es.monitoringserver.monitoringserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.monitoringserver.models.GraphicCard;
import es.monitoringserver.models.Id.GraphicCardId;

public interface IGraphicCardRepository extends JpaRepository<GraphicCard, GraphicCardId>
{
}
