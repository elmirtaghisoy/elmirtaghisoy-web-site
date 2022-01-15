package az.elmirtaghisoywebsite.component.mapper;

import az.elmirtaghisoywebsite.component.entity.PostEntity;
import az.elmirtaghisoywebsite.component.request.PostRequest;
import az.elmirtaghisoywebsite.component.response.PostResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ObjectMapper {

    PostEntity R2E(PostRequest request);

    PostResponse E2R(PostEntity entity);

}