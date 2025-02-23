public class Bullet {
    private double positionX;
    private double positionY;
    private double velocityX;
    private double velocityY;
    private Ball shape;
    private GameArena arena;
    private Gun gun;

    public Bullet(GameArena a, Gun g, double posX, double posY, double velX,
                  double velY, double s, String col) {
        gun = g;
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

    public void destroy() {
        gun.getBullets().remove(this);
        arena.removeBall(shape);
    }
}
