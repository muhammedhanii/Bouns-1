package com.parking.smartparking.command;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Command Invoker for managing and executing commands.
 */
@Component
public class CommandInvoker {
    private final Queue<Command> commandQueue = new LinkedList<>();

    public void addCommandToQueue(Command cmd) {
        commandQueue.add(cmd);
    }

    public Object executeNext() {
        Command command = commandQueue.poll();
        if (command != null) {
            return command.execute();
        }
        return null;
    }

    public List<Command> getQueue() {
        return new ArrayList<>(commandQueue);
    }

    public void clearQueue() {
        commandQueue.clear();
    }

    public int getQueueSize() {
        return commandQueue.size();
    }
}
