package example

import example.entities.Role
import io.micronaut.context.ApplicationContext
import io.micronaut.core.type.Argument
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

    ApplicationContext getApplicationContext() {
        embeddedServer.applicationContext
    }

    @AutoCleanup
    @Shared
    HttpClient httpClient = applicationContext.createBean(HttpClient, embeddedServer.URL)

    BlockingHttpClient getClient() {
        httpClient.toBlocking()
    }

    void "there is an BookController bean"() {
        expect:
        applicationContext.containsBean(BookController)
    }

    void "/books endpoint is available for unauthenticated users"() {
        given:
        String authority = "ROLE_USER"
        String username = "john"
        String password = "aeon"

        when:
        UserRepository userRepository = applicationContext.getBean(UserRepository)

        then:
        noExceptionThrown()

        when:
        RoleRepository roleRepository = applicationContext.getBean(RoleRepository)

        then:
        noExceptionThrown()

        when:
        userRepository.save(username, password)

        then:
        userRepository.count() == old(userRepository.count()) + 1

        when:
        Role role = roleRepository.save(authority)

        then:
        roleRepository.count() == old(roleRepository.count()) + 1

        when:
        userRepository.addUserRole(username, role)

        then:
        userRepository.findRolesByUsername(username).size() == old(userRepository.findRolesByUsername(username).size()) + 1

        when:
        HttpRequest req = HttpRequest.GET("/books").basicAuth(username, password)
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

        cleanup:
        userRepository.delete(username)
        roleRepository.delete(authority)
    }
}
