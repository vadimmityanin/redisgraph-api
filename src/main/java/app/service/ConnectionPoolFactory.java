package app.service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.util.Pool;

public class ConnectionPoolFactory {

    private static int MIN_IDLE = 8;
    private static int MAX_IDLE = 16;
    private static int MAX_TOTAL = 256;
    private static int PORT = 6379;
    private static int TIMEOUT = 60_000;
    private static String HOST = "localhost";

    private static Pool<Jedis> pool;

    static {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMinIdle(MIN_IDLE);
        poolConfig.setMaxIdle(MAX_IDLE);
        poolConfig.setMaxTotal(MAX_TOTAL);
        pool = new JedisPool(poolConfig, HOST, PORT, TIMEOUT);
    }

    private ConnectionPoolFactory() {

    }

    static Pool<Jedis> getConnectionPool() {
        return pool;
    }

}
