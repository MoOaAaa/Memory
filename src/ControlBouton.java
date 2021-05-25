import javax.swing.*;
import javax.swing.text.Utilities;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ControlBouton implements ActionListener {
    Model model;
    View view;
    protected static int buttonCount = 0;
    protected JButton lastPressedButton = null;

    public ControlBouton(Model model, View view) {
        this.model = model;
        this.view = view;
        view.setBoutonsController(this);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JButton button = (JButton) actionEvent.getSource();
        button.setEnabled(false);
        ImageIcon thisIcon = (ImageIcon) button.getDisabledIcon();
        buttonCount++;

        if (!model.isPartieEstDemarree()) {
            model.demarrePartie();
        }

        if (buttonCount == 2) {
            ImageIcon thatIcon = (ImageIcon) lastPressedButton.getDisabledIcon();
            boolean isPair = thisIcon.equals(thatIcon);

            if (!isPair) {
                model.setEssais(model.getEssais() - 1);
                view.getLabelEssais().setText("Essais: " + model.getEssais());
                JButton lastButton = lastPressedButton;
                TimerTask timerAction = new TimerTask() {
                    @Override
                    public void run() {
                        button.setEnabled(true);
                        lastButton.setEnabled(true);
                    }
                };
                Timer timer = new Timer();
                timer.schedule(timerAction,500L);

            }
            buttonCount = 0;
        }

        ArrayList<JButton> boutonsDispo = new ArrayList<>();
        for (JButton bouton : view.getBoutons()) {
            if (bouton.isEnabled()) {
                boutonsDispo.add(bouton);
            }
        }

        lastPressedButton = button;

        if (boutonsDispo.size() == 0) {
            model.getChrono().terminate();
            JDialog alert = new JDialog(view, "Victoire");
            alert.add(new JLabel("Vous avez gagné la partie en " + model.getChrono().getTemps()));
            model.updateScores();
            alert.setSize(new Dimension(500,200));
            alert.setVisible(true);
        }

        if (model.getEssais() == 0) {
            model.getChrono().terminate();
            JDialog dialog = new JDialog(view, "Defaite");
            dialog.add(new JLabel("Vous avez perdu!"));
            dialog.setSize(new Dimension(500,200));
            dialog.setVisible(true);
//            lastPressedButton.setEnabled(false);
//            button.setEnabled(false);
//            for (JButton bouton : boutonsDispo) {
//                bouton.setEnabled(false);
//            }
        }
    }
}
