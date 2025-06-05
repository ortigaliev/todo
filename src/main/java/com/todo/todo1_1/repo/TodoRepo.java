package com.todo.todo1_1.repo;

import com.todo.todo1_1.Entity.Todo;
import com.todo.todo1_1.dto.TodoDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepo extends CrudRepository<Todo, Long> {

    @Query("SELECT new com.todo.todo1_1.dto.TodoDto(t) FROM Todo t " +
            "WHERE t.user.id = :userId")
    List<TodoDto> findTodosByUserId(@Param("userId") Long userId);
}
