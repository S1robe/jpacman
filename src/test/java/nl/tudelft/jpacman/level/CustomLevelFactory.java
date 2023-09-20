package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.points.DefaultPointCalculator;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;

import java.util.List;

public class CustomLevelFactory extends LevelFactory{
    /**
     * Creates a new level factory.
     *
     * @param spriteStore     The sprite store providing the sprites for units.
     * @param ghostFactory    The factory providing ghosts.
     * @param pointCalculator The algorithm to calculate the points.
     */
    public CustomLevelFactory(PacManSprites spriteStore, GhostFactory ghostFactory, PointCalculator pointCalculator) {
        super(spriteStore, ghostFactory, pointCalculator);
    }

    /**
     * Creates a new level from the provided data.
     *
     * @param board          The board with all ghosts and pellets occupying their squares.
     * @param ghosts         A list of all ghosts on the board.
     * @param startPositions A list of squares from which players may start the game.
     * @return A new level for the board.
     */
    @Override
    public Level createLevel(Board board, List<Ghost> ghosts, List<Square> startPositions) {
        return new Level(board,ghosts,startPositions,new DefaultPlayerInteractionMap(new DefaultPointCalculator()));
    }
}
