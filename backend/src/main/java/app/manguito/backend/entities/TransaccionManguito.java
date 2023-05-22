package app.manguito.backend.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@PrimaryKeyJoinColumn(name = "transaccion_id", referencedColumnName = "id")
@Table(name = "transaccion_manguito")
public class TransaccionManguito extends Transaccion{

    @Column(name = "cantidad")
    private Integer cantidad;

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
