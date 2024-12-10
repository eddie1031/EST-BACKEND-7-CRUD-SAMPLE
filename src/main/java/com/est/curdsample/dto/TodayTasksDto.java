package com.est.curdsample.dto;

import com.est.curdsample.domain.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodayTasksDto {
    List<TaskDto> uncompletedTasks = new ArrayList<>();
    List<TaskDto> completedTasks = new ArrayList<>();

    public static TodayTasksDto from(List<TaskDto> tasks) {

//        todayTasksDto.completedTasks = tasks.stream()
//                .filter(t -> t.isCompleteStatus())
//                .toList();
//
//        todayTasksDto.uncompletedTasks = tasks.stream()
//                .filter(t -> !t.isCompleteStatus())
//                .toList();

        Map<Boolean, List<TaskDto>> result = tasks
                .stream()
                .collect(
                    Collectors.partitioningBy(TaskDto::isCompleteStatus)
                );

        return new TodayTasksDto(
                result.get(false),
                result.get(true)
        );
    }
}
