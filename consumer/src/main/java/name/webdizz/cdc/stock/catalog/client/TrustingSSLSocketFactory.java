package name.webdizz.cdc.stock.catalog.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;

class TrustingSSLSocketFactory extends SSLSocketFactory implements X509TrustManager, X509KeyManager {

    private static final String[] ENABLED_CIPHER_SUITES = { "SSL_RSA_WITH_3DES_EDE_CBC_SHA" };
    private final SSLSocketFactory delegate;
    private final String serverAlias;
    private final PrivateKey privateKey;
    private final X509Certificate[] certificateChain;

    TrustingSSLSocketFactory() {
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(new KeyManager[] { this }, new TrustManager[] { this }, new SecureRandom());
            this.delegate = sc.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.privateKey = null;
        this.certificateChain = null;
        this.serverAlias = "";
    }

    static Socket setEnabledCipherSuites(Socket socket) {
        SSLSocket.class.cast(socket).setEnabledCipherSuites(ENABLED_CIPHER_SUITES);
        return socket;
    }

    @Override
    public String[] getDefaultCipherSuites() {
        return ENABLED_CIPHER_SUITES;
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return ENABLED_CIPHER_SUITES;
    }

    @Override
    public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
        return setEnabledCipherSuites(delegate.createSocket(s, host, port, autoClose));
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException {
        return setEnabledCipherSuites(delegate.createSocket(host, port));
    }

    @Override
    public Socket createSocket(InetAddress host, int port) throws IOException {
        return setEnabledCipherSuites(delegate.createSocket(host, port));
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException {
        return setEnabledCipherSuites(delegate.createSocket(host, port, localHost, localPort));
    }

    @Override
    public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
        return setEnabledCipherSuites(delegate.createSocket(address, port, localAddress, localPort));
    }

    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }

    public void checkClientTrusted(X509Certificate[] certs, String authType) {
    }

    public void checkServerTrusted(X509Certificate[] certs, String authType) {
    }

    @Override
    public String[] getClientAliases(String keyType, Principal[] issuers) {
        return null;
    }

    @Override
    public String chooseClientAlias(String[] keyType, Principal[] issuers, Socket socket) {
        return null;
    }

    @Override
    public String[] getServerAliases(String keyType, Principal[] issuers) {
        return null;
    }

    @Override
    public String chooseServerAlias(String keyType, Principal[] issuers, Socket socket) {
        return serverAlias;
    }

    @Override
    public X509Certificate[] getCertificateChain(String alias) {
        return certificateChain;
    }

    @Override
    public PrivateKey getPrivateKey(String alias) {
        return privateKey;
    }
}
