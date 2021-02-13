package ru.dvfedotov.process;

import static org.camunda.spin.Spin.JSON;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.connect.Connectors;
import org.camunda.connect.httpclient.HttpConnector;
import org.camunda.connect.httpclient.HttpRequest;
import org.camunda.connect.httpclient.HttpResponse;
import org.camunda.spin.json.SpinJsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.dvfedotov.process.domain.Warrior;

@Component
public class TaskPrepareToBattle implements JavaDelegate {

    private final Logger log = LoggerFactory.getLogger(TaskPrepareToBattle.class);

    @Value("${maxWarriors}")
    private int maxWarriors;

    @Value("${url}")
    private String url;

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

    private Warrior create() {

        Warrior warrior = null;
        HttpConnector httpConnector = Connectors.getConnector(HttpConnector.ID);
        HttpRequest request = httpConnector.createRequest()
            .url(url)
            .get();
        Map headers = new HashMap<>();
        headers.put("Content-type", "application/json");

        request.setRequestParameter("headers", headers);

        HttpResponse response = request.execute();

        if (response.getStatusCode() == 200 || !response.getResponse().isEmpty()) {
            SpinJsonNode node = JSON(response.getResponse());
            warrior.setAlive(true);
            /*
            Первый способ инициализировать воина.
            warrior.setTitle(node.prop("name.title").stringValue());
            warrior.setName(node.prop("name.findName").stringValue());
            warrior.setHp(Integer.parseInt(node.prop("random.number").stringValue()));
            */

            warrior = JSON(response.getResponse()).mapTo(Warrior.class);
        }
        response.close();
        return warrior;
    }
}
