package es.reaktor.reaktor.repository;

import es.reaktor.models.Id.SoundCardId;
import es.reaktor.models.SoundCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISoundCardRepository extends JpaRepository <SoundCard, SoundCardId>
{
}
