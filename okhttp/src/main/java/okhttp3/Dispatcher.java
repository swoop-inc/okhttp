package okhttp3;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 *
 */
public interface Dispatcher {
    ExecutorService executorService();

    /**
     * Set the maximum number of requests to execute concurrently. Above this requests queue in
     * memory, waiting for the running calls to complete.
     *
     * <p>If more than {@code maxRequests} requests are in flight when this is invoked, those
     * requests will remain in flight.
     */
    void setMaxRequests(int maxRequests);

    int getMaxRequests();

    /**
     * Set the maximum number of requests for each host to execute concurrently. This limits
     * requests by the URL's host name. Note that concurrent requests to a single IP address may
     * still exceed this limit: multiple hostnames may share an IP address or be routed through
     * the same HTTP proxy.
     *
     * <p>If more than {@code maxRequestsPerHost} requests are in flight when this is invoked, those
     * requests will remain in flight.
     */
    void setMaxRequestsPerHost(int maxRequestsPerHost);

    int getMaxRequestsPerHost();

    /**
     * Set a callback to be invoked each time the dispatcher becomes idle (when the number of
     * running calls returns to zero).
     *
     * <p>Note: The time at which a {@linkplain Call call} is considered idle is different depending
     * on whether it was run {@linkplain Call#enqueue(Callback) asynchronously} or
     * {@linkplain Call#execute() synchronously}. Asynchronous calls become idle after the
     * {@link Callback#onResponse onResponse} or {@link Callback#onFailure onFailure} callback has
     * returned. Synchronous calls become idle once {@link Call#execute() execute()} returns. This
     * means that if you are doing synchronous calls the network layer will not truly be idle until
     * every returned {@link Response} has been closed.
     */
    void setIdleCallback(Runnable idleCallback);

    /**
     * Cancel all calls currently enqueued or executing. Includes calls executed both {@linkplain
     * Call#execute() synchronously} and {@linkplain Call#enqueue asynchronously}.
     */
    void cancelAll();

    /** Returns a snapshot of the calls currently awaiting execution. */
    List<Call> queuedCalls();

    /** Returns a snapshot of the calls currently being executed. */
    List<Call> runningCalls();

    int queuedCallsCount();

    int runningCallsCount();

    void enqueue(RealCall.AsyncCall call);

    void executed(RealCall call);

    void finished(RealCall.AsyncCall call);

    void finished(RealCall call);
}
