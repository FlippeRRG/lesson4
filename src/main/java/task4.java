import sun.nio.ch.ThreadPool;

import java.io.*;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class task4 {
//      1 Создать файл (наполнить рандомными данными)
//      2 Создать 10 потоков для чтения данных из файла (чтение из файла, вывод на консоль)
//      3 Сделать ограничение, что одновременно может читать только 4 потока
//      4 Все потоки должны прочитать данные из файла

    public static int activaThreadCounter = 0;
    private static Object sync = new Object();

    public static void main(String[] args) {
        File file = new File("123");
        file.mkdir();
        Random random = new Random(  );
        try {
            RandomAccessFile writefile = new RandomAccessFile( "123/task4", "rw" );

            FileWriter write = new FileWriter( "123/task4", true );
            for (int i = 0; i < 10; i++) {
                int a = random.nextInt( 500 );
                String hello = "hello" + a;
                writefile.writeUTF( hello );
            }
            write.flush();
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ExecutorService service = Executors.newFixedThreadPool( 4 );


        for (int i = 0; i < 30; i++) {
            final int finalI = i;
            service.submit( new Runnable() {
                public void run() {
                    StringBuffer result = new StringBuffer(  );

                    try {
                        FileReader read = new FileReader("123/task4");
                        int c;
                        while((c = read.read()) != -1){
                            result.append( (char)c );
                        }
                        Thread.sleep( 500 );
                        System.out.println("Поток " + finalI + " завершен! Результат чтения: " + result);
                        read.close();
                        notify();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } );
        }


    }

    private static void ThreadStart (){

        new Thread( new Runnable() {
            public void run() {
                activaThreadCounter++;

                if (activaThreadCounter > 4){
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    FileReader read = new FileReader("123/task4");
                    int c;
                    while((c = read.read()) != -1){
                        System.out.print((char)c);
                    }
                    read.close();
                    activaThreadCounter--;
                    notify();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
