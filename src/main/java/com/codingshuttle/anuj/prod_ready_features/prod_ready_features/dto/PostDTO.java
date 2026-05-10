package com.codingshuttle.anuj.prod_ready_features.prod_ready_features.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostDTO {
    private Long postId;
    private String title;
    private String description;

}
