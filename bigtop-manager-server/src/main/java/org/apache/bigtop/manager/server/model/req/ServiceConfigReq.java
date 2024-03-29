package org.apache.bigtop.manager.server.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class ServiceConfigReq {

    @NotBlank
    @Schema(example = "ZOOKEEPER")
    private String serviceName;

    private String configDesc;

    private List<@Valid TypeConfigReq> configs;
}

