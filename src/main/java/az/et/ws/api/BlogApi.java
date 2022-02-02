package az.et.ws.api;

import az.et.ws.component.request.PostRequest;
import az.et.ws.component.response.PostResponse;
import az.et.ws.component.response.SuccessResponse;
import az.et.ws.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BlogApi {

    private final BlogService blogService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/blog/create")
    public SuccessResponse<PostResponse> createTraining(@RequestBody PostRequest request) {
        return SuccessResponse.create(blogService.createBlog(request));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/blog/all")
    @PreAuthorize("hasAnyAuthority('BLOG')")
    public SuccessResponse<List<PostResponse>> getAllTraining() {
        return SuccessResponse.fetch(blogService.getAllBlog());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/blog/{id}")
    public SuccessResponse<PostResponse> getTrainingById(@PathVariable("id") Long blogId) {
        return SuccessResponse.fetch(blogService.getBlogById(blogId));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/blog/update")
    public SuccessResponse<PostResponse> updateBlog(@RequestBody PostRequest request) {
        return SuccessResponse.update(blogService.updateBlog(request));
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/blog/{id}/delete")
    public SuccessResponse<PostResponse> deleteBlogById(@PathVariable("id") Long blogId) {
        blogService.deleteBlogById(blogId);
        return SuccessResponse.delete();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/ping")
    public SuccessResponse<String> ping() {
        return SuccessResponse.fetch("Service is work");
    }

}
