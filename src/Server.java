
import java.io.IOException;
import java.net.ServerSocket;


public class Server {

    public Server()  {

        try(ServerSocket socket = new ServerSocket(1234)) {

            while (true) {

                GameRoom game = new GameRoom();

                ServerPlayer player1 = new ServerPlayer(socket.accept(),game,"1");

                ServerPlayer player2 = new ServerPlayer(socket.accept(),game,"2");

                player1.setOpponent(player2);

                player2.setOpponent(player1);

                player1.start();

                player2.start();

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Server s = new Server();
    }
}
