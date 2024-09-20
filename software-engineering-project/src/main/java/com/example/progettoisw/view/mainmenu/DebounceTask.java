package com.example.progettoisw.view.mainmenu;

import javafx.util.Duration;

public class DebounceTask extends Thread {

    private final Duration duration;
    private final Runnable task;
    private boolean isExecuted = false;

    public DebounceTask(Duration duration, Runnable task) {
        super();

        this.duration = duration;
        this.task = task;
    }

    @Override
    public void run() {
        super.run();

        try {
            Thread.sleep((long) duration.toMillis());
            isExecuted = true;

            task.run();
        } catch (InterruptedException e) {
        }
    }

    public boolean isExecuted() {
        return isExecuted;
    }
}
