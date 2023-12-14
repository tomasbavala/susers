package service;

import model.Command;
import model.User;

import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;

public class CommandProcessor implements Runnable {
    private final BlockingQueue<Command> commandQueue;
    private final Database database;

    public CommandProcessor(BlockingQueue<Command> commandQueue, Database database) {
        this.commandQueue = commandQueue;
        this.database = database;
    }

    public void run() {
        try {
            while(true) {
                Command command = this.commandQueue.take();
                switch (command.getType()) {
                    case "Add":
                        User user = (User) command.getData();
                        this.database.addUser(user);
                        break;
                    case "PrintAll":
                        this.database.printAllUsers();
                        break;
                    case "DeleteAll":
                        this.database.deleteAllUsers();
                        break;
                }
            }
        } catch (SQLException | InterruptedException var3) {
            Thread.currentThread().interrupt();
        }
    }
}