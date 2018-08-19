public class MultifunctionDevice {

        private volatile boolean printerReady = true;
        private volatile boolean scannerReady = true;
        private Object syncPrinter = new Object();
        private Object syncScanner = new Object();
        private int numberOfPrintingDocument = 0;
        private int NumberOfScanningDocument = 0;


        public void printer(final int pages){

            new Thread( new Runnable() {
                public void run() {
                    synchronized (syncPrinter){
                        numberOfPrintingDocument++;
                        while (!printerReady){
                            try {
                                syncPrinter.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        printerReady = false;
                        for (int i = 1; i <= pages; i++) {
                            try {
                                System.out.println("Отпечатаны " + i + " страниц, документа №" + numberOfPrintingDocument);
                                Thread.sleep( 50 );
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        printerReady = true;
                    }
                }
            } ).start();
        }

        public void scanner(final int pages){
            new Thread( new Runnable() {
                public void run() {
                    synchronized (syncScanner){
                        NumberOfScanningDocument++;
                        while (!scannerReady){
                            try {
                                syncScanner.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        scannerReady = false;
                        for (int i = 1; i <= pages; i++) {
                            try {
                                System.out.println("Отсканированы " + i + " страниц, документа №" + NumberOfScanningDocument);
                                Thread.sleep( 50 );
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        scannerReady = true;
                    }
                }
            } ).start();
        }
}
