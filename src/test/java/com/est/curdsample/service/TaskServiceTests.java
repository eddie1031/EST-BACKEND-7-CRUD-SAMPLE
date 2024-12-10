package com.est.curdsample.service;

import com.est.curdsample.domain.Task;
import com.est.curdsample.dto.TaskDto;
import com.est.curdsample.exception.TaskNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@DisplayName("TaskService 클래스의")
class TaskServiceTests {

    @Autowired
    private TaskService service;

    @Nested
    @DisplayName("save 메서드는")
    class Describe_save_task {

        @Nested
        @DisplayName("task에 대한 내용이 포함되어 요청이 들어온다면")
        class Context_with_valid_task {

            TaskDto dto = new TaskDto(
                    null,
                    "task 1",
                    "task 1",
                    1,
                    false,
                    LocalDateTime.now().toString(),
                    LocalDateTime.now().toString()
            );

            @Test
            @DisplayName("task를 생성하여 저장한 후 code를 포함한 dto를 반환한다.")
            void it_return_dto_including_code() throws Exception {
                TaskDto saved = service.saveTask(dto);
                assertThat(saved.getCode()).isNotNull();
            }


        }

    }

    @Nested
    @DisplayName("findByCode 메서드는")
    class Describe_find_by_code {

        String VALID_CODE;
        String INVALID_CODE = "INVALID_CODE";

        @BeforeEach
        void setUp() {
            TaskDto dto = new TaskDto(
                    null,
                    "task 1",
                    "task 1",
                    1,
                    false,
                    LocalDateTime.now().toString(),
                    LocalDateTime.now().toString()
            );
            TaskDto taskDto = service.saveTask(dto);
            VALID_CODE = taskDto.getCode();
        }

        @Nested
        @DisplayName("유효한 code를 부여하였다면")
        class Context_with_valid_code {

            @Test
            @DisplayName("저장되어있는 task를 반환한다.")
            void it_return_and_find_saved_task() throws Exception {
                Task findTask = service.findByCode(VALID_CODE);
                assertThat(findTask).isNotNull();
                assertThat(findTask.getCode()).isEqualTo(VALID_CODE);
            }

        }

        @Nested
        @DisplayName("무효한 code를 부여하였다면")
        class Context_with_invalid_code {

            @Test
            @DisplayName("task를 찾지 못하고 오류를 발생시킬 것이다.")
            void it_raise_exception() throws Exception {

                assertThatThrownBy(
                        () -> {
                            service.findByCode(INVALID_CODE);
                        }
                ).isInstanceOf(TaskNotFoundException.class);

            }

        }

    }

    @Nested
    @DisplayName("update 메서드는")
    class Describe_update {

        TaskDto saved = null;

        @BeforeEach
        void setUp() {
            TaskDto dto = new TaskDto(
                    null,
                    "task 1",
                    "task 1",
                    1,
                    false,
                    LocalDateTime.now().toString(),
                    LocalDateTime.now().toString()
            );
            saved = service.saveTask(dto);
        }


        @Nested
        @DisplayName("변경될 task가 포함된 dto를 요청으로 받았다면")
        class Context_with_update_dto {

            TaskDto update = new TaskDto(
                    null,
                    "updated 1",
                    "updated 1",
                    1,
                    false,
                    LocalDateTime.now().toString(),
                    LocalDateTime.now().toString()
            );

            @Test
            @DisplayName("task를 수정한 다음 dto를 반환한다")
            void it_return_updated_task() throws Exception {
                update.setCode(saved.getCode());
                TaskDto updated = service.update(update);

                assertThat(updated.getCode()).isEqualTo(saved.getCode());
                assertThat(updated.getTitle()).isNotEqualTo(saved.getTitle());
                assertThat(updated.getDescription()).isNotEqualTo(saved.getDescription());
            }

        }

    }


}