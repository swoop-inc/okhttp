package okhttp3;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;

/**
 * For connections to origin servers that resolve to multiple IP addresses (sometimes known
 * as DNS round robin load balancing), set this property to true in order to have a route
 * randomly selected (as opposed to the order in which the DNS was resolved). This does not
 * affect the way different routes are retried upon connection failures, only the order in
 * which they're selected.
 * <p/>
 * This is especially useful given the way JVMs cache DNS resolutions (sometimes for the
 * lifetime of the JVM session).
 * <p/>
 * This setting is defaulted to false.
 */
public class RoundRobinDns implements Dns {
  @Override
  public List<InetAddress> lookup(String hostname) throws UnknownHostException {
    List<InetAddress> addresses = SYSTEM.lookup(hostname);
    Collections.shuffle(addresses);
    return addresses;
  }
}
