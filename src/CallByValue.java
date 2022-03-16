import java.util.Arrays;

//ejemplo del examen
class CallMeMaybe{
    static void inc1(int[] array){
        array = new int[]{2,3,4,5};
    }
    static void inc2(int[] array){
        array[0] += 1;
        array[1] += 1;
        array[2] += 1;
        array[3] += 1;
    }
    static void inc3(int number){
        number = number + 1111;
    }

    public static void main(String[] args) {
        int[] array1 = new int[]{1,2,3,4};
        int[] array2 = new int[]{1,2,3,4};
        int number = 1234;

        inc1(array1);
        inc2(array2);
        inc3(number);

        System.out.println(Arrays.toString(array1));
        System.out.println(Arrays.toString(array2));
        System.out.println(number);
    }
}
//-------------------------------------------Unterricht Beispiel--------------------------------------------------------
class Point{
    public double x, y;
    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }
    public void shift(double dx, double dy){
        x += dx;
        y +=dy;
    }
}
class testUnterrichtBeispiel{
    static void f(){
        Point p = new Point(13.0, 8.3);
        g(p);
        System.out.println("x: " + p.x + ", y: " + p.y);
    }
    static void g(Point p){
        p.shift(7.3,12.0);
        System.out.println("x: " + p.x + ", y: " + p.y);
    }
    //eigene Beispiele
    static void h(Point p){                     //altera al punto p como Objeto -> call by value
        p = new Point(15.5,15.5);
    }
    static void i(Point p){                     //altera atributos del punto p -> call by reference
        p.x += 100.0;
        p.y += 100.0;
    }

    public static void main(String[] args) {
        Point punto1 = new Point(10.0,20.0);            //Punto1 = Punto2
        Point punto2 = new Point(10.0,20.0);
        //f();

        h(punto1);
        i(punto2);
        System.out.println(punto1.x + " ; " + punto1.y);    //Punto 1 = 10 , 20
        System.out.println(punto2.x + " ; " + punto2.y);    //Punto 2 = 110 , 120
    }
}
