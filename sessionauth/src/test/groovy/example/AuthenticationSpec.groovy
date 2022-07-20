package example

import geb.spock.GebSpec
import io.micronaut.context.ApplicationContext
import io.micronaut.runtime.server.EmbeddedServer
import org.openqa.selenium.Cookie
import spock.lang.AutoCleanup
import spock.lang.Shared

class AuthenticationSpec extends GebSpec {

    @AutoCleanup
    @Shared
    EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer)

    ApplicationContext getApplicationContext() {
        embeddedServer.applicationContext
    }

    @Shared
    UserRepository userRepository = applicationContext.getBean(UserRepository)

    def "user can authenticate"() {
        given:
        String username = 'john'
        String password = 'aegon'

        browser.baseUrl = embeddedServer.URL.toString()

        expect:
        !browser.driver.manage().cookies.find { cookie -> cookie.name == 'SESSION' }

        when:
        userRepository.save(username, password)

        then:
        userRepository.count() == old(userRepository.count()) + 1

        when:
        to LoginPage

        then:
        at LoginPage

        when:
        LoginPage loginPage = browser.page(LoginPage)
        loginPage.login(username, password)

        then:
        at HomePage

        when:
        HomePage homePage = browser.page(HomePage)

        then:
        homePage.username == username

        when:
        Set<Cookie> cookies = browser.driver.manage().cookies

        then:
        cookies.find { cookie -> cookie.name == 'SESSION' }

        when:
        homePage.logout()

        then:
        at HomePage

        and:
        !homePage.username

        cleanup:
        userRepository.delete(username)
    }
}
