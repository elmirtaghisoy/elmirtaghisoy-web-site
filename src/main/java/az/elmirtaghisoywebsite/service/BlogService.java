package az.elmirtaghisoywebsite.service;

import az.elmirtaghisoywebsite.component.mapper.ObjectMapper;
import az.elmirtaghisoywebsite.component.request.PostRequest;
import az.elmirtaghisoywebsite.component.response.PostResponse;
import az.elmirtaghisoywebsite.repository.PostRepository;
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
        return objectMapper.E2R(postRepository.save(objectMapper.R2E(request)));
    }

    public List<PostResponse> getAllBlog() {
        return postRepository
                .findAll()
                .stream()
                .map(objectMapper::E2R)
                .collect(Collectors.toList());
    }

    public PostResponse getBlogById(Long blogId) {
        return objectMapper.E2R(postRepository.getById(blogId));
    }

    public PostResponse updateBlog(PostRequest request) {
        if (blogIsExist(request.getId())) {
            return objectMapper.E2R(postRepository.save(objectMapper.R2E(request)));
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
