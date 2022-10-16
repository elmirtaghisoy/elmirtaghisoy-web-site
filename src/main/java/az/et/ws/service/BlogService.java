package az.et.ws.service;

import az.et.ws.component.criteria.PostSearchCriteria;
import az.et.ws.component.entity.PostEntity;
import az.et.ws.component.exception.EventNotAcceptableException;
import az.et.ws.component.mapper.ObjectMapper;
import az.et.ws.component.query.SearchQuery;
import az.et.ws.component.request.PostRequest;
import az.et.ws.component.response.PostResponse;
import az.et.ws.component.statemachine.blog.BlogEvent;
import az.et.ws.component.statemachine.blog.BlogState;
import az.et.ws.component.statemachine.blog.BlogStateChangeInterceptor;
import az.et.ws.repository.postgres.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final PostRepository postRepository;
    private final ObjectMapper objectMapper;
    private final StateMachineFactory<BlogState, BlogEvent> blogEventStateMachineFactory;
    private final BlogStateChangeInterceptor blogStateChangeInterceptor;

    public PostResponse createBlog(PostRequest request) {
        PostEntity postEntity = objectMapper.r2e(request);
        postEntity.setState(BlogState.WAITING_FOR_REVIEW);
        return objectMapper.e2r(postRepository.save(postEntity));
    }

    public PostResponse getBlogForReview(Long id) {
        StateMachine<BlogState, BlogEvent> sm = build(id);
        sendEvent(id, sm, BlogEvent.GET_FOR_REVIEW);
        return getBlogById(id);
    }

    public PostResponse approveBlog(Long id) {
        StateMachine<BlogState, BlogEvent> sm = build(id);
        sendEvent(id, sm, BlogEvent.APPROVE);
        return getBlogById(id);
    }

    public PostResponse commentBlog(Long id) {
        StateMachine<BlogState, BlogEvent> sm = build(id);
        sendEvent(id, sm, BlogEvent.COMMENT);
        return getBlogById(id);
    }

    @Cacheable("BlogListCache")
    public Page<PostResponse> getAllBlog(PostSearchCriteria searchCriteria, Pageable pageable) {

        List<PostResponse> response = postRepository
                .findAll(
                        SearchQuery.createPostSearchQuery(searchCriteria),
                        pageable
                )
                .stream()
                .map(objectMapper::e2r)
                .collect(Collectors.toList());

        return new PageImpl<>(response);
    }

    public PostResponse getBlogById(Long blogId) {
        return objectMapper.e2r(postRepository.findById(blogId).orElseThrow(EntityNotFoundException::new));
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

    private void sendEvent(Long blogId, StateMachine<BlogState, BlogEvent> sm, BlogEvent event) {
        Message<BlogEvent> msg = MessageBuilder.withPayload(event)
                .setHeader("blog_id", blogId)
                .build();
        if (!sm.sendEvent(msg)) {
            throw new EventNotAcceptableException();
        }
    }

    private StateMachine<BlogState, BlogEvent> build(Long blogId) {
        PostEntity post = postRepository.findById(blogId).orElseThrow(EntityNotFoundException::new);

        StateMachine<BlogState, BlogEvent> sm = blogEventStateMachineFactory.getStateMachine(Long.toString(blogId));

        sm.stop();

        sm.getStateMachineAccessor()
                .doWithAllRegions(sma -> {
                    sma.addStateMachineInterceptor(blogStateChangeInterceptor);
                    sma.resetStateMachine(new DefaultStateMachineContext<>(post.getState(), null, null, null));
                });

        sm.start();

        return sm;
    }

}
