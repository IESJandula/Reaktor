package es.monitoringserver.monitoringserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.monitoringserver.models.InternetConnectionNetworkCard;
import es.monitoringserver.models.Id.InternetConnectionNetworkCardId;

public interface IinternetConnectionNetworkCardRepository extends JpaRepository<InternetConnectionNetworkCard, InternetConnectionNetworkCardId>
{
}
