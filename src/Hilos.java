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
            /*try {
                Thread.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("Hilo bloqueado, imposible su interrupción XD");
            }*/
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
    public MarcoRebote(){
        setBounds(600,300,400,350);
        setTitle ("Rebotes");
        lamina=new LaminaPelota();
        add(lamina, BorderLayout.CENTER);
        JPanel laminaBotones=new JPanel();
        ponerBoton(laminaBotones, "Dale!", new ActionListener(){
            public void actionPerformed(ActionEvent evento){
                comienza_el_juego();
            }
        });
        ponerBoton(laminaBotones, "Salir", new ActionListener(){
            public void actionPerformed(ActionEvent evento){
                System.exit(0);
            }
        });
        ponerBoton(laminaBotones,"Detener",e -> detener());
        add(laminaBotones, BorderLayout.SOUTH);
    }
    //Ponemos botones
    public void ponerBoton(Container c, String titulo, ActionListener oyente){
        JButton boton=new JButton(titulo);
        c.add(boton);
        boton.addActionListener(oyente);
    }
    //Añade pelota y la bota 1000 veces
    private Thread thread;
    public void comienza_el_juego (){
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
        thread = new Thread(runnable);
        thread.start();
    }
    //para detener o interrumpir
    public void detener(){
        //thread.stop();
        thread.interrupt();     //lanza interrupted exception debido a método sleep() ejecutado
    }
    private LaminaPelota lamina;
}

//------------------------------------------------------Ejemplo 2-------------------------------------------------------

