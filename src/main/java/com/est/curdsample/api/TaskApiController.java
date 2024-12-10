package com.est.curdsample.api;

import com.est.curdsample.dto.GeneralApiResponse;
import com.est.curdsample.dto.TaskDto;
import com.est.curdsample.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskApiController {

    private final TaskService service;

    @PatchMapping("/{code}/status")
    public GeneralApiResponse<TaskDto> updateTaskStatus(
            @PathVariable("code") String code
    ) {
        TaskDto taskDto = service.checkTaskByCode(code);
        return GeneralApiResponse.<TaskDto>builder()
                .data(taskDto)
                .msg("Successfully Checked!")
                .build();
    }

    @DeleteMapping("/{code}")
    public GeneralApiResponse<Void> deleteTask(@PathVariable("code") String code) {
        service.removeTaskByCode(code);
        return GeneralApiResponse.<Void>builder()
                .msg("Successfully Removed!")
                .build();
    }

}
