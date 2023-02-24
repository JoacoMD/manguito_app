package app.manguito.backend.entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "emprendimiento")
public class Emprendimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombreEmprendimiento;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "imagen_perfil_id")
    private Imagen imagenPerfil;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "imagen_banner_id")
    private Imagen banner;

    @Column(name = "url")
    private String url;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "precio_manguito")
    private Double precioManguito;

    @Column(name = "mostrar_top_donadores")
    private Boolean mostrarTopDonadores = true;

    @Column(name = "ocultar_manguitos_recibidos")
    private Boolean ocultarManguitosRecibidos = false;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "emprendimiento_categoria",
            joinColumns = @JoinColumn(name="emprendimiento_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id", referencedColumnName = "id")
    )
    private List<Categoria> categorias;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.MERGE, orphanRemoval = true)
    @JoinColumn(name = "emprendimiento_id")
    private List<RedSocialEmprendimiento> redesSociales;

    @OneToMany(mappedBy = "emprendimiento", cascade = CascadeType.ALL)
    private List<Plan> planes;

//    @OneToMany(mappedBy = "emprendimiento", cascade = CascadeType.ALL)
//    private List<Post> posts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreEmprendimiento() {
        return nombreEmprendimiento;
    }

    public void setNombreEmprendimiento(String nombreEmprendimiento) {
        this.nombreEmprendimiento = nombreEmprendimiento;
    }

    public Imagen getImagenPerfil() {
        return imagenPerfil;
    }

    public void setImagenPerfil(Imagen imagenPerfil) {
        this.imagenPerfil = imagenPerfil;
    }

    public Imagen getBanner() {
        return banner;
    }

    public void setBanner(Imagen banner) {
        this.banner = banner;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecioManguito() {
        return precioManguito;
    }

    public void setPrecioManguito(Double precioManguito) {
        this.precioManguito = precioManguito;
    }

    public Boolean getMostrarTopDonadores() {
        return mostrarTopDonadores;
    }

    public void setMostrarTopDonadores(Boolean mostrarTopDonadores) {
        this.mostrarTopDonadores = mostrarTopDonadores;
    }

    public Boolean getOcultarManguitosRecibidos() {
        return ocultarManguitosRecibidos;
    }

    public void setOcultarManguitosRecibidos(Boolean ocultarManguitosRecibidos) {
        this.ocultarManguitosRecibidos = ocultarManguitosRecibidos;
    }

    public List<Categoria> getCategorias() {
        if (this.categorias == null) {
            this.categorias = new ArrayList<>();
        }
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public List<Plan> getPlanes() {
        if (this.planes == null) {
            this.planes = new ArrayList<>();
        }
        return planes;
    }

    public void setPlanes(List<Plan> planes) {
        this.planes = planes;
    }

    public List<RedSocialEmprendimiento> getRedesSociales() {
        if (this.redesSociales == null) {
            this.redesSociales = new ArrayList<>();
        }
        return redesSociales;
    }

    public void setRedesSociales(List<RedSocialEmprendimiento> redesSociales) {
        this.redesSociales = redesSociales;
    }

//    public List<Post> getPosts() {
//        if (this.posts == null) {
//            this.posts = new ArrayList<>();
//        }
//        return posts;
//    }
}
