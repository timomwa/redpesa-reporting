package ke.co.technovation.httputils;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Allows us to use a self signed cert as the underlying trust manager.
 * @author allen
 */
public class CustomTrustManager implements X509TrustManager
{

    private X509TrustManager trustManager;

    public CustomTrustManager(TrustManagerFactory tmf)
    {
        super();
        for(TrustManager manager : tmf.getTrustManagers())
        {
            if(manager instanceof X509TrustManager)
            {
                trustManager = ((X509TrustManager) manager);
                return;
            }
        }

        throw new RuntimeException("X509 Manager not configured in Trust Store");
    }

    @Override
    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException
    {
        //trustManager.checkClientTrusted(x509Certificates, s);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException
    {
        //trustManager.checkServerTrusted(x509Certificates, s);
    }

    @Override
    public X509Certificate[] getAcceptedIssuers()
    {
        return trustManager.getAcceptedIssuers();
    }
}
