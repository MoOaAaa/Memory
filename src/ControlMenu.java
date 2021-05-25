import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlMenu implements ActionListener {
    Model model;
    View view;

    public ControlMenu(Model model, View view) {
        this.model = model;
        this.view = view;
        view.setMenuController(this);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(view.getNouvellePartie())){
            reset(new Model());
        }else if (actionEvent.getSource().equals(view.getMeilleursScores())){
            JDialog scores = new JDialog(view,"Meilleurs Scores");
            JLabel label = new JLabel(model.displayScores());
            label.setHorizontalAlignment(SwingConstants.CENTER);
            scores.add(label);
            scores.setSize(new Dimension(400,200));
            scores.setVisible(true);
        }
    }

    public void reset(Model model){
        this.model = model;
        this.view.setVisible(false);
        this.view = new View(model);
        ControlBouton controlBouton = new ControlBouton(model,view);
        ControlMenu controlMenu = new ControlMenu(model,view);
        view.display();
    }
}
