package ke.co.technovation.httputils;

import org.apache.commons.io.IOUtils;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Collection;

/**
 * @author allen
 */
public class SSLUtil {
    
    
    public static SSLContext getSSLContext(String protocol, InputStream serverCertificate, InputStream clientCert,
            InputStream clientKeyFileDerFormat, String keyPassword) throws Exception {
        SSLContext sslContext = SSLContext.getInstance(protocol);
        TrustManager[] trustManagers = getTrustManagers(getCertificate(serverCertificate));
        KeyManager[] keyManagers = getKeyManagers(clientCert, clientKeyFileDerFormat, keyPassword);
        sslContext.init(keyManagers, trustManagers, new SecureRandom());
        return sslContext;
    }

    /**
     * Get the TrustManagers given the given server certificate
     * 
     * @param certificate
     * @return
     * @throws Exception
     */
    private static TrustManager[] getTrustManagers(Certificate certificate) throws Exception {
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(null, null);
        ks.setCertificateEntry("serverCert", certificate);
        final TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(ks);
        X509TrustManager trustManager = new CustomTrustManager(tmf);
        return new TrustManager[] { trustManager };
    }

    /**
     * Allows for two way ssl connections
     * 
     * @param clientKeyFileDerFormat
     * @param clientCert
     * @return
     * @throws Exception
     */
    private static KeyManager[] getKeyManagers(InputStream clientCert, InputStream clientKeyFileDerFormat,
            String keyPassword) throws Exception {
        if (clientCert == null || clientKeyFileDerFormat == null)
            return new KeyManager[0];

        KeyStore clientKeyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        clientKeyStore.load(null, null);

        byte[] key = IOUtils.toByteArray(clientKeyFileDerFormat);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey ff = kf.generatePrivate(new PKCS8EncodedKeySpec(key));

        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        byte[] certBytes = IOUtils.toByteArray(clientCert);

        // Handle cert chains vs single cert.
        Collection c = cf.generateCertificates(new ByteArrayInputStream(certBytes));
        Certificate[] certs;
        if (c.size() == 1) {
            certs = new Certificate[] { getCertificate(new ByteArrayInputStream(certBytes)) };
        } else {
            certs = (Certificate[]) c.toArray();
        }

        clientKeyStore.setKeyEntry("clientKeyCert", ff, keyPassword.toCharArray(), certs);
        keyManagerFactory.init(clientKeyStore, keyPassword.toCharArray());

        return keyManagerFactory.getKeyManagers();
    }

    private static Certificate getCertificate(InputStream certificateStream) throws Exception {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        return certificateFactory.generateCertificate(certificateStream);
    }
}