package Util;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

public class Redis {
    static final String IP = "192.168.43.154";
    static final int PORT = 9002;

    public static String getValue(String key) {

        Set<HostAndPort> jedisClusterNode = new HashSet<HostAndPort>();
        jedisClusterNode.add(new HostAndPort(IP, PORT));
        JedisCluster jedis = new JedisCluster(jedisClusterNode);
        String Value = jedis.get(key);
        jedis.close();
        return Value;
    }

    public static void setValue(String key, String value) {
        Set<HostAndPort> jedisClusterNode = new HashSet<HostAndPort>();
        jedisClusterNode.add(new HostAndPort(IP, PORT));
        JedisCluster jedis = new JedisCluster(jedisClusterNode);
        String Value = jedis.setex(key, 2, value);
        jedis.close();
    }
}
