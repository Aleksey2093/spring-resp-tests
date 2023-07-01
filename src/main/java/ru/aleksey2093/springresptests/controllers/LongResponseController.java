package ru.aleksey2093.springresptests.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.aleksey2093.springresptests.dto.DataRowRequest;
import ru.aleksey2093.springresptests.dto.DataRowTest;
import ru.aleksey2093.springresptests.service.LongResponseService;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping(path = "long-response-controller")
public class LongResponseController {

    @Autowired
    private LongResponseService longResponseService;

    @GetMapping(path = "async",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @Operation(
            summary = "Тест длинного запроса",
            description = "Тест длинного запроса")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Операция выполнена успешно",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = DataRowTest.class))))
    })
    public void asyncTest(@RequestParam int count, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        httpServletResponse.addHeader("Content-Type", MediaType.APPLICATION_OCTET_STREAM_VALUE);
        AsyncContext asyncContext = httpServletRequest.startAsync();
        asyncContext.start(() -> {
            try {
                DataRowRequest build = DataRowRequest.builder().count(count).start(LocalDateTime.now()).build();
                longResponseService.asyncTest(build, httpServletResponse.getOutputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                asyncContext.complete();
            }
        });
    }


}
