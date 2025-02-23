public class Enemy {
    private Ball sprite;
    private int enemyPower = 1;
    private boolean moving;
    public int health;

    public Enemy(double x, double y, double diameter, int health) {
        sprite = new Ball(x, y, diameter, "RED");
        this.health = health;
    }

    public Ball getShape() {
        return sprite;
    }

    public double getEnemyX() {
        return sprite.getXPosition();
    }

    public double getEnemyY() {
        return sprite.getYPosition();
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    // moves this enemy towards the given player by 1 unit
    public void move(Player player) {
        if (moving) {
            double playerX = player.getShape().getXPosition();
            double playerY = player.getShape().getYPosition();

            double enemyX = sprite.getXPosition();
            double enemyY = sprite.getYPosition();

            // a^2 + b^2 = c^2
            double length = (Math.sqrt(Math.pow(playerX - enemyX, 2) +
                                       Math.pow(playerY - enemyY, 2)));
            double normalX = enemyX + ((playerX - enemyX) / length);
            double normalY = enemyY + ((playerY - enemyY) / length);

            sprite.setXPosition(normalX);
            sprite.setYPosition(normalY);
        }
    }

    public boolean checkCollision(Player player) {
        if (sprite.collides(player.getShape())) {
            player.damage(enemyPower);
            return true;
        }
        return false;
    }

    public boolean checkCollision(Bullet bullet) {
        if (sprite.collides(bullet.getShape())) {
            return true;
        }
        return false;
    }
}
