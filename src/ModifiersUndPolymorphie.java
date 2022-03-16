import java.util.List;

class Modifiers {
    private String privado;         //accessed by: Class
    String predeterminado;          //accessed by: Class, Package, Underclass(same pkg)
    protected String protegido;     //accessed by: Class, Package, Underclass(same pkg), Underclass(diff pkg)
    public String publico;          //accessed by: Class, Package, Underclass(same pkg), Underclass(diff pkg), World

    public void visibilityClass(String str){
        this.privado = "privadoClass";
        this.predeterminado = "predeterminadoClass";
        this.protegido = "protegidoClass";
        this.publico = "publicoClass";
    }
}

class ModifiersPackage{
    Modifiers modifiers = new Modifiers();
    public void visibilityPackage(String str){
        modifiers.predeterminado = "predeterminadoPackage";
        modifiers.protegido = "protegidoPackage";
        modifiers.publico = "publicoPackage";
    }
}

class ModifiersChild extends Modifiers{
    public void visibilityUnderClassSAME(String str){


        predeterminado = "predeterminadoUnderclassSAME";
        protegido = "protegidoUnderclassSAME";
        publico = "publicoUnderclassSAME";
    }
}
class ModifiersPackageChild extends ModifiersPackage{
    public void visibilityUnderClassDIFF(String str){
        modifiers.predeterminado = "predeterminadoUnderclassDIFF";
        modifiers.protegido = "protegidoUnderclassDIFF";
        modifiers.publico = "publicoUnderclassDIFF";
    }
}

//----------------------------------------------Beispiel der Unterricht-------------------------------------------------

class Eating{
    public static void main(String[] args) {
        Pizza special = new Pizza(275);
        System.out.println("Calories per serving : " + special.calories_per_serving());
    }
}
class Food{
    //------------------------------------------------------------------------------------------------------------------
    private int CALORIES_PER_GRAMM = 9;
    private int fat, servings;
    //------------------------------------------------------------------------------------------------------------------
    public Food(int num_fat_grams, int num_servings){
        fat = num_fat_grams;
        servings = num_servings;
    }
    //------------------------------------------------------------------------------------------------------------------
    private int calories(){                                         //private, also für die Unterklasse Pizza verborgen
        return fat*CALORIES_PER_GRAMM;
    }
    public int calories_per_serving(){
        return calories()/servings;
    }
}
class Pizza extends Food{
    public Pizza(int num_fat) {
        super(num_fat,8);
    }
}

//-----------------------------------------Verschatten von Attributen---------------------------------------------------

class Parent{
    protected int a;
    public int getA(){
        return a;
    }
}
class Child extends Parent{
    protected int a;
    public void setA(int arg){
        a=arg;                  //bezieht sich auf dieser Klasse
    }
    /*public int getA(){        //wird diese Methode eingeschaltet, dann wird in der Main Methode gerufen und nicht
        return a;               //die von der Parent-Klasse
    }*/
    public void setParentA(int arg){
        super.a = arg;          //bezieht sich auf der Parent Klasse
    }
}
class TestClass{
    public static void main(String[] args) {
        Child c = new Child();
        c.setA(12);
        c.setParentA(36);
        System.out.println("el Padre tiene " + c.getA() + " años y el Hijo tiene " + c.a + " años.");
    }
}
//------------------------------Verschatten von Attributen unterschiedlicher Typen--------------------------------------
class one{
    public static void main(String[] args) {
        H h = new H();
        h.setA(17.3f);
        float a_in_H = h.getA();
        System.out.println("a in h ist " + a_in_H);
    }
}
class G{
    int a;
}
class H extends G{
    float a;

    void setA(float arg){
        a = arg;
        super.a = (int)arg*2;
    }

    float getA(){
        System.out.println("a in G ist " + super.a);
        return a;
    }
}
//----------------------------------------------Abstrakten Klassen------------------------------------------------------
abstract class AbstractoXD{
    int xd;
    public void setXd(int xd) {
        this.xd = xd;
    }
    public abstract void inkrement();
    //public abstract static void dekrement();
}
class PruebaAbstracto{
    //AbstractoXD abstracto = new AbstractoXD;
}
class hijo extends AbstractoXD{

    @Override
    public void inkrement() {
        xd++;
    }
}
//-----------------------------------------------Finale Klassen---------------------------------------------------------
final class FinalXD{
    private final int xd = 10;
    private int xdnt = 20;

    public int getXd() {
        return xd;
    }

    public int getXdnt() {
        return xdnt;
    }

    public void setXdnt(int xdnt) {
        //this.xd = 30;
        this.xdnt = xdnt;
    }
}
//class underFinal extends FinalXD{}
interface InterfaceXD{
    abstract void inkrement();
    abstract void dekrement();
    void setXd();
    final int xd = 9;
    int segundo = 7;

    default void writeXD() {
        System.out.println("XD Método implementado en interface");
    }
}
class InterfacePrueba implements InterfaceXD{

    @Override
    public void inkrement() {
        //segundo++;
    }

    @Override
    public void dekrement() {
        //xd--;
    }

    @Override
    public void setXd() {
        //xd = 9;
    }
}
class PruebaInterface2{
    public static void main(String[] args) {
        InterfacePrueba interfacePrueba = new InterfacePrueba();
        interfacePrueba.writeXD();
        System.out.println(InterfaceXD.segundo);
        System.out.println(InterfaceXD.xd);
    }
}
//----------------------------------------------------Polymorphie-------------------------------------------------------

//Zuerst werden interfaces geschrieben..................................................................................
interface Tamable{
    int CONST = 1;
    static int getNumber() {
        return CONST;
    }
    default void doThing() {
        System.out.println("im Tamable and im doing a thing XD");
    }
    boolean isTamed();
    void getPetted();
}
interface CanSwim{
    int CONST = 29;
    static int getNumber(){
        return CONST;
    }
    default void doThing(){
        System.out.println("I can swim and im doing a Thing XD");
    }
    void swim();
}
//ElderAbstractClass....................................................................................................
abstract class Animal{
    private String name;
    public int age;

    public abstract void eat();
    public abstract void sleep();
    public abstract void makeSound();
}
//SubAbstractClasses....................................................................................................
abstract class Bird extends Animal{
    private boolean canFly;

    public abstract void hatchEgg( Object egg);
}
abstract class Mammal extends Animal{
    public boolean isPregnant;
    public int sizeOfLiter;
    public int age;
}
abstract class Fisch extends Animal{
    private boolean isSaltWaterFish;
}
//ChildNONAbstractClasses...............................................................................................
class Cat extends Mammal implements Tamable{
    private int livesLeft;

    public void drinkMilk(){
        System.out.println("Im a Cat drinking Milk XD");
    }
    @Override
    public void eat() {
        System.out.println("Im a Cat eating XD");
    }
    @Override
    public void sleep() {
        System.out.println("Im a Cat sleepeing XD");
    }
    @Override
    public void makeSound() {
        System.out.println("meowwwwwww XD");
    }
    //Interfaces Methode
    @Override
    public boolean isTamed() {
        return CONST>0;
    }

    @Override
    public void getPetted() {
        System.out.println("Imma Catto Pet right now XD");
    }
}
class Dog extends Mammal implements Tamable, CanSwim{
    public int age;
    public List<Object> tricks;

    public void doTrick(Object Trick){
        System.out.println("Imma Dog doing a Trick XD");
    }
    @Override
    public void eat() {
        System.out.println("Imma Dog eating");
    }
    @Override
    public void sleep() {
        System.out.println("Imma Dog sleeping zzz");
    }
    @Override
    public void makeSound() {
        System.out.println("woof woof XD");
    }

    //Methode from Interfaces

    @Override
    public void doThing() {
        Tamable.super.doThing();
    }

    @Override
    public boolean isTamed() {
        return true;
    }

    @Override
    public void getPetted() {
        System.out.println("Imma Dogo as pet xdnt");
    }

    @Override
    public void swim() {
        System.out.println("Imma swimming Dogo XDXDXDDDD");
    }
}
class Cow extends Mammal{
    private float milkAmount;

    public float giveMilk(float f){
        return f*milkAmount;
    }
    @Override
    public void eat() {
        System.out.println("Imma Cow and im eating muuuuuuu");
    }
    @Override
    public void sleep() {
        System.out.println("Imma Cow and im Sleeping zzz");
    }
    @Override
    public void makeSound() {
        System.out.println("MUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU");
    }
}
//TestClass.............................................................................................................
class TestPolyAnimals{
    public static void main(String[] args) {
        Dog d = new Dog();
        Mammal m = d;
        Animal a = d;
        Tamable t = d;
        CanSwim cs = d;

        d.doTrick(new Object());
        System.out.println(m.sizeOfLiter);
        a.makeSound();
        t.getPetted();
        cs.swim();

        /*((Dog)a)*///a.doTrick(new Object());

        if(a instanceof Dog){
            Dog aAsDog = (Dog)a;
            System.out.println("after Cast");
            aAsDog.doTrick(new Object());
        }

        Animal[] animals= {new Cat(), new Dog(), new Cow()};
        for (int i = 0; i < animals.length; i++) {
            animals[i].makeSound();
        }
    }
}
//-------------------------------------------------------------------------------------------------------------------
interface sabeManejar{
    int notaConduccion = 20;
    static int getNota(){
        return notaConduccion;
    }
}
interface noSabe {
    int notaConduccion = 10;

    static int getNota() {
        return notaConduccion;

    }
}
class ElderPoly{
    public int age = 48;
}
class ChildPoly extends ElderPoly{
    public int age = 21;
}
class hermano implements sabeManejar, noSabe{
    public int age = 15;
}
class TestPoly{
    public static void main(String[] args) {
        ChildPoly child = new ChildPoly();
        ElderPoly elder = child;
        System.out.println("edad del padre es: " + elder.age + " y la edad del hijo es: " + child.age);

        //System.out.println(hermano.notaConduccion);
    }
}