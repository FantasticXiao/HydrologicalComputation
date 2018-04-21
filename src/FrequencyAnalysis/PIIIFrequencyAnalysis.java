package FrequencyAnalysis;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @function do frequency analysis with p-III distribute
 *            two return period
 * @Author Amos Zhao
 * @Data 2018.04.20
 * @version 1.0
 */
public class PIIIFrequencyAnalysis {
    private double ex;
    private double cs;
    private double cv;
    private double csv=2;

    /**
     * Important!!!
     * the unit of  frequency is %
     * which means 0.999 in series is 99.9
     */
    private List<Double> frequencies=new ArrayList<>();
    private List<Double> values=new ArrayList<>();
    private List<Double> plotFrequencies=new ArrayList<>();

    public double getEx() {
        return ex;
    }

    public void setEx(double ex) {
        this.ex = ex;
    }

    public double getCs() {
        return cs;
    }

    public void setCs(double cs) {
        this.cs = cs;
    }

    public double getCv() {
        return cv;
    }

    public void setCv(double cv) {
        this.cv = cv;
    }

    public double getCsv() {
        return csv;
    }

    public void setCsv(double csv) {
        this.csv = csv;
        this.cs=this.cv*csv;
    }

    public List<Double> getValues() {
        return values;
    }

    public List<Double> getFrequencies() {
        return frequencies;
    }

    public List<Double> getPlotValues() {
        return FrequencyDictionary.listSimulatePoints(plotFrequencies, ex, cs, cv);
    }

    public List<Double> getPlotFrequencies() {
        return plotFrequencies;
    }

    public void setPlotFrequencies(List<Double> plotFrequencies) {
        this.plotFrequencies = plotFrequencies;
    }

    PIIIFrequencyAnalysis(double ex, double cv, double cs) {
        this.ex = ex;
        this.cs = cs;
        this.cv = cv;
    }
    PIIIFrequencyAnalysis(double ex, double cv) {
        this.ex = ex;
        this.cv = cv;
        setCsv(csv);
    }

    /**
     * there is no extrem value in data series
     * @param values series
     */
    PIIIFrequencyAnalysis(List<Double> values) {
        this.setPlotFrequencies(FrequencyDictionary.getPlotFrequencies());
        this.values = values;
        this.values.sort(Collections.reverseOrder());
        frequencies=new ArrayList<>();
        calculateParameter();
        for(int i=1;i<=values.size();i++){
            frequencies.add(new BigDecimal((i/(values.size()+1.0))*100)
                    .setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue());
        }
    }

    /**
     * Unified treatment method
     * one return period
     * @param values data series
     * @param N1 first return period length
     * @param a1 the count of first series
     * @param l1 the value which be count in first series who belongs to ordinary period
     */
    PIIIFrequencyAnalysis(List<Double> values, int N1, int a1, int l1) {
        this.setPlotFrequencies(FrequencyDictionary.getPlotFrequencies());
        this.values = values;
        this.values.sort(Collections.reverseOrder());
        frequencies=new ArrayList<>();
        calculateParameter();
        for(int i=1;i<=a1;i++){
            frequencies.add(new BigDecimal((i/(N1+1.0))*100)
                    .setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        double tmp=frequencies.get(a1-1)/100;
        for(int i=l1+1;i<=values.size()-a1;i++){
            frequencies.add(new BigDecimal((tmp+(1-tmp)* i/(values.size()-a1+1.0))*100)
                    .setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue());
        }
    }

    /**
     * Unified treatment method
     * two return period
     * @param values data series
     * @param N1 first return period length
     * @param a1 the data size of first series
     * @param l1 the value which be count in first series who belongs to second period
     * @param N2 second return period length
     * @param a2 the data size of second series
     * @param l2 the value which be count in second  series who belongs to ordinary period
     */
    PIIIFrequencyAnalysis(List<Double> values, int N1, int a1, int l1, int N2, int a2, int l2) {
        this.setPlotFrequencies(FrequencyDictionary.getPlotFrequencies());
        this.values = values;
        this.values.sort(Collections.reverseOrder());
        frequencies=new ArrayList<>();
        calculateParameter();
        for(int i=1;i<=a1;i++){
            frequencies.add(new BigDecimal((i/(N1+1.0))*100)
                    .setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        double tmp=frequencies.get(a1-1)/100;
        for(int i=l1+1;i<=a2;i++){
            frequencies.add(new BigDecimal((tmp+(1-tmp)* i/(N2+1.0))*100)
                    .setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        tmp=frequencies.get(frequencies.size()-1)/100;
        for(int i=l2+1;i<=values.size()-a1-a2;i++){
            frequencies.add(new BigDecimal((tmp+(1-tmp)* i/(values.size()-a1-a2+1.0))*100)
                    .setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue());
        }
    }

    /**
     * @see FrequencyDictionary
     */
    public double getValueByFrequency(double frequency){
        return FrequencyDictionary.getValueByFrequency(ex,cv,cs,frequency);
    }
    /**
     * @see FrequencyDictionary
     */
    public double getFrequencyByValue(double value){
        return FrequencyDictionary.getFrequencyByValue(ex,cv,cs,value);
    }

    /**
     * calculate initial parameter by Moment method
     */
    private void calculateParameter(){
        double sum=0;
        //(ki-1)^2
        double sumk2=0;
        //(ki-1)^3
        double sumk3=0;
        for (Double value1 : values) {
            sum += value1;
        }
        sum=sum/values.size();
        for (Double value : values) {
            sumk2 += Math.pow(value / sum - 1, 2);
            sumk3 += Math.pow(value / sum - 1, 3);
        }
        this.setEx(new BigDecimal(sum).setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue());
        this.setCv(new BigDecimal(Math.sqrt(sumk2/(values.size()-1))).setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue());
        this.setCs(new BigDecimal(sumk3/((values.size()-3)*Math.pow(this.getCv(),3))).setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue());
    }
}
