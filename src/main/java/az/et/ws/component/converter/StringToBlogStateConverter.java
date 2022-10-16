package az.et.ws.component.converter;

import az.et.ws.component.exception.WrongEnumTypeException;
import az.et.ws.component.statemachine.blog.BlogState;
import org.springframework.core.convert.converter.Converter;

public class StringToBlogStateConverter implements Converter<String, BlogState> {
    @Override
    public BlogState convert(String source) {
        try {
            return BlogState.valueOf(source.toUpperCase());
        } catch (Exception e) {
            throw new WrongEnumTypeException();
        }
    }
}