
/**
 * [WriteLeaderboar.java]
 * Serializable class. Sent by the client when writing a score record
 * @author Sunny Jiao
 * @version 1.0
 */

import java.io.Serializable;

@SuppressWarnings("serial")
public class WriteLeaderboard implements Serializable {

    String name;
    int score;
}
