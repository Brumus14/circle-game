public class Bullet {
    private double positionX;
    private double positionY;
    private double velocityX;
    private double velocityY;
    private Ball shape;
    private GameArena arena;

    public Bullet(GameArena a, double posX, double posY, double velX,
                  double velY, double s, String col) {
        positionX = posX;
        positionY = posY;
        velocityX = velX;
        velocityY = velY;

        arena = a;

        shape = new Ball(posX, posY, s, col);
        arena.addBall(shape);
    }

    public void update() {
        positionX += velocityX;
        positionY += velocityY;

        shape.setXPosition(positionX);
        shape.setYPosition(positionY);
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public Ball getShape() {
        return shape;
    }
}
