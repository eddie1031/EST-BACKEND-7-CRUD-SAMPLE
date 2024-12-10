package com.est.curdsample.dto;

import com.est.curdsample.domain.Task;
import com.est.curdsample.util.PriorityResolver;
import com.est.curdsample.util.TimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDescription {

    private String code;

    private String title;
    private String description;

    private Integer priority;
    private boolean completeStatus;

    private String startDate;
    private String dueDate;

    private String createdAt;
    private String updatedAt;

    public static TaskDescription from(Task task) {
        return new TaskDescription(
                task.getCode(),
                task.getTitle(),
                task.getDescription(),
                task.getPriority(),
                task.isCompleteStatus(),
                TimeFormatter.convertToStr(task.getStartTime()),
                TimeFormatter.convertToStr(task.getEndTime()),
                TimeFormatter.convertToStr(task.getCreatedAt()),
                TimeFormatter.convertToStr(task.getUpdatedAt())
        );
    }

    public String getPriorityLevel() {
        return PriorityResolver.resolve(priority);
    }

}
