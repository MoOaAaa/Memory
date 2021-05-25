import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Vector;

public class Model {
    protected int tailleGrille;
    protected ArrayList<ImageIcon> listeImages;
    protected static final Integer NBR_SCORES_ENREGISTRES = 3;
    protected final Float[] bestScores;
    protected int[] order;
    protected int nbrImages;
    protected int essais;
    protected final Chrono chrono;
    protected boolean partieEstDemarree = false;

    public Model() {

        this.tailleGrille = 4;
        this.nbrImages = tailleGrille*2;
        this.chrono = new Chrono();
        this.essais = (tailleGrille<<2)/2;
        listeImages = new ArrayList<>();
        bestScores = new Float[NBR_SCORES_ENREGISTRES];
        chargerImages("images/img");
        this.partieEstDemarree = false;
        this.readScores();
        order = shuffle();
    }

    public void chargerImages(String chemin){
        try {
            File folder = new File(chemin);
            File[] listOfFiles = folder.listFiles();

            for(final File file : listOfFiles){
                if(file.isFile()){
                    BufferedImage img = ImageIO.read(new File(chemin+'/'+file.getName()));
                    ImageIcon icon = new ImageIcon(img);
                    icon = new ImageIcon(icon.getImage().getScaledInstance(175,175, BufferedImage.SCALE_SMOOTH));
                    //System.out.println(file.getName());
                    getListeImages().add(icon);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int[] shuffle(){
        int[] order = new int[getTailleGrille()<<2];
        Vector v = new Vector();
        for(int i =0; i < getTailleGrille()<<2 ; i++){
            v.add((int)(i%((getTailleGrille()<<2)/2)));
        }
        for (int i = 0; i < getTailleGrille()<<2; i++){
            int rand  = (int)(Math.random()*v.size());
            order[i] = (Integer)(v.elementAt(rand));
            v.removeElementAt(rand);
        }
        return order;
    }

    public void readScores(){
        try {
            BufferedReader br = new BufferedReader(new FileReader("scores.txt"));
            for (int i = 0; i < NBR_SCORES_ENREGISTRES; i++) {
                String s = br.readLine();
                if (s == null) {
                    s = "0.0";
                }
                float v = Float.parseFloat(s.replace(",","."));
                bestScores[i] = v;
            }
            br.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void updateScores(){
        Chrono chrono = this.chrono;
        String score =chrono.getTemps();
//        System.out.println(score.replace(',','.'));
        float s = Float.parseFloat(score.replace(',','.'));
        for (int i = 0; i < NBR_SCORES_ENREGISTRES; i++) {
            if (this.bestScores[i] >= s || this.bestScores[i] == 0.0) {
                this.bestScores[i] = s;
                break;
            }
        }

        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("scores.txt"));
            DecimalFormat format = new DecimalFormat("#######0.0");
            for (int i= 0; i< NBR_SCORES_ENREGISTRES; i++){
                bw.write("" + format.format(this.bestScores[i]));
                bw.newLine();
            }
            bw.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public String displayScores(){
        ArrayList<String> s = new ArrayList<>();
        s.add("<html>");
        for (int i = 0; i<NBR_SCORES_ENREGISTRES;i++){
            s.add((i+1) + "-" + this.bestScores[i] + " s");
        }
        s.add("</html>");
        return String.join("<br>",s);
    }

    public ArrayList<ImageIcon> getListeImages() {
        return listeImages;
    }

    public int getTailleGrille() {
        return tailleGrille;
    }

    public void setTailleGrille(int tailleGrille) {
        this.tailleGrille = tailleGrille;
    }

    public int[] getOrder() {
        return order;
    }

    public int getEssais() {
        return essais;
    }

    public Chrono getChrono() {
        return chrono;
    }

    public boolean isPartieEstDemarree() {
        return partieEstDemarree;
    }

    public void demarrePartie(){
        this.partieEstDemarree=true;
        this.chrono.run();
    }

    public void setEssais(int essais) {
        this.essais = essais;
    }

}
