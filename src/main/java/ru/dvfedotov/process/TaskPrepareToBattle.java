package ru.dvfedotov.process;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TaskPrepareToBattle implements JavaDelegate {

    private final Logger log = LoggerFactory.getLogger(TaskPrepareToBattle.class);

    @Value("${maxWarriors}")
    private int maxWarriors;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        int warriors = (int) delegateExecution.getVariable("warriors");
        int enemyWarriors = (int) (Math.random() * 100);

        maxWarriors = maxWarriors == 0 ? 100 : maxWarriors;

        if (warriors < 1 || warriors > 100) {
            throw new BpmnError("warriorsError");
        }

        List<Boolean> army = new ArrayList<>(Collections.nCopies(warriors, true));

        log.info("Prepare to battle! Enemy army =  " + enemyWarriors + " vs. our army: " + warriors);

        delegateExecution.setVariable("army", army);
        delegateExecution.setVariable("enemyWarriors", enemyWarriors);

    }
}
