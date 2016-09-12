package chess.tests;

import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.Test;


@RunWith(Suite.class)
@Suite.SuiteClasses({PieceTests.class, BoardTests.class, GameTests.class})

public class ChessTests {

}
