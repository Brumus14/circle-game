public class Player {
    Ball sprite;
    int health;

    public Player(Ball sprite, int health) {
        this.sprite = sprite;
        this.health = health;
    }

    public Ball getSprite() {
        return sprite;
    }

    public void damage(int damageTaken) {
        health -= damageTaken;
    }
}