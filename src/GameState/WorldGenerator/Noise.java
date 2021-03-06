package GameState.WorldGenerator;

/*
 * Copied from google, and it works.
 * Don't touch this except if you're 200% sure of how many bugs
 * you're gonna make.
 */
public class Noise {
	
	private long seed;

	public Noise(long seed) {
	    this.seed = seed;
	}

	public int getNoise(int x, int range) {
	    int selectionSize = 6  * 6;
	    float noise = 0;

	    range /= 2;
	    while (selectionSize > 0) {

	        int selectionIndex = x / selectionSize;

	        float distanceToIndex = (x % selectionSize) / (float) (selectionSize);

	        float leftRandom = random(selectionIndex, range);
	        float rightRandom = random(selectionIndex + 1, range);

	        noise += (1 - distanceToIndex) * leftRandom + distanceToIndex * rightRandom;

	        selectionSize /= 2;
	        range /= 2;

	        range = Math.max(1, range);
	    }

	    return Math.round(noise * (noise < 0 ? -1 : 1));
	}

	private int random(int x, int range) {
	    return (int) ((x + seed) ^ 10) % range;
	}
	
}