package multithreading;

import java.util.Arrays;
import java.util.MissingResourceException;

/**
 * This class is handling a thread pool.
 * @param <T> the parameter for the thread's job
 */
public class ThreadPool<T> {
    private static final int SPARE_THREADS = 2;
    private int _numThreads;
    private Thread[] _threads;
    private ParamGetter<T> _getter;
    private Runnable<T> _target;

    /**
     * Default constructor.
     * Sets the number of threads to use all the cores minus 2 for spare (it will always be greater than 0).
     */
    public ThreadPool() {
        _numThreads = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
        if (_numThreads < 2) {
            _numThreads = 1;
        }
    }

    /**
     * Returns the number of threads.
     */
    public int getNumThreads() {
        return _numThreads;
    }

    /**
     * Returns if the thread pool is currently using any threads.
     */
    public boolean isRunning() {
        if (_threads == null) {
            return false;
        }

        return Arrays.stream(_threads).anyMatch(Thread::isAlive);
    }

    /**
     * Chaining method for setting the number of threads the thread pool will use.
     * @param numThreads the number of threads
     * @exception IllegalArgumentException when {@code numThreads} is greater or equals to 0
     * @return the current thread pool
     */
    public ThreadPool<T> setNumThreads(int numThreads) {
        if (numThreads <= 0) {
            throw new IllegalArgumentException("Number of threads must be greater than 0");
        }

        _numThreads = numThreads;
        return this;
    }

    /**
     * Chaining method for setting the method in order to give the threads the parameter for their job.
     * @param getter implementation of {@link ParamGetter<T>}
     * @exception NullPointerException when {@code getter} is {@code null}
     * @return the current thread pool
     */
    public ThreadPool<T> setParamGetter(ParamGetter<T> getter) {
        if (getter == null) {
            throw new NullPointerException("getter cannot be null");
        }

        _getter = getter;
        return this;
    }


    /**
     * Chaining method for setting the threads' job.
     * @param target implementation of {@link Runnable<T>}
     * @exception NullPointerException when {@code target} is {@code null}
     * @return the current thread pool
     */
    public ThreadPool<T> setTarget(Runnable<T> target) {
        if (target == null) {
            throw new NullPointerException("target cannot be null");
        }

        _target = target;
        return this;
    }

    /**
     * Executes all the threads with the given getter and target.
     * @exception UnsupportedOperationException when already executing or missing 1 of getter and target
     */
    public void execute() {
        if (isRunning()) {
            throw new UnsupportedOperationException("Already executing");
        }

        try {
            if (_getter == null) {
                throw new MissingResourceException("Missing resource", ParamGetter.class.getName(), "");
            }
            if (_target == null) {
                throw new MissingResourceException("Missing resource", Runnable.class.getName(), "");
            }

            // initialize all the threads
            _threads = new Thread[_numThreads];
            for (int i = 0; i < _numThreads; ++i) {
                _threads[i] = new Thread(() -> {
                    // running until target returns false
                    while (_target.run(_getter.get()));
                });
            }

            // run all the threads
            for (Thread thread : _threads) {
                thread.start();
            }
        }
        catch (MissingResourceException e) {
            throw new UnsupportedOperationException("ThreadPool didn't receive " + e.getClassName());
        }
    }

    /**
     * Joins all the threads.
     */
    public void join() {
        if (!isRunning()) {
            _threads = null;
            return;
        }

        for (Thread thread : _threads) {
            try {
                thread.join();
            } catch (Exception e) { }
        }
        _threads = null;
    }

    /**
     * The {@code ParamGetter<T>} should be implemented in order to give the thread pool's threads a parameter when they start a new job.
     * @param <T> the type of the parameter to give the threads
     */
    public interface ParamGetter<T> {
        T get();
    }

    /**
     * The {@code Runnable<T>} should be implemented in order the thread pool's threads to run with a given parameter.
     * If returns true, the thread will continue to the next job. Else, the thread will stop and die.
     * @param <T> the type of the parameter the method will get
     */
    public interface Runnable<T> {
        boolean run(T param);
    }
}
