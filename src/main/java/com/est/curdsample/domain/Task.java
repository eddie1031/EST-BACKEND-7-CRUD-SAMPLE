package com.est.curdsample.domain;

import com.est.curdsample.dto.TaskDto;
import com.est.curdsample.util.TimeFormatter;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Entity
@Table(name = "tasks")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task {

    @Id
    @Column(name = "tasks_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "task_code", unique = true, nullable = false)
    private String code;

    private String title;
    private String description;

    private Integer priority;

    private boolean completeStatus = false;

    private LocalDate startTime;
    private LocalDate endTime;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    public void updateCheck() {
        this.completeStatus = !this.completeStatus;
    }

    public void update(TaskDto taskDto) {
        this.title = taskDto.getTitle();
        this.description = taskDto.getDescription();
        this.priority = taskDto.getPriority();

        this.completeStatus = taskDto.isCompleteStatus();

//        task.startTime = taskDto.getStartTime();
        this.startTime = TimeFormatter.convertToLocalDate(taskDto.getStartTime());
//        task.endTime = taskDto.getEndTime();
        this.endTime = TimeFormatter.convertToLocalDate(taskDto.getEndTime());

        this.updatedAt = LocalDateTime.now();
    }

    public static Task of(TaskDto taskDto) {
        Task task = new Task();

        task.code = taskDto.getCode();
        task.title = taskDto.getTitle();
        task.description = taskDto.getDescription();
        task.priority = taskDto.getPriority();
        task.completeStatus = taskDto.isCompleteStatus();
//        task.startTime = taskDto.getStartTime();
        task.startTime = TimeFormatter.convertToLocalDate(taskDto.getStartTime());
//        task.endTime = taskDto.getEndTime();
        task.endTime = TimeFormatter.convertToLocalDate(taskDto.getEndTime());

        return task;
    }

}
