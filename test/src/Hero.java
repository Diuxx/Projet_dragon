import org.newdawn.slick.Input;

public class Hero extends Personnage {

    // construct
    public Hero(String nom, float x, float y, int pointDeVie) {
        super(nom, x, y, pointDeVie);
    }

    // seul le hero peut être contrôlé
    public void controle(int key) {
        // --
        switch (key) {
            case Input.KEY_UP:
                super.setDirection(0);
                super.marcher();
                break;
            case Input.KEY_LEFT:
                super.setDirection(1);
                super.marcher();
                break;
            case Input.KEY_DOWN:
                super.setDirection(2);
                super.marcher();
                break;
            case Input.KEY_RIGHT:
                super.setDirection(3);
                super.marcher();
                break;
        }
    }
}
