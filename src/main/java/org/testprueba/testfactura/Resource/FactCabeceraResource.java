package org.testprueba.testfactura.Resource;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.testprueba.testfactura.DTO.FactCabeceraDto;
import org.testprueba.testfactura.DTO.FactDetalleDto;
import org.testprueba.testfactura.Service.FactCabeceraService;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/cabecera")
public class FactCabeceraResource {
    private Logger logger = Logger.getLogger(FactCabeceraResource.class.getName());

    @Autowired
     private FactCabeceraService factCabeceraService;

    @PostMapping("/listar")
    public ResponseEntity<?> getAllCabeceras() {
        try {
            List<FactCabeceraDto> espectaculos = factCabeceraService.listarFacturas();
            return new ResponseEntity<>(espectaculos, HttpStatus.OK);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception: {0}", e.getMessage());
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<FactCabeceraDto> getFacturaPorId(@PathVariable Long id) {
        try {
            FactCabeceraDto facturaDto = factCabeceraService.buscarFacturaPorId(id);
            return new ResponseEntity<>(facturaDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/crear")
     public ResponseEntity<FactCabeceraDto> crearActualizarFactura(@RequestBody FactCabeceraDto factCabeceraDto) {
         FactCabeceraDto facturaGuardada = factCabeceraService.crearActualizarFactura(factCabeceraDto);
         return ResponseEntity.ok(facturaGuardada);
     }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<FactCabeceraDto> actualizarFactura(@PathVariable Long id, @RequestBody FactCabeceraDto factCabeceraDto) {
        try {
            FactCabeceraDto facturaActualizada = factCabeceraService.actualizarFactura(id, factCabeceraDto);
            return ResponseEntity.ok(facturaActualizada);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarFactura(@PathVariable Long id) {
        try {
            factCabeceraService.eliminarFactura(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404: Not Found
        }
    }




}
