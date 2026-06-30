package com.example.tskmngr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private Long id;

    private String text;

    private Long userId;

    private String userName;

    private Long taskId;
}
