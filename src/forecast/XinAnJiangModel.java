package forecast;

import java.util.*;

/**
 * @author Amos_zhao
 * @Date 2018-05-04
 * @Description small time scale model designed for grid unit
 * This class is main entrance
 * use map store model state
 */
public class XinAnJiangModel {
    private static final double VALUE_EQUALS_ZERO = 0.0001;
    public static final String EVAPOTRANSPIRATION_IN_UP_LAYER = "EU";
    public static final String EVAPOTRANSPIRATION_IN_LOW_LAYER = "EL";
    public static final String EVAPOTRANSPIRATION_IN_DEEP_LAYER = "ED";
    public static final String EVAPOTRANSPIRATION_IN_TOTAL = "ET";
    public static final String RAINFALL_NET = "PE";
    public static final String WATER_STORAGE_IN_UP_LAYER = "WU";
    public static final String WATER_STORAGE_IN_LOW_LAYER = "WL";
    public static final String WATER_STORAGE_IN_DEEP_LAYER = "WD";
    public static final String WATER_STORAGE_IN_TOTAL = "WT";
    public static final String RUNOFF_IN_TOTAL = "RT";
    public static final String RUNOFF_ON_SURFACE = "RS";
    public static final String RUNOFF_ON_INNER_FLOW = "RI";
    public static final String RUNOFF_ON_UNDERGROUND = "RG";
    public static final String FLOW_AREA = "FR";
    public static final String FREE_WATER_STORAGE = "S0";
    public static final String DISCHARGE_IN_TOTAL= "QT";
    public static final String DISCHARGE_ON_SURFACE = "QS";
    public static final String DISCHARGE_ON_INNER_FLOW = "QI";
    public static final String DISCHARGE_ON_UNDERGROUND = "QG";
    public static final String DISCHARGE_THIS_TIME = "Q";
    /**
     * dt
     * time step of the model
     * unit:minute
     * the minute of a hydrological day is 1440
     */
    private int dt;
    /**
     * unit:km^2
     * natural basin which divide by water shed
     * when gird is applied ,it is a fixed value
     */
    private double area;
    /**
     * description
     * a variable which to store some unknown thing
     * location longitude latitude elevation
     * vegetation district and so on
     * document profile or something like this
     * can be explicit when it's required
     */
    private String description;
    /**
     * kc
     * Conversion coefficient of evapotranspiration
     * a important parameter which control water balance
     * this parameter is used to convert e601(china) observation into evapotranspiration ability in unit area
     * unit:null
     * usually this value smaller than 1.0
     */
    private double kc;
    private double c;
    private double wm;
    private double um;
    private double lm;
    private double dm;
    private double b;
    private double imp;
    private double ex;
    private double sm;
    private double ki;
    private double kg;
    private double ci;
    private double cg;
    private double cr;
    private int l;
    private double ke;
    private double xe;
    private int n;
    /**
     * model state parameters
     * the control authority should be private actually in order to protect the model;
     */
    private double eu;
    private double el;
    private double ed;
    private double et;
    private double pe;
    private double wu;
    private double wl;
    private double wd;
    private double wt;
    private double r;
    private double rs;
    private double ri;
    private double rg;
    private double fr;
    private double s0;
    private double qt;
    private double qs;
    private double qi=0;
    private double qg=0;
    private double q;
    private double wmm;
    private double smm;
    private List<Double> hydroGraph;
    private LimitSizeQueue qtHistory;
    private List<Double> futureRunoffProcess = new ArrayList<>();
    private Double[] channel0;
    private Double[] channel1;
    public String getDescription() {
        return this.description;
    }
    public double getModelStates(String name) {
        Map<String,Double> map=new HashMap<>(20);
        map.put(EVAPOTRANSPIRATION_IN_UP_LAYER,eu);
        map.put(EVAPOTRANSPIRATION_IN_LOW_LAYER,el);
        map.put(EVAPOTRANSPIRATION_IN_DEEP_LAYER,ed);
        map.put(EVAPOTRANSPIRATION_IN_TOTAL,et);
        map.put(WATER_STORAGE_IN_UP_LAYER,wu);
        map.put(WATER_STORAGE_IN_LOW_LAYER,wl);
        map.put(WATER_STORAGE_IN_DEEP_LAYER,wd);
        map.put(WATER_STORAGE_IN_TOTAL,wt);
        map.put(RUNOFF_IN_TOTAL,r);
        map.put(RUNOFF_ON_SURFACE,rs);
        map.put(RUNOFF_ON_INNER_FLOW,ri);
        map.put(RUNOFF_ON_UNDERGROUND,rg);
        map.put(DISCHARGE_IN_TOTAL,qt);
        map.put(DISCHARGE_ON_SURFACE,qs);
        map.put(DISCHARGE_ON_INNER_FLOW,qi);
        map.put(DISCHARGE_ON_UNDERGROUND,qg);
        map.put(DISCHARGE_THIS_TIME,q);
        map.put(RAINFALL_NET,pe);
        map.put(FLOW_AREA,fr);
        map.put(FREE_WATER_STORAGE,s0);
        return map.get(name);
    }

    public List<Double> getModelFutureRunOff() {
        return futureRunoffProcess;
    }
    public List<Double> getHydroGraph(){
        return hydroGraph;
    }
    public XinAnJiangModel setModelInfo(int dt, double area, String description) {
        this.dt = dt;
        this.area = area;
        this.description = description;
        return this;
    }

    public XinAnJiangModel setModelInitialValue(double wu, double wl, double wd, double s0) {
        this.wu = wu;
        this.wl = wl;
        this.wd = wd;
        this.wt = wu + wl + wd;
        this.s0 = s0;
        return this;
    }

    public XinAnJiangModel setSoilWaterStorageParameters(double um, double lm, double dm) {
        this.um = um;
        this.lm = lm;
        this.dm = dm;
        wm = um + lm + dm;
        return this;
    }
    public XinAnJiangModel setBoundary(double qu ) {
        this.qi = 0.4*qu;
        this.qg = 0.4*qu;
        for (int i=0;i<l;i++){
            qtHistory.offer(qu);
        }
        Arrays.fill(channel0,qu);
        Arrays.fill(channel1,qu);
        return this;
    }

    public XinAnJiangModel setEvapotranspirationParam(double kc, double c) {
        this.kc = kc;
        this.c = c;
        return this;
    }

    public XinAnJiangModel setRunoffGenerationParameters(double b, double imp) {
        this.b = b;
        this.imp = imp;
        this.fr=imp;
        wmm = wm * (1 + b)/(1-imp);
        return this;
    }

    public XinAnJiangModel setWaterSourceDivideParameters(double sm, double ex, double ki, double kg) {
        this.ex = ex;
        this.sm = sm;
        this.ki = ki;
        this.kg = kg;
        smm = sm * (1 + ex);
        return this;
    }

    public XinAnJiangModel setHillsideConfluenceParameters(double ci, double cg, double cr, int lag) {
        this.ci = ci;
        this.cg = cg;
        this.cr = cr;
        this.l = lag;
        qtHistory = new LimitSizeQueue(lag);
        return this;
    }

    public XinAnJiangModel setMuskingumParameters(double ke, double xe, int n) {
        this.ke = ke;
        this.xe = xe;
        this.n = n;

        double k1 = ke / n;
        double t = Math.floor(k1);
        double x = 0.5 - n * (1 - 2 * xe) / 2;

        double c0 = (0.5 * t - k1 * x) / (0.5 * t + k1 - k1 * x);
        double c1 = (0.5 * t + k1 * x) / (0.5 * t + k1 - k1 * x);
        double c2 = (-0.5 * t + k1 - k1 * x) / (0.5 * t + k1 - k1 * x);

        channel0=new Double[n];
        channel1=new Double[n];


        this.hydroGraph = new ArrayList<>();
        this.hydroGraph.add(c0);
        this.hydroGraph.add(c1 + c0 * c2);
        double tmp = c1 + c0 * c2;
        while (c2 * tmp > VALUE_EQUALS_ZERO) {
            tmp = tmp * c2;
            this.hydroGraph.add(tmp);
        }
        trimHydroGraph();
        List<Double> i2 = new ArrayList<>();
        List<Double> i1 = new ArrayList<>();
        List<Double> o1 = new ArrayList<>();
        List<Double> o2 = new ArrayList<>();
        for (int i = 2; i <= n; i++) {
            i2.clear();
            i1.clear();
            o2.clear();
            o1.clear();
            i2.add(c0 * hydroGraph.get(0) < VALUE_EQUALS_ZERO ? 0 : c0 * hydroGraph.get(0));
            i1.add(0.0);
            o1.add(0.0);
            o2.add(i2.get(0));
            for (int j = 1; j < hydroGraph.size(); j++) {
                i2.add(c0 * hydroGraph.get(j) < VALUE_EQUALS_ZERO ? 0 : c0 * hydroGraph.get(j));
                i1.add(c1 * hydroGraph.get(j - 1) < VALUE_EQUALS_ZERO ? 0 : c1 * hydroGraph.get(j - 1));
                o1.add(c2 * o2.get(j - 1) < VALUE_EQUALS_ZERO ? 0 : c2 * o2.get(j - 1));
                o2.add(i2.get(j) + i1.get(j) + o1.get(j));
            }
            o2.add(c1 * hydroGraph.get(hydroGraph.size() - 1) < VALUE_EQUALS_ZERO
                    ? 0 : c1 * hydroGraph.get(hydroGraph.size() - 1)
                    + o2.get(hydroGraph.size() - 1) * c2 < VALUE_EQUALS_ZERO
                    ? 0 : o2.get(hydroGraph.size() - 1) * c2);
            tmp = o2.get(hydroGraph.size());
            while (c2 * tmp > VALUE_EQUALS_ZERO) {
                tmp = tmp * c2;
                o2.add(tmp);
            }
            this.hydroGraph.clear();
            this.hydroGraph.addAll(o2);
            trimHydroGraph();
        }
        return this;
    }

    public void run(double em, double p, double qt) {
        evapotranspiration(em, p);
        runoffGenerationUnderSaturatedCondition();
        waterSoilConditionChange(p);
        waterSourceDivision();
        runoffConcentration();
        runoffConcentrationInUnitArea();
        runoffConcentrationInRiverNetUseUH(qt);
        //runoffConcentrationInRiverNetWithOutUH(qt);
    }

    private void evapotranspiration(double em, double p) {
        eu = 0;
        el = 0;
        ed = 0;
        double ep = em * kc;
        if (p + wu >= ep) {
            eu = ep;
            el = 0;
            ed = 0;
        } else {
            eu = wu + p;
            if (wl > c * lm) {
                el = (ep - eu) * wl / lm;
                ed = 0;
            } else {
                if (wl >= c * (ep - eu)) {
                    el = (ep - eu) * c;
                    ed = 0;
                } else {
                    el = wl;
                    ed = c * (ep - eu) - wl;
                    ed = ed > wd ? wd : ed;
                }
            }
        }
        pe = p - eu - el - ed < 0 ? 0 : p - eu - el - ed;
        et = eu + el + ed;
    }

    private void runoffGenerationUnderSaturatedCondition() {
        double a = wmm * (1 - Math.pow((1 - wt / wm), 1 / (1 + b)));
        if (pe + a >= wmm) {
            r = wt + pe - wm;
        } else {
            r = wt + pe - wm + wm * Math.pow(1 - (pe + a) / wmm, 1 + b);
        }
        r=r<VALUE_EQUALS_ZERO?0.0:r;
    }

    private void waterSoilConditionChange(double p) {
        if (pe == 0) {
            wu=wu+p-eu;
            wl -= el;
            wd -= ed;
        } else {
            if (wu + pe - r >= um) {
                double tmp = pe - r + wu - um;
                wu = um;
                if (tmp + wl >= lm) {
                    tmp -= lm - wl;
                    wl = lm;
                    wd = tmp + wd > dm ? dm : tmp + wd;
                } else {
                    wl += tmp;
                }
            } else {
                wu = pe - r + wu;
            }
        }
        wt = wu + wl + wd;
    }

    private void waterSourceDivision() {
        rs = 0;
        ri = 0;
        rg = 0;
        double fr1;
        double kig = (1 - Math.pow(1 - ki - kg, dt / 1440.0)) / (1 + kg / ki);
        double kgg = kig * kg / ki;

        if (pe > 0) {
            fr1 = r / pe < 0.001d ? 0.001d : r / pe;
            s0 = s0 * fr / fr1;
            double smmf=ex<VALUE_EQUALS_ZERO?smm:smm*(1-Math.pow(1d-fr1,1d/ex));
            double smf=smmf/(1+ex);
            double nn = new Float(r / (fr1 * 5.0d)).intValue() + 1;
            double peInPeriod = r / (fr1 * nn);
            double tmp = kig;
            kig = (1 - Math.pow(1 - kig - kgg, 1 / nn)) / (1 + kgg / kig);
            kgg = kig * kgg / tmp;
            for (int i = 1; i <= nn; i++) {
                double rsd=0;
                double rid=0;
                double rgd=0;
                s0=s0>smf?smf:s0;
                double au = smmf * (1 - Math.pow(1d - s0 / smf, 1 / (1 + ex)));
                if (peInPeriod + au >= smmf) {
                    rsd = (peInPeriod + s0 - smf) * fr;
                } else {
                    rsd = (peInPeriod + s0 - smf + smf * Math.pow(1 - (peInPeriod + au) / smmf, ex + 1))*fr;
                }
                s0+=peInPeriod-rsd/fr1;
                rid=s0*kig*fr1;
                rgd=s0*kgg*fr1;
                s0=s0*(1-kig-kgg);
                rs+=rsd;
                ri+=rid;
                rg+=rgd;
            }
            fr = fr1;
        } else {
            rs = 0;
            ri = s0 * kig * fr;
            rg = s0 * kgg * fr;
            s0=s0*(1-kig-kgg);
            fr=VALUE_EQUALS_ZERO;
        }
        rs = pe * imp + rs * (1 - imp);
        ri = ri * (1 - imp);
        rg = rg * (1 - imp);
    }

    private void runoffConcentration() {
        double cit = Math.pow(ci, dt / 1440d);
        double cgt = Math.pow(cg, dt / 1440d);
        double qtTemp=0;
        qs= rs * 50 * area / (3 * dt);
        qi = cit * qi + (1 - cit) * ri * 50 * area / (3 * dt);
        qg = cgt * qg + (1 - cgt) * rg * 50 * area / (3 * dt);
        qtTemp = qs + qi + qg;
        qtHistory.offer(qtTemp);
    }

    private void runoffConcentrationInUnitArea() {
        qt = qt * cr + (1 - cr) * qtHistory.getFirst();
    }

    private void runoffConcentrationInRiverNetUseUH(double qu) {
        if (futureRunoffProcess.size() == 0) {
            for (double d : hydroGraph) {
                futureRunoffProcess.add(d * (qt + qu));
            }
        } else {
            for (int i = 0; i < futureRunoffProcess.size(); i++) {
                futureRunoffProcess.set(i, futureRunoffProcess.get(i)
                        + (qt + qu) * hydroGraph.get(i));
            }
            futureRunoffProcess.add((qt + qu) * hydroGraph.get(hydroGraph.size() - 1));
        }
        q = futureRunoffProcess.get(0);
        futureRunoffProcess.remove(0);
    }
    private void runoffConcentrationInRiverNetWithOutUH(double qu) {
        double k1 = ke / n;
        double t = Math.floor(k1);
        double x = 0.5 - n * (1 - 2 * xe) / 2;

        double c0 = (0.5 * t - k1 * x) / (0.5 * t + k1 - k1 * x);
        double c1 = (0.5 * t + k1 * x) / (0.5 * t + k1 - k1 * x);
        double c2 = (-0.5 * t + k1 - k1 * x) / (0.5 * t + k1 - k1 * x);

        channel1[0]=qt+qu;
        for (int i=1;i<n;i++){
            channel1[i]=c0*channel1[i-1]*c1*channel0[i-1]+channel0[i]*c2;
        }
        for (int i=0;i<n;i++){
            channel0[i]=channel1[i];
        }
        q=channel1[n-1];
    }
    private void trimHydroGraph() {
        double sum = 1;
        int zeroCount=0;
        for (int i = 0; i < hydroGraph.size(); i++) {
            if(hydroGraph.get(i)<VALUE_EQUALS_ZERO){
                hydroGraph.set(i,0d);
            }
            zeroCount=hydroGraph.get(i)==0?zeroCount+1:zeroCount;
            sum -= hydroGraph.get(i);
        }
         double tmp = sum/(hydroGraph.size()-zeroCount);
        for (int i = 0; i < hydroGraph.size(); i++) {
            if (hydroGraph.get(i)!=0){
                hydroGraph.set(i,tmp+hydroGraph.get(i));
            }
        }
    }
}
