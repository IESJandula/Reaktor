package es.reaktor.reaktor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.reaktor.models.Motherboard;

public interface IMotherboardRepository extends JpaRepository<Motherboard, String>
{
    Motherboard findByMotherBoardSerialNumber(String motherBoardSerialNumber);


}
