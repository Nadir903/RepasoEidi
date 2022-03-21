//Sincronizar es que un hilo empiece cuando recién termine el anterior
class Sincronizado{
    public static void main(String[] args) {
        HilosVarios hilo1 = new HilosVarios();
        HilosVarios hilo2 = new HilosVarios();
        HilosVarios hilo3 = new HilosVarios();
        HilosVarios hilo4 = new HilosVarios();
        HilosVarios hilo5 = new HilosVarios();
        HilosVarios hilo6 = new HilosVarios();
        HilosVarios hilo7 = new HilosVarios();
        HilosVarios hilo8 = new HilosVarios();
        HilosVarios hilo9 = new HilosVarios();
        HilosVarios hilo10 = new HilosVarios();
        HilosVarios[] hilos = {hilo1,hilo2,hilo3,hilo4,hilo5,hilo6,hilo7,hilo8,hilo9,hilo10};
        for (HilosVarios hilo : hilos) {
            hilo.start();
            try{
                hilo.join();                    //sin el join(), el orden de ejecución de los hilos es random
            }catch (InterruptedException e){    //Con el join(), la ejecución es ordenada de 0 a 9
                e.printStackTrace();
            }
        }
        System.out.println("terminadas las tareas XD");
    }
}
class HilosVarios extends Thread {
    public void run(){
        for (int i = 0; i < 2; i++) {
            System.out.println("ejecutando hilo " + getName());
            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
