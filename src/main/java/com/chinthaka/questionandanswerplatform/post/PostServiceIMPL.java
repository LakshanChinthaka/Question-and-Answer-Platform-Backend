package com.chinthaka.questionandanswerplatform.post;

import com.chinthaka.questionandanswerplatform.childComment.request.response.ChildCommentResponseDTO;
import com.chinthaka.questionandanswerplatform.comment.Comment;
import com.chinthaka.questionandanswerplatform.comment.CommentRepo;
import com.chinthaka.questionandanswerplatform.comment.response.CommentResponseDTO;
import com.chinthaka.questionandanswerplatform.excaption.HandleException;
import com.chinthaka.questionandanswerplatform.excaption.NotFoundException;
import com.chinthaka.questionandanswerplatform.like.LikeRepo;
import com.chinthaka.questionandanswerplatform.like.UserLike;
import com.chinthaka.questionandanswerplatform.post.request.PostRequestDTO;
import com.chinthaka.questionandanswerplatform.post.response.PostResponseDTO;
import com.chinthaka.questionandanswerplatform.utils.EntityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PostServiceIMPL implements IPostService {

    private final PostRepo postRepo;
    private final CommentRepo commentRepo;
    private final LikeRepo likeRepo;

    public PostServiceIMPL(PostRepo postRepo, CommentRepo commentRepo, LikeRepo likeRepo) {
        this.postRepo = postRepo;
        this.commentRepo = commentRepo;
        this.likeRepo = likeRepo;
    }

    @Override
    public String createPost(PostRequestDTO postRequestDTO) {

        if (Objects.equals(postRequestDTO.getUserID(), " ") || Objects.equals(postRequestDTO.getDescription(), "") || postRequestDTO.getType() == null) {
            throw new NotFoundException("All the fields are required");
        }

        try {

            final Post post = new Post(
                    postRequestDTO.getUserID(),
                    postRequestDTO.getDescription(),
                    postRequestDTO.getType(),
                    LocalDateTime.now()
            );

            postRepo.save(post);
            return "Successfully added";
        } catch (RuntimeException e) {
            log.error("Error while creating post: {}", e.getMessage());
            throw new HandleException("Something went wrong creating post");
        }
    }

    @Override
    public PostResponseDTO postGetById(long id) {

        return null;
    }

    //all post and comment and child comment and total details of
    @Override
    public Page<PostResponseDTO> findAllPost(Pageable pageable) {
        try {
            Page<Post> postPage = postRepo.findAllByIsDeleteFalse(pageable);
            return postPage.map(post -> {
                List<UserLike> userLikeList = likeRepo.findByPost(post.getId());

                //PARENT COMMENT
                List<CommentResponseDTO> commentDTOS = post.getComments().stream()
                        .map(comment -> {
                            //CHILD COMMENT
                            List<ChildCommentResponseDTO> childDTOS = comment.getChildComments().stream()
                                    .map(child -> {
                                        ChildCommentResponseDTO childRes = new ChildCommentResponseDTO();
                                        childRes.setId(child.getId());
                                        childRes.setComment(child.getComment());
                                        childRes.setUserID(child.getUserID());
                                        childRes.setCommentId(child.getCommentId());
                                        childRes.setTimeStamp(EntityUtils.convertToDateTime(child.getTimeStamp()));
                                        childRes.setLikeCount(child.getLikeCount());
                                        List<UserLike> childCommentLikeList = likeRepo.findByChildComment(child.getId());
                                        childRes.setChildLikeList(childCommentLikeList);
                                        return childRes;
                                    }).collect(Collectors.toList());
                            //PARENT COMMENT
                            CommentResponseDTO comDTO = new CommentResponseDTO();
                            comDTO.setId(comment.getId());
                            comDTO.setComment(comment.getComment());
                            comDTO.setUserID(comment.getUserID());
                            comDTO.setTimeStamp(EntityUtils.convertToDateTime(comment.getTimeStamp()));
                            comDTO.setReplyCount(comment.getReplyCount());
                            comDTO.setLikeCount(comment.getLikeCount());
                            List<UserLike> commentLikeList = likeRepo.findByComment(comment.getId());
                            comDTO.setCommentLikeList(commentLikeList);
                            comDTO.setChildComment(childDTOS);
                            return comDTO;
                        }).collect(Collectors.toList());

                PostResponseDTO dto = new PostResponseDTO();
                dto.setPostId(post.getId());
                dto.setUserID(post.getUserID());
                dto.setDescription(post.getDescription());
                dto.setType(post.getType());
                dto.setPostDate(EntityUtils.convertToDateTime(post.getTimeStamp()));
                dto.setReplyCount(post.getReplyCount());
                dto.setLikeCount(post.getLikeCount());
                dto.setUserLikeList(userLikeList);

                dto.setComments(commentDTOS);
                return dto;
            });
        } catch (RuntimeException e) {
            log.error("Error while fetching posts: {}", e.getMessage());
            throw new HandleException("Something went wrong fetching posts");
        }
    }


    @Override
    public String deletePost(long id) {
        if (id <= 0) {
            throw new NotFoundException("Post id is required");
        }
        Post post = postRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Post not found for id: " + id));
        post.setIsDelete(true);
        postRepo.save(post);
        return "Delete success";
    }

    @Override
    @Transactional
    public void addLike(long id, boolean like, String userId, String type) {

        Post post = postRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Post not found for id: " + id));
        UserLike userAlreadyLike = likeRepo.findByTypeIdAndUserIdAndType(id, userId, type);
        //Default true
        Integer likeCount = post.getLikeCount();
        if (!like) { // false
            if (Objects.isNull(userAlreadyLike)) {
                post.setLikeCount(likeCount + 1);
                UserLike userLike = new UserLike(
                        type,
                        id,
                        userId
                );
                likeRepo.save(userLike); // true
            } else {
                if (Objects.equals(userAlreadyLike.getLikeStatus(), false)) {
                    post.setLikeCount(likeCount + 1);
                    userAlreadyLike.setLikeStatus(true); //true
                    likeRepo.save(userAlreadyLike);
                }
            }
        } else {
            if (likeCount > 0) {
                post.setLikeCount(likeCount - 1);
                userAlreadyLike.setLikeStatus(false); // false
                likeRepo.save(userAlreadyLike);
            }
        }
        postRepo.save(post);
    }
}
