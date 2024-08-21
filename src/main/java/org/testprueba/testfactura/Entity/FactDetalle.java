package org.testprueba.testfactura.Entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(schema = "public", name = "factDetalle")
public class FactDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private Integer cantidad;
    private Date fechaRegistra;
    private BigDecimal precio;
    private String producto;
    private Double subtotal;


    @ManyToOne
    @JoinColumn(name = "id_cab", referencedColumnName = "id")
    private FactCabecera cabecera;

    // Constructor por defecto
    public FactDetalle() {
    }

    // Getters y Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Date getFechaRegistra() {
        return fechaRegistra;
    }

    public void setFechaRegistra(Date fechaRegistra) {
        this.fechaRegistra = fechaRegistra;
    }

    public FactCabecera getCabecera() {
        return cabecera;
    }

    public void setCabecera(FactCabecera cabecera) {
        this.cabecera = cabecera;
    }
}
