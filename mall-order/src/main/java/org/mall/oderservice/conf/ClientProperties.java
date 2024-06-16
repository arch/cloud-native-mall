package org.mall.oderservice.conf;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;

@ConfigurationProperties(prefix = "mall")
public record ClientProperties(@NotNull URI catalogServiceUri) {
}