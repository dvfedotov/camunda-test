package ru.dvfedotov.process;

import static org.camunda.spin.Spin.JSON;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.camunda.connect.Connectors;
import org.camunda.connect.httpclient.HttpConnector;
import org.camunda.connect.httpclient.HttpRequest;
import org.camunda.connect.httpclient.HttpResponse;
import org.camunda.spin.SpinList;
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

        if (warriors < 1 || warriors > maxWarriors) {
            throw new BpmnError("warriorsError");
        }

        List<Warrior> army = new ArrayList<>();

        for (int i = 0; i <= warriors; i++) {
            army.add(create());
        }

        log.info("Prepare to battle! Enemy army =  " + enemyWarriors + " vs. our army: " + warriors);

//        ObjectValue jsonArmy = Variables.objectValue(army).serializationDataFormat("application/json").create();
        /*
        при добавлении в  application.yaml
        camunda:
             bpm:
                default-serelization-format: application/json
         */
        ObjectValue jsonArmy = Variables.objectValue(army).create();

        delegateExecution.setVariable("army", army);
        delegateExecution.setVariable("jsonArmy", jsonArmy);
        delegateExecution.setVariable("enemyWarriors", enemyWarriors);

    }


    private Warrior create() throws IOException {
        log.info("*****************Create warriors******************");
        Warrior warrior = new Warrior();
        HttpConnector httpConnector = Connectors.getConnector(HttpConnector.ID);
        HttpRequest request = httpConnector.createRequest()
            .url(url)
            .get();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-type", "application/json");

        request.setRequestParameter("headers", headers);

        HttpResponse response = request.execute();
        log.info("JSON = " + response.getResponse());

        if (response.getStatusCode() == 200 || !response.getResponse().isEmpty()) {

            SpinJsonNode node = JSON(response.getResponse());
            SpinJsonNode customerProperty = node.prop("data");
            SpinList customers = customerProperty.elements();
//            SpinJsonNode customer = (SpinJsonNode)customers.get(0);
//            warrior.setFirstName(customer.prop("firstname").stringValue());
//            warrior.setLastName(customer.prop("lastname").stringValue());
//            warrior.setUuid(customer.prop("uuid").stringValue());
            warrior = JSON(customers.get(0)).mapTo(Warrior.class);
            warrior.setAlive(true);
            warrior.setHp((int) (Math.random() * 100));
//            List<HashMap<String, String>> data = node.prop("data").mapTo(ArrayList.class);
//            Map<String, String> warriors = Collections.unmodifiableMap( data.get(0));
//            warrior.setAlive(true);
//            warrior.setFirstName(warriors.get("firstname"));
//            warrior.setLastName(warriors.get("lastname"));
//            warrior.setUuid(warriors.get("uuid"));
//            warrior.setHp((int) (Math.random() * 100));

            log.info("Create warrior = " + warrior);
        }
        response.close();
        return warrior;
    }
}
