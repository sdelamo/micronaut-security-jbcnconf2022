package example

import io.micronaut.context.ApplicationContext
import io.micronaut.security.handlers.LoginHandler
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class LoginHandlerSpec extends Specification {

    @AutoCleanup
    @Shared
    ApplicationContext applicationContext = ApplicationContext.run()

    def "LoginHandler exists"() {
        expect:
        applicationContext.containsBean(LoginHandler)
    }
}
