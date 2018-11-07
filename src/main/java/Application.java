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

    public static final int TOTAL_ITEM_NUMBER = 25_000;

    private static ObjectMapper mapper;
    private static Service service;
    private static List<Item> itemList = new LinkedList<>();
    private static List<String> serializedItemList = new LinkedList<>();

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public Application() {
        mapper = new ObjectMapper();
        mapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false);
        service = new Service(Item.class, mapper);
        prepareTestData(TOTAL_ITEM_NUMBER);
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

    public static void main(String[] args) throws JsonProcessingException {
        Application example = new Application();
        Long timeAtStart;
        Long timeAtFinish;

        logger.info("");
        logger.info("Total object number: " + TOTAL_ITEM_NUMBER);
        logger.info("Start to save non serialized objects. Wait...");

        timeAtStart = System.nanoTime();
        example.getService().createGraph("testGraph");
        example.getService().saveNodes("TestTable1", itemList);
        timeAtFinish = System.nanoTime();
        Long nonSerializedObjectsSavingTime = timeAtFinish - timeAtStart;
        example.getService().deleteGraph();

        logger.info("Start to save serialized objects. Wait...");

        timeAtStart = System.nanoTime();
        example.getService().saveSerializedNodes("TestTable1", serializedItemList);
        timeAtFinish = System.nanoTime();
        Long serializedObjectsSavingTime = timeAtFinish - timeAtStart;
        example.getService().deleteGraph();

        Long serializationOverhead = nonSerializedObjectsSavingTime - serializedObjectsSavingTime;

        logger.info("Non serialized objects saving time: " + nonSerializedObjectsSavingTime * 1e-9);
        logger.info("Serialized objects saving time: " + serializedObjectsSavingTime * 1e-9);
        logger.info("Serialization overhead time: " + serializationOverhead * 1e-9);
    }
}
