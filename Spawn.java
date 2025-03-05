import java.util.*;

public class Spawn {
    List<List<Integer>> types = new ArrayList<>();
    List<Integer> repeats = new ArrayList<>();
    List<Integer> timings = new ArrayList<>();

    public Spawn(List<List<Integer>> types, List<Integer> repeats, List<Integer> timings) {
        this.types = types;
        this.repeats = repeats;
        this.timings = timings;
    }
}
