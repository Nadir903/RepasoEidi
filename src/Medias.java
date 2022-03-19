import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

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