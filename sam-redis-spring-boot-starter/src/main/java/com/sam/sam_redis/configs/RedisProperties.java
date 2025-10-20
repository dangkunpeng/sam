// redis-spring-boot-starter/src/main/java/com/example/redisstarter/RedisProperties.java
package com.sam.sam_redis.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.redis")
public class RedisProperties {
    private String host = "localhost";
    private int port = 6379;
    private String password="samRedis";

    public String getHost() { return host; }
    public void setHost(String host) { this.host = host; }
    public int getPort() { return port; }
    public void setPort(int port) { this.port = port; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
