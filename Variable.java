import java.util.ArrayList;
public class Variable {
    private String name;
    private double value;
    private int index;
    private ArrayList<int[]> connections;

    public Variable(String name, double value, int index) {
        this.name = name;
        this.value = value;
        this.index = index;
        connections = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    public boolean addConection(int[] connection){
        for (int[] conn : connections) {
            if (conn[0] == connection[0] && conn[1] == connection[1]) {
                return false;
            }
        }
        connections.add(connection);
        return true;

    }

    public ArrayList<int[]> getConnections() {
        return connections;
    }

    public double getValue() {
        return value;
    }
}
