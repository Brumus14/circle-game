import java.util.*;
import java.time.Duration;
import java.time.Instant;

public class Gun {
    private int level;
    private double bulletSpeed;
    private double cooldown;
    private Player player;
    private GunShape shape;
    private GameArena arena;
    private Instant lastShotTime = Instant.now();
    private List<Bullet> bullets = new ArrayList<>();
    private List<GunShootPoint> shootPoints = new ArrayList<>();

    public Gun(GameArena a, Player p, double bs, double cd) {
        arena = a;
        player = p;
        bulletSpeed = bs;
        cooldown = cd;

        shape = new GunShape(arena, shootPoints, p.getPositionX(),
                             p.getPositionY());
        shape.changeShape(1);
    }

    public void updateRotation() {
        double rotation =
            Math.atan2(-(arena.getMousePositionX() - player.getPositionX()),
                       arena.getMousePositionY() - player.getPositionY()) *
            180 / Math.PI;

        shape.setRotation(rotation);
    }

    public void updatePosition() {
        shape.setPositionX(player.getPositionX());
        shape.setPositionY(player.getPositionY());
    }

    public void shoot() {
        Instant currentTime = Instant.now();

        if (Duration.between(lastShotTime, currentTime).getNano() / 1e9 >=
            cooldown) {
            for (GunShootPoint point : shootPoints) {
                double rotation = point.getBarrel().getRotation();
                double directionX = -Math.sin(Math.toRadians(rotation));
                double directionY = Math.cos(Math.toRadians(rotation));

                System.out.print(point.getBarrel().getXPosition());
                System.out.println(point.getBarrel().getXOriginPosition());

                Bullet bullet =
                    new Bullet(arena, this,
                               point.getBarrel().getXOriginPosition() +
                                   directionX * point.getOffset(),
                               point.getBarrel().getYOriginPosition() +
                                   directionY * point.getOffset(),
                               directionX * bulletSpeed,
                               directionY * bulletSpeed, 20, "red");
                bullets.add(bullet);

                lastShotTime = currentTime;
            }
        }
    }

    public void updateBullets() {
        List<Bullet> bulletsToRemove = new ArrayList<>();

        for (Bullet bullet : bullets) {
            bullet.update();

            if (bullet.getPositionX() < 0 || bullet.getPositionX() > 1600 ||
                bullet.getPositionY() < 0 || bullet.getPositionY() > 900) {
                bulletsToRemove.add(bullet);
            }
        }

        for (Bullet bullet : bulletsToRemove) {
            bullet.destroy();
        }
    }

    public List<Bullet> getBullets() {
        return bullets;
    }
}
