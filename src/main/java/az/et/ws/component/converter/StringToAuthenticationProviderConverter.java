package az.et.ws.component.converter;

import az.et.ws.component.entity.AuthenticationProvider;
import az.et.ws.component.exception.WrongAuthenticationTypeException;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.converter.Converter;

public class StringToAuthenticationProviderConverter implements Converter<String, AuthenticationProvider> {
    @Override
    public AuthenticationProvider convert(String source) {
        try {
            return AuthenticationProvider.valueOf(source.toUpperCase());
        } catch (ConversionFailedException e) {
            throw new WrongAuthenticationTypeException();
        }
    }
}