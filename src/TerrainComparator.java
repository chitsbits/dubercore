import java.util.Comparator;

public class TerrainComparator implements Comparator<Terrain>{

    @Override
    public int compare(Terrain t1, Terrain t2) {

        if (t1.totalCost < t2.totalCost){
            return 1;
        }
        else if (t1.totalCost > t2.totalCost){
            return -1;
        }

        return 0;
    }
    
}