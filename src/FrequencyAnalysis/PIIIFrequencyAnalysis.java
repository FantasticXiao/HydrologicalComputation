package FrequencyAnalysis;

import java.util.List;
import java.util.Map;

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
    private double csv;

    private int N1;
    private int N2;
    private int l1;
    private int l2;

    private List<Double> values;
    private List<Double> frequencies;

    private List<Double> plotValues;
    private List<Double> plotFrequencies;

    private Map<String ,List<Double>> map;
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

    public void setData(List<Double> values) {
        this.values = values;
    }

    public int getN1() {
        return N1;
    }

    public void setN1(int n1) {
        N1 = n1;
    }

    public int getN2() {
        return N2;
    }

    public void setN2(int n2) {
        N2 = n2;
    }

    public int getL1() {
        return l1;
    }

    public void setL1(int l1) {
        this.l1 = l1;
    }

    public int getL2() {
        return l2;
    }

    public void setL2(int l2) {
        this.l2 = l2;
    }

    public void setValues(List<Double> values) {
        this.values = values;
    }

    public List<Double> getFrequencies() {
        return frequencies;
    }

    public void setFrequencies(List<Double> frequencies) {
        this.frequencies = frequencies;
    }

    public List<Double> getPlotValues() {
        return plotValues;
    }

    public void setPlotValues(List<Double> plotValues) {
        this.plotValues = plotValues;
    }

    public List<Double> getPlotFrequencies() {
        plotFrequencies=FrequencyDictionary.getPlotFrequencies();
        return plotFrequencies;
    }

    public void setPlotFrequencies(List<Double> plotFrequencies) {
        this.plotFrequencies = plotFrequencies;
    }

    public Map<String, List<Double>> getMap() {
        return map;
    }

    public void setMap(Map<String, List<Double>> map) {
        this.map = map;
    }

    public PIIIFrequencyAnalysis(double ex, double cs, double cv) {
        this.ex = ex;
        this.cs = cs;
        this.cv = cv;
    }
    public PIIIFrequencyAnalysis(double ex, double cv,double csv,int id) {
        this.ex = ex;
        this.cv = cv;
        setCsv(csv);
    }

    /**
     * there is no extrem value in data series
     * @param values series
     */
    public PIIIFrequencyAnalysis(List<Double> values) {
        this.values = values;
        //TODO 矩法计算参数
        //TODO 计算经验点距
    }

    /**
     * Unified treatment method
     * one return period
     * @param values data series
     * @param N1 first return period length
     * @param l1 the value which be count in first series who belongs to ordinary period
     */
    public PIIIFrequencyAnalysis(List<Double> values,int N1,int l1) {
        this.values = values;
        this.N1=N1;
        this.l1=l1;
        //TODO 矩法计算参数
        //TODO 计算经验点距 一个重现期
    }

    /**
     * Unified treatment method
     * two return period
     * @param values data series
     * @param N1 first return period length
     * @param l1 the value which be count in first series who belongs to second period
     * @param N2 second return period length
     * @param l2 the value which be count in second  series who belongs to ordinary period
     */
    public PIIIFrequencyAnalysis(List<Double> values,int N1,int l1,int N2,int l2) {
        this.values = values;
        this.N1=N1;
        this.l1=l1;
        this.N2=N2;
        this.l2=l2;
        //TODO 矩法计算参数
        //TODO 计算经验点距 两个重现期
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
     * get simulate data with specific parameter
     * @return list  Map<String,List<Double>>  key:1.frequencies 2.values
     * @see FrequencyDictionary
     */
    public Map<String,List<Double>> listSimulatePoints(){
        plotFrequencies=FrequencyDictionary.getPlotFrequencies();
        plotValues=FrequencyDictionary.listSimulatePoints(plotFrequencies,ex,cs,cv);
        map.put("frequencies",plotFrequencies);
        map.put("values",plotFrequencies);
        return map;
    }
}
