import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class View extends JFrame{
    protected Model model;

    protected JMenuItem nouvellePartie;
    protected JMenuItem meilleursScores;

    protected ButtonGroup boutonsGrille;
    protected ArrayList<JButton> boutons;
    protected JLabel labelChrono;
    protected JLabel labelEssais;

    public View(Model model) {
        this.model=model;
        setTitle("Memory");
        initItems();
        addWidgetsToWindow();
        pack();
        display();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void initItems() {
        nouvellePartie = new JMenuItem("Nouvelle Partie");
        meilleursScores = new JMenuItem("Meilleurs scores");



        labelEssais = new JLabel("Essais restant :" + model.getEssais());
        labelEssais.setFont(new Font("Arial",Font.PLAIN,20));

        boutonsGrille = new ButtonGroup();
        boutons = new ArrayList<>();
        for (int i = 0; i< model.getTailleGrille()<<2; i++){
            JButton button = new JButton();
            button.setIcon(new ImageIcon("images/pattern.png"));
            button.setDisabledIcon(model.getListeImages().get(model.getOrder()[i]));
            button.setPreferredSize(new Dimension(100,100));
            boutons.add(button);
            boutonsGrille.add(button);
        }
    }

    public void addWidgetsToWindow(){
        JPanel panelGeneral = new JPanel();
        BoxLayout boxLayout = new BoxLayout(panelGeneral,BoxLayout.Y_AXIS);
        panelGeneral.setLayout(boxLayout);

        JMenuBar menuBar= new JMenuBar();
        JMenu options = new JMenu("Options");
        options.add(nouvellePartie);
        options.add(meilleursScores);
        menuBar.add(options);
        setJMenuBar(menuBar);

        labelChrono = new JLabel("",SwingConstants.CENTER);
        model.getChrono().setLabelTemps(getLabelChrono());
//        labelChrono.setFont(new Font("Arial",Font.PLAIN,20));
//        labelChrono.setHorizontalTextPosition(SwingConstants.CENTER);
//        labelChrono.setBorder(new EmptyBorder(10,0,10,0));
        panelGeneral.add(labelChrono);

        JPanel panelGrille = new JPanel();
        GridLayout gridLayout = new GridLayout(model.getTailleGrille(),model.getTailleGrille());
        panelGrille.setLayout(gridLayout);
        for (JButton button : boutons){
            panelGrille.add(button);
        }
        panelGeneral.add(panelGrille);

        labelEssais.setHorizontalAlignment(SwingConstants.CENTER);
        labelEssais.setBorder(new EmptyBorder(10,0,10,0));
        panelGeneral.add(labelEssais);

        setContentPane(panelGeneral);
    }

    public void display(){
        setVisible(true);
    }

    public void setMenuController(ActionListener handler){
        nouvellePartie.addActionListener(handler);
        meilleursScores.addActionListener(handler);
    }

    public  void setBoutonsController(ActionListener handler){
        for (JButton button: boutons) {
            button.addActionListener(handler);
        }
    }

    public JLabel getLabelChrono() {
        return labelChrono;
    }

    public JLabel getLabelEssais() {
        return labelEssais;
    }

    public void setLabelEssais(JLabel labelEssais) {
        this.labelEssais = labelEssais;
    }

    public JMenuItem getNouvellePartie() {
        return nouvellePartie;
    }

    public void setNouvellePartie(JMenuItem nouvellePartie) {
        this.nouvellePartie = nouvellePartie;
    }

    public JMenuItem getMeilleursScores() {
        return meilleursScores;
    }

    public void setMeilleursScores(JMenuItem meilleursScores) {
        this.meilleursScores = meilleursScores;
    }

    public ButtonGroup getBoutonsGrille() {
        return boutonsGrille;
    }

    public void setBoutonsGrille(ButtonGroup boutonsGrille) {
        this.boutonsGrille = boutonsGrille;
    }

    public ArrayList<JButton> getBoutons() {
        return boutons;
    }

    public void setBoutons(ArrayList<JButton> boutons) {
        this.boutons = boutons;
    }
}
