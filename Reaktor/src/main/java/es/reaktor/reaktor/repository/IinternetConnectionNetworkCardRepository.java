package es.reaktor.reaktor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.reaktor.models.InternetConnectionNetworkCard;
import es.reaktor.models.Id.InternetConnectionNetworkCardId;

public interface IinternetConnectionNetworkCardRepository extends JpaRepository<InternetConnectionNetworkCard, InternetConnectionNetworkCardId>
{
}
