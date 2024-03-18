package com.chinthaka.questionandanswerplatform.comment;

import com.chinthaka.questionandanswerplatform.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CommentRepo extends JpaRepository<Comment,Long> {

    List<Comment> findAllByPost(Post post);
}
