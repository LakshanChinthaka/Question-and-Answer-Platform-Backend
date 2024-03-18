package com.chinthaka.questionandanswerplatform.like;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepo extends JpaRepository<UserLike, Long> {

    UserLike findByTypeIdAndUserIdAndType(long id, String userId, String type);

    @Query(value = "SELECT * FROM user_like WHERE type = 'post' AND type_id = :id AND like_status = true", nativeQuery = true)
    List<UserLike> findByPost(Long id);

    @Query(value = "SELECT * FROM user_like WHERE type = 'com' AND type_id = :id AND like_status = true", nativeQuery = true)
    List<UserLike> findByComment(Long id);

    @Query(value = "SELECT * FROM user_like WHERE type = 'child' AND type_id = :id AND like_status = true", nativeQuery = true)
    List<UserLike> findByChildComment(Long id);
}
