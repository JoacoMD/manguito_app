package app.manguito.backend.entities;

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

    @Column(name = "imagen_perfil")
    private String imagenPerfil;

    @Column(name = "imagen_banner")
    private String banner;

    @Column(name = "url")
    private String url;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "precio_manguito")
    private Double precioManguito;

    @Column(name = "mostrar_top_donadores")
    private Boolean mostrarTopDonadores;

    @Column(name = "ocultar_manguitos_recibidos")
    private Boolean ocultarManguitosRecibidos;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "emprendimiento_categoria",
            joinColumns = @JoinColumn(name="emprendimiento_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id", referencedColumnName = "id")
    )
    private List<Categoria> categorias;

    @OneToMany(mappedBy = "emprendimiento", cascade = CascadeType.ALL)
    private List<Plan> planes;

    @OneToMany(mappedBy = "emprendimiento", cascade = CascadeType.ALL)
    private List<RedSocialEmprendimiento> redesSociales;

    @OneToMany(mappedBy = "emprendimiento", cascade = CascadeType.ALL)
    private List<Post> posts;

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

    public String getImagenPerfil() {
        return imagenPerfil;
    }

    public void setImagenPerfil(String imagenPerfil) {
        this.imagenPerfil = imagenPerfil;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
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

    public List<Post> getPosts() {
        if (this.posts == null) {
            this.posts = new ArrayList<>();
        }
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
