package com.itwang.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "url")
@Data
public class UrlWhiteListProperties {

    private List<String> whiteList;
}
