package az.elmirtaghisoywebsite.api;

import az.elmirtaghisoywebsite.component.request.PostRequest;
import az.elmirtaghisoywebsite.component.response.PostResponse;
import az.elmirtaghisoywebsite.component.response.SuccessResponse;
import az.elmirtaghisoywebsite.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BlogApi {

    private final BlogService blogService;

    @PostMapping(value = "/blog/create")
    public SuccessResponse<PostResponse> createTraining(@RequestBody PostRequest request) {
        blogService.createBlog(request);
        return SuccessResponse.create();
    }

    @GetMapping(value = "/blogs")
    public SuccessResponse<List<PostResponse>> getAllTraining() {
        return SuccessResponse.fetch(blogService.getAllBlog());
    }

}
