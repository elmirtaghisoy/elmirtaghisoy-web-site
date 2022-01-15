package az.elmirtaghisoywebsite.service;

import az.elmirtaghisoywebsite.component.entity.PostEntity;
import az.elmirtaghisoywebsite.component.mapper.ObjectMapper;
import az.elmirtaghisoywebsite.component.request.PostRequest;
import az.elmirtaghisoywebsite.component.response.PostResponse;
import az.elmirtaghisoywebsite.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final PostRepository postRepository;
    private final ObjectMapper objectMapper;

    public void createBlog(PostRequest request) {
        PostEntity entity = objectMapper.R2E(request);
        postRepository.save(entity);
    }

    public List<PostResponse> getAllBlog() {
        List<PostResponse> postList = postRepository
                .findAll()
                .stream()
                .map(objectMapper::E2R)
                .collect(Collectors.toList());
        return postList;
    }
}
