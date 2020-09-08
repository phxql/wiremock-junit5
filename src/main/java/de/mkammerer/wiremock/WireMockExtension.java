package de.mkammerer.wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.Options;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * JUnit 5 extension which starts WireMock before the test run.
 * <p>
 * <p>
 * Usage:
 * <pre>
 * public class YourTest {
 *     &#64;RegisterExtension
 *     WireMockExtension wireMock = new WireMockExtension();
 *
 *     &#64;Test
 *     void test() {
 *         wireMock.stubFor(
 *             WireMock.get("/hello").willReturn(WireMock.ok("world"))
 *         );
 *
 *         URI uri = URI.create(String.format("http://localhost:%d/hello", wireMock.port()));
 *     }
 * }
 * </pre>
 */
public class WireMockExtension extends WireMockServer implements BeforeTestExecutionCallback, AfterTestExecutionCallback {
    /**
     * {@link WireMockServer#WireMockServer()}
     */
    public WireMockExtension() {
    }

    /**
     * {@link WireMockServer#WireMockServer(Options)}
     *
     * @param options options
     */
    public WireMockExtension(Options options) {
        super(options);
    }

    /**
     * {@link WireMockServer#WireMockServer(int)}
     *
     * @param port port
     */
    public WireMockExtension(int port) {
        super(port);
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        stop();
        resetAll();
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        start();
    }
}
