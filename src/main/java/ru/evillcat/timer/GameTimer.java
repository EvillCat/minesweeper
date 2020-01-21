package ru.evillcat.timer;

import ru.evillcat.event.bus.EventBus;
import ru.evillcat.event.minesweeper.type.TimerTickEvent;

import java.util.Timer;
import java.util.TimerTask;

public class GameTimer {

    private static final Object SYNC = new Object();
    private final EventBus eventBus;

    private volatile int seconds = 0;
    private Timer timer;

    public GameTimer(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void run() {
        timer = new Timer();
        timer.schedule(createTask(), 0, 1000);
    }

    public int getSeconds() {
        return seconds;
    }

    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

    public void resetTimer() {
        seconds = 0;
        timer.purge();
    }

    public void restart() {
        timer = new Timer();
        timer.schedule(createTask(), 0, 1000);
    }

    private TimerTask createTask() {
        return new TimerTask() {
            @Override
            public void run() {
                synchronized (SYNC) {
                    eventBus.publish(new TimerTickEvent(++seconds));
                }
            }
        };
    }
}


