import java.io.*;
import java.util.Arrays;

public class Main {

    private static Object sync = new Object();
    static volatile char currentL = 'A';
    File file = new File("1");

    public static void main(String[] args) throws FileNotFoundException {


        new Thread( new Runnable() {
            public void run() {
                for (int i = 0; i < 5; i++) {
                    synchronized (sync){
                        while(currentL != 'A'){
                            try {
                                sync.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.print("A ");
                        currentL = 'B';
                        sync.notifyAll();
                    }
                }
            }
        } ).start();

        new Thread( new Runnable() {
            public void run() {
                for (int i = 0; i < 5; i++) {
                    synchronized (sync){
                        while(currentL != 'B'){
                            try {
                                sync.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.print("B ");
                        currentL = 'C';
                        sync.notifyAll();
                    }
                }
            }
        } ).start();

        new Thread( new Runnable() {
            public void run() {
                for (int i = 0; i < 5; i++) {
                    synchronized (sync){
                        while(currentL != 'C'){
                            try {
                                sync.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.print("C ");
                        currentL = 'A';
                        sync.notifyAll();
                    }
                }
            }
        } ).start();

        filePrinter();
    }


    private static void filePrinter () throws FileNotFoundException {
        final String sync = "123";

        new Thread( new Runnable() {
            public void run() {
                try {
                    final FileWriter stream1 = new FileWriter( "1", true );
                    synchronized (sync) {
                        for (int i = 0; i < 10; i++) {
                            stream1.write( "Hello world\n" );
                            Thread.sleep( 50 );
                        }
                        stream1.flush();
                        stream1.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } ).start();
        new Thread( new Runnable() {
            public void run() {
                try {
                    final FileWriter stream1 = new FileWriter( "1", true );
                    synchronized (sync){
                        for (int i = 0; i < 10; i++) {
                            stream1.write( "Hello Java world\n" );
                            Thread.sleep( 50 );
                        }
                        stream1.flush();
                        stream1.close();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } ).start(); new Thread( new Runnable() {
            public void run() {
                try {
                    final FileWriter stream1 = new FileWriter( "1", true );
                    synchronized (sync){
                        for (int i = 0; i < 10; i++) {
                            stream1.write( "Hello Big world\n" );
                            Thread.sleep( 50 );
                        }
                        stream1.flush();
                        stream1.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } ).start();
//        new Thread( new Runnable() {
//            public void run() {
//                for (int i = 0; i < 11; i++) {
//                    try {
//                        stream.write( arr[i] );
//                        stream.
//                        Thread.sleep( 50 );
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }
//        } ).start();
//
//        new Thread( new Runnable() {
//            public void run() {
//                for (int i = 0; i < 11; i++) {
//                    try {
//                        stream.write( arr[i] );
//                        Thread.sleep( 50 );
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }
//        } ).start();
    }
}
