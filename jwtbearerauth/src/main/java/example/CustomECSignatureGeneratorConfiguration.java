package example;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.JWK;
import io.micronaut.security.token.jwt.signature.ec.ECSignatureGeneratorConfiguration;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.core.annotation.Nullable;
import jakarta.inject.Singleton;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.text.ParseException;

@Named("generator")
@Singleton
public class CustomECSignatureGeneratorConfiguration implements ECSignatureGeneratorConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(CustomECSignatureGeneratorConfiguration.class);
    @Nullable
    private ECPublicKey ecPublicKey;

    @Nullable
    private ECPrivateKey ecPrivateKey;

    private JWSAlgorithm jwsAlgorithm = JWSAlgorithm.ES256;

    public CustomECSignatureGeneratorConfiguration(AppConfiguration appConfiguration) {

        try {
            JWK privateJWK = JWK.parse(appConfiguration.getPrivateKey());
            if (privateJWK instanceof ECKey) {
                ECKey ecKey = (ECKey) privateJWK;
                ecPrivateKey = ecKey.toECPrivateKey();
            }
            JWK publicJWK = JWK.parse(appConfiguration.getPublicKey());
            if (publicJWK instanceof ECKey) {
                ECKey ecKey = (ECKey) publicJWK;
                ecPublicKey = ecKey.toECPublicKey();
            }

        } catch (JOSEException e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("jose exception: {}", e.getMessage());
            }
        } catch (ParseException e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("parse exception: {}", e);
            }
        }
    }

    @Override
    public ECPrivateKey getPrivateKey() {
        return ecPrivateKey;
    }

    @Override
    public ECPublicKey getPublicKey() {
        return ecPublicKey;
    }

    @Override
    public JWSAlgorithm getJwsAlgorithm() {
        return jwsAlgorithm;
    }
}
