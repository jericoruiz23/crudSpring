package org.testprueba.testfactura.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.testprueba.testfactura.Entity.FactCabecera;

import java.util.List;

@Repository
public interface FactCabeceraRepository extends JpaRepository<FactCabecera, Long> {

    List<FactCabecera> findByEstado(String estado);
}
