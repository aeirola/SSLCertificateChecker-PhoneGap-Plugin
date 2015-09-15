package nl.xservices.plugins;

import java.util.ArrayList;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.util.Arrays;
import java.io.IOException;

class AdditionalCipherSuiteSSLSocketFactory extends SSLSocketFactory {
  /**
    Adapted from http://stackoverflow.com/a/16714084
  **/
  private final SSLSocketFactory delegate;
  private final String additionalCipherSuite;

  public AdditionalCipherSuiteSSLSocketFactory(SSLSocketFactory delegate, String additionalCipherSuite) {
      this.delegate = delegate;
      this.additionalCipherSuite = additionalCipherSuite;
  }

  @Override
  public String[] getDefaultCipherSuites() {
      return setupPreferredDefaultCipherSuites(this.delegate);
  }

  @Override
  public String[] getSupportedCipherSuites() {
      return setupPreferredSupportedCipherSuites(this.delegate);
  }

  @Override
  public Socket createSocket(String arg0, int arg1) throws IOException,
          UnknownHostException {

      Socket socket = this.delegate.createSocket(arg0, arg1);
      String[] cipherSuites = setupPreferredDefaultCipherSuites(delegate);
      ((SSLSocket)socket).setEnabledCipherSuites(cipherSuites);

      return socket;
  }

  @Override
  public Socket createSocket(InetAddress arg0, int arg1) throws IOException {

      Socket socket = this.delegate.createSocket(arg0, arg1);
      String[] cipherSuites = setupPreferredDefaultCipherSuites(delegate);
      ((SSLSocket)socket).setEnabledCipherSuites(cipherSuites);

      return socket;
  }

  @Override
  public Socket createSocket(Socket arg0, String arg1, int arg2, boolean arg3)
          throws IOException {

      Socket socket = this.delegate.createSocket(arg0, arg1, arg2, arg3);
      String[] cipherSuites = setupPreferredDefaultCipherSuites(delegate);
      ((SSLSocket)socket).setEnabledCipherSuites(cipherSuites);

      return socket;
  }

  @Override
  public Socket createSocket(String arg0, int arg1, InetAddress arg2, int arg3)
          throws IOException, UnknownHostException {

      Socket socket = this.delegate.createSocket(arg0, arg1, arg2, arg3);
      String[] cipherSuites = setupPreferredDefaultCipherSuites(delegate);
      ((SSLSocket)socket).setEnabledCipherSuites(cipherSuites);

      return socket;
  }

  @Override
  public Socket createSocket(InetAddress arg0, int arg1, InetAddress arg2,
          int arg3) throws IOException {

      Socket socket = this.delegate.createSocket(arg0, arg1, arg2, arg3);
      String[] cipherSuites = setupPreferredDefaultCipherSuites(delegate);
      ((SSLSocket)socket).setEnabledCipherSuites(cipherSuites);

      return socket;
  }

  private String[] setupPreferredDefaultCipherSuites(SSLSocketFactory sslSocketFactory) {
      String[] defaultCipherSuites = sslSocketFactory.getDefaultCipherSuites();

      ArrayList<String> suitesList = new ArrayList<String>(Arrays.asList(defaultCipherSuites));
      if (this.additionalCipherSuite != null) {
        suitesList.remove(this.additionalCipherSuite);
        suitesList.add(0, this.additionalCipherSuite);
      }

      return suitesList.toArray(new String[suitesList.size()]);
  }

  private String[] setupPreferredSupportedCipherSuites(SSLSocketFactory sslSocketFactory) {
      String[] supportedCipherSuites = sslSocketFactory.getSupportedCipherSuites();

      ArrayList<String> suitesList = new ArrayList<String>(Arrays.asList(supportedCipherSuites));
      if (this.additionalCipherSuite != null) {
        suitesList.remove(this.additionalCipherSuite);
        suitesList.add(0, this.additionalCipherSuite);
      }

      return suitesList.toArray(new String[suitesList.size()]);
  }
}
