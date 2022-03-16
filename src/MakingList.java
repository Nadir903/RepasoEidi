//-----------------------------------------------einfache Liste---------------------------------------------------------
class List {
    public Object info;
    public List next;
    public List(Object info, List next) {
        this.info = info;
        this.next = next;
    }
    public List(Object info) {
        this.info = info;
        next = null;
    }
    public void insert(Object info) {
        next = new List(info, next);
    }
    public void delete() {
        if (next != null) {
            next = next.next;
        }
    }
    private int length(){
        int result = 1;
        for (List t = next; t!= null; t = t.next) {
            result++;
        }
        return result;
    }
    public static List arrayToList(Object[] array){
        List result = null;
        if(array!=null){
            for (int i = array.length-1 ; i >=0 ; i--) {
                result = new List(array[i], result);
            }
        }
        return result;
    }
    public Object[] listToArray(){
        List t = this;
        int n = length();
        Object[] array = new Object[n];
        for (int i = 0; i < length(); i++) {
            array[i]= info;
            t= t.next;
        }
        return array;
    }
    public static boolean isEmpty(List list){
        return list==null;
    }
    public static String toString(List list){
        return list == null? "[]" : list.toString();
    }
    public String toString() {
        StringBuilder result = new StringBuilder("[ " + info);
        for (List t = next; t != null; t = t.next) {
            result.append(", ").append(t.info);
        }
        return result + "]";
    }
}
/*class Collection{
    public static void main(String[] args) {
        Object numero = 7;
        Object palabra = "XD";
        Object valor = true;
        Object caracter = 'c';

        List miLista = new List(numero);
        miLista.insert(palabra);
        miLista.insert(valor);
        miLista.insert(caracter);

        System.out.println(miLista.toString());
        System.out.println(List.isEmpty(miLista));
    }
}*/
//-------------------------------------------------------Stack----------------------------------------------------------
class Stack{
    private List list;

    public Stack(){
        list = null;
    }
    public boolean isEmpty(){
        return list==null;
    }
    public Object pop(){
        if(list == null){return null;}
        Object result = list.info;
        list = list.next;
        return result;
    }
    public void push(Object a){
        list = new List(a,list);
    }
    public String toString(){
        return List.toString(list);
    }
}
//---------------------------------------------------StackArray---------------------------------------------------------
class StackArray{
    private int sp;
    private int[] array;

    public StackArray(){
        sp = -1;
        array = new int[10];
    }
    public boolean isEmpty(){
        return sp<0;
    }
    public int pop(){
        return array[sp--];
    }
    public void push(int x){
        sp++;
        if(sp == array.length){
            int[] newArray = new int[sp*2];
            for (int i = 0; i <sp ; i++) {
                newArray[i] = array[i];
            }
            array = newArray;
        }
        array[sp] = x;
    }
    public String toString(){
        String result = "[ " + array [0];
        for (int i = 1; i <= sp; i++) {
            result = result + ", " + array[i];
        }
        return result + " ]";
    }
}
/*class PlayGroundStackArray{
    public static void main(String[] args) {
        StackArray sa = new StackArray();
        sa.push(1);
        sa.push(2);
        sa.push(3);
        sa.push(4);
        sa.push(5);
        sa.push(6);
        System.out.println(sa.toString());
    }
}*/
class Queue{
    private List first;
    private List last;

    public Queue(){
        first = last = null;
    }
    public boolean isEmpty(){
        return first == null;
    }
    public Object dequeue() {
        if (isEmpty()) {
            return null;
        }
        Object result = first.info;
        if (last == first) {
            last = null;
        }
        first = first.next;
        return result;
    }
    public void enqueue(Object x){
        if(isEmpty()){
            first = last = new List(x);
        }else{
            last.next = new List(x);
            last = last.next;
        }
    }
    public String toString(){
        return List.toString(first);
    }
}
class QueueArray{
    int first;
    int last;
    int[] array;

    public QueueArray(){
        first = last = -1;
        array = new int[10];
    }
    public boolean isEmpty(){
        return first==-1;
    }
    public void enqueue(int x){
        if(isEmpty()){
            first = last = 0;
        }else{
            int a = array.length;
            last = (last+1)%a;
            if(last==first){                    //list voll
                int[] newArray = new int[a*2];
                for (int i = 0; i < a; i++) {
                    newArray[i] = array[(first + i)%a];
                    first = 0;
                    last = a;
                    array = newArray;
                }
            }
        }
        array[last]= x;
    }
    public int dequeue(){
        if(first == -1){return -1;}
        int result = array[first];
        if(first == last){
            first = last = -1;
        }else{
            first = (first+1)% array.length;
        }
        return result;
    }
}
//--------------------------------------------List with Generics--------------------------------------------------------
class GenericList<T>{
    public T info;
    public GenericList<T> next;

    public GenericList(T info, GenericList<T> next){
        this.info = info;
        this.next = next;
    }
    public GenericList(T info){
        this.info = info;
        this.next = null;
    }
    public void insert(T info){
        this.next = new GenericList<T>(info,next);
    }
    public void delete(){
        if(next != null){
            next = next.next;
        }
    }
    public String toString() {
        StringBuilder result = new StringBuilder("[ " + info);
        for (GenericList t = next; t != null; t = t.next) {
            result.append(", ").append(t.info);
        }
        return result + "]";
    }
}
/*class GenericListPlayGround{
    public static void main(String[] args) {
        GenericList<Integer> listaGenerica = new GenericList<Integer>(0);
        for (int i = 1; i <=10 ; i++) {
            listaGenerica.insert(i);
        }
        System.out.println(listaGenerica.toString());
    }
}*/
interface Executable{
    void execute();
}
class ExecutableList<E extends Executable>{
    E element;
    ExecutableList<E> next;
    void executeAll(){
        element.execute();
        if(next==null){return;}
        else {next.executeAll();}
    }
}