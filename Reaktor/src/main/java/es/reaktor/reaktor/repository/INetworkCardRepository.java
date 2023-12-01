package es.reaktor.reaktor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.reaktor.models.NetworkCard;
import es.reaktor.models.Id.NetworkCardId;

public interface INetworkCardRepository extends JpaRepository<NetworkCard, NetworkCardId>
{
}
