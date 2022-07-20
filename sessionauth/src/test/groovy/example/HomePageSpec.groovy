package example

import geb.spock.GebSpec
import io.micronaut.context.ApplicationContext
import io.micronaut.runtime.server.EmbeddedServer
import spock.lang.*

class HomePageSpec extends GebSpec {

    @AutoCleanup
    @Shared
    EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer)

    def "anonymous user can visit home page"() {
        given:
        browser.baseUrl = embeddedServer.URL.toString()

        when:
        to HomePage

        then:
        at HomePage

        when:
        HomePage homePage = browser.page(HomePage)

        then:
        !homePage.username
    }

}
