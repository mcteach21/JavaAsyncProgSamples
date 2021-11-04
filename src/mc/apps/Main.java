package mc.apps;

import mc.apps.async.ThreadRunnableCallableFuture;
import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("***************************************");
        System.out.println("************* Async. Prog. ************");
        System.out.println("***************************************");

        new ThreadRunnableCallableFuture().Demo();

        System.out.println("main thread done! bye.");
        System.out.println("***************************************");
    }
}
