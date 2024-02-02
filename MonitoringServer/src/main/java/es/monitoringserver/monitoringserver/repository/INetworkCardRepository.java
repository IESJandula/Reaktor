package es.monitoringserver.monitoringserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.monitoringserver.models.NetworkCard;
import es.monitoringserver.models.Id.NetworkCardId;

public interface INetworkCardRepository extends JpaRepository<NetworkCard, NetworkCardId>
{
}
