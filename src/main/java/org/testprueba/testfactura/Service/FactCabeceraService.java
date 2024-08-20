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

import java.util.List;

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
        List<FactCabecera> facturas = factCabeceraRepository.findAll();
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

        // Iterar sobre los detalles y asignar la cabecera antes de guardar la cabecera
        for (FactDetalleDto detalleDto : factCabeceraDto.getDetalles()) {
            FactDetalle detalle = factDetallesMapper.toEntity(detalleDto);
            detalle.setCabecera(factCabecera);  // Asignar la cabecera a cada detalle
            factCabecera.getDetalles().add(detalle);  // Agregar el detalle a la cabecera
        }

        // Guardar la cabecera y los detalles de una vez
        FactCabecera cabeceraGuardada = factCabeceraRepository.save(factCabecera);

        // Convertir la cabecera guardada a DTO para la respuesta
        return factCabeceraMapper.toDto(cabeceraGuardada);
    }

    @Transactional
    public FactCabeceraDto actualizarFactura(Long id, FactCabeceraDto factCabeceraDto) {
        // Buscar la factura por su ID
        FactCabecera facturaExistente = factCabeceraRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Factura no encontrada con id: " + id));

        // Actualizar los campos de la entidad con los datos del DTO
        facturaExistente.setCi(factCabeceraDto.getCi());
        facturaExistente.setDireccion(factCabeceraDto.getDireccion());
        facturaExistente.setTelefono(factCabeceraDto.getTelefono());
        facturaExistente.setCliente(factCabeceraDto.getCliente());
        facturaExistente.setFecha(factCabeceraDto.getFecha());

        // Actualizar detalles (opcional, dependiendo de la lógica de tu aplicación)
        facturaExistente.getDetalles().clear();  // Limpiar los detalles existentes
        for (FactDetalleDto detalleDto : factCabeceraDto.getDetalles()) {
            FactDetalle detalle = factDetallesMapper.toEntity(detalleDto);
            detalle.setCabecera(facturaExistente);  // Asignar la cabecera a cada detalle
            facturaExistente.getDetalles().add(detalle);  // Agregar el detalle a la cabecera
        }

        // Guardar la entidad actualizada en la base de datos
        FactCabecera facturaActualizada = factCabeceraRepository.save(facturaExistente);

        // Convertir la entidad actualizada a DTO y retornarla
        return factCabeceraMapper.toDto(facturaActualizada);
    }


    public void eliminarFactura(Long id) {
        // Verificar si la factura existe antes de eliminarla
        factCabeceraRepository.deleteById(id);
        System.out.println("Eliminado correctamente el " + id);
    }

}





