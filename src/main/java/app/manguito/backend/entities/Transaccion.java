package app.manguito.backend.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @ManyToOne
    @JoinColumn(name = "emprendimiento_id")
    private Emprendimiento destinatario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Emprendimiento getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Emprendimiento destinatario) {
        this.destinatario = destinatario;
    }
}
