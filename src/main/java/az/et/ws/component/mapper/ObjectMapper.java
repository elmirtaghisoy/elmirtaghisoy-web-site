package az.et.ws.component.mapper;

import az.et.ws.component.entity.PostEntity;
import az.et.ws.component.request.PostRequest;
import az.et.ws.component.response.PostResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ObjectMapper {

    PostEntity r2e(PostRequest request);

    PostResponse e2r(PostEntity entity);

}
