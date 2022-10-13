package az.et.ws.api;

import az.et.ws.component.criteria.PostSearchCriteria;
import az.et.ws.component.request.PostRequest;
import az.et.ws.component.response.PostResponse;
import az.et.ws.component.response.SuccessResponse;
import az.et.ws.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BlogApi {

    private final BlogService blogService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/blog/create")
    @PreAuthorize("hasAnyAuthority('ALL','BLOG','CREATE')")
    public SuccessResponse<PostResponse> createBlog(@RequestBody PostRequest request) {
        return SuccessResponse.create(blogService.createBlog(request));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/blog/review/{id}")
    @PreAuthorize("hasAnyAuthority('ALL','BLOG','REVIEW')")
    public SuccessResponse<PostResponse> getBlogForReview(
            @PathVariable("id") Long id
    ) {
        return SuccessResponse.fetch(blogService.getBlogForReview(id));
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/blog/approve/{id}")
    @PreAuthorize("hasAnyAuthority('ALL','BLOG','APPROVE')")
    public SuccessResponse<PostResponse> approveBlog(
            @PathVariable("id") Long id
    ) {
        return SuccessResponse.fetch(blogService.approveBlog(id));
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/blog/comment/{id}")
    @PreAuthorize("hasAnyAuthority('ALL','BLOG','COMMENT')")
    public SuccessResponse<PostResponse> commentBlog(
            @PathVariable("id") Long id
    ) {
        return SuccessResponse.fetch(blogService.commentBlog(id));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/blog/all")
    @PreAuthorize("hasAnyAuthority('ALL','BLOG','GET')")
    public SuccessResponse<Page<PostResponse>> getAllBlog(
            Pageable pageable
    ) {
        return SuccessResponse.fetch(blogService.getAllBlog(pageable, new PostSearchCriteria()));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/blog/{id}")
    @PreAuthorize("hasAnyAuthority('ALL','BLOG','GET')")
    public SuccessResponse<PostResponse> getBlogById(@PathVariable("id") Long blogId) {
        return SuccessResponse.fetch(blogService.getBlogById(blogId));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/blog/update")
    @PreAuthorize("hasAnyAuthority('ALL','BLOG','UPDATE')")
    public SuccessResponse<PostResponse> updateBlog(@RequestBody PostRequest request) {
        return SuccessResponse.update(blogService.updateBlog(request));
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/blog/{id}/delete")
    @PreAuthorize("hasAnyAuthority('BLOG','DELETE')")
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
