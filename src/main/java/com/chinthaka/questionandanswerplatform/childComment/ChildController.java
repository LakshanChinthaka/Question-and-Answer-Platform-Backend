package com.chinthaka.questionandanswerplatform.childComment;

import com.chinthaka.questionandanswerplatform.childComment.request.ChildCommentRequestDTO;
import com.chinthaka.questionandanswerplatform.post.request.PostRequestDTO;
import com.chinthaka.questionandanswerplatform.utils.StandardResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/child")
@Slf4j
public class ChildController {

    private final IChildComment commentService;

    public ChildController(IChildComment commentService) {
        this.commentService = commentService;
    }

    @PostMapping()
    public ResponseEntity<StandardResponse> addComment(@RequestBody ChildCommentRequestDTO childCommentRequestDTO){
        log.info("POST request received on /api/v1/child/{}", childCommentRequestDTO.toString());
        final String response = commentService.addComment(childCommentRequestDTO);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }

    //add Like
    @PutMapping("/{id}/{like}/{user}/{type}")
    public ResponseEntity<StandardResponse> addLike(
            @PathVariable(name = "id") long commentId,
            @PathVariable(name = "like") boolean like,
            @PathVariable(name = "user") String userId,
            @PathVariable(name = "type") String type
    ){
        log.info("POST request received on /api/v1/child/{}/{}/{}/{}",commentId,like,userId,type);
        commentService.addLike(commentId,like,userId,type);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",null), HttpStatus.OK);
    }

    @DeleteMapping(value = "/remove/{id}")
    public ResponseEntity<StandardResponse> deleteChildComment(@PathVariable(name = "id") long id){
        log.info("DELETE request received on /api/v1/deleteChildComment/{}", id);
        final String response = commentService.deleteChildComment(id);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }
}
