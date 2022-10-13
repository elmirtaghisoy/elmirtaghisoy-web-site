package az.et.ws.repository.postgres;

import az.et.ws.component.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends PagingAndSortingRepository<PostEntity, Long> {

    Page<PostEntity> findAll(Specification<PostEntity> specification, Pageable pageable);
}
