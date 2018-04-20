package FrequencyAnalysis;

import java.math.BigDecimal;

public class FrequencyParameter {
    private double xp;
    private double cs;
    private double cv;

    public double getXp() {
        return xp;
    }

    public void setXp(double xp) {
        this.xp = xp;
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

    /**
     * 构造方法
     * @param xp 均值
     * @param cs 偏态系数
     * @param cv 离势系数
     */
    public FrequencyParameter(double xp, double cs, double cv) {
        this.xp = xp;
        this.cs = cs;
        this.cv = cv;
    }

    public FrequencyParameter() {
    }

    @Override
    public String toString() {
        return "FrequencyParameter{" +
                "xp=" + xp +
                ", cs=" + cs +
                ", cv=" + cv +
                '}';
    }
    /**
     *连续系列参数估计
     * 根据样本估计参数
     * @param samples 样本序列（无需排序） 个数应大于3个
     * @return FrequencyParameter 样本无偏估计参数
     */
    protected FrequencyParameter(double[] samples){
        for(int i=0;i<samples.length-1;i++) {
            for (int j = 0; j < samples.length - i - 1; j++) {
                if (samples[j] < samples[j + 1]) {
                    double temp = samples[j + 1];
                    samples[j + 1] = samples[j];
                    samples[j] = temp;
                }
            }
        }
        double sum=0;
        //(ki-1)^2
        double sumk2=0;
        //(ki-1)^3
        double sumk3=0;
        for (int i = 0; i <samples.length; i++) {
            sum+=samples[i];
        }
        sum=sum/samples.length;
        for (int i = 0; i <samples.length ; i++) {
            sumk2+=Math.pow (samples[i]/sum-1,2);
            sumk3+=Math.pow (samples[i]/sum-1,3);;
        }
        this.setXp(new BigDecimal(sum).setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue());
        this.setCv(new BigDecimal(Math.sqrt(sumk2/(samples.length-1))).setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue());
        this.setCs(new BigDecimal(sumk3/((samples.length-3)*Math.pow(this.getCv(),3))).setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue());
    }
}
