public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Model model = new Model();
                View view = new View(model);
                ControlBouton controlBouton = new ControlBouton(model,view);
                ControlMenu controlMenu = new ControlMenu(model,view);
            }
        });
    }
}
