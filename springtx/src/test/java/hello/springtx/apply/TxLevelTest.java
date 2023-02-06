package hello.springtx.apply;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@SpringBootTest
public class TxLevelTest {

    /**
     * @Transactional 엔 두가지 규칙이 잇다.
     * 1. 우선순위 규칙
     * 2. 클래스에 적용하면 메서드는 모두 작용
     *
     * 인터페이스에도 @Transactional을 적용할 수 있다. 이 경우 구체적인 것이 더 높은 우선순위를 가진다.
     * 1. 클래스의 메서드 (우선순위가 가장 높음.)
     * 2. 클래스의 타입
     * 3. 인터페이스의 메서드
     * 4. 인터페이스의 타입 (우선순위가 가장 낮음.)
     */

    @Autowired
    LevelService service;

    @Test
    void orderTest() {
        service.write();
        service.read();
    }

    @TestConfiguration
    static class TxApplyLevelConfig {

        @Bean
        LevelService levelService() {
            return new LevelService();
        }
    }

    @Slf4j
    @Transactional(readOnly = true)
    static class LevelService {

        @Transactional(readOnly = false)
        public void write() {
            log.info("call write");
        printTxInfo();
        }

        public void read() {
            log.info("call read");
            printTxInfo();
        }

        private void printTxInfo() {
            boolean txActive =
                    TransactionSynchronizationManager.isActualTransactionActive();
            log.info("tx active = {}", txActive);

            boolean readOnly =
                    TransactionSynchronizationManager.isCurrentTransactionReadOnly();
            log.info("tx readOnly = {}", readOnly);
        }
    }

}
