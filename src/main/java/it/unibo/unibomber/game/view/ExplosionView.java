package it.unibo.unibomber.game.view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.controller.impl.Explosion;
import it.unibo.unibomber.utilities.Direction;
import it.unibo.unibomber.utilities.Pair;
import it.unibo.unibomber.utilities.UploadRes;
import it.unibo.unibomber.utilities.Constants.UI.Game;

import static it.unibo.unibomber.utilities.Constants.UI.SpritesMap;
import static it.unibo.unibomber.utilities.Constants.Explode.DEFAULT_EXPLOSION_ANIMATION_INDEX;

/**
 * Explosion View class.
 */
public final class ExplosionView implements GameLoop {
    private Explosion controller;
    private BufferedImage[][] animations;
    private int frame;
    private int indexDirection;

    /**
     * Explosion view constructor.
     * 
     * @param controller
     */
    public ExplosionView(final Explosion controller) {
        loadSprites();
        indexDirection = 8;
        this.controller = controller;
    }

    private void loadSprites() {
        animations = new BufferedImage[SpritesMap.ROW_EXPLOSION_SPRITES][SpritesMap.COL_EXPLOSION_SPRITES];
        for (Integer j = 0; j < SpritesMap.ROW_EXPLOSION_SPRITES; j++) {
            for (Integer i = 0; i < animations[j].length; i++) {
                animations[j][i] = UploadRes.getSpriteAtlas("bomb/explosion.png").getSubimage(
                        i * Game.EXPLOSION_DEFAULT, j * Game.EXPLOSION_DEFAULT,
                        Game.EXPLOSION_DEFAULT, Game.EXPLOSION_DEFAULT);
            }
        }
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(final Graphics g) {
        List<List<Pair<Integer, Integer>>> explosions = controller.getExplosionList();
        frame++;
        for (int i = 0; i < explosions.size(); i++) {
            if (!explosions.get(i).isEmpty()) {
                Pair<Integer, Integer> center = explosions.get(i).get(0);
                for (Pair<Integer, Integer> p1 : explosions.get(i)) {
                    g.drawImage(
                            getCorrectImage(Direction.getDistance(p1, center),
                                    Direction.extractDirecionBetweenTwo(center, p1).get(), i),
                            Math.round(p1.getY() * Game.getTilesSize()),
                            Math.round(p1.getX() * Game.getTilesSize()),
                            (int) (Game.getTilesDefault() * Game.SCALE),
                            (int) (Game.getTilesDefault() * Game.SCALE),
                            null);
                }
            }
        }
    }

    private BufferedImage getCorrectImage(final int distance, final Direction dir, final int id) {
        int d = distance != controller.getBombPower(id) ? 1 : 0;
        getDirectionIndex(dir);
        if (dir == Direction.CENTER) {
            return animations[frame % SpritesMap.ROW_EXPLOSION_SPRITES][indexDirection];
        } else {
            return animations[frame % SpritesMap.ROW_EXPLOSION_SPRITES][indexDirection + d];
        }
    }

    private void getDirectionIndex(final Direction dir) {
        switch (dir) {
            case UP:
                indexDirection = DEFAULT_EXPLOSION_ANIMATION_INDEX * 6;
                break;
            case DOWN:
                indexDirection = DEFAULT_EXPLOSION_ANIMATION_INDEX * 2;
                break;
            case RIGHT:
                indexDirection = DEFAULT_EXPLOSION_ANIMATION_INDEX * 4;
                break;
            case LEFT:
                indexDirection = DEFAULT_EXPLOSION_ANIMATION_INDEX * 0;
                break;
            default:
                indexDirection = DEFAULT_EXPLOSION_ANIMATION_INDEX * 8;
                break;
        }
    }
}