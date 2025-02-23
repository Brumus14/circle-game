import java.util.*;
import java.time.Duration;
import java.time.Instant;

public class Gun {
    private int level;
    private double bulletSpeed;
    private double cooldown;
    private Player player;
    private Rectangle shape;
    private GameArena arena;
    private Instant lastShotTime = Instant.now();
    private List<Bullet> bullets = new ArrayList<>();

    public Gun(GameArena a, Player p, double bs, double cd) {
        arena = a;
        player = p;
        bulletSpeed = bs;
        cooldown = cd;

        shape = new Rectangle(player.getPositionX(), player.getPositionY(), 100,
                              20, "grey");
        shape.setYOriginRelative(0.5);
        arena.addRectangle(shape);
    }

    public void updateRotation() {
        double angle =
            Math.atan2(arena.getMousePositionX() - player.getPositionX(),
                       -(arena.getMousePositionY() - player.getPositionY())) *
                180 / Math.PI -
            90;

        shape.setRotation(angle);
    }

    public void updatePosition() {
        shape.setXPosition(player.getPositionX());
        shape.setYPosition(player.getPositionY());
    }

    public void shoot() {
        double rotation = shape.getRotation();
        double directionX = Math.cos(Math.toRadians(rotation));
        double directionY = Math.sin(Math.toRadians(rotation));

        Instant currentTime = Instant.now();

        if (Duration.between(lastShotTime, currentTime).getNano() / 1e9 >=
            cooldown) {
            Bullet bullet = new Bullet(
                arena, player.getPositionX() + directionX * 100,
                player.getPositionY() + directionY * 100,
                directionX * bulletSpeed, directionY * bulletSpeed, 20, "red");
            bullets.add(bullet);

            lastShotTime = currentTime;
        }
    }

    public void updateBullets() {
        List<Bullet> bulletsToRemove = new ArrayList<>();

        for (Bullet bullet : bullets) {
            bullet.update();

            if (bullet.getPositionX() < 0 || bullet.getPositionX() > 1600 ||
                bullet.getPositionY() < 0 || bullet.getPositionY() > 900) {
                System.out.println("out");
                arena.removeBall(bullet.getShape());

                bulletsToRemove.add(bullet);
            }
        }

        bullets.removeAll(bulletsToRemove);
    }

    public List<Bullet> getBullets() {
        return bullets;
    }
}
