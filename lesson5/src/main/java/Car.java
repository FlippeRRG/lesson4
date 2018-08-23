import java.util.concurrent.CountDownLatch;

public class Car implements Runnable {
    private static int CARS_COUNT;
    private static int counterOfFinish;

    static {
        CARS_COUNT = 0;
        counterOfFinish = 0;
    }
    private static  CountDownLatch cdl;
    private Race race;
    private int speed;
    private String name;
    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }
    @Override
    public void run() {
        try {
            cdl = new CountDownLatch( CARS_COUNT );
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");

            cdl.countDown();
            if (cdl.getCount()==0){
                System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < race.getStages().size(); i++) {
            try {
                cdl.await();
                Thread.sleep( 100 ); // Для того чтобы объявление о победителе гарантированно вывелось до начала гонки - если нет слипа то обьявление бывает не в начале.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            race.getStages().get(i).go(this);
            cdl.countDown();
        }


        try {
            cdl.await();
            counterOfFinish++;
            if (counterOfFinish==4){
                System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка Закончилась!!!");
            } else if(counterOfFinish==1){
                System.out.println(this.getName() + " Winner!");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
