package hello.springtx.apply;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@SpringBootTest
public class InternalCallV2Test {

    /**
     * InternalService클래스를 만들고 internal()메서드를 여기로 옮겼다.
     * 이렇게 메서드 내부 호출을 외부 호출로 변경.
     * CallService에는 트랜잭션 관련 코드가 전혀 없으므로 프랜잭션 프록시가 적용되지 않는다.
     * InternalService에는 트랜잭션 관련 코드가 있으므로 트랜잭션 프록시가 적용된다.
     */

    @Autowired
    CallService callService;

    @Test
    void externalCallV2() {
        callService.external();
    }

    @TestConfiguration
    static class InternalCallV2Config {

        @Bean
        CallService callService() {
            return new CallService(internalService());
        }

        @Bean
        InternalService internalService() {
            return new InternalService();
        }
    }

    @Slf4j
    @RequiredArgsConstructor
    static class CallService {

        private final InternalService internalService;

        public void external() {
            log.info("call external");
            printTxInfo();
            internalService.internal();
        }

        private void printTxInfo() {
            boolean txActive =
                    TransactionSynchronizationManager.isActualTransactionActive();
            log.info("tx active = {}", txActive);
        }

    }

    @Slf4j
    static class InternalService {

        @Transactional
        public void internal() {
            log.info("call internal");
            printTxInfo();
        }

        private void printTxInfo() {
            boolean txActive =
                    TransactionSynchronizationManager.isActualTransactionActive();
            log.info("tx active = {}", txActive);
        }
    }


}
