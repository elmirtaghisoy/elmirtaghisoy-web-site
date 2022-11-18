package az.et.ws.api;

import az.et.ws.component.criteria.PostSearchCriteria;
import az.et.ws.component.filegenerator.CustomByteArrayResource;
import az.et.ws.component.request.PostRequest;
import az.et.ws.component.response.PostResponse;
import az.et.ws.component.response.SuccessResponse;
import az.et.ws.component.statemachine.blog.BlogState;
import az.et.ws.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BlogApi {

    private final BlogService blogService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/blog/create")
    @PreAuthorize("hasAnyAuthority('ALL','BLOG','CREATE')")
    public SuccessResponse<PostResponse> createBlog(
            @RequestPart(name = "request") PostRequest request,
            @RequestPart(name = "files", required = false) List<MultipartFile> files
    ) {
        return SuccessResponse.create(blogService.createBlog(request, files));
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
            Pageable pageable,
            @RequestParam("header") String header,
            @RequestParam("state") BlogState state,
            @RequestParam("tagIds") List<Long> tagIds
    ) {
        return SuccessResponse.fetch(
                blogService.getAllBlog(
                        new PostSearchCriteria(header, state, tagIds),
                        pageable
                )
        );
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
    public SuccessResponse<ObjectUtils.Null> deleteBlogById(@PathVariable("id") Long blogId) {
        blogService.deleteBlogById(blogId);
        return SuccessResponse.delete();
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/blog/{id}/generate")
    @PreAuthorize("hasAnyAuthority('ALL','BLOG')")
    public ResponseEntity<CustomByteArrayResource> generateBlogPdfById(@PathVariable("id") Long blogId) {
        CustomByteArrayResource pdfFile = blogService.generateBlogPdfById(blogId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_PDF_VALUE))
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("%s%s%s", "attachment; filename=\"", pdfFile.getDescription(), "\""))
                .body(pdfFile);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/ping")
    public SuccessResponse<String> ping() {
        return SuccessResponse.fetch("Service is work");
    }

}
