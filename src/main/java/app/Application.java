package app;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import app.model.Item;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import app.service.Service;
import app.util.ItemGenerator;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Application {

    private static ObjectMapper om = new ObjectMapper();
    private static List<Item> items = new LinkedList<>();
    static {
        try {
            for (int i = 0; i <1000_000 ; i++) {
                items.add(ItemGenerator.generateRandomItem());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    private int totalItemNumber;
//
//    private ObjectMapper mapper;
//    private Service service;
//    private List<Item> itemList = new LinkedList<>();
//    private List<String> serializedItemList = new LinkedList<>();
//
//    private static final Logger logger = LoggerFactory.getLogger(Application.class);
//
//    public Application(int totalItemNumber) {
//        this.totalItemNumber = totalItemNumber;
//        mapper = new ObjectMapper();
//        mapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false);
//        service = new Service(Item.class, mapper);
//        prepareTestData(this.totalItemNumber);
//    }
//
//    private void prepareTestData(int size) {
//        try {
//            for (int i = 0; i < size; i++) {
//                Item item = ItemGenerator.generateRandomItem();
//                itemList.add(item);
//                serializedItemList.add(mapper.writeValueAsString(item));
//            }
//        } catch (JsonProcessingException jspe) {
//            throw new RuntimeException(jspe);
//        }
//    }
//
//    public ObjectMapper getMapper() {
//        return mapper;
//    }
//
//    public void setMapper(ObjectMapper mapper) {
//        this.mapper = mapper;
//    }
//
//    public Service getService() {
//        return service;
//    }
//
//    public void setService(Service service) {
//        this.service = service;
//    }
//
//    public void runNonSerializedObjectsSavingPerformanceTest() {
//        Long timeAtStart;
//        Long timeAtFinish;
//
//        logger.info("");
//        logger.info("Total object number: " + totalItemNumber);
//        logger.info("Start to save non serialized objects. Wait...");
//
//        timeAtStart = System.nanoTime();
//        service.createGraph("testGraph");
//        service.saveNodes("TestTable", itemList);
//        timeAtFinish = System.nanoTime();
//        service.deleteGraph();
//
//        Long nonSerializedObjectsSavingTime = timeAtFinish - timeAtStart;
//        logger.info("Non serialized objects saving time: " + nonSerializedObjectsSavingTime * 1e-9);
//    }
//
//    public void runSerializedObjectsSavePerformanceTest() {
//        Long timeAtStart;
//        Long timeAtFinish;
//
//        logger.info("");
//        logger.info("Total object number: " + totalItemNumber);
//        logger.info("Start to save serialized objects. Wait...");
//
//        timeAtStart = System.nanoTime();
//        service.createGraph("testGraph");
//        service.saveSerializedNodes("TestTable", serializedItemList);
//        timeAtFinish = System.nanoTime();
//        service.deleteGraph();
//
//        Long serializedObjectsSavingTime = timeAtFinish - timeAtStart;
//        logger.info("Serialized objects saving time: " + serializedObjectsSavingTime * 1e-9);
//    }
//
//    public void runSerializationPerformanceTest() throws JsonProcessingException {
//        List<String> testDataList = new LinkedList<>();
//        Long timeAtStart;
//        Long timeAtFinish;
//
//        logger.info("");
//        logger.info("Total object number: " + totalItemNumber);
//        logger.info("Start serialize objects. Wait...");
//
//        timeAtStart = System.nanoTime();
//        itemList.forEach((item) -> {
//            try {
//                testDataList.add(mapper.writeValueAsString(item));
//            } catch (JsonProcessingException jspe) {
//                throw new RuntimeException(jspe);
//            }
//        });
//        timeAtFinish = System.nanoTime();
//
//        logger.info("Objects serialization time: " + (timeAtFinish - timeAtStart) * 1e-9);
//    }

    @Benchmark
    @BenchmarkMode({Mode.SingleShotTime})
    @Warmup(iterations = 0)
    @Fork(4)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void testMethod() throws Exception {
        LinkedList<String> strings = new LinkedList<>();
        for (Item item : items) {
            Service.getSerialized(item);
        }

    }

    public static void main(String[] args) throws Exception {

//        app.Application application = new app.Application(1000_000);

//        application.runNonSerializedObjectsSavePerformanceTest();

//        application.runSerializedObjectsSavePerformanceTest();

//        for (int i = 0; i < 10; i ++) {
//            app.Application application = new app.Application(1000);
//            application.runSerializationPerformanceTest();
//            Thread.sleep(10000);
//        }
        Options opt = new OptionsBuilder()
                .include(Application.class.getSimpleName())
                .build();

        new Runner(opt).run();

    }
}
