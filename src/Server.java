import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.ServerError;

public class Server {

    public Server()  {

        try(ServerSocket socket = new ServerSocket(41994)) {

            while (true) {

                GameRoom game = new GameRoom();

                ServerPlayer player1 = new ServerPlayer(socket.accept(),game);

                ServerPlayer player2 = new ServerPlayer(socket.accept(),game);

                player1.start();

                player2.start();

                player1.setOpponent(player2);

                player2.setOpponent(player1);

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        Server s = new Server();
    }
}
