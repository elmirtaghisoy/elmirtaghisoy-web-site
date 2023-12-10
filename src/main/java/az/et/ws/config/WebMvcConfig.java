package az.et.ws.config;

import az.et.ws.component.converter.StringToAuthenticationProviderConverter;
import az.et.ws.component.converter.StringToBlogStateConverter;
//import az.et.ws.component.interceptor.AllRequestInterceptor;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    //    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:3000,http://localhost:8081")
//                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
//                .allowedHeaders("*")
//                .allowCredentials(true)
//                .maxAge(3600);
//    }
//    @Autowired
//    AllRequestInterceptor allRequestInterceptor;


    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToAuthenticationProviderConverter());
        registry.addConverter(new StringToBlogStateConverter());
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(allRequestInterceptor);
//    }
}