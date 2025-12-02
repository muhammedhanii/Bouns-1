package com.parking.smartparking.command;

/**
 * Command interface for the Command Pattern.
 */
public interface Command {
    Object execute();
    void undo();
}
