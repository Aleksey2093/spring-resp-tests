package ru.aleksey2093.springresptests.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@Schema(description = "Тестовые данные")
public class DataRowTestChild {

    @Schema(description = "время", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime ts;

    @Schema(description = "что-то")
    private UUID uuid;

    @Schema(description = "t1 поле")
    private long t1;

    @Schema(description = "t2 поле")
    private long t2;

}
