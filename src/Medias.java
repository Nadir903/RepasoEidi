import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

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