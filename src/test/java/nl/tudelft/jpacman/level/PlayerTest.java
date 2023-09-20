package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.npc.ghost.GhostMapParser;
import nl.tudelft.jpacman.sprite.PacManSprites;
import nl.tudelft.jpacman.sprite.Sprite;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PlayerTest {




    private Launcher launcher;

    @BeforeEach
    void setup(){
        launcher = new Launcher();
        launcher.launch();
        launcher.getGame().start();
    }

    @AfterEach
    void teardown(){
        launcher.getGame().stop();
        launcher.dispose();
    }

    @Test
    void testIsAlive(){
        // Player should be alive after game start
        Player p = launcher.getGame().getPlayers().get(0);
        Assertions.assertThat(p.isAlive()).isTrue();
    }

    @Test
    void testPlayerMove(){
        Player pacman = launcher.getGame().getPlayers().get(0);
        Square pacmanStartSpot = pacman.getSquare();
        launcher.getGame().getLevel().move(pacman, Direction.NORTH);
        Assertions.assertThat(pacmanStartSpot).isEqualTo(pacman.getSquare()); // Never moved cause upward collision
        launcher.getGame().getLevel().move(pacman, Direction.SOUTH);
        Assertions.assertThat(pacmanStartSpot).isEqualTo(pacman.getSquare()); // Never moved cause downward collision

        launcher.getGame().getLevel().move(pacman, Direction.EAST);
        launcher.getGame().getLevel().move(pacman, Direction.WEST);
        Assertions.assertThat(pacmanStartSpot).isEqualTo(pacman.getSquare()); // mvoed right so left should be same
        launcher.getGame().getLevel().move(pacman, Direction.WEST);
        launcher.getGame().getLevel().move(pacman, Direction.EAST);
        Assertions.assertThat(pacmanStartSpot).isEqualTo(pacman.getSquare()); // moved right back to start spot should be same as start.
    }
}
