package ru.dvfedotov.process;

import java.util.ArrayList;
import java.util.Random;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TaskFightEnemy implements JavaDelegate {

    private final Logger log = LoggerFactory.getLogger(TaskFightEnemy.class);

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        ArrayList<Boolean> army = (ArrayList<Boolean>) delegateExecution.getVariable("army");
        int enemyWarriors = (int) delegateExecution.getVariable("enemyWarriors");

        Thread.sleep(1000);

        if (new Random().nextBoolean()) {
            enemyWarriors--;
            log.info("Enemy warrior killed! Enemy = " + enemyWarriors);
        } else {
            army.remove(army.size() - 1);
            log.info("Our warrior killed! Warrior =" + army.size());
        }
        delegateExecution.setVariable("enemyWarriors", enemyWarriors);
        delegateExecution.setVariable("warriors", army.size());
        delegateExecution.setVariable("army", army);
    }
}
