package example

import io.micronaut.context.ApplicationContext
import io.micronaut.security.token.jwt.signature.ec.ECSignatureGeneratorConfiguration
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class ECSignatureGeneratorConfigurationSpec extends Specification {

    @AutoCleanup
    @Shared
    ApplicationContext applicationContext = ApplicationContext.run()


    void "ECSignatureGeneratorConfiguration bean exists"() {
        expect:
        applicationContext.containsBean(ECSignatureGeneratorConfiguration)
        applicationContext.getBean(CustomECSignatureGeneratorConfiguration)
    }
}
