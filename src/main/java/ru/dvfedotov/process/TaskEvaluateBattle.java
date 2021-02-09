package ru.dvfedotov.process;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TaskEvaluateBattle implements JavaDelegate {

    private final Logger log = LoggerFactory.getLogger(TaskEvaluateBattle.class);

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        int warriors = (int) delegateExecution.getVariable("warriors");
        int enemyWarriors = (int) delegateExecution.getVariable("enemyWarriors");
        log.info("warriors = " + warriors);
        log.info("enemyWarriors = " + enemyWarriors);
        String battleStatus = "Undefined";
        boolean isWin = false;

        if ((warriors - enemyWarriors) > 1) {
            isWin = true;
            battleStatus = "Victory";
        }

        delegateExecution.setVariable("isWin", isWin);
        delegateExecution.setVariable("battleStatus", battleStatus);

    }
}
