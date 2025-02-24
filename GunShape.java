import java.util.*;

public class GunShape {
    private GameArena arena;
    private List<Shape> shapes = new ArrayList<>();
    private double positionX;
    private double positionY;
    private double rotation = 0;
    private List<GunShootPoint> shootPoints;

    public GunShape(GameArena a, List<GunShootPoint> sp, double posX,
                    double posY) {
        arena = a;
        positionX = posX;
        positionY = posY;
        shootPoints = sp;
    }

    public void changeShape(int s) {
        shootPoints.clear();

        for (Shape shape : shapes) {
            if (shape instanceof Ball) {
                arena.removeBall((Ball)shape);
            } else if (shape instanceof Rectangle) {
                arena.removeRectangle((Rectangle)shape);
            }
        }

        shapes.clear();

        switch (s) {
        case 0:
            shapes.add(new Ball(0, 0, 20, "grey"));
            Rectangle barrel = new Rectangle(0, 0, 20, 60, "grey");
            barrel.setXOriginRelative(0.5);
            shapes.add(barrel);

            shootPoints.add(new GunShootPoint(barrel, 60));

            break;
        case 1:
            Rectangle barrel1 = new Rectangle(0, 0, 20, 60, "grey");
            barrel1.setXOriginRelative(-0.1);
            shapes.add(barrel1);

            shootPoints.add(new GunShootPoint(barrel1, 60));

            Rectangle barrel2 = new Rectangle(0, 0, 20, 60, "grey");
            barrel2.setXOriginRelative(1.1);
            shapes.add(barrel2);

            shootPoints.add(new GunShootPoint(barrel2, 60));

            break;
        }

        for (Shape shape : shapes) {
            if (shape instanceof Ball) {
                Ball ball = (Ball)shape;

                ball.setXPosition(positionX);
                ball.setYPosition(positionY);
                arena.addBall(ball);
            } else if (shape instanceof Rectangle) {
                Rectangle rectangle = (Rectangle)shape;

                rectangle.setXPosition(positionX);
                rectangle.setYPosition(positionY);
                rectangle.setRotation(rotation);
                arena.addRectangle(rectangle);
            }
        }
    }

    public void setPositionX(double x) {
        positionX = x;

        for (Shape shape : shapes) {
            if (shape instanceof Ball) {
                Ball ball = (Ball)shape;
                ball.setXPosition(positionX);
            } else if (shape instanceof Rectangle) {
                Rectangle rectangle = (Rectangle)shape;
                rectangle.setXPosition(positionX);
            }
        }
    }

    public void setPositionY(double y) {
        positionY = y;

        for (Shape shape : shapes) {
            if (shape instanceof Ball) {
                Ball ball = (Ball)shape;
                ball.setYPosition(positionY);
            } else if (shape instanceof Rectangle) {
                Rectangle rectangle = (Rectangle)shape;
                rectangle.setYPosition(positionY);
            }
        }
    }

    public void setRotation(double r) {
        rotation = r;

        for (Shape shape : shapes) {
            if (shape instanceof Rectangle) {
                Rectangle rectangle = (Rectangle)shape;
                rectangle.setRotation(rotation);
            }
        }
    }

    public double getRotation() {
        return rotation;
    }
}
