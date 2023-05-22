package br.com.mecnet.mecnet.modules.search.Dtos;

import br.com.mecnet.mecnet.modules.catalog.Entity.CatalogProduct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchProductCatalogDto {
    private String name;
    private String brand;
    private String manufacturer;


    public Specification<CatalogProduct> toSpac() {

       return (root, query, builder)->{
           List<Predicate> predicate = new ArrayList<>();
            if(StringUtils.hasText(name)){
                Path<String> fieldName = root.<String>get("name");
                Predicate predicateName = builder.equal(fieldName, name);
                predicate.add(predicateName);
            }
           return builder.and(predicate.toArray(new Predicate[0]));
        };
    }
    public Specification<CatalogProduct> toSpacLike() {

        return (root, query, builder)->{
            List<Predicate> predicate = new ArrayList<>();
            if(StringUtils.hasText(name)){
                Path<String> fieldName = root.<String>get("name");
                Predicate predicateName = builder.like(fieldName, "%"+name+"%");
                predicate.add(predicateName);
            }
            return builder.and(predicate.toArray(new Predicate[0]));
        };
    }
}
