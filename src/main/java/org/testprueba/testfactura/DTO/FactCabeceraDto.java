package org.testprueba.testfactura.DTO;

import java.util.Date;
import java.util.List;

public class FactCabeceraDto {
    private Long id;
    private String ci;
    private String direccion;
    private String telefono;
    private String cliente ;
    private Date fecha;
    private String estado;
    private Double total;

    private List<FactDetalleDto> detalles;

    public FactCabeceraDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<FactDetalleDto> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<FactDetalleDto> detalles) { this.detalles = detalles; }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
