package com.capstone2.dnsos;


public class Test {

    static class DisplayThread extends Thread {
        private String info;

        DisplayThread(String info) {
            this.info = info;
        }

        public void run() {
            System.out.println(info);
        }
    }

    public static void main(String[] args) {
        // Tạo và khởi chạy một luồng để hiển thị tên CPU
        Thread cpuThread = new Thread(new Runnable() {
            public void run() {
                String cpuName = System.getenv("PROCESSOR_IDENTIFIER");
                System.out.println("Tên của CPU: " + cpuName);
            }
        });
        cpuThread.start();

        // Tạo và khởi chạy một luồng để hiển thị tên các luồng đang chạy
        Thread runningThreads = new Thread(new Runnable() {
            public void run() {
                ThreadGroup group = Thread.currentThread().getThreadGroup();
                while (group.getParent() != null) {
                    group = group.getParent();
                }
                Thread[] threads = new Thread[group.activeCount()];
                group.enumerate(threads);
                System.out.println("Các luồng đang chạy:");
                for (Thread thread : threads) {
                    if (thread != null) {
                        System.out.println("- " + thread.getName());
                    }
                }
            }
        });
        runningThreads.start();
    }

}

