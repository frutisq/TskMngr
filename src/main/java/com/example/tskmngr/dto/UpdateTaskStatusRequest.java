package com.example.tskmngr.dto;

import com.example.tskmngr.entity.Status;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateTaskStatusRequest {
    private Status status;
}
