package org.apache.bigtop.manager.stack.common.repo;

import com.google.auto.service.AutoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.bigtop.manager.common.utils.shell.ShellExecutor;
import org.apache.bigtop.manager.common.utils.shell.ShellResult;
import org.apache.bigtop.manager.stack.common.enums.PackageManagerType;
import org.apache.bigtop.manager.stack.common.exception.StackException;
import org.apache.bigtop.manager.stack.spi.PackageManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@AutoService(PackageManager.class)
public class AptPackageManager implements PackageManager {

    private static final String APT = "/usr/bin/apt";
    private static final String APT_GET = "/usr/bin/apt-get";

    @Override
    public ShellResult installPackage(Collection<String> packages) {
        List<String> builderParameters = new ArrayList<>();
        builderParameters.add(APT_GET);

        builderParameters.add("install");

        builderParameters.add("-y");

        builderParameters.addAll(packages);

        log.debug("builderParameters: {}", builderParameters);

        try {
            ShellResult output = ShellExecutor.execCommand(builderParameters);
            log.info("[AptPackageManager] [installPackage] output: {}", output);
            return output;
        } catch (IOException e) {
            throw new StackException(e);
        }
    }

    @Override
    public ShellResult uninstallPackage(Collection<String> packages) {
        List<String> builderParameters = new ArrayList<>();
        builderParameters.add(APT_GET);

        builderParameters.add("remove");

        builderParameters.add("-y");

        builderParameters.addAll(packages);

        log.debug("builderParameters: {}", builderParameters);

        try {
            ShellResult output = ShellExecutor.execCommand(builderParameters);
            log.info("[AptPackageManager] [uninstallPackage] output: {}", output);
            return output;
        } catch (IOException e) {
            throw new StackException(e);
        }
    }

    @Override
    public String listPackages() {
        List<String> builderParameters = new ArrayList<>();
        builderParameters.add(APT);

        builderParameters.add("list");

        log.debug("builderParameters: {}", builderParameters);

        try {
            ShellResult output = ShellExecutor.execCommand(builderParameters);
            log.info("[AptPackageManager] [listPackages] output: {}", output);
            return output.getOutput();
        } catch (IOException e) {
            throw new StackException(e);
        }
    }

    @Override
    public String getName() {
        return PackageManagerType.APT.name();
    }
}
