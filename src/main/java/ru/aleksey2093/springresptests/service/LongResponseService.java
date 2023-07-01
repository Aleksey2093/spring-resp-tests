package ru.aleksey2093.springresptests.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aleksey2093.springresptests.dto.DataRowRequest;
import ru.aleksey2093.springresptests.dto.DataRowTest;
import ru.aleksey2093.springresptests.dto.DataRowTestChild;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Random;
import java.util.UUID;

@Service
public class LongResponseService {

    Random random = new Random();

    ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper.copy();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @SneakyThrows
    public void asyncTest(DataRowRequest request, OutputStream outputStream) throws IOException {
        LocalDate start = request.getStart().toLocalDate();
        outputStream.write("[\n".getBytes(StandardCharsets.UTF_8));
        boolean first = true;
        for (int i = 0; i < request.getCount(); i++, start = start.plusDays(1)) {
            Thread.sleep(100);
            LocalDateTime nextDay = start.plusDays(1).atStartOfDay();
            for (LocalDateTime localTime = LocalTime.MIN.atDate(start); localTime.isBefore(nextDay);
                    localTime = localTime.plusHours(1)) {

                DataRowTest dataRowTest = DataRowTest.builder()
                        .uuid(UUID.randomUUID())
                        .t1(UUID.randomUUID().getLeastSignificantBits())
                        .t2(UUID.randomUUID().getMostSignificantBits())
                        .ts(localTime)
                        .build();

                if (random.nextBoolean()) {
                    dataRowTest.setChild(
                            DataRowTestChild.builder()
                                    .uuid(UUID.randomUUID())
                                    .t1(UUID.randomUUID().getLeastSignificantBits())
                                    .t2(UUID.randomUUID().getMostSignificantBits())
                                    .ts(localTime)
                                    .build()
                    );
                }

                if (first) {
                    first = false;
                } else {
                    outputStream.write("\n,\n".getBytes(StandardCharsets.UTF_8));
                }

                byte[] s = objectMapper.writeValueAsBytes(dataRowTest);

                outputStream.write(s);

            }
            outputStream.flush();
        }
        outputStream.write("]\n".getBytes(StandardCharsets.UTF_8));
    }


}
