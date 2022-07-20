package example

import geb.spock.GebSpec
import io.micronaut.context.ApplicationContext
import io.micronaut.runtime.server.EmbeddedServer
import spock.lang.AutoCleanup
import spock.lang.Shared

class LoginPageSpec extends GebSpec {

    @AutoCleanup
    @Shared
    EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer)

    def "anonymous user can visit home page"() {
        given:
        browser.baseUrl = embeddedServer.URL.toString()

        when:
        to LoginPage

        then:
        at LoginPage
    }

}
