package az.et.ws.component.statemachine.blog;

import az.et.ws.component.entity.PostEntity;
import az.et.ws.repository.postgres.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class BlogStateChangeInterceptor extends StateMachineInterceptorAdapter<BlogState, BlogEvent> {

    private final PostRepository postRepository;

    @Override
    public void preStateChange(State<BlogState, BlogEvent> state, Message<BlogEvent> message, Transition<BlogState, BlogEvent> transition, StateMachine<BlogState, BlogEvent> stateMachine, StateMachine<BlogState, BlogEvent> rootStateMachine) {
        Optional.ofNullable(message).ifPresent(msg -> {
            Optional.ofNullable(Long.class.cast(msg.getHeaders().getOrDefault("blog_id", -1L))).ifPresent(blogId -> {
                PostEntity post = postRepository.findById(blogId).orElseThrow(EntityNotFoundException::new);
                post.setState(state.getId());
                postRepository.save(post);
            });
        });
    }

}
