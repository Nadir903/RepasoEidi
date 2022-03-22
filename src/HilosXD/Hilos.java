package HilosXD;

import java.awt.geom.*;

import javax.swing.*;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
//----------------------------------------------Ejemplo 1 de Pildoras informaticas--------------------------------------
class UsoThreads {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        JFrame marco=new MarcoRebote();
        marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        marco.setVisible(true);

    }
}
//Hilo------------------------------------------------------------------------------------------------------------------
class PelotaHilos implements Runnable{
    private Pelota pelota;
    private Component componente;
    public PelotaHilos(Pelota pelota, Component componente){
        this.pelota = pelota;
        this.componente = componente;
    }
    @Override
    public void run() {
        System.out.println("hilo interrumpido : " + Thread.interrupted());          //antes de dale() es falso
        for (int i=1; ! Thread.currentThread().isInterrupted() ; i++){              //i<=3000      Thread.interrupted()
            pelota.mueve_pelota(componente.getBounds());
            componente.paint(componente.getGraphics());
            try {
                Thread.sleep(4);
            } catch (InterruptedException e) {
                //e.printStackTrace();
                //System.out.println("Hilo bloqueado, imposible su interrupción XD");
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("hilo interrumpido : " + Thread.interrupted());          //despues de interrumpir() es true
    }
}
//Movimiento de la pelota-----------------------------------------------------------------------------------------------
class Pelota{
    // Mueve la pelota invirtiendo posición si choca con límites
    public void mueve_pelota(Rectangle2D limites){
        x+=dx;
        y+=dy;
        if(x<limites.getMinX()){
            x=limites.getMinX();
            dx=-dx;
        }
        if(x + TAMX>=limites.getMaxX()){
            x=limites.getMaxX() - TAMX;
            dx=-dx;
        }
        if(y<limites.getMinY()){
            y=limites.getMinY();
            dy=-dy;
        }
        if(y + TAMY>=limites.getMaxY()){
            y=limites.getMaxY()-TAMY;
            dy=-dy;
        }
    }
    //Forma de la pelota en su posición inicial
    public Ellipse2D getShape(){
        return new Ellipse2D.Double(x,y,TAMX,TAMY);
    }
    private static final int TAMX=15;
    private static final int TAMY=15;
    private double x=0;
    private double y=0;
    private double dx=1;
    private double dy=1;
}
// Lámina que dibuja las pelotas----------------------------------------------------------------------------------------
class LaminaPelota extends JPanel{
    //Añadimos pelota a la lámina
    public void add(Pelota b){
        pelotas.add(b);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2=(Graphics2D)g;
        for(Pelota b: pelotas){
            g2.fill(b.getShape());
        }
    }
    private ArrayList<Pelota> pelotas= new ArrayList<>();
}
//Marco con lámina y botones--------------------------------------------------------------------------------------------
class MarcoRebote extends JFrame{
    private Thread thread1, thread2, thread3;
    JButton arranca1, arranca2, arranca3, detener1, detener2, detener3;
    public MarcoRebote(){
        setBounds(600,300,700,350);
        setTitle ("Rebotes");
        lamina=new LaminaPelota();
        add(lamina, BorderLayout.CENTER);
        JPanel laminaBotones=new JPanel();

        arranca1 = new JButton("hilo 1");
        arranca1.addActionListener(e -> comienza_el_juego(e));
        laminaBotones.add(arranca1);

        arranca2 = new JButton("hilo 2");
        arranca2.addActionListener(e -> comienza_el_juego(e));
        laminaBotones.add(arranca2);

        arranca3 = new JButton("hilo 3");
        arranca3.addActionListener(this::comienza_el_juego);
        laminaBotones.add(arranca3);

        detener1 = new JButton("detener hilo 1");
        detener1.addActionListener(e -> detener(e));
        laminaBotones.add(detener1);

        detener2 = new JButton("detener hilo 2");
        detener2.addActionListener(e -> detener(e));
        laminaBotones.add(detener2);

        detener3 = new JButton("detener hilo 3");
        detener3.addActionListener(this::detener);
        laminaBotones.add(detener3);

        add(laminaBotones, BorderLayout.SOUTH);
    }

    //Añade pelota y la bota 1000 veces

    public void comienza_el_juego (ActionEvent e){
        Pelota pelota=new Pelota();
        lamina.add(pelota);
        /*for (int i=1; i<=3000; i++){
            pelota.mueve_pelota(lamina.getBounds());
            lamina.paint(lamina.getGraphics());
            try {
                Thread.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
        Runnable runnable = new PelotaHilos(pelota,lamina);
        //runnable.run();           ---> si solo se implementa asi, NO sera multitarea

        if(e.getSource().equals(arranca1)){
            thread1 = new Thread(runnable);
            thread1.start();
        }else if (e.getSource().equals(arranca2)) {
            thread2 = new Thread(runnable);
            thread2.start();
        }else if (e.getSource().equals(arranca3)) {
            thread3 = new Thread(runnable);
            thread3.start();
        }

    }
    //para detener o interrumpir
    public void detener(ActionEvent e){
        if(e.getSource().equals(detener1)){
            thread1.interrupt();
        }else if (e.getSource().equals(detener2)) {
            thread2.interrupt();
        }else if (e.getSource().equals(detener3)) {
            thread3.interrupt();
        }
    }
    private LaminaPelota lamina;
}

//------------------------------------------------------Ejemplo 2-------------------------------------------------------

