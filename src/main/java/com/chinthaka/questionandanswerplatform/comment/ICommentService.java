package com.chinthaka.questionandanswerplatform.comment;

import com.chinthaka.questionandanswerplatform.comment.request.CommentRequestDTO;

public interface ICommentService {

    String addComments(CommentRequestDTO commentRequestDTO);

    void addLike(long commentId, boolean like, String userID, String type);

    String deleteComment(long id);
}
