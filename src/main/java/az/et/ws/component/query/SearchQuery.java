package az.et.ws.component.query;

import az.et.ws.component.criteria.PostSearchCriteria;
import az.et.ws.component.entity.PostEntity;
import az.et.ws.component.specification.PostSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public interface SearchQuery {

    static Specification<PostEntity> createPostSearchQuery(PostSearchCriteria criteria) {
        return new PostSpecification(criteria);
    }

}
