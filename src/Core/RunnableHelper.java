
package Core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RunnableHelper 
{
    private static ExecutorService executor;
    public static long AppThread = 0;

    public static <T, R> R BeginInvoke(Callable<T> fun) throws Exception { return BeginInvoke(fun, true, null); }
    public static <T, R> R BeginInvoke(Callable<T> fun, Callable<T> post) throws Exception { return BeginInvoke(fun, true, post); }
    public static <T, R> R BeginInvoke(Callable<T> fun, Boolean valThread, Callable<T> post) throws Exception
    {
        if (fun == null)
            throw new IllegalArgumentException("The function is null");

        if (valThread && AppThread != 0 && AppThread != Thread.currentThread().getId())
        {
            fun.call();
            fun.wait();
            if (post != null)
                post.call();
            return null;
        }
        executor = executor == null ? Executors.newCachedThreadPool() : executor;
        executor.submit(() -> { try { fun.call(); if (post != null) { post.call(); } } catch (Exception ex) { } });                    
        return null;
    }
    public static <T, R> R RunAsync(Callable<T> function) throws Exception { return RunAsync(function, true); }
    public static <T, R> R RunAsync(Callable<T> function, Boolean validateThread) throws Exception
    {
        if (function == null)
            throw new IllegalArgumentException("The function is null");
        
        if (validateThread && AppThread != 0 && AppThread != Thread.currentThread().getId())
        {
            Object response = function.call();
            function.wait();
            return (R) response;
        }
        executor = executor == null ? Executors.newCachedThreadPool() : executor; 
        Future<?> resp = executor.submit(function);
        return null; //(R)resp.get();
    }    
    
    public static <K> List<K> WaitAll(List<Callable<K>> tasks) throws Exception
    {
        List<K> responses = new ArrayList<>();
        executor = executor == null ? Executors.newCachedThreadPool() : executor; 
        List<Future<K>> response = executor.invokeAll(tasks);
        for (Future<K> item : response)
            responses.add(item.get());
        return responses;
    }
}