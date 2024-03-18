package com.chinthaka.questionandanswerplatform.childComment;

import com.chinthaka.questionandanswerplatform.childComment.request.ChildCommentRequestDTO;
import com.chinthaka.questionandanswerplatform.comment.Comment;
import com.chinthaka.questionandanswerplatform.comment.CommentRepo;
import com.chinthaka.questionandanswerplatform.excaption.HandleException;
import com.chinthaka.questionandanswerplatform.excaption.NotFoundException;
import com.chinthaka.questionandanswerplatform.like.LikeRepo;
import com.chinthaka.questionandanswerplatform.like.UserLike;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@Slf4j
public class ChildCommentIMPL implements IChildComment{

    private final ChildCommentRepo childCommentRepo;
    private final CommentRepo commentRepo;
    private final LikeRepo likeRepo;

    public ChildCommentIMPL(ChildCommentRepo childCommentRepo, CommentRepo commentRepo, LikeRepo likeRepo) {
        this.childCommentRepo = childCommentRepo;
        this.commentRepo = commentRepo;
        this.likeRepo = likeRepo;
    }

    @Override
    public String addComment(ChildCommentRequestDTO childCommentRequestDTO) {
        boolean isCommentIdNull = childCommentRequestDTO.getCommentId() <= 0;
        boolean isDecNull = Objects.equals(childCommentRequestDTO.getDescription(), "");
        boolean isUserIdNull = Objects.equals(childCommentRequestDTO.getUserID(), " ");

        if (isUserIdNull || isDecNull || isCommentIdNull) {
            throw new NotFoundException("All the fields are required");
        }

        Comment comment = commentRepo.findById(childCommentRequestDTO.getCommentId())
                .orElseThrow(() -> new NotFoundException("Post not found"));
        try {

            final ChildComment childComment = new ChildComment(
                    childCommentRequestDTO.getCommentId(),
                    childCommentRequestDTO.getDescription(),
                    childCommentRequestDTO.getUserID(),
                    LocalDateTime.now(),
                    comment

            );

            childCommentRepo.save(childComment);
            return "Successfully added";
        } catch (RuntimeException e) {
            log.error("Error while creating post: {}", e.getMessage());
            throw new HandleException("Something went wrong creating post");
        }
    }

    @Override
    public void addLike(long commentId, boolean like, String userId, String type) {
        ChildComment childComment = childCommentRepo.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment not found for id: " + commentId));

        UserLike userAlreadyLike = likeRepo.findByTypeIdAndUserIdAndType(commentId, userId, type);
        //Default true
        Integer likeCount = childComment.getLikeCount();
        if (!like) { // false
            if (Objects.isNull(userAlreadyLike)) {
                childComment.setLikeCount(likeCount + 1);
                UserLike userLike = new UserLike(
                        type,
                        commentId,
                        userId
                );
                likeRepo.save(userLike); // true
            } else {
                if (Objects.equals(userAlreadyLike.getLikeStatus(), false)) {
                    childComment.setLikeCount(likeCount + 1);
                    userAlreadyLike.setLikeStatus(true); //true
                    likeRepo.save(userAlreadyLike);
                }
            }
        } else {
            if (likeCount > 0) {
                childComment.setLikeCount(likeCount - 1);
                userAlreadyLike.setLikeStatus(false); // false
                likeRepo.save(userAlreadyLike);
            }
        }
        childCommentRepo.save(childComment);

    }

    @Override
    public String deleteChildComment(long id) {
        if (id <= 0) {
            throw new NotFoundException("Comment id is required");
        }
        ChildComment childComment = childCommentRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Child Comment not found for id: " + id));
        childComment.setIsDelete(true);
        childCommentRepo.save(childComment);
        return "Delete success";
    }
}
