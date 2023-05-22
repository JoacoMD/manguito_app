package app.manguito.backend.entities;

import javax.persistence.*;

@Entity
@PrimaryKeyJoinColumn(name = "transaccion_id", referencedColumnName = "id")
@Table(name = "suscripcion")
public class Suscripcion extends Transaccion{

    @ManyToOne
    @JoinColumn(name = "plan_comprado_id")
    private Plan planComprado;

    public Plan getPlanComprado() {
        return planComprado;
    }

    public void setPlanComprado(Plan planComprado) {
        this.planComprado = planComprado;
    }
}
