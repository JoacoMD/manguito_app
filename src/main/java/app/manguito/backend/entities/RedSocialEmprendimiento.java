package app.manguito.backend.entities;


import javax.persistence.*;

@Entity
@Table(name = "red_social_emprendimiento")
public class RedSocialEmprendimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "url")
    private String url;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "red_social_id")
    private RedSocial redSocial;

    @ManyToOne
    @JoinColumn(name = "emprendimiento_id")
    private Emprendimiento emprendimiento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public RedSocial getRedSocial() {
        return redSocial;
    }

    public void setRedSocial(RedSocial redSocial) {
        this.redSocial = redSocial;
    }

    public Emprendimiento getEmprendimiento() {
        return emprendimiento;
    }

    public void setEmprendimiento(Emprendimiento emprendimiento) {
        this.emprendimiento = emprendimiento;
    }
}
