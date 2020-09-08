package de.mkammerer.wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.common.Notifier;
import com.github.tomakehurst.wiremock.common.ProxySettings;
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
     */
    public WireMockExtension(Options options) {
        super(options);
    }

    /**
     * {@link WireMockServer#WireMockServer(int, Integer, FileSource, boolean, ProxySettings, Notifier)}
     */
    public WireMockExtension(int port, Integer httpsPort, FileSource fileSource, boolean enableBrowserProxying, ProxySettings proxySettings, Notifier notifier) {
        super(port, httpsPort, fileSource, enableBrowserProxying, proxySettings, notifier);
    }

    /**
     * {@link WireMockServer#WireMockServer(int, FileSource, boolean, ProxySettings)}
     */
    public WireMockExtension(int port, FileSource fileSource, boolean enableBrowserProxying, ProxySettings proxySettings) {
        super(port, fileSource, enableBrowserProxying, proxySettings);
    }

    /**
     * {@link WireMockServer#WireMockServer(int, FileSource, boolean)}
     */
    public WireMockExtension(int port, FileSource fileSource, boolean enableBrowserProxying) {
        super(port, fileSource, enableBrowserProxying);
    }

    /**
     * {@link WireMockServer#WireMockServer(int)}
     */
    public WireMockExtension(int port) {
        super(port);
    }

    /**
     * {@link WireMockServer#WireMockServer(int, Integer)}
     */
    public WireMockExtension(int port, Integer httpsPort) {
        super(port, httpsPort);
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
