package com.est.curdsample.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
public class TaskPageDto {

    private boolean hasNext;
    private List<TaskDto> data;

}
