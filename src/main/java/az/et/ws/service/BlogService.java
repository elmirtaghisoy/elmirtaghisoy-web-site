package az.et.ws.service;

import az.et.ws.component.mapper.ObjectMapper;
import az.et.ws.component.request.PostRequest;
import az.et.ws.component.response.PostResponse;
import az.et.ws.repository.postgres.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final PostRepository postRepository;
    private final ObjectMapper objectMapper;

    public PostResponse createBlog(PostRequest request) {
        return objectMapper.e2r(postRepository.save(objectMapper.r2e(request)));
    }

    public List<PostResponse> getAllBlog() {
        return postRepository
                .findAll()
                .stream()
                .map(objectMapper::e2r)
                .collect(Collectors.toList());
    }

    public PostResponse getBlogById(Long blogId) {
        return objectMapper.e2r(postRepository.getById(blogId));
    }

    public PostResponse updateBlog(PostRequest request) {
        if (blogIsExist(request.getId())) {
            return objectMapper.e2r(postRepository.save(objectMapper.r2e(request)));
        } else {
            throw new EntityNotFoundException();
        }
    }

    public void deleteBlogById(Long blogId) {
        postRepository.deleteById(blogId);
    }

    private boolean blogIsExist(Long blogId) {
        return postRepository.existsById(blogId);
    }

}
