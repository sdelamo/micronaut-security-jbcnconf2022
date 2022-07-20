package example

import io.micronaut.context.ApplicationContext
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class AuthoritiesFetcherSpec extends Specification {

    @Shared
    @AutoCleanup
    ApplicationContext applicationContext = ApplicationContext.run()

    void "there is an AuthoritiesFetcher bean"() {
        expect:
        applicationContext.containsBean(AuthoritiesFetcher)
    }
}
