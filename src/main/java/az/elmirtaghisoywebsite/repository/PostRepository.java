package az.elmirtaghisoywebsite.repository;

import az.elmirtaghisoywebsite.component.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
}
