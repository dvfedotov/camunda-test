package ru.dvfedotov.process;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TaskTestError implements JavaDelegate {

    private final Logger log = LoggerFactory.getLogger(TaskTestError.class);


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        int warriors = (int) delegateExecution.getVariable("warriors");
        log.info("**************************Test Error warriors: " + warriors);
        if (warriors < 10) {
            log.info("Ups this is Exception in TaskTestError! ");
            throw new RuntimeException("***********New RuntimeException***********");
        }
    }
}
