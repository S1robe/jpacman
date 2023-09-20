package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.game.GameFactory;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.points.DefaultPointCalculator;
import nl.tudelft.jpacman.points.PointCalculatorLoader;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CollisionTest {

    //Crash test dummy time.

    static PacManSprites pacManSprites;
    static GhostFactory ghostFactory;
    static CustomLevelFactory levelFactory;
    static GameFactory gameFactory;
    static MapParser mapParser;

    static Game game;
    static Level customLevel;

    Player pacman;

    /**
     *
     * These are bulky loads so we load them first for efficiency.
     * This doesnt affect the state of the tests as these dont need to be refreshed between tests.
     *
     */
    @BeforeAll
    static void init(){
        pacManSprites = new PacManSprites();
        ghostFactory = new GhostFactory(pacManSprites);
        levelFactory = new CustomLevelFactory(pacManSprites, ghostFactory, new PointCalculatorLoader().load());
        gameFactory = new GameFactory(new PlayerFactory(pacManSprites));
        mapParser = new MapParser(levelFactory, new BoardFactory(pacManSprites));
        try {
            customLevel = mapParser.parseMap("/collisionMap.txt");
        } catch (IOException e) {
            Assertions.fail("Map Parser failed to load collisionMap.txt");
        }
        game = gameFactory.createSinglePlayerGame(customLevel, new DefaultPointCalculator());
    }

    @BeforeEach
    void setup(){
        pacman = game.getPlayers().get(0);
        game.start();
    }

    @Test
    void collideCoinThenWallThenGhost(){
        customLevel.move(pacman, Direction.WEST); // Should collide with coin
        // Check for things that do coin things
        Assertions.assertThat(pacman.getScore()).isEqualTo(10); // coins add 10 to score.

        Square wall = pacman.squaresAheadOf(1);
        customLevel.move(pacman, Direction.WEST);
        Assertions.assertThat(pacman.squaresAheadOf(1)).isEqualTo(wall); // Square should be the same, it is unchanged.

        //continue marching right, to the ghost,
        ScheduledExecutorService timer = Executors.newScheduledThreadPool(1);
        timer.scheduleAtFixedRate(() -> {
            if (pacman.isAlive()){
                customLevel.move(pacman, Direction.EAST);
            } else {
                timer.shutdown();
            }
        }, 0, 100, TimeUnit.MILLISECONDS);

        // This will fail if the task is interrupted.
        Assertions.assertThatCode(() -> {
            if (timer.awaitTermination(1, TimeUnit.SECONDS))
                timer.shutdown();
            }).doesNotThrowAnyException();

        // End of game should be caused by interaction with ghost.
        Assertions.assertThat(pacman.getKiller()).isInstanceOf(Ghost.class);
    }

    @AfterEach
    void tearDown(){
        if(game.isInProgress())
            game.stop();
    }



}
