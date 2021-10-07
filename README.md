# WireMock JUnit 5 Extension

## What is this?

A JUnit 5 extension for [WireMock](http://wiremock.org/) - the JUnit 5 equivalent of [`@WireMockRule`](http://wiremock.org/docs/junit-rule/).

Note that as of 2.31.0 there's support for JUnit5 in WireMock itself: http://wiremock.org/docs/junit-jupiter/

## How to use

Include the dependency in your build:

```xml
<dependency>
  <groupId>de.mkammerer.wiremock-junit5</groupId>
  <artifactId>wiremock-junit5</artifactId>
  <version>1.1.0</version>
  <scope>test</scope>
</dependency>
```

then use `WireMockExtension` in your code:

```java
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import java.net.URI;
import de.mkammerer.wiremock.WireMockExtension;

public class YourTest {
    @RegisterExtension
    WireMockExtension wireMock = new WireMockExtension();

    @Test
    void test() {
        wireMock.stubFor(
            WireMock.get("/hello").willReturn(WireMock.ok("world"))
        );
        URI uri = wireMock.getBaseUri().resolve("/hello");
        
        // Now make your call against uri, wiremock will answer with "world"
    }
}
```

The extension exposes the same API as `WireMockServer`.

See the [test](src/test/java/de/mkammerer/wiremock/WireMockExtensionTest.java) as a reference.

## Changelog

The [changelog can be found here](CHANGELOG.md).

## License

[LGPLv3](https://www.gnu.org/licenses/lgpl-3.0.html)
