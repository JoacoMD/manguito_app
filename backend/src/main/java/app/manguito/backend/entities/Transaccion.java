package app.manguito.backend.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "transaccion")
public abstract class Transaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @Column(name = "mensaje")
    private String mensaje;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "contacto")
    private String contacto;

    @Column(name = "monto")
    private Double monto;

    @Column(name = "estado")
    private String estado;

    @ManyToOne
    @JoinColumn(name = "emprendimiento_id")
    private Emprendimiento destinatario;
}
