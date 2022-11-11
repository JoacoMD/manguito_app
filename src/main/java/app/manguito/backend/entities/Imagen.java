package app.manguito.backend.entities;

import javax.persistence.*;

@Entity
@Table(name = "imagen")
public class Imagen {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "archivo")
    private byte[] archivo;

    @Column
    private Integer height;

    @Column
    private Integer width;

    public void setId(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }
}
