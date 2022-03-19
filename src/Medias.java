import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

//------------------------------------------First Example (One Way)-----------------------------------------------------
class Server{
    public static void main(String[] args) {
        try{
            ServerSocket server = new ServerSocket(1234);
            Socket socket = server.accept();    //establishes connection

            DataInputStream data = new DataInputStream(socket.getInputStream());
            String strSI = data.readUTF();

            System.out.println("Message: " + strSI);
            socket.close();

        }catch (Exception e){
            System.out.println(e + " mensaje no recibido");
        }
    }
}
class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost",1234);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF("Hola mundo");
            out.flush();
            out.close();
            socket.close();
        }catch (Exception e){
            System.out.println(e + " mensaje no enviado");
        }
    }
}
//--------------------------------------------------Eidi-Beispiele------------------------------------------------------
class SocketEidi{
    public static void main(String[] args) throws IOException{
        Socket socket = new Socket("localhost",9999);
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        out.print("hola mundo");
        out.flush();
        out.close();
        socket.close();
    }
}
class ServerEidi{
    public static void main(String[] args) {
        try{
            ServerSocket server = new ServerSocket(9999);
            Socket socket = server.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while(!in.ready()){ /*ready(liefert true, sofern ein Zeichen gelesen werden kann*/}
            String incoming = in.readLine();
            in.close();
            socket.close();
            server.close();
            System.out.println(incoming);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
//Bidirektionale Communication
class NetworkServer{
    private final ServerSocket server;
    NetworkServer() throws IOException{
        this.server = new ServerSocket(1234);
    }
    private void connectAndTalk(){
        Socket socket = null;
        try{
            socket = server.accept();
            comunicate(socket);
        }catch(IOException | InterruptedException e){
            e.printStackTrace();
        } finally {
            if(socket!= null){
                try{
                    socket.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
    private void comunicate(Socket socket) throws IOException, InterruptedException{
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        String s;
        do{
            s = in.readLine();
            System.out.println("server received: " + s);
            if(s.equals("Es begab sich zu der Zeit")){
                TimeUnit.SECONDS.sleep(4);
                out.println("dass ein Gebot ausging vom Kaiser Augustus");
            }else if(s.equals("dass alle Welt geschätzt würde")) {
                TimeUnit.SECONDS.sleep(3);
                out.println("das machte sich auch auf Joseph aus der Stadt Nazareth");
            }else if(s.equals("in das jüdische Land zur Stadt Davids")) {
                TimeUnit.SECONDS.sleep(3);
                out.println("die da heißt Bethlehem");
            }
        }while (!s.equals("Ende"));
    }

    public static void main(String[] args) {
        class NetworkClient{
            public static void main(String[] args) {
                Socket socket = null;
                try{
                    socket = new Socket("localhost",1234);
                    OutputStream out = socket.getOutputStream();
                    PrintStream ps = new PrintStream(out,true);
                    InputStream in = socket.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    ps.println("Es begab sich aber zu der Zeit,");
                    System.out.println("client sent: Es begab sich aber zu der Zeit,");
                    System.out.println("client received: "+reader.readLine());
                    ps.println("dass alle Welt geschaetzet wuerde.");
                    System.out.println("client sent: dass alle Welt geschaetzet wuerde.");

                    System.out.println("client received: "+reader.readLine());
                    ps.println("in das juedische Land zur Stadt Davids,");
                    System.out.println("client sent: in das juedische Land zur Stadt Davids,");

                    System.out.println("client received: "+reader.readLine());
                    ps.println("ENDE");
                    System.out.println("client sent: ENDE");
                }catch(IOException e){
                    e.printStackTrace();
                }finally {
                    if(socket != null){
                        try {
                            socket.close();
                            System.out.println("Alles beendet");
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            }

        }
    }
}


//----------------------------------------------------Eidi-Ende---------------------------------------------------------
class SocketFromPGDP{
    private String host;
    private String path;
    public SocketFromPGDP(String host, String path){
        this.host = host;
        this.path = path;
    }
    public String getHost() {return host;}
    public String getPath() {return path;}
    //-------------------------------------
    public String send(int port){
        try (Socket socket = new Socket(host,port);){
            //cliente
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.print("GET " +"/"+ path + " HTTP/1.1" + "\r\n");
            out.print("Host: " + host + "\r\n");
            out.print("\r\n");
            out.flush();
            //servidor
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line = "";
            while(line != null){
                line = in.readLine();
                response.append(line).append("\n");
            }
            return response.toString();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("nothing found");
            return null;
        }
    }
    public static void main(String[] args) throws IOException {
        SocketFromPGDP httpRequest = new SocketFromPGDP("man1.pgdp.sse.in.tum.de", "showchar.1.html");
        System.out.println(httpRequest.send(443));
    }
}
//------------------------------------------------Eingabe und Ausgabe---------------------------------------------------
//For Bytes
class FromData{
    public static void main(String[] args) throws FileNotFoundException, IOException{
        String message = "C:\\Users\\nadir\\OneDrive\\Documentos\\Informatica\\Semestre1\\Eidi\\ForStreams.txt";
        FileInputStream file = new FileInputStream(message);
        int t = 0;
        while (t != -1){
            t = file.read();
            System.out.print((char)t);        //ohne char casting werde nur Zahlen geliefert
        }
        System.out.println("\n");
        //liefert : There are mysteries to the universe we were never ment to solve,
        //but who we are, and why we are here are not among them
        //those answers carry we in us. I am Optimus Prime, and I send this message to
        //our creators, let the planet Earth alone, cause I'm coming for you...￿
    }
}
class FromDataDataInPutStream{
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String message = "C:\\Users\\nadir\\OneDrive\\Documentos\\Informatica\\Semestre1\\Eidi\\ForStreams.txt";
        FileInputStream file = new FileInputStream(message);
        DataInputStream data = new DataInputStream(file);
        char x;
        for (int i = 0; i < file.available()/2; ++i) {
            x = data.readChar();
            System.out.print(x);
        }
        System.out.println("\n");
        //liefert: 周敲攠慲攠浹獴敲楥猠瑯⁴桥⁵湩癥牳攠睥⁷敲攠湥癥爠浥湴⁴漠獯汶攬ഊ扵琠睨漠睥⁡牥Ⱐ慮搠睨礠睥⁡牥⁨敲攠慲攠湯琠
        // 慭潮朠瑨敭ഊ瑨潳攠慮獷敲     XD readChar() liest nicht ein Latin-1-Zeichen, sondern die 16-Bit representation
        // eines Unicode-Zeichen.
    }
}
class CopiarXD{
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String message = "C:\\Users\\nadir\\OneDrive\\Documentos\\Informatica\\Semestre1\\Eidi\\ForStreams.txt";
        String destiny = "C:\\Users\\nadir\\OneDrive\\Documentos\\Informatica\\Semestre1\\Eidi\\ForStreamsOutput.txt";
        FileInputStream file = new FileInputStream(message);
        FileOutputStream copy = new FileOutputStream(destiny);
        int t = 0;
        while(t != -1){
            t = file.read();
            copy.write(t);
        }
        file.close();
        copy.close();
    }
}
//For Zeichen
class CountLines{
    public static void main(String[] args){
        try{
            String Path = "C:\\Users\\nadir\\OneDrive\\Documentos\\Informatica\\Semestre1\\Eidi\\ForStreams.txt";
            FileReader file = new FileReader(Path);
            BufferedReader reader = new BufferedReader(file);
            int i = 0;
            while(reader.readLine()!= null){ i ++;}
            reader.close();
            System.out.println("Number of Lines ist: " + i );
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
class CopyWithZeichen{
    public static void main(String[] args) {
        try{
            String Path = "C:\\Users\\nadir\\OneDrive\\Documentos\\Informatica\\Semestre1\\Eidi\\StreamText.txt";
            String destiny = "C:\\Users\\nadir\\OneDrive\\Documentos\\Informatica\\Semestre1\\Eidi\\StreamTextCopied.txt";
            BufferedReader reader = new BufferedReader(new FileReader(Path));
            PrintWriter writer = new PrintWriter(new FileWriter(destiny));
            StringBuilder message = new StringBuilder();
            String line = "";
            while (line != null){
                line = reader.readLine();
                message.append(line).append("\n");
            }
            writer.write(message.toString());
            writer.flush();
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
class SameLikeBeforeButFromTheClass{
    public static void main(String[] args) {
        try{
            String Path = "C:\\Users\\nadir\\OneDrive\\Documentos\\Informatica\\Semestre1\\Eidi\\StreamText.txt";
            String destiny = "C:\\Users\\nadir\\OneDrive\\Documentos\\Informatica\\Semestre1\\Eidi\\StreamTextCopied.txt";
            FileReader in = new FileReader(Path);
            FileWriter out = new FileWriter(destiny);
            for (int i = in.read(); i != -1 ; i = in.read()) {
                out.write(i);
            }
            in.close();
            out.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}