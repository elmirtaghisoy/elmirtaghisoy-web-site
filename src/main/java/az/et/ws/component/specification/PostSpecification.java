package az.et.ws.component.specification;

import az.et.ws.component.criteria.PostSearchCriteria;
import az.et.ws.component.entity.PostEntity;
import az.et.ws.component.entity.PostEntity_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class PostSpecification implements Specification<PostEntity> {

    private final PostSearchCriteria criteria;

    public PostSpecification(PostSearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<PostEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(criteria)) {
            if (Objects.nonNull(criteria.getHeader())) {
                predicates.add(cb.like(root.get(PostEntity_.header), "%" + criteria.getHeader() + "%"));
            }
            if (Objects.nonNull(criteria.getState())) {
                predicates.add(
                        cb.equal(root.get(PostEntity_.state), criteria.getState())
                );
            }
        }
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
