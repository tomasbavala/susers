package service;

import model.Command;
import model.User;

import java.util.concurrent.BlockingQueue;

public class CommandProducer implements Runnable {
    private final BlockingQueue<Command> commandQueue;

    public CommandProducer(BlockingQueue<Command> commandQueue) {
        this.commandQueue = commandQueue;
    }

    public void run() {
        try {
            this.commandQueue.put(new Command("Add", new User(1, "a1", "Robert")));
            this.commandQueue.put(new Command("Add", new User(2, "a2", "Martin")));
            this.commandQueue.put(new Command("PrintAll", null));
            this.commandQueue.put(new Command("DeleteAll", null));
            this.commandQueue.put(new Command("PrintAll", null));
        } catch (InterruptedException var2) {
            Thread.currentThread().interrupt();
        }

    }
}
