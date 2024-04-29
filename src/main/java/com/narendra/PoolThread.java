package com.narendra;

import java.util.concurrent.BlockingQueue;

public class PoolThread implements Runnable {

    private Thread        thread    = null;
    private BlockingQueue<Runnable> taskQueue = null;
    private boolean       isStopped = false;

    public PoolThread(BlockingQueue<Runnable> queue){
        taskQueue = queue;
    }

    public void run(){
        this.thread = Thread.currentThread();
        while(!isStopped()){
            try{
                Runnable runnable = taskQueue.take();
                runnable.run();
            } catch(Exception e){
                //log or otherwise report exception,
                //but keep pool thread alive.
            }
        }
    }

    public synchronized void doStop(){
        isStopped = true;
        //break pool thread out of dequeue() call.
        this.thread.interrupt();
    }

    public synchronized boolean isStopped(){
        return isStopped;
    }
}
