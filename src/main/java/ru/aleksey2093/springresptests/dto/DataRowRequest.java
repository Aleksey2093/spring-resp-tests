package ru.aleksey2093.springresptests.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@Schema(description = "Запрос")
public class DataRowRequest {

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, minimum = "1")
    @NotNull
    @Min(1)
    private int count;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, pattern = "yyyy-MM-dd HH:mm")
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime start;

}
