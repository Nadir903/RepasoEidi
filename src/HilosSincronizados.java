import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//Sincronizar es que un hilo empiece cuando recién termine el anterior
class Sincronizado{
    public static void main(String[] args) {
        HilosVarios hilo0 = new HilosVarios();
        HilosVariosSincronizados hilo1 = new HilosVariosSincronizados(hilo0);
        HilosVariosSincronizados hilo2 = new HilosVariosSincronizados(hilo1);   //hasta que el hilo1 no termine su ejecución
        HilosVariosSincronizados hilo3 = new HilosVariosSincronizados(hilo2);   //el hilo2 no comenzará
        HilosVariosSincronizados hilo4 = new HilosVariosSincronizados(hilo3);
        HilosVariosSincronizados hilo5 = new HilosVariosSincronizados(hilo4);
        HilosVariosSincronizados hilo6 = new HilosVariosSincronizados(hilo5);
        HilosVariosSincronizados hilo7 = new HilosVariosSincronizados(hilo6);
        HilosVariosSincronizados hilo8 = new HilosVariosSincronizados(hilo7);
        HilosVariosSincronizados hilo9 = new HilosVariosSincronizados(hilo8);
        HilosVariosSincronizados[] hilos = {hilo1, hilo2, hilo3, hilo4, hilo5, hilo6, hilo7, hilo8, hilo9};
        for (HilosVariosSincronizados hilo : hilos) {                           //hilos sincronizados entre sí
            hilo.start();
        }
        hilo0.start();
        System.out.println("terminadas las tareas XD");     //main liberado, se ejecuta mientras los demás
    }                                                        // hilos están ejecutándose sincronizadamente
}
class HilosVarios extends Thread {
    public void run(){
        for (int i = 0; i < 2; i++) {
            System.out.println("ejecutando hilo " + getName());
        }
    }
}
class HilosVariosSincronizados extends Thread {
    private Thread hilo;
    public HilosVariosSincronizados(Thread hilo){
        this.hilo = hilo;
    }
    public void run(){
        try{
            hilo.join();                            //lo que hace es que el hilo anterior sera joineado Xd para asi
        }catch(InterruptedException e){             //no llamar a join tantas veces
            e.printStackTrace();
        }
        for (int i = 0; i < 2; i++) {
            System.out.println("ejecutando hilo " + getName());
        }
    }
}

//--------------------------------------------------------Banco---------------------------------------------------------
//------------------------------------------Saldo total debe ser 200.000 euros------------------------------------------
//---------------------------------------------Solo hay 100 cuentas de banco--------------------------------------------
class BancoDesincronizado{
    public static void main(String[] args) throws InterruptedException {
        Banco banco = new Banco();
        for (int i = 0; i < 100; i++) {
            EjecuciónDeTransferencias runnable = new EjecuciónDeTransferencias(banco,i,2000);
            Thread thread = new Thread(runnable);
            thread.start();
            //thread.join();        si el bucle infinito de run() se reduce a i<10 por ejemplo, todos los Threads
            //                      correrían en orden, del 0 al 99. En caso de que siga infinito, se queda en la
            //                      cuenta 0, se drenara su saldo completamente y no podrá comenzar el segundo Thread.
        }
    }
}
class Banco{
    private final double[] cuentas;
    private Lock lineaDeCódigo = new ReentrantLock();
    public Banco(){
        cuentas = new double[100];
        for (int i = 0; i < cuentas.length; i++) {
            cuentas[i] = 2000;
        }
    }
    public void transferir(int cuentaOrigen, int cuentaDestino, double cantidad){
        lineaDeCódigo.lock();
        try{
            if(cantidad > cuentas[cuentaOrigen]) {
                //System.out.printf("¡Saldo insuficiente!: monto -> %10.2f || saldo actual -> %d \n",cantidad,cuentaOrigen);
                return;
            }
            System.out.print(Thread.currentThread() + " -> ");
            cuentas[cuentaOrigen] -= cantidad;
            System.out.printf("%10.2f de cuenta %d para cuenta %d \t",cantidad,cuentaOrigen,cuentaDestino);
            cuentas[cuentaDestino] += cantidad;
            System.out.printf("||   Saldo total del banco: %10.2f%n", getSaldoDelBanco());
        }finally {
            lineaDeCódigo.unlock();
        }

    }
    public double getSaldoDelBanco(){
        double sumaTotal = 0;
        for (double saldo: cuentas) {
            sumaTotal += saldo;
        }
        return sumaTotal;
    }
}
class EjecuciónDeTransferencias implements Runnable{
    private Banco banco;
    private int idOrigen;
    private double cantidadMaxima;
    public EjecuciónDeTransferencias(Banco banco, int idOrigen, double cantidadMaxima){
        this.banco = banco;
        this.idOrigen = idOrigen;
        this.cantidadMaxima = cantidadMaxima;
    }
    @Override
    public void run() {
        //un segundo hilo de ejecución puede empezar antes de que el actual termine y allí es donde se pierde la
        //cantidad total, para evitar ello se debe usar un Lock();
        //>>>mirar método transferir en clase Banco<<<
        while (true){   //crear bucle infinito
            int idDestino = (int)(Math.random()*100);
            double cantidad = cantidadMaxima*Math.random();
            banco.transferir(idOrigen,idDestino,cantidad);
            try{
                Thread.sleep((int)(Math.random()*100));
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

    }
}