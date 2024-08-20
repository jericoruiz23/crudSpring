package org.testprueba.testfactura.Entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(schema = "public", name = "factCabecera")
public class FactCabecera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String ci;
    private String direccion;
    private String telefono;
    private String cliente ;
    private Date fecha;

    @OneToMany(mappedBy = "cabecera", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FactDetalle> detalles;

    public FactCabecera() {
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

    public List<FactDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<FactDetalle> detalles) {
        this.detalles = detalles;
    }
}
