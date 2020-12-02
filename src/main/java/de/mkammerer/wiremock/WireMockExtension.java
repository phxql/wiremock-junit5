package de.mkammerer.wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.common.Notifier;
import com.github.tomakehurst.wiremock.common.ProxySettings;
import com.github.tomakehurst.wiremock.core.Options;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.net.URI;

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
 *         URI uri = wireMock.getBaseUri().resolve("/hello");
 *
 *         // Now make your call against uri, wiremock will answer with "world"
 *     }
 * }
 * </pre>
 */
public class WireMockExtension extends WireMockServer implements BeforeEachCallback, AfterEachCallback {
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
    public void afterEach(ExtensionContext context) throws Exception {
        stop();
        resetAll();
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        start();
    }

    /**
     * Returns the base URI where the wiremock server is running.
     *
     * @return the base uri where the wiremock server is running
     * @throws IllegalStateException if wiremock server is not running or HTTP is disabled
     */
    public URI getBaseUri() {
        // Use https only if http is disabled and https is enabled
        boolean https = this.options.getHttpDisabled() && this.options.httpsSettings().enabled();

        return URI.create(String.format(
            "%s://localhost:%d",
            https ? "https" : "http",
            https ? httpsPort() : port()
        ));
    }

    /**
     * Returns the HTTPS base URI where the wiremock server is running.
     *
     * @return the HTTPS base uri where the wiremock server is running
     * @throws IllegalStateException if wiremock server is not running or HTTPS is not enabled
     */
    public URI getBaseUriHttps() {
        return URI.create(String.format("https://localhost:%d", httpsPort()));
    }
}
