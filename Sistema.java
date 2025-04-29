import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Sistema {
    private Scanner in;
    private ArrayList<Variable> variables;
    private double[][] mtx;
    private int cont;

    public Sistema(){
        in = new Scanner(System.in);    
        variables = new ArrayList<>();
        cont = 0;
    }
    public void init(){
        System.out.println("Por favor, indique o path do arquivo a ler:");
        String path = in.nextLine();
        try{
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line = reader.readLine();
        while(line != null){
            String[] parts = line.split(" ");
            if(parts.length == 2){
            String name = parts[0];
            double value = Double.parseDouble(parts[1]);
            Variable v = new Variable(name, value, cont);
            cont++;
            variables.add(v);
            }
            else{
            String src = parts[0];
            String dest = parts[1];
            int rate = Integer.parseInt(parts[2]);
            for (Variable v : variables) {
                if (v.getName().equals(src)) {
                for (Variable w : variables) {
                    if (w.getName().equals(dest)) {
                    int[] connection = {w.getIndex(), rate};
                    if(w.getIndex()==v.getIndex()){
                        System.out.println("Alerta!!!! retroalimentação inesperada");
                    }
                    if (!v.addConection(connection)) {
                        System.out.println("Conexão já existente entre " + src + " e " + dest);
                    }
                    }
                }
                }
            }
            }
            line = reader.readLine();
        }
        double[][] mtx = initMatrix(variables.size());
        reader.close();
        //System.out.println(printMtx(mtx));
        double[] x = Seidel(mtx);
        double[] y = Jacobi(mtx);
        //System.out.println(printVector(x));
        System.out.println(checkResults(mtx, x));
        System.out.println(checkResults(mtx, y));
        System.out.println(diff(x,y));
        }
        catch(Exception e){
        System.err.println(e);
        }
    }

    public double[][] initMatrix(int size){
        mtx = new double[size][size+1];
        for(int i = 0; i < size; i++){
        for(int j = 0; j < size+1; j++){
            mtx[i][j] = 0.0;
        }
        }
        for(Variable v: variables){
        mtx[v.getIndex()][v.getIndex()] += -1.0;
        for(int[] connection: v.getConnections()){
            mtx[v.getIndex()][connection[0]] = connection[1]/100.0;
        }
        }
        for(int i = 0; i < mtx.length; i++){
        mtx[i][mtx[i].length-1] = -variables.get(i).getValue();
        }
        return mtx;
    }

    public String printVector(double[] vector){
        String s = "";
        for(int i=0; i<vector.length; i++){
            s+=vector[i] + "\n";
        }
        return s;
    }

    public String printMtx(double[][] mtx){
        String s = "";
        for(int i = 0; i < mtx.length; i++){
        for(int j = 0; j < mtx[i].length; j++){
            s += mtx[i][j] + " ";
        }
        s += "\n";
        }
        return s;
    }

    public double[] Seidel(double[][] mtx){
        int size = mtx.length;
        double[] x = new double[size];
        for(int i=0; i<size; i++){
            x[i] = 0;
        }
        double diff = 6718238;
        while(diff > 1e-5){
            diff = 0;
            for(int i=0; i<size; i++){
                double res = -mtx[i][size];
                for(int j=0; j<size; j++){
                    if(i!=j){
                        res += x[j] * mtx[i][j];
                    }
                }
                diff += Math.abs(x[i]-res);
                x[i] = res;
            }
        }
        return x;
    }

    public double[] Jacobi(double[][] mtx){
        int size = mtx.length;
        double[] x = new double[size];
        double[] y = new double[size];
        for(int i=0; i<size; i++){
            x[i] = 0;
            y[i] = 0;
        }
        double diff = 6718238;
        while(diff > 1e-5){
            for(int i=0; i<size; i++){
                y[i] = x[i];
            }
            diff = 0;
            for(int i=0; i<size; i++){
                double res = -mtx[i][size];
                for(int j=0; j<size; j++){
                    if(i!=j){
                        res += y[j] * mtx[i][j];
                    }
                }
                diff += Math.abs(x[i]-res);
                x[i] = res;
            }
        }
        return x;
    }

    public double[] Gauss(double[][] mtx){
        int size = mtx.length;
        double[] x = new double[size];
        for(int i=0; i<size-1; i++){
            for(int j=i; j<size; j++){

            }
        }
        return x;
    }

    public double[] somaLinha(double[] l1, double[] l2, double fatorL2){
        double[] x = new double[l1.length];
        return x;
    }

    public boolean checkResults(double[][] mtx, double[] vector){
        int size = mtx.length;
        double diff = 0;
        double sum;
        for(int i=0; i<size; i++){
            sum = 0;
            for(int j=0; j<size; j++){
                sum += mtx[i][j] * vector[j];
            }
            diff = Math.abs(sum-mtx[i][size]);
            if(diff>1e-5){
                return false;
            }
        }
        return true;
    }

    public double diff(double[] x, double[] y){
        double diff = 0;
        int size = x.length;
        for(int i=0; i<size; i++){
            diff += Math.abs(x[i]-y[i]);
        }
        return diff;
    }
}
