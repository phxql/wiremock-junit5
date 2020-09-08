# WireMock JUnit 5 Extension

## What is this?

A JUnit 5 extension for [WireMock](http://wiremock.org/) - the JUnit 5 equivalent of [`@WireMockRule`](http://wiremock.org/docs/junit-rule/).

## How to use

Include the dependency in your build:

```xml
<dependency>
  <groupId>de.mkammerer.wiremock-junit5</groupId>
  <artifactId>wiremock-junit5</artifactId>
  <version>1.0.0</version>
</dependency>
```

then use `WireMockExtension` in your code:

```java
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import java.net.URI;

public class YourTest {
    @RegisterExtension
    WireMockExtension wireMock = new WireMockExtension();
     
    @Test
    void test() {
        wireMock.stubFor(
            WireMock.get("/hello").willReturn(WireMock.ok("world"))
        );
        URI uri = URI.create(String.format("http://localhost:%d/hello", wireMock.port()));
    }
}
```

The extension exposes the same API as `WireMockServer`.

## License

[LGPLv3](https://www.gnu.org/licenses/lgpl-3.0.html)