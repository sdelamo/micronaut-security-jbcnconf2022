package example

import io.micronaut.context.ApplicationContext
import io.micronaut.inject.qualifiers.Qualifiers
import io.micronaut.security.token.jwt.signature.SignatureConfiguration
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class SignatureConfigurationSpec extends Specification {

    @AutoCleanup
    @Shared
    ApplicationContext applicationContext = ApplicationContext.run()

    void "SignatureConfiguration bean exists"() {
        expect:
        applicationContext.containsBean(SignatureConfiguration)

        and:
        applicationContext.containsBean(SignatureConfiguration, Qualifiers.byName("generator"))
    }
}
