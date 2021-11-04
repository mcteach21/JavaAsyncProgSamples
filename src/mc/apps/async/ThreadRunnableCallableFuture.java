package mc.apps.async;

import java.util.concurrent.*;
import java.util.function.Supplier;

public class ThreadRunnableCallableFuture {

    class SimpleThread extends Thread {
        @Override
        public void run() {
            System.out.println("I'm thread.."+Thread.currentThread().getName()+", running..");
        }
    }

    class SimpleRunnableClass implements Runnable {
        @Override
        public void run() {
            System.out.println("I'm runnable object.."+Thread.currentThread().getName()+", running..");
        }
    }

    public void Demo() throws ExecutionException, InterruptedException {
        Thread thread = new SimpleThread();
        thread.start();


        Thread thread2 = new Thread(){
            @Override
            public void run() {

            }
        };

        Runnable runnable_0 = new SimpleRunnableClass();
        Thread thread3 = new Thread(runnable_0);      // runnable comme argument
        thread3.start();

        Thread thread4 = new Thread(() -> {
            System.out.println("I'm runnable object.."+Thread.currentThread().getName()+", running..");
        });
        thread4.start();


        Callable<String> callable = () -> {
            String msg = "hello from callable!";
            System.out.println(msg);
            Thread.sleep(3000);
            return msg.toUpperCase();
        };

        int availableProcessors_count = Runtime.getRuntime().availableProcessors();
        System.out.println("availableProcessors_count = "+availableProcessors_count);
        // newFixedThreadPool(availableProcessors_count);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future_result = executor.submit(callable);

        int i=0;
        while (!future_result.isDone()) {
            System.out.println(++i+"FutureTask is not finished yet...");
        }
        System.out.println("result : "+future_result.get());

        Future<?> future_with_runnable = executor.submit(() -> System.out.println("hello from runnable!"));
        future_with_runnable.get();


        FutureTask<String> future_task = new FutureTask(()->"hello from callble!");
        new Thread(future_task).start();

        System.out.println(future_task.get());

        Runnable runnable = () -> System.out.println("CompletableFuture : running 'runnable'.."+Thread.currentThread().getName()+"..");
        CompletableFuture<Void> future_runnable = CompletableFuture.runAsync(runnable);

        Supplier<String> supplier = () -> "CompletableFuture : running 'supplier'.."+Thread.currentThread().getName()+"..";
        CompletableFuture<String> future_supplier = CompletableFuture.supplyAsync(supplier) ;

        System.out.println();
        System.out.println("future_runnable");
        future_runnable.get();
        System.out.println("future_supplier");
        System.out.println(future_supplier.get());
        System.out.println();

        CompletableFuture<String> future_completable = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {
            Thread.sleep(500);
            future_completable.complete("CompletableFuture completed! "+Thread.currentThread().getName());
            return null;
        });

        System.out.println("future_completable");
        System.out.println(future_completable.get());

        System.out.println();

//        Supplier<Void> supplier_void = () -> null;
        future_completable
                .thenApply(String::toUpperCase)
                .thenAccept(System.out::println);


//        CompletableFuture.anyOf(future_supplier, future_runnable)
//                .thenApply(val -> "Done with : " + val)
//                .thenAccept(System.out::println);


        System.out.println();
        executor.shutdown();
    }
}
