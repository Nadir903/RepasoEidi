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