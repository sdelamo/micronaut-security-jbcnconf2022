package example

import io.micronaut.context.ApplicationContext
import io.micronaut.security.authentication.AuthenticationProvider
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class CustomAuthenticationProviderSpec extends Specification {

    @Shared
    @AutoCleanup
    ApplicationContext applicationContext = ApplicationContext.run()

    void "there is an AuthenticationProvider bean"() {
        expect:
        applicationContext.containsBean(AuthenticationProvider)
    }
}
