package es.reaktor.reaktor.repository;

import es.reaktor.models.Id.NetworkCardId;
import es.reaktor.models.NetworkCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface INetworkCardRepository extends JpaRepository<NetworkCard, NetworkCardId>
{
}
