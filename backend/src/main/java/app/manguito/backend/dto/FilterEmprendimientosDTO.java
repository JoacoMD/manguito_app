package app.manguito.backend.dto;

import app.manguito.backend.entities.Categoria;
import app.manguito.backend.entities.Emprendimiento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class FilterEmprendimientosDTO {

    private String nombre;
    private List<String> categorias;
    private Integer page;

    public Integer getPage() {
        return page == null ? 0 : this.page;
    }

    public Predicate[] buildPredicates(CriteriaBuilder builder, Root<Emprendimiento> root) {
        List<Predicate> predicates = new ArrayList<>();
        Join<Emprendimiento, Categoria> joinCate = root.join("categorias");

        if (StringUtils.isNotEmpty(getNombre())) {
            predicates.add(builder.like(builder.lower(root.get("nombreEmprendimiento")), "%" + getNombre().toLowerCase() + "%"));
        }

        if (getCategorias() != null && getCategorias().size() > 0) {
            predicates.add(joinCate.get("nombre").in(getCategorias()));
        }

        return predicates.toArray(new Predicate[0]);
    }

}
