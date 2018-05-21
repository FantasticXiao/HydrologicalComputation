package forecast;

import java.util.LinkedList;


/**
 * @author amos_zhao
 */
public class LimitSizeQueue{

    private int size;

    private LinkedList<Double> queue = new LinkedList<>();

    LimitSizeQueue(int size){
        this.size = size;
        Double empty=0.0;
        for (int i = 0; i <size ; i++) {
            queue.offer(empty);
        }
    }
    public void offer(Double e){
        if(queue.size() >= size){
            queue.poll();
        }
        queue.offer(e);
    }

    public Double getFirst() {
        return queue.getFirst();
    }

    public Double getLast() {
        return queue.getLast();
    }

}