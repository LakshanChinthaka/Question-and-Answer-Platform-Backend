package com.chinthaka.questionandanswerplatform.like;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Data
@DynamicUpdate
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "user_like")
public class UserLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_Id")
    private long typeId;

    @Column(name = "type")
    private String type;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "like_status")
    private Boolean likeStatus = true;

    public UserLike(String type, long id,  String userId) {
        this.type = type;
        this.typeId = id;
        this.userId = userId;
    }
}
