package com.rosettastone.succor.backgroundtask;


/**
 * Model class for global tasks statistic
 * (processed, unprocessed, processed with errors messages).
 */
public class MessageStatistic {

    private long completed;

    private long active;

    private long cancelled;

    private long total;


    public long getCompleted() {
        return completed;
    }

    public void setCompleted(long completed) {
        this.completed = completed;
    }

    public long getActive() {
        return active;
    }

    public void setActive(long active) {
        this.active = active;
    }

    public long getCancelled() {
        return cancelled;
    }

    public void setCancelled(long cancelled) {
        this.cancelled = cancelled;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
