import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import static java.lang.Thread.sleep;


public class Chrono{
    private JLabel labelTemps;
    private float temps;
    private Timer timer;

    Chrono(){
        this.temps = 0;
    }

    public void run(){
        if(timer != null) timer.stop();
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ignored) {
                temps += 0.1;
                labelTemps.setText(getDisplayTime());
                //System.out.println(temps);
            }
        });
        timer.start();
    }




    public void terminate(){
        timer.stop();
    }

    public void setLabelTemps(JLabel labelTemps) {
        this.labelTemps = labelTemps;
        this.labelTemps.setText(this.getDisplayTime());
    }

    public String getTemps() {
        DecimalFormat format = new DecimalFormat("#######0.0");
        return format.format(this.temps);
    }

    public String getDisplayTime(){
        return String.format("Temps : %ss",this.getTemps());
    }
}
