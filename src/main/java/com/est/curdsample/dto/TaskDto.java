package com.est.curdsample.dto;

import com.est.curdsample.domain.Task;
import com.est.curdsample.util.PriorityResolver;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    private String code;

    @NotBlank(message = "작업이름은 반드시 입력되어야 합니다.")
    private String title;
    private String description;

    @Min(value = 0)
    private Integer priority;

    private boolean completeStatus;

    @NotBlank(message = "날짜는 반드시 입력되어야 합니다.")
    @Pattern(regexp = "^(0[1-9]|1[0-2])/([0-2][0-9]|3[01])/\\d{4}$", message = "올바른 날짜를 입력하여 주시기 바랍니다.")
    private String startTime;

    @NotBlank(message = "날짜는 반드시 입력되어야 합니다.")
    @Pattern(regexp = "^(0[1-9]|1[0-2])/([0-2][0-9]|3[01])/\\d{4}$", message = "올바른 날짜를 입력하여 주시기 바랍니다.")
    private String endTime;

    public static TaskDto from(Task task) {
        TaskDto taskDto = new TaskDto();

        taskDto.code = task.getCode();
        taskDto.title = task.getTitle();
        taskDto.description = task.getDescription();
        taskDto.priority = task.getPriority();
        taskDto.completeStatus = task.isCompleteStatus();

        taskDto.startTime = task.getStartTime().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        taskDto.endTime = task.getEndTime().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));

        return taskDto;
    }

    public String getPriorityLevel() {
        return PriorityResolver.resolve(priority);
    }

}
