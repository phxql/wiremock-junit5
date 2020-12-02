package de.mkammerer.wiremock;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class WireMockExtensionTest {
    @RegisterExtension
    WireMockExtension wireMock = new WireMockExtension();

    @Test
    void test() throws IOException {
        wireMock.stubFor(
            WireMock.get("/hello").willReturn(WireMock.ok("world"))
        );

        URL url = wireMock.getBaseUri().resolve("/hello").toURL();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
            String line = reader.readLine();

            assertThat(line).isEqualTo("world");
        }
    }

    @Nested
    public class HttpOnlyTest {
        @RegisterExtension
        WireMockExtension wireMock = new WireMockExtension(33533);

        @Test
        void getBaseUri() {
            assertThat(wireMock.getBaseUri()).isEqualTo(URI.create("http://localhost:33533"));
        }

        @Test
        void getBaseUriHttps() {
            assertThatThrownBy(() ->
                wireMock.getBaseUriHttps()
            ).isInstanceOf(IllegalStateException.class);
        }
    }

    @Nested
    public class HttpsOnlyTest {
        @RegisterExtension
        WireMockExtension wireMock = new WireMockExtension(WireMockConfiguration.options().httpDisabled(true).httpsPort(33433));

        @Test
        void getBaseUri() {
            assertThat(wireMock.getBaseUri()).isEqualTo(URI.create("https://localhost:33433"));
        }

        @Test
        void getBaseUriHttps() {
            assertThat(wireMock.getBaseUriHttps()).isEqualTo(URI.create("https://localhost:33433"));
        }
    }

    @Nested
    public class HttpAndHttpsTest {
        @RegisterExtension
        WireMockExtension wireMock = new WireMockExtension(33533, 33433);

        @Test
        void getBaseUri() {
            assertThat(wireMock.getBaseUri()).isEqualTo(URI.create("http://localhost:33533"));
        }

        @Test
        void getBaseUriHttps() {
            assertThat(wireMock.getBaseUriHttps()).isEqualTo(URI.create("https://localhost:33433"));
        }
    }
}