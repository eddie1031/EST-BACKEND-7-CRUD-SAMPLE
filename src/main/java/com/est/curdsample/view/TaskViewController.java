package com.est.curdsample.view;

import com.est.curdsample.dto.TaskDescription;
import com.est.curdsample.dto.TaskDto;
import com.est.curdsample.dto.TaskPageDto;
import com.est.curdsample.dto.TodayTasksDto;
import com.est.curdsample.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class TaskViewController {

    private final TaskService service;

    @GetMapping("/")
    public String index(Model model) {
        List<TaskDto> tasks = service.getTasksDueToToday();
        model.addAttribute("tasks", TodayTasksDto.from(tasks));
        return "index";
    }

    @GetMapping("/tasks")
    public String showList(Model model, Pageable pageable) {
        TaskPageDto tasks = service.getTaskList(pageable.getPageNumber());
        model.addAttribute("tasks", tasks);
        return "tasks/list";
    }

    @GetMapping("/tasks/more")
    public String loadMoreTasks(Model model, int page) {
        TaskPageDto tasks = service.getTaskList(page);
        model.addAttribute("tasks", tasks);
        model.addAttribute("page", page);
        return "fragments/page_parts :: taskPagePart";
    }

    @GetMapping("/tasks/append")
    public String showAddPage(Model model) {
        model.addAttribute("taskDto", new TaskDto());
        return "tasks/add";
    }

    @GetMapping("/tasks/{code}/edit")
    public String showEditPage(@PathVariable String code, Model model) {
        TaskDto task = service.getTaskByCode(code);
        model.addAttribute("task", task);
        return "tasks/edit";
    }

    @PostMapping("/tasks/{code}/edit")
    public String updateTask(@PathVariable String code, TaskDto task) {
        task.setCode(code);
        log.info("task = {}", task);
        service.update(task);
        return "redirect:/tasks/" + code;
    }

    @PostMapping("/tasks/append")
    public String doAddTask(
            @Valid TaskDto req,
            BindingResult bindingResult,
            Model model
    ) {

        if(bindingResult.hasErrors()){
            model.addAttribute("taskDto", req);
            log.info("Error occurred!");
            return "tasks/add";
        }

        service.saveTask(req);
        return "redirect:/tasks/append";
    }

    @GetMapping("/tasks/{code}")
    public String showTaskDetailPage(
            @PathVariable String code,
            Model model
    ) {
        TaskDescription task = service.getDescriptionByCode(code);
        model.addAttribute("task", task);
        return "tasks/detail";
    }


}
