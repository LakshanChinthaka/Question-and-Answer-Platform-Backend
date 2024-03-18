package com.chinthaka.questionandanswerplatform.comment;

import com.chinthaka.questionandanswerplatform.comment.request.CommentRequestDTO;
import com.chinthaka.questionandanswerplatform.excaption.HandleException;
import com.chinthaka.questionandanswerplatform.excaption.NotFoundException;
import com.chinthaka.questionandanswerplatform.like.LikeRepo;
import com.chinthaka.questionandanswerplatform.like.UserLike;
import com.chinthaka.questionandanswerplatform.post.Post;
import com.chinthaka.questionandanswerplatform.post.PostRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.chinthaka.questionandanswerplatform.like.LikeType.post;

@Service
@Slf4j
public class CommentServiceIMPL implements ICommentService {

    private final PostRepo postRepo;
    private final CommentRepo commentRepo;
    private final LikeRepo likeRepo;

    public CommentServiceIMPL(PostRepo postRepo, CommentRepo commentRepo, LikeRepo likeRepo) {
        this.postRepo = postRepo;
        this.commentRepo = commentRepo;
        this.likeRepo = likeRepo;
    }

    @Override
    @Transactional
    public String addComments(CommentRequestDTO commentRequestDTO) {
        if (Objects.equals(commentRequestDTO.getPostId(), " ") || Objects.equals(commentRequestDTO.getComment(), "")) {
            throw new NotFoundException("All the fields are required");
        }

        Post post = postRepo.findById(commentRequestDTO.getPostId())
                .orElseThrow(() -> new NotFoundException("Post not found for id: " + commentRequestDTO.getPostId()));

        try {
            // Create a new Comment object
            Comment comment = new Comment(
                    commentRequestDTO.getComment(),
                    LocalDateTime.now(),
                    post,
                    commentRequestDTO.getUserID()
            );

            commentRepo.save(comment);
            post.setReplyCount(post.getReplyCount() + 1);
            postRepo.save(post);
            return "Successfully added";
        } catch (RuntimeException e) {
            log.error("Error while add comment: {}", e.getMessage());
            throw new HandleException("Something went wrong add comment");
        }
    }

    @Override
    public void addLike(long commentId, boolean like, String userID, String type) {
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Post not found for id: " + commentId));

        UserLike userAlreadyLike = likeRepo.findByTypeIdAndUserIdAndType(commentId, userID, type);
        //Default true
        Integer likeCount = comment.getLikeCount();
        if (!like) { // false
            if (Objects.isNull(userAlreadyLike)) {
                comment.setLikeCount(likeCount + 1);
                UserLike userLike = new UserLike(
                        type,
                        commentId,
                        userID
                );
                likeRepo.save(userLike); // true
            } else {
                if (Objects.equals(userAlreadyLike.getLikeStatus(), false)) {
                    comment.setLikeCount(likeCount + 1);
                    userAlreadyLike.setLikeStatus(true); //true
                    likeRepo.save(userAlreadyLike);
                }
            }
        } else {
            if (likeCount > 0) {
                comment.setLikeCount(likeCount - 1);
                userAlreadyLike.setLikeStatus(false); // false
                likeRepo.save(userAlreadyLike);
            }
        }
        commentRepo.save(comment);
    }

    @Override
    public String deleteComment(long id) {
        if (id <= 0) {
            throw new NotFoundException("Post id is required");
        }
        Comment comment = commentRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Comment not found for id: " + id));
        comment.setIsDelete(true);
        commentRepo.save(comment);
        return "Delete success";
    }
}
