import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.Service;
import util.ItemGenerator;

import java.util.LinkedList;
import java.util.List;

public class Application {

    private int totalItemNumber;

    private ObjectMapper mapper;
    private Service service;
    private List<Item> itemList = new LinkedList<>();
    private List<String> serializedItemList = new LinkedList<>();

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public Application(int totalItemNumber) {
        this.totalItemNumber = totalItemNumber;
        mapper = new ObjectMapper();
        mapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false);
        service = new Service(Item.class, mapper);
        prepareTestData(this.totalItemNumber);
    }

    private void prepareTestData(int size) {
        try {
            for (int i = 0; i < size; i++) {
                Item item = ItemGenerator.generateRandomItem();
                itemList.add(item);
                serializedItemList.add(mapper.writeValueAsString(item));
            }
        } catch (JsonProcessingException jspe) {
            throw new RuntimeException(jspe);
        }
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public void runNonSerializedObjectsSavingPerformanceTest() {
        Long timeAtStart;
        Long timeAtFinish;

        logger.info("");
        logger.info("Total object number: " + totalItemNumber);
        logger.info("Start to save non serialized objects. Wait...");

        timeAtStart = System.nanoTime();
        service.createGraph("testGraph");
        service.saveNodes("TestTable", itemList);
        timeAtFinish = System.nanoTime();
        service.deleteGraph();

        Long nonSerializedObjectsSavingTime = timeAtFinish - timeAtStart;
        logger.info("Non serialized objects saving time: " + nonSerializedObjectsSavingTime * 1e-9);
    }

    public void runSerializedObjectsSavePerformanceTest() {
        Long timeAtStart;
        Long timeAtFinish;

        logger.info("");
        logger.info("Total object number: " + totalItemNumber);
        logger.info("Start to save serialized objects. Wait...");

        timeAtStart = System.nanoTime();
        service.createGraph("testGraph");
        service.saveSerializedNodes("TestTable", serializedItemList);
        timeAtFinish = System.nanoTime();
        service.deleteGraph();

        Long serializedObjectsSavingTime = timeAtFinish - timeAtStart;
        logger.info("Serialized objects saving time: " + serializedObjectsSavingTime * 1e-9);
    }

    public void runSerializationPerformanceTest() throws JsonProcessingException {
        List<String> testDataList = new LinkedList<>();
        Long timeAtStart;
        Long timeAtFinish;

        logger.info("");
        logger.info("Total object number: " + totalItemNumber);
        logger.info("Start serialize objects. Wait...");

        timeAtStart = System.nanoTime();
        itemList.forEach((item) -> {
            try {
                testDataList.add(mapper.writeValueAsString(item));
            } catch (JsonProcessingException jspe) {
                throw new RuntimeException(jspe);
            }
        });
        timeAtFinish = System.nanoTime();

        logger.info("Objects serialization time: " + (timeAtFinish - timeAtStart) * 1e-9);
    }

    public static void main(String[] args) throws JsonProcessingException, InterruptedException {

//        Application application = new Application(1000_000);

//        application.runNonSerializedObjectsSavePerformanceTest();

//        application.runSerializedObjectsSavePerformanceTest();

        for (int i = 0; i < 10; i ++) {
            Application application = new Application(1000);
            application.runSerializationPerformanceTest();
            Thread.sleep(10000);
        }
    }
}
