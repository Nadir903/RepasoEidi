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