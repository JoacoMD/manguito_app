package app.manguito.backend.entities;

import javax.persistence.*;

@Entity
@Table(name = "red_social")
public class RedSocial {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "icono_id")
    private Imagen icono;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Imagen getIcono() {
        return icono;
    }

    public void setIcono(Imagen icono) {
        this.icono = icono;
    }
}
