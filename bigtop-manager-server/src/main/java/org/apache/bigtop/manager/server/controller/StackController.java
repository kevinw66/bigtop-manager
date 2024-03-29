package org.apache.bigtop.manager.server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apache.bigtop.manager.server.model.vo.ServiceConfigVO;
import org.apache.bigtop.manager.server.model.vo.StackConfigVO;
import org.apache.bigtop.manager.server.model.vo.ServiceComponentVO;
import org.apache.bigtop.manager.server.model.vo.StackVO;
import org.apache.bigtop.manager.server.service.StackService;
import org.apache.bigtop.manager.server.utils.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Stack Controller")
@RestController
@RequestMapping("/stacks")
public class StackController {

    @Resource
    private StackService stackService;

    @Operation(summary = "list", description = "List stacks")
    @GetMapping
    public ResponseEntity<List<StackVO>> list() {
        return ResponseEntity.success(stackService.list());
    }

    @Operation(summary = "list", description = "List stacks components")
    @GetMapping("/{stackName}/{stackVersion}/components")
    public ResponseEntity<List<ServiceComponentVO>> components(@PathVariable String stackName, @PathVariable String stackVersion) {
        return ResponseEntity.success(stackService.components(stackName, stackVersion));
    }

    @Operation(summary = "list", description = "List stacks configurations")
    @GetMapping("/{stackName}/{stackVersion}/configurations")
    public ResponseEntity<List<ServiceConfigVO>> configurations(@PathVariable String stackName, @PathVariable String stackVersion) {
        return ResponseEntity.success(stackService.configurations(stackName, stackVersion));
    }
}
