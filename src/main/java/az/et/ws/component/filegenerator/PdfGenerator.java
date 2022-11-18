package az.et.ws.component.filegenerator;

import az.et.ws.component.response.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.ISpringTemplateEngine;

@Component
@RequiredArgsConstructor
public class PdfGenerator {

    private final ISpringTemplateEngine templateEngine;

    public String generateBlogPdf(PostResponse post) {
        Context context = new Context();
        context.setVariable("blog", post);
        return templateEngine.process("pdf/blog-pdf", context);
    }

}
