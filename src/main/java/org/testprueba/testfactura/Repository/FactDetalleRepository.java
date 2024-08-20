package org.testprueba.testfactura.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.testprueba.testfactura.Entity.FactDetalle;

@Repository
public interface FactDetalleRepository extends JpaRepository<FactDetalle, Long> {
}
