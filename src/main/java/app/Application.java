package app;

import app.model.Item;
import app.service.SearchService;
import app.service.Service;
import app.util.ItemGenerator;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    @State(Scope.Benchmark)
    public static class TestData {
        private ObjectMapper mapper;
        private Service service;
        private List<Item> itemList = new LinkedList<>();
        private List<String> serializedItemList = new LinkedList<>();

        @Param({"1000"})
        private int totalItemNumber = 100_000;

        public TestData() {
            mapper = new ObjectMapper();
            mapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false);
            service = new Service(mapper);
            service.createGraph("test");
        }

        @Setup(Level.Invocation)
        public void prepareTestData() {
            try {
                for (int i = 0; i < totalItemNumber; i++) {
                    Item item = ItemGenerator.generateRandomItem();
                    itemList.add(item);
                    serializedItemList.add(mapper.writeValueAsString(item));
                }
            } catch (JsonProcessingException jspe) {
                throw new RuntimeException(jspe);
            }
        }
    }


    @Benchmark
    @Warmup(iterations = 3, time = 3)
    @Measurement(iterations = 5, time = 3)
    @BenchmarkMode(Mode.AverageTime)
    public void runSerializedObjectsSavePerformanceTest(TestData testData) {
        testData.service.createGraph("testGraph");
        testData.service.saveSerializedNodes("TestTable", testData.serializedItemList);
        testData.service.deleteGraph();
    }

//    @Benchmark
//    @Warmup(iterations = 2, time = 3)
//    @Measurement(iterations = 5, time = 3)
//    @BenchmarkMode(Mode.AverageTime)
//    public void runSerializationPerformanceTest(TestData testData) {
//        testData.itemList.forEach((item) -> {
//            try {
//                Service.getSerialized(item);
//            } catch (JsonProcessingException jspe) {
//                throw new RuntimeException(jspe);
//            }
//        });
//    }

    public static void main(String[] args) throws Exception {

//        for (int i = 0; i < 10; i ++) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    TestData td = new TestData();
//                    td.prepareTestData();
//                    new Application().runSerializedObjectsSavePerformanceTest(td);
//                }
//            }).start();
//        }

        SearchService.search();
//        Options opt = new OptionsBuilder()
//                .include(Application.class.getSimpleName())
//                .build();
//
//        new Runner(opt).run();
    }
}
