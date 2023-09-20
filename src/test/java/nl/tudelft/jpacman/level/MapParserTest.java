package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.PacmanConfigurationException;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.points.PointCalculatorLoader;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class MapParserTest {

    static PacManSprites pms;
    MapParser mp;
    static GhostFactory gf;
    static LevelFactory lf;


    @BeforeAll
    static void init(){
        pms = new PacManSprites();
        gf = new GhostFactory(pms);
        lf = new LevelFactory(pms, gf, new PointCalculatorLoader().load());
    }

    @BeforeEach
    void setup(){
        mp = new MapParser(lf, new BoardFactory(pms));
    }

    @AfterEach
    void tearDown(){
        mp = null;
    }

    @Test
    void testLoadSimpleMap() {
        Assertions.assertThatCode(() -> mp.parseMap("/simplemap.txt")).doesNotThrowAnyException();
    }

    @Test
    void testLoadEmptyMap(){
        Assertions.assertThatThrownBy(() -> mp.parseMap("/emptyMap.txt")).isInstanceOf(PacmanConfigurationException.class);
    }

    @Test
    void testLoadCertainDeathMap() {
        Assertions.assertThatCode(() -> mp.parseMap("/certainDeathMap.txt")).doesNotThrowAnyException();
    }

    @Test
    void testNonExistentMap(){
        // Map doesnt exist
        Assertions.assertThatThrownBy(() -> mp.parseMap("/IdontExistMap.txt")).isInstanceOf(PacmanConfigurationException.class);
    }
}

