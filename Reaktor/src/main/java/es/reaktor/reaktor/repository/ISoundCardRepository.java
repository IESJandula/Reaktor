package es.reaktor.reaktor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.reaktor.models.SoundCard;
import es.reaktor.models.Id.SoundCardId;

public interface ISoundCardRepository extends JpaRepository <SoundCard, SoundCardId>
{
}
