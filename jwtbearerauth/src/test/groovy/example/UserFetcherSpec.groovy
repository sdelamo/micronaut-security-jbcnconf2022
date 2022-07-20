package example

import io.micronaut.context.ApplicationContext
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification


class UserFetcherSpec extends Specification {

    @Shared
    @AutoCleanup
    ApplicationContext applicationContext = ApplicationContext.run()

    void "there is an UserFetcher bean"() {
        expect:
        applicationContext.containsBean(UserFetcher)
    }
}
