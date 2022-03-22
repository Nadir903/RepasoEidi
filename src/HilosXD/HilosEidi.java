package HilosXD;

import javax.xml.crypto.Data;
import java.util.concurrent.Semaphore;

class MyThread extends Thread{
    public void hello(String s){
        System.out.println(s);
    }
    public void run(){
        hello("Imma pure Thread and say Hello");
    }
    public static void main(String[] args) {
        MyThread thread = new MyThread();
        thread.start();
        System.out.println("Thread has been started");
    }
}
class MyRunnable implements Runnable{
    public void hello(String s){
        System.out.println(s);
    }
    @Override
    public void run() {
        hello("Imma Runnable in a Thread and im running");
    }
    public static void main(String[] args) {
        Thread t = new Thread(new MyRunnable());
        t.start();
        System.out.println("Thread has been started");
    }
}
//Implementierung von Monitoren
class Inc implements Runnable{
    public static int x = 0;
    private static void pause(int t){
        try{
            Thread.sleep((int)(Math.random()*t*1000));
        }catch(InterruptedException e){
            System.err.println(e.toString());
        }
    }
    public void run(){
        String threadName = Thread.currentThread().getName();
        pause(3);
        int y = x;
        System.out.println( threadName + " read " + y);
        pause(4);
        x = y+1;
        System.out.println( threadName + " wrote " + (y+1));
    }
    public static void main(String[] args) {
        (new Thread(new Inc())).start();
        pause(2);
        (new Thread(new Inc())).start();
        pause(2);
        (new Thread(new Inc())).start();
    }
}
//manera sincronizada
class Count{
    private int count = 0;
    public synchronized void inc(){
        String threadName = Thread.currentThread().getName();
        int y = count;
        System.out.println( threadName + " read " + y);
        count = y+1;
        System.out.println(threadName + " wrote " + (y+1));
    }
}
class IncSync implements Runnable{
    private static Count x = new Count();
    public void run(){
        x.inc();
    }
    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            (new Thread(new IncSync())).start();
        }
    }
}
//----------------------------------------------Producer und Consumer...................................................
class Buffer2{
    private int cap, free, first,last;
    private Data[] a;
    public Buffer2(int n){
        free = cap = n; first = last = 0;
        a = new Data[n];
    }
    public synchronized void produce(Data d) throws InterruptedException{
        while (free == 0){
            wait();
        }
        free--;
        a[last] = d;
        last = (last+1)%cap;
        notifyAll();
    }
    public synchronized Data consume() throws InterruptedException{
        while (free == cap){
            wait();
        }
        free++;
        Data result = a[first];
        first = (first+1)%cap;
        notifyAll();
        return result;
    }
}
//---------------------------------------------------Semaphore----------------------------------------------------------
//Producers und Consumers warten in verschiedenen Schlangen. Producers warten darauf dass free > 0 und Consumers dass
//cap - free > 0
class Semáforo{
    private int semáforo;
    public Semáforo(int n){
        semáforo = n;
    }
    public synchronized void up(){  //synchronized heißt, dass nur ein Thread kann gleichzeitig diesen Stückcode lesen.
        semáforo++;
        if(semáforo <= 0 ){
            this.notify();  //semáforo++<=0 significa que semáforo era negativo(rojo): hay al menos un Thread esperando
        }
    }
    public synchronized void down() throws InterruptedException{
        semáforo--;
        if(semáforo<0){
            this.wait();    //--semáforo<0 significa que semáforo <=0 (rojo o amarillo): se debe esperar
        }
    }
}
//-------------up() aumenta la variable semáforo y down() la disminuye
//-------------semáforo es positivo, entonces su valor da la cantidad de recursos disponibles
//-------------semáforo es negativo, entonces su valor da la cantidad de Threads esperando
//-------------up() Operación despierta a un Thread que estaba esperando
class Buffer{
    private int cap,first,last;
    private Semáforo free, occupied;
    private Data[] a;
    public Buffer(int n){
        cap = n;
        first = last = 0;
        a = new Data[n];
        free = new Semáforo(n);
        occupied = new Semáforo(0);
    }
    /*public synchronized void produce(Data d) throws InterruptedException {
        free.down();        //potentially makes current producer wait
        a[last] = d;
        last = (last+1)%cap;
        occupied.up();      //potentially wakes up waiting consumer
    }
    public synchronized Data consume() throws InterruptedException {
        occupied.down();    //potentially makes current consumer wait
        Data result = a[first];
        first = (first+1)%cap;
        free.up();          //potentially wakes up waiting producer
        return result;
    }*/
    //Fehlerhaft weil jeder Producer braucht zwei Locks, für den Puffer und den Semaphor...-...-...-...-...-...-...-...-
    public synchronized void produce(Data d) throws InterruptedException {
        free.down();
        synchronized (this){
            a[last] = d;
            last = (last+1)%cap;
        }
        occupied.up();
    }
    public synchronized Data consume() throws InterruptedException {
        Data result;
        occupied.down();
        synchronized (this){
            result = a[first];
            first = (first+1)%cap;
        }
        free.up();
        return result;
    }
}