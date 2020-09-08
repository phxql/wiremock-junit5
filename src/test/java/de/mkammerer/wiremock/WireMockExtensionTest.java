package de.mkammerer.wiremock;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class WireMockExtensionTest {
    @RegisterExtension
    WireMockExtension wireMock = new WireMockExtension();

    @Test
    void test() throws IOException {
        wireMock.stubFor(
            WireMock.get("/hello").willReturn(WireMock.ok("world"))
        );

        URL url = URI.create(String.format("http://localhost:%d/hello", wireMock.port())).toURL();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
            String line = reader.readLine();

            assertThat(line).isEqualTo("world");
        }
    }
}