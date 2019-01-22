import javax.servlet.MultipartConfigElement;
import java.io.*;
import java.util.LinkedList;
import static spark.Spark.*;


public class Main {
    public static void main(String args[]){
        /**LinkedList<Double> nums = Main.readFile("src\\main\\java\\test1.txt");
        System.out.println("Media de la prueba 1: " + String.valueOf(medium(nums)));
        System.out.println("Desviacion Estandar de la prueba 1: " + String.valueOf(standardDesviation(nums)));
        nums = Main.readFile("src\\main\\java\\test2.txt");
        System.out.println("Media de la prueba 2: " + String.valueOf(medium(nums)));
        System.out.println("Desviacion Estandar de la prueba 2: " + String.valueOf(standardDesviation(nums)));**/
        port(getPort());
        staticFiles.location("/");
        //get("/hello", (req,res) -> "Hello Heroku");

        post("/num", (request, response) -> {
            /**request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
            try (InputStream is = request.raw().getPart("uploaded_file").getInputStream()) {
                // Use the input stream to create a file
                System.out.println("Algo paso");
            }
            return "File uploaded";**/
            System.out.println("Algo apso");
            System.out.println(request.queryParams("uploadText"));
            LinkedList<Double> nums = new LinkedList<>();
            //for(char c : request.queryParams("uploadText")){ }
            return "YEAH";
        });
    }

    static int getPort(){
        if(System.getenv("PORT") != null){
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }

    /**
     * Calculates de Mediam of a list of doubles
     * @param nums Link List of doubles
     * @return The Medium
     */
    public static double medium(LinkedList<Double> nums){
        Double dub = 0.0;
        for(Double d : nums) dub += d;
        return dub/nums.size();
    }

    /**
     * Calculates the standard desviation of a list that
     * @param nums Link List of doubles
     * @return The Standard Desviation
     */
    public static double standardDesviation(LinkedList<Double> nums){
        Double dub = 0.0;
        double medium = medium(nums);
        for(Double d : nums) dub+=Math.pow(d-medium,2);
        return Math.sqrt(dub/(nums.size()-1));
    }


    /**
     * Reads a file with the tests
     * @param fileName the name of the test
     * @return Linked List of the numbers entered
     */
    private static LinkedList<Double> readFile(String fileName){
        LinkedList<Double> nums = new LinkedList<Double>();
        String line = null;
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
                //System.out.println(line);
                nums.add(Double.parseDouble(line));
            }
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Archivo no encontrado");
        }
        catch(IOException ex) {
            System.out.println("Error leyendo el archivo");
        }
        return nums;
    }
}
