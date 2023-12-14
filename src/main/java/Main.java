import model.Command;
import service.CommandProcessor;
import service.CommandProducer;
import service.Database;

import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    public static void main(String[] args) {

        BlockingQueue<Command> commandQueue = new ArrayBlockingQueue<>(10);

        try {
            Database database = new Database();
            Thread producerThread = new Thread(new CommandProducer(commandQueue));
            Thread consumerThread = new Thread(new CommandProcessor(commandQueue, database));

            producerThread.start();
            consumerThread.start();

            producerThread.join();
            consumerThread.join();

            database.closeConnection();
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}