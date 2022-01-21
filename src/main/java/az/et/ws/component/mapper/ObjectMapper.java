package az.et.ws.component.mapper;

import az.et.ws.component.entity.PostEntity;
import az.et.ws.component.request.PostRequest;
import az.et.ws.component.response.PostResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ObjectMapper {

    PostEntity R2E(PostRequest request);

    PostResponse E2R(PostEntity entity);

}