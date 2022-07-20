package example

import io.micronaut.context.ApplicationContext

import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class PasswordEncoderSpec extends Specification {

    @Shared
    @AutoCleanup
    ApplicationContext applicationContext = ApplicationContext.run()

    void "there is an PasswordEncoder bean"() {
        expect:
        applicationContext.containsBean(PasswordEncoder)
    }
}
