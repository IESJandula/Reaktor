package es.reaktor.reaktor.repository;

import es.reaktor.models.InternetConnection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IinternetConnectionRepository extends JpaRepository<InternetConnection, Long>
{
}
