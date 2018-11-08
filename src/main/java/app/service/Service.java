package app.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redislabs.redisgraph.RedisGraphAPI;
import com.redislabs.redisgraph.ResultSet;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.util.Pool;

import java.util.List;
import java.util.Optional;

public class Service {

    private static ObjectMapper objectMapper;
    private static RedisGraphAPI api;
    private static Pool<Jedis> connectionPool = ConnectionPoolFactory.getConnectionPool();

    public Service(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> void saveNode(String tableName, T node) {
        try {
            String serializedNode = getSerialized(node);
            saveSerializedNode(tableName, serializedNode);
        } catch (JsonProcessingException jspe) {
            throw new RuntimeException(jspe);
        }
    }

    public static <T> String getSerialized(T node) throws JsonProcessingException {
        return objectMapper.writeValueAsString(node);
    }

    public void saveSerializedNode(String tableName, String serializedNode) {
        api.query("CREATE (:" + tableName + serializedNode + ")");
    }

    public <T> void saveNodes(String tableName, List<T> nodes) {
        nodes.forEach((node) -> saveNode(tableName, node));
    }

    public void saveSerializedNodes(String tableName, List<String> nodes) {
        StringBuilder query = new StringBuilder("CREATE ");
        nodes.forEach((node) -> {
            query.append("(")
                    .append(tableName)
                    .append(node)
                    .append("),");
        });
        api.query(query.replace(query.length() - 1, query.length(), "").toString());
    }

    public void createGraph(String name) {
        api = new RedisGraphAPI(name, connectionPool);
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
