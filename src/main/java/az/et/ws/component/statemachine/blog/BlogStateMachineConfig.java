package az.et.ws.component.statemachine.blog;


import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@EnableStateMachineFactory
@Configuration
public class BlogStateMachineConfig extends EnumStateMachineConfigurerAdapter<BlogState, BlogEvent> {

    @Override
    public void configure(StateMachineStateConfigurer<BlogState, BlogEvent> states) throws Exception {
        states.withStates()
                .initial(BlogState.WAITING_FOR_REVIEW)
                .states(EnumSet.allOf(BlogState.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<BlogState, BlogEvent> transitions) throws Exception {
        transitions
                .withExternal()
                .source(BlogState.WAITING_FOR_REVIEW)
                .target(BlogState.IN_REVIEW)
                .event(BlogEvent.GET_FOR_REVIEW)
                .and()
                .withExternal()
                .source(BlogState.IN_REVIEW)
                .target(BlogState.APPROVED)
                .event(BlogEvent.APPROVE)
                .and()
                .withExternal()
                .source(BlogState.IN_REVIEW)
                .target(BlogState.COMMENTED)
                .event(BlogEvent.COMMENT);
    }

}
