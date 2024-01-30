package es.reaktor.reaktor.repository;

import es.reaktor.models.Id.InternetConnectionNetworkCardId;
import es.reaktor.models.InternetConnectionNetworkCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IinternetConnectionNetworkCardRepository extends JpaRepository<InternetConnectionNetworkCard, InternetConnectionNetworkCardId>
{
}
