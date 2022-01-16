package az.elmirtaghisoywebsite.api;

import az.elmirtaghisoywebsite.component.request.PostRequest;
import az.elmirtaghisoywebsite.component.response.PostResponse;
import az.elmirtaghisoywebsite.component.response.SuccessResponse;
import az.elmirtaghisoywebsite.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
public class BlogApi {

    private final BlogService blogService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/blog/create")
    public SuccessResponse<PostResponse> createTraining(@RequestBody PostRequest request) {
        return SuccessResponse.create(blogService.createBlog(request));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/blog/all")
    public SuccessResponse<List<PostResponse>> getAllTraining() {
        return SuccessResponse.fetch(blogService.getAllBlog());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/blog/{id}")
    public SuccessResponse<PostResponse> getTrainingById(@PathVariable(value = "id") Long blogId) {
        return SuccessResponse.fetch(blogService.getBlogById(blogId));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/blog/update")
    public SuccessResponse<PostResponse> updateBlog(@RequestBody PostRequest request) {
        return SuccessResponse.update(blogService.updateBlog(request));
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/blog/{id}/delete")
    public SuccessResponse<PostResponse> deleteBlogById(@PathVariable(value = "id") Long blogId) {
        blogService.deleteBlogById(blogId);
        return SuccessResponse.delete();
    }

}
