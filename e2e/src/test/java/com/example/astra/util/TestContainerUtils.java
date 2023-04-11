package com.example.astra.util;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InspectContainerResponse;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.ContainerState;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;

import java.util.Optional;

public class TestContainerUtils {
    public static void linkContainers(
            DockerComposeContainer<?> dockerComposeContainer,
            GenericContainer<?> genericContainer,
            String serviceName
    ) {
        String networkId = findServiceNetworkId(dockerComposeContainer, serviceName);
        genericContainer.withNetwork(createNetwork(networkId));
    }

    private static String findServiceNetworkId(DockerComposeContainer<?> dockerComposeContainer, String serviceName) {
        Optional<ContainerState> containerName = dockerComposeContainer.getContainerByServiceName(serviceName);
        if (containerName.isPresent()) {
            DockerClient client = DockerClientFactory.instance().client();
            InspectContainerResponse containerInfo = containerName.get().getContainerInfo();
            Optional<String> networkName = containerInfo.getNetworkSettings().getNetworks().keySet().stream().findFirst();
            if (networkName.isPresent()) {
                return client.inspectNetworkCmd().withNetworkId(networkName.get()).exec().getId();
            }
        }
        return null;
    }

    private static Network createNetwork(String networkId) {
        return new Network() {
            @Override
            public String getId() {
                return networkId;
            }

            @Override
            public void close() {

            }

            @Override
            public Statement apply(Statement base, Description description) {
                return null;
            }
        };
    }
}
