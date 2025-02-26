package core.basesyntax;

import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 10;
    private final int startPoint;
    private final int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (finishPoint - startPoint < THRESHOLD) {
            return findSum(startPoint, finishPoint);
        }

        int mid = startPoint + (finishPoint - startPoint) / 2;
        RecursiveTask<Long> leftTask = new MyTask(startPoint, mid);
        RecursiveTask<Long> rightTask = new MyTask(mid, finishPoint);

        leftTask.fork();
        rightTask.fork();
        long leftRes = leftTask.join();
        long rightRes = rightTask.join();

        return leftRes + rightRes;
    }

    private Long findSum(int start, int finish) {
        long sum = 0L;
        for (int i = start; i < finish; i++) {
            sum += i;
        }
        return sum;
    }
}
