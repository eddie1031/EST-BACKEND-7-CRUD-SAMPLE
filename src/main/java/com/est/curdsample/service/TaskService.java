package com.est.curdsample.service;

import com.est.curdsample.dao.TaskRepository;
import com.est.curdsample.domain.Task;
import com.est.curdsample.dto.TaskDescription;
import com.est.curdsample.dto.TaskDto;
import com.est.curdsample.dto.TaskPageDto;
import com.est.curdsample.exception.TaskNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskService {

    private final TaskRepository repository;

    public List<TaskDto> getTasksDueToToday() {
        return repository.findTenTasksDueToToday()
                .stream()
                .map(TaskDto::from)
                .toList();
    }

    public TaskPageDto getTaskList(int pageNum) {

        final int SIZE = 5;
        PageRequest page = PageRequest.of(pageNum, SIZE, Sort.Direction.DESC, "createdAt");

        Slice<Task> tasks = repository.findAll(page);

        return TaskPageDto.builder()
                .hasNext(tasks.hasNext())
                .data(tasks.stream()
                        .map(TaskDto::from)
                        .toList())
                .build();
    }

    @Transactional
    public TaskDto saveTask(TaskDto taskDto) {
        taskDto.setCode(genCode());
        repository.save(Task.of(taskDto));
        return taskDto;
    }

    @Transactional
    public TaskDto checkTaskByCode(String code) {
        Task findTask = findByCode(code);
        findTask.updateCheck();
        return TaskDto.from(findTask);
    }

    @Transactional
    public TaskDto update(TaskDto taskDto) {

        Task findTask = findByCode(taskDto.getCode());
        findTask.update(taskDto);

        return taskDto;
    }

    public Task findByCode(String code) {
        return repository.findByCode(code)
                .orElseThrow(
                        () -> new TaskNotFoundException("Could not find task with code " + code)
                );
    }

    public TaskDto getTaskByCode(String code) {
        return TaskDto.from(findByCode(code));
    }

    public TaskDescription getDescriptionByCode(String code) {
        return TaskDescription.from(findByCode(code));
    }

    @Transactional
    public String removeTaskByCode(String code) {
        Task task = findByCode(code);
        repository.delete(task);
        return task.getCode();
    }

    private String genCode() {
        return UUID.randomUUID().toString();
    }

}
