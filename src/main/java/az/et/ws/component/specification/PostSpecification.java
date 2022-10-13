package az.et.ws.component.specification;

import az.et.ws.component.criteria.PostSearchCriteria;
import az.et.ws.component.entity.PostEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Component
public class PostSpecification implements Specification<PostEntity> {

    private final PostSearchCriteria criteria;

    public PostSpecification(PostSearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<PostEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return null;
    }
}
