package HydrologicalForecast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Amos_zhao
 * @Date 2018-05-04
 * @Description small time scale model designed for grid unit
 * This class is main entrance
 */
public class XinAnJiangModel {
    //Time invariant parameters
    private double basinArea;
    private double kc;
    private double um;
    private double lm;
    private double c;
    private double b;
    private double wm;
    private double imp;
    private double ex;
    private double sm;
    private double ki;
    private double kg;
    private double ci;
    private double cg;
    private double cr;
    private double l;

    /**
     * dt
     * time step of model
     * count by minute and the minute of a day is 1440
     */
    private int dt;
    //Basin state
    private double wu;
    private double wl;
    private double wd;
    private double rs;
    private double ri;
    private double rg;
    private double pe;
    private double r;
    private double s0;
    private double qt;

    private Map<String,Double> basinHistoryState=new HashMap<String,Double>();
    private List<Double> qtList=new ArrayList<>();
    //Input
    private double p;
    private double em;
    //Output
    private double eu;
    private double el;
    private double ed;

    public void main(){
        evapotranspiration();
        runoffGenerationUnderSaturatedVondition();
        waterSoilConditionChange();
        threeWaterSourceDivision();
    }
    private void evapotranspiration(){
        double ep=em*kc;
        double dm=wm-lm-um;
        if(p+wu>=ep){
            eu=ep;el=0;ed=0;
        }else{
            eu=wu+p;
            if(wl>c*lm){
                el=(ep-eu)*wl/lm;
                ed=0;
            }else{
                if(wl>=c*(ep-eu)){
                    el=(ep-eu)*c;
                    ed=0;
                }else{
                    el=wl;
                    ed=c*(ep-eu)-wl;
                    ed=ed>=dm?dm:ed;
                }
            }
        }
        pe=p-eu-el-ed;
    }
    private void runoffGenerationUnderSaturatedVondition(){
        if(pe<0){
            r= 0;
        }
        double w0=wu+wl+wd;
        double wmm=wm*(1+b);
        double a=wmm*(1-Math.pow((1-w0/wm),1/(1+b)));
        if(pe+a>=wmm){
            r= w0+pe-wm;
        }else{
            r= w0+pe-wm+wm*Math.pow(1-(pe+a)/wmm,1+b);
        }
    }
    private void waterSoilConditionChange(){
        double dm=wm-lm-um;
        wu-=eu;
        wl-=el;
        wd-=ed;
        if(pe>0){
            if(wu+pe-r>=um){
                double tmp=pe-r+wu-um;
                wu=um;
                if(tmp+wl>=lm){
                    tmp-=lm-wl;
                    wl=lm;
                    wd+=tmp;
                    wd=wd>dm?dm:wd;
                }else {
                    wl+=tmp;
                }
            }else{
                wu+=pe-r;
            }
        }
    }
    private void threeWaterSourceDivision(){
        //TODO how can i store history basin state
        double smm=sm*(1+ex);
        double kig=(1-Math.pow(1-ki+kg,dt/1440))/(1+kg/ki);
        double kgg=kig*kg/ki;
        if (pe<=0){
            ri=0;rs=0;rg=0;s0=0;
        }else{
            double fr=r/pe;
            s0=basinHistoryState.get("s0")*basinHistoryState.get("fr")/fr;
            basinHistoryState.put("fr",fr);
            double nn=Math.floor(pe/5+1);
            double peInPeriod=pe/nn;
            double tmp=kig;
            kig=(1-Math.pow(1-kig-kgg,1/nn))/(1+kgg/kig);
            kgg=kig*kgg/tmp;
            ri=0; rs=0;rg=0;
            double smmf=0;
            if(ex<0.001){
                smmf=smm;
            }else{
                smmf=smm*(1-Math.pow(1-fr,1/ex));
            }
            double smf=smmf/(1+ex);
            for (int i = 1; i <=nn ; i++) {
                s0=s0>smf?smf:s0;
                double au=smmf*(1-Math.pow(1-s0/smf,1/(1+ex)));
                if(peInPeriod+au<=0){
                    ri=0;rs=0;rg=0;s0=0;
                }else if(peInPeriod+au>=smmf){
                    rs+=(peInPeriod+au-smf)*fr;
                    ri+=kig*smf*fr;
                    rg+=kgg*smf*fr;
                    s0=smf-(ri+rg)/fr;
                }else{
                    rs+=fr*(peInPeriod+s0-smf+smf*Math.pow(1-(peInPeriod+au)/smmf,ex+1))*fr;
                    ri+=kig*(s0+pe-rs/fr)*fr;
                    rg+=kgg*(s0+pe-rs/fr)*fr;
                    s0+=peInPeriod-(rs+ri+rg)/fr;
                }
            }
            basinHistoryState.put("s0",s0);
            rs=pe*imp+rs*(1-imp);
            ri=ri*(1-imp);
            rg=rg*(1-imp);
        }
    }
    private void runoffConcentration(){
        double cit=Math.pow(ci,dt/1440);
        double cgt=Math.pow(cg,dt/1440);
        double qs=rs*50/3;
        double qi=cit*basinHistoryState.get("qi")+(1-cit)*ri*50*basinArea/(3*dt);
        double qg=cgt*basinHistoryState.get("qg")+(1-cgt)*rg*50*basinArea/(3*dt);
        basinHistoryState.put("qi",qi);
        basinHistoryState.put("qg",qg);
        qt=qs+qi+qg;
    }

    /**
     * Lagging algorithm
     * which happened in unit areae
     */
    private void runoffConcentrationInRiverNet(){

    }
}
