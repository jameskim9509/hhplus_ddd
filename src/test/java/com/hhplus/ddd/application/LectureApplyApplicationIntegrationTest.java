package com.hhplus.ddd.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@SpringBootTest
@AutoConfigureTestDatabase(
        connection= EmbeddedDatabaseConnection.H2,
        replace = AutoConfigureTestDatabase.Replace.ANY
)
@TestPropertySource("classpath:application-test.properties")
public class LectureApplyApplicationIntegrationTest {
    @Autowired
    private LectureApplyApplication lectureApplyApplication;

    @DisplayName("동시에 동일한 특강 신청시 최대 30명")
    @Test
    void _40명의_사용자_동시_요청시_30명_성공()
    {
        List<CompletableFuture<Boolean>> futures = new ArrayList<>();
        for(int i = 0; i < 40; i++) {
            final Long userId = (long) (i + 1);
            futures.add(CompletableFuture.supplyAsync(
                    () -> {
                        try {
                            lectureApplyApplication.applyLecture(userId, 1L);
                            return true;
                        } catch (RuntimeException re) {
                            return false;
                        }
                    }
            ));
        }

        CompletableFuture[] futuresArray = futures.toArray(new CompletableFuture[futures.size()]);

        CompletableFuture.allOf(futuresArray)
                .thenRun(() ->
                {
                    Long success_count = futures.stream()
                            .filter(f -> {
                                try {
                                    return f.get();
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            })
                            .count();

                    Assertions.assertThat(success_count).isEqualTo(30);
                }).join();
    }

    @DisplayName("동일한 신청자가 동일한 강의를 5번 신청할 경우 1번만 성공")
    @Test
    void 동일한_신청자_같은_강의_5번_신청시_1번만_성공()
    {
        List<CompletableFuture<Boolean>> futures = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            futures.add(CompletableFuture.supplyAsync(
                    () -> {
                        try {
                            lectureApplyApplication.applyLecture(2L, 2L);
                            return true;
                        } catch (RuntimeException re) {
                            return false;
                        }
                    }
            ));
        }

        CompletableFuture[] futuresArray = futures.toArray(new CompletableFuture[futures.size()]);

        CompletableFuture.allOf(futuresArray)
                .thenRun(() ->
                {
                    Long success_count = futures.stream()
                            .filter(f -> {
                                try {
                                    return f.get();
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            })
                            .count();

                    Assertions.assertThat(success_count).isEqualTo(1);
                }).join();
    }
}
