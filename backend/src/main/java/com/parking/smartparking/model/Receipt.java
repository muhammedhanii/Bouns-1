package com.parking.smartparking.model;

import java.time.LocalDateTime;

/**
 * Model class representing a Receipt in the parking system.
 */
public class Receipt {
    private Long receiptId;
    private Long ticketId;
    private double duration;
    private double amount;
    private LocalDateTime exitTime;

    public Receipt() {}

    public Receipt(Long receiptId, Long ticketId, double duration, double amount, LocalDateTime exitTime) {
        this.receiptId = receiptId;
        this.ticketId = ticketId;
        this.duration = duration;
        this.amount = amount;
        this.exitTime = exitTime;
    }

    public Long getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Long receiptId) {
        this.receiptId = receiptId;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }

    public double calculateFee() {
        // Rate: $2 per hour, minimum $5
        double fee = Math.max(5.0, duration * 2.0);
        return Math.round(fee * 100.0) / 100.0;
    }

    public String getReceiptDetails() {
        return String.format("Receipt[id=%d, ticket=%d, duration=%.2fh, amount=$%.2f, exit=%s]",
            receiptId, ticketId, duration, amount, exitTime);
    }
}
