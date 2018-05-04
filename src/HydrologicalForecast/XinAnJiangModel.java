package HydrologicalForecast;

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

    //Basin state
    private double wu;
    private double wl;
    private double wd;
    //Input
    private double p;
    private double em;
    //Output
    private double eu;
    private double el;
    private double ed;

    /**
     * FIXME evapotranspiration process should coupled with rain off gengeration process
     * which in short should named after ER???
     */


    public void evapotranspiration(){
        double ep=em*kc;
        double dm=wm-lm-um;
        //three layer evapotranspiration model
        //calculate output here
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
    }

    public double runoffGeneration(double pe){
        double w0=wu+wl+wd;
        double wmm=wm*(1+b);
        double a=wmm*(1-Math.pow((1-w0/wm),1/(1+b)));
        double r=0.0;
        if(pe+a>=wmm){

        }else{

        }
        return r;
    }






}
