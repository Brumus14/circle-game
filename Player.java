public class Player {
    private double positionX;
    private double positionY;
    private double speed;
    private double rotation = 0;
    private double health;
    private GameArena arena;
    private Ball shape;
    private Ball gunPivot;
    private Gun gun;

    // maybe make separate gun class
    public Player(GameArena a, double posX, double posY, double s, double h) {
        positionX = posX;
        positionY = posY;
        speed = s;
        health = h;
        arena = a;

        shape = new Ball(positionX, positionY, 80, "blue");
        arena.addBall(shape);

        gun = new Gun(arena, this, 10, 0.2);

        gunPivot = new Ball(positionX, positionY, 20, "grey");
        arena.addBall(gunPivot);
    }

    public void update() {
        gun.updateBullets();
        handleInput();
    }

    public void handleInput() {
        if (arena.leftMousePressed()) {
            gun.shoot();
        }

        gun.updateRotation();

        double movementDeltaX = 0;
        double movementDeltaY = 0;

        if (arena.letterPressed('W')) {
            movementDeltaY -= 1;
        }

        if (arena.letterPressed('S')) {
            movementDeltaY += 1;
        }

        if (arena.letterPressed('A')) {
            movementDeltaX -= 1;
        }

        if (arena.letterPressed('D')) {
            movementDeltaX += 1;
        }

        double movementMagnitude = Math.sqrt(Math.pow(movementDeltaX, 2) +
                                             Math.pow(movementDeltaY, 2));

        if (movementMagnitude == 0) {
            return;
        }

        movementDeltaX /= movementMagnitude;
        movementDeltaY /= movementMagnitude;

        if (!arena.shiftPressed()) {
            movementDeltaX *= speed;
            movementDeltaY *= speed;
        } else {
            movementDeltaX *= speed * 4;
            movementDeltaY *= speed * 4;
        }

        move(movementDeltaX, movementDeltaY);

        gun.updatePosition();

        gunPivot.setXPosition(positionX);
        gunPivot.setYPosition(positionY);
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double x) {
        positionX = x;
        shape.setXPosition(positionX);
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double y) {
        positionY = y;
        shape.setYPosition(positionY);
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rot) {
        rotation = rot;
    }

    public void move(double x, double y) {
        positionX += x;
        positionY += y;

        shape.setXPosition(positionX);
        shape.setYPosition(positionY);
    }

    public void rotate(double rot) {
        rotation += rot;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double h) {
        health = h;
    }

    public void damage(double d) {
        health -= d;

        if (health < 0) {
            health = 0;
        }
    }

    public Ball getShape() {
        return shape;
    }
}
