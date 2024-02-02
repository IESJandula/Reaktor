package es.monitoringserver.monitoringserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.monitoringserver.models.InternetConnection;

public interface IinternetConnectionRepository extends JpaRepository<InternetConnection, Long>
{
}
