package com.sam.sam_biz.customer;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "sam.app")
public class SamAppConfig {

    private String version;

    private Recon recon = new Recon();
    private Snow snow = new Snow();
    private Dfm dfm = new Dfm();

    public static class Recon extends ServerInfo {

    }

    public static class Snow extends ServerInfo {
    }

    public static class Dfm extends ServerInfo {
    }
}