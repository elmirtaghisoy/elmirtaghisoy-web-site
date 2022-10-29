package az.et.ws.component.specification;

import az.et.ws.component.criteria.PostSearchCriteria;
import az.et.ws.component.entity.PostEntity;
import az.et.ws.component.entity.PostEntity_;
import az.et.ws.component.entity.PostTagEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.ArrayList;
import java.util.Collection;
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
            if (!CollectionUtils.isEmpty(criteria.getTagIds())) {
                criteriaQuery.distinct(true);
                predicates.add(
                        root.join(PostEntity_.TAGS).get(PostEntity_.ID).in(criteria.getTagIds())
                );
            }
        }
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
