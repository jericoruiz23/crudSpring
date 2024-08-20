package org.testprueba.testfactura.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.testprueba.testfactura.Entity.FactCabecera;

@Repository
public interface FactCabeceraRepository extends JpaRepository<FactCabecera, Long> {
}
