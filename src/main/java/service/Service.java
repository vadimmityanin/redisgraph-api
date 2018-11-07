package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redislabs.redisgraph.RedisGraphAPI;
import com.redislabs.redisgraph.ResultSet;

import java.util.List;
import java.util.Optional;

public class Service {

    private ObjectMapper objectMapper;
    private Class clazz;
    private static RedisGraphAPI api;

    public Service(Class clazz, ObjectMapper objectMapper) {
        this.clazz = clazz;
        this.objectMapper = objectMapper;
    }

    public <T> void saveNode(String tableName, T node) {
        try {
            String serializedNode = objectMapper.writeValueAsString(node);
            saveSerializedNode(tableName, serializedNode);
        } catch (JsonProcessingException jspe) {
            throw new RuntimeException(jspe);
        }
    }

    public void saveSerializedNode(String tableName, String serializedNode) {
        api.query("CREATE (:" + tableName + serializedNode + ")");
    }

    public <T> void saveNodes(String tableName, List<T> nodes) {
        nodes.forEach((node) -> saveNode(tableName, node));
    }

    public void saveSerializedNodes(String tableName, List<String> nodes) {
        nodes.forEach((node) -> saveSerializedNode(tableName, node));
    }

    public void createGraph(String name) {
        api = new RedisGraphAPI(name);
    }

    public void deleteGraph() {
        api.deleteGraph();
    }

    public Optional<String> getNodeByProperty(String tableName, String propertyName, String propertyValue) {
        RedisGraphAPI api = new RedisGraphAPI("localhost");
        ResultSet result = api.query("MATCH (object:" + tableName + " {" + propertyName + ":" + propertyValue + "}) RETURN object");
        return result.hasNext() ? Optional.of(result.next().toString()) : Optional.empty();
    }
}
