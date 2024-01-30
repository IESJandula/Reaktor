package es.monitoringserver.monitoringserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.monitoringserver.models.SoundCard;
import es.monitoringserver.models.Id.SoundCardId;

public interface ISoundCardRepository extends JpaRepository <SoundCard, SoundCardId>
{
}
