package example

import io.micronaut.context.ApplicationContext
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.runtime.server.EmbeddedServer
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification


class BookControllerSpec extends Specification {

    @AutoCleanup
    @Shared
    EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer)

    @AutoCleanup
    @Shared
    HttpClient httpClient = embeddedServer.applicationContext.createBean(HttpClient, embeddedServer.URL)

    BlockingHttpClient getClient() {
        httpClient.toBlocking()
    }

    void "/books endpoint is available for unauthenticated users"() {
        given:
        String username = "sherlock";
        String password = "elementary";

        when:
        HttpRequest req = HttpRequest.GET("/books")
            .basicAuth(username, password)
            //.header(HttpHeaders.AUTHORIZATION, "Basic " + new String(Base64.encoder.encode((username + ":" + password).bytes)))
        HttpResponse<List<Book>> resp = client.exchange(req, Argument.of(List, Book))

        then:
        noExceptionThrown()
        resp.status() == HttpStatus.OK

        when:
        List<Book> books = resp.body()

        then:
        books
        books.size() == 3
        books.find { new Book("1491950358", "Building Microservices") }
        books.find { new Book("1680502395", "Release It!") }
        books.find { new Book("0321601912", "Continuous Delivery") }
    }
}
