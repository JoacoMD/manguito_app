package app.manguito.backend.entities;

import javax.persistence.*;

@Entity
@Table(name = "imagen")
public class Imagen {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "filename")
    private String filename;

    @Column
    private String type;

    public Imagen(String filename, String type) {
        this.filename = filename;
        this.type = type;
    }

    public Imagen() {}

    public void setId(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String extension) {
        this.type = extension;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
