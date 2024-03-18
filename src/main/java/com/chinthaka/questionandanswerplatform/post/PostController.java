package com.chinthaka.questionandanswerplatform.post;

import com.chinthaka.questionandanswerplatform.PostType.PostType;
import com.chinthaka.questionandanswerplatform.post.request.PostRequestDTO;
import com.chinthaka.questionandanswerplatform.post.response.PostResponseDTO;
import com.chinthaka.questionandanswerplatform.utils.StandardResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/post")
@Slf4j
public class PostController {

    private final IPostService postService;

    public PostController(IPostService postService) {
        this.postService = postService;
    }


    @PostMapping()
    public ResponseEntity<StandardResponse> createPost(@RequestBody PostRequestDTO postRequestDTO){
        log.info("POST request received on /api/v1/post/{}", postRequestDTO.toString());
        final String response = postService.createPost(postRequestDTO);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<StandardResponse> postGetById(@PathVariable(name = "id") long id){
        log.info("GET request received on /api/v1/post/{}", id);
        PostResponseDTO response = postService.postGetById(id);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<StandardResponse> findAllPost(
            @PageableDefault(sort = "id",direction = Sort.Direction.DESC) Pageable pageable){
        log.info("GET request received on /api/v1/post/all");
        Page<PostResponseDTO> response = postService.findAllPost(pageable);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }

    @DeleteMapping(value = "/remove/{id}")
    public ResponseEntity<StandardResponse> deletePost(@PathVariable(name = "id") long id){
        log.info("DELETE request received on /api/v1/post/{}", id);
        final String response = postService.deletePost(id);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }

    //get post type
    @GetMapping("/category")
    public ResponseEntity<StandardResponse> getAllPostCategory(){
        log.info("GET request received on /api/v1/post/all");
        PostType[] postTypes = PostType.values();
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",postTypes), HttpStatus.OK);
    }

    //add Like
    @PutMapping("/{id}/{like}/{user}/{type}")
    public ResponseEntity<StandardResponse> addLike(
            @PathVariable(name = "id") long id,
            @PathVariable(name = "like") boolean like,
              @PathVariable(name = "user") String userId,
              @PathVariable(name = "type") String type
    ){
        log.info("POST request received on /api/v1/post/{}/{}/{}/{}",id,like,userId,type);
        postService.addLike(id,like,userId,type);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",null), HttpStatus.OK);
    }

}
