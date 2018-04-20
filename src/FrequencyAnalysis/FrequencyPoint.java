package FrequencyAnalysis;

public class FrequencyPoint {
    private double frequency;
    private double value;
    public double getFrequency() {
        return frequency;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public FrequencyPoint(double frequency, double value) {
        this.frequency = frequency;
        this.value = value;
    }

    public FrequencyPoint() {
    }

    @Override
    public String toString() {
        return "FrequencyPoint{" +
                "frequency=" + frequency +
                ", value=" + value +
                '}';
    }
}
