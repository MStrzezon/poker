package pl.edu.agh.kis.pz1.server;

import pl.edu.agh.kis.pz1.game.Game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    private final ServerSocket serverSocket;
    public static int maxPlayers = 4;
    private static final Logger logger = Logger.getLogger( Server.class.getName() );
    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;}

    public void startServer() {
        try {
            while (!serverSocket.isClosed()) {
                    Socket socket = serverSocket.accept();
                    logger.info("A new client has connected!");
                    ClientHandler clientHandler = new ClientHandler(socket);

                    Thread thread = new Thread(clientHandler);
                    thread.start();
            }
        } catch (IOException e) {
            closeServerSocket();
        }
    }
    public void closeServerSocket() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch(IOException e) {
            logger.info(e.getMessage());

        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            logger.log(Level.WARNING, "Usage: java -jar ... <max_players(2-4)>");
            System.exit(1);
        }
        maxPlayers = Integer.parseInt(args[0]);
        ServerSocket serverSocket = new ServerSocket(1234);
        Server server = new Server(serverSocket);
        server.startServer();
    }
}
