package es.reaktor.reaktor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.reaktor.models.InternetConnection;

public interface IinternetConnectionRepository extends JpaRepository<InternetConnection, Long>
{
}
