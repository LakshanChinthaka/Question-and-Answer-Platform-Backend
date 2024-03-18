package com.chinthaka.questionandanswerplatform.post.request;

import com.chinthaka.questionandanswerplatform.PostType.PostType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDTO {
    private String userID;
    private String description;
    private PostType type;

}
