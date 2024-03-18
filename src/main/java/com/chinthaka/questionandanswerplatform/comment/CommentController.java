package com.chinthaka.questionandanswerplatform.comment;

import com.chinthaka.questionandanswerplatform.comment.request.CommentRequestDTO;
import com.chinthaka.questionandanswerplatform.utils.StandardResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/comment")
@Slf4j
public class CommentController {

    private final ICommentService commentService;

    public CommentController(ICommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping()
    public ResponseEntity<StandardResponse> addComments(@RequestBody CommentRequestDTO commentRequestDTO){
        log.info("POST request received on /api/v1/comment/{}", commentRequestDTO.toString());
        final String response = commentService.addComments(commentRequestDTO);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }

    @PutMapping("/{id}/{like}/{user}/{type}")
    public ResponseEntity<StandardResponse> addLike(
            @PathVariable(name = "id") long commentId,
            @PathVariable(name = "like") boolean like,
            @PathVariable(name = "user") String userID,
            @PathVariable(name = "type") String type
    ){
        log.info("POST request received on /api/v1/comment/{}/{}/{}/{}",commentId,like,userID,type);
        commentService.addLike(commentId,like,userID,type);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",null), HttpStatus.OK);
    }

    @DeleteMapping(value = "/remove/{id}")
    public ResponseEntity<StandardResponse> deleteComment(@PathVariable(name = "id") long id){
        log.info("DELETE request received on /api/v1/comment/{}", id);
        final String response = commentService.deleteComment(id);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }

}
