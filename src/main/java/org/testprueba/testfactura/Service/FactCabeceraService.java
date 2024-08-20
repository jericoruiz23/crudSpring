package org.testprueba.testfactura.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.testprueba.testfactura.DTO.FactCabeceraDto;
import org.testprueba.testfactura.DTO.FactDetalleDto;
import org.testprueba.testfactura.Entity.FactCabecera;
import org.testprueba.testfactura.Entity.FactDetalle;
import org.testprueba.testfactura.Mappers.FactCabeceraMapper;
import org.testprueba.testfactura.Mappers.FactDetallesMapper;
import org.testprueba.testfactura.Repository.FactCabeceraRepository;
import org.testprueba.testfactura.Repository.FactDetalleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service

public class FactCabeceraService {

    @Autowired
    private FactCabeceraRepository factCabeceraRepository;
    @Autowired
    private FactCabeceraMapper factCabeceraMapper;
    @Autowired
    private FactDetalleRepository factDetalleRepository;
    @Autowired
    private FactDetallesMapper factDetallesMapper;


    public List<FactCabeceraDto> listarFacturas() {
        String estado = "ACTIVO";
        List<FactCabecera> facturas = factCabeceraRepository.findByEstado(estado);

        return factCabeceraMapper.toDtoList(facturas);
    }

    public FactCabeceraDto buscarFacturaPorId(Long id) {
        // Buscar la factura por su ID
        FactCabecera factura = factCabeceraRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Factura no encontrada con id: " + id));

        // Convertir la entidad a DTO
        return factCabeceraMapper.toDto(factura);
    }

    @Transactional  // Asegura que se haga en una sola transacción
    public FactCabeceraDto crearActualizarFactura(FactCabeceraDto factCabeceraDto) {
        // Convertir DTO a entidad
        FactCabecera factCabecera = factCabeceraMapper.toEntity(factCabeceraDto);
        // Inicializar el total de la cabecera
        Double total = 0.0;

        for (FactDetalle detalle : factCabecera.getDetalles()) {
            // Calcular el subtotal como cantidad * precio
            detalle.setSubtotal(detalle.getCantidad() * detalle.getPrecio().doubleValue());

            // Sumar el subtotal al total de la cabecera
            total += detalle.getSubtotal();

            // Asignar la cabecera al detalle
            detalle.setCabecera(factCabecera);
        }

        // Asignar el total calculado a la cabecera
        factCabecera.setTotal(total);

        // Guardar la cabecera y los detalles de una vez (Hibernate gestionará los detalles)
        FactCabecera cabeceraGuardada = factCabeceraRepository.save(factCabecera);

        // Convertir la cabecera guardada a DTO para la respuesta
        return factCabeceraMapper.toDto(cabeceraGuardada);
    }


    @Transactional
    public FactCabeceraDto actualizarFactura(Long id, FactCabeceraDto factCabeceraDto) {
        // Buscar la factura por su ID
        FactCabecera facturaExistente = factCabeceraRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Factura no encontrada con id: " + id));

        // Actualizar los campos de la cabecera
        facturaExistente.setCi(factCabeceraDto.getCi());
        facturaExistente.setDireccion(factCabeceraDto.getDireccion());
        facturaExistente.setTelefono(factCabeceraDto.getTelefono());
        facturaExistente.setCliente(factCabeceraDto.getCliente());
        facturaExistente.setFecha(factCabeceraDto.getFecha());

        // Crear un mapa de los detalles existentes para actualizarlos o eliminarlos según sea necesario
        Map<Long, FactDetalle> detallesExistentesMap = facturaExistente.getDetalles().stream()
                .collect(Collectors.toMap(FactDetalle::getId, Function.identity()));

        // Crear una lista de nuevos detalles que se agregarán
        List<FactDetalle> nuevosDetalles = new ArrayList<>();

        // Iterar sobre los detalles del DTO
        for (FactDetalleDto detalleDto : factCabeceraDto.getDetalles()) {
            if (detalleDto.getId() != null && detallesExistentesMap.containsKey(detalleDto.getId())) {
                // Actualizar el detalle existente
                FactDetalle detalleExistente = detallesExistentesMap.get(detalleDto.getId());
                detalleExistente.setCantidad(detalleDto.getCantidad());
                detalleExistente.setProducto(detalleDto.getProducto());
                detalleExistente.setPrecio(detalleDto.getPrecio());
                detalleExistente.setFechaRegistra(detalleDto.getFechaRegistra());
                detalleExistente.setSubtotal(detalleDto.getCantidad() * detalleDto.getPrecio().doubleValue());
                detallesExistentesMap.remove(detalleDto.getId());  // Remover el detalle del mapa (ya está procesado)
            } else {
                // Crear un nuevo detalle
                FactDetalle nuevoDetalle = factDetallesMapper.toEntity(detalleDto);
                nuevoDetalle.setCabecera(facturaExistente);
                nuevoDetalle.setSubtotal(nuevoDetalle.getCantidad() * nuevoDetalle.getPrecio().doubleValue());
                nuevosDetalles.add(nuevoDetalle);  // Agregar el nuevo detalle a la lista
            }
        }

        // Eliminar los detalles que no están en el DTO (detalles huérfanos)
        facturaExistente.getDetalles().removeIf(detalle -> detallesExistentesMap.containsKey(detalle.getId()));

        // Agregar los nuevos detalles a la cabecera
        facturaExistente.getDetalles().addAll(nuevosDetalles);

        // Calcular el nuevo total de la cabecera
        Double total = facturaExistente.getDetalles().stream()
                .mapToDouble(FactDetalle::getSubtotal)
                .sum();
        facturaExistente.setTotal(total);

        // Guardar la cabecera actualizada en la base de datos
        FactCabecera facturaActualizada = factCabeceraRepository.save(facturaExistente);

        return factCabeceraMapper.toDto(facturaActualizada);
    }



    @Transactional  // Asegura que se haga en una sola transacción
    public FactCabeceraDto cambiarEstadoFactura(Long id) {
        // Buscar la cabecera en el repositorio
        FactCabecera factCabecera = factCabeceraRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Factura no encontrada con id: " + id));

        // Verificar el estado actual
        if ("ACTIVO".equals(factCabecera.getEstado())) {
            // Cambiar el estado a "INACTIVO"
            factCabecera.setEstado("INACTIVO");

            // Guardar la entidad actualizada en la base de datos
            FactCabecera cabeceraGuardada = factCabeceraRepository.save(factCabecera);

            // Retornar la cabecera guardada como DTO
            return factCabeceraMapper.toDto(cabeceraGuardada);
        } else {
            throw new IllegalStateException("La factura ya está inactiva o en un estado diferente");
        }
    }


    public FactCabeceraDto consultarFactById(Long id) {
        if (id == null) {
            return null; // Si el ID es nulo, retornar null
        }
        try {
            FactCabecera cabecera = factCabeceraRepository.findById(id).orElse(null); // Manejar la ausencia del registro con 'orElse(null)'
            if (cabecera == null) {
                return null; // Si no se encuentra, retornar null
            } else {
                FactCabeceraDto cabeceraDto = factCabeceraMapper.toDto(cabecera);
                return cabeceraDto; // Retornar el DTO
            }
        } catch (Exception e) {
            return null;
        }
    }

}





