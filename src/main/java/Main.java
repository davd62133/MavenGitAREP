import javax.servlet.MultipartConfigElement;
import java.io.*;
import java.util.LinkedList;
import static spark.Spark.*;


public class Main {
    public static void main(String args[]){
        port(getPort());
        staticFiles.location("/");

        pruebas();
        post("/resultados", (request, response) -> {
            LinkedList<Double> nums = new LinkedList<>();
            String html = "<html>"
                    +"<head>"
                    +"<title>Resultados</title>"
                    +"</head>"
                    +"<body>"
                    +"<h2>Resultados</h2><p>";
            try{
                for(String s : request.queryParams("uploadText").split(";")){
                    nums.add(Double.parseDouble(s));
                }
                html +=
                    "Datos ingresados: " + nums + "<p>"
                    +"La media de los datos es: " + medium(nums) + "<p>"
                    +"La desviacion estandar de los datos es: " + standardDesviation(nums) + "<p>"
                    +"<a href=\"/index.html\"><button>Volver</button></a>"
                    +"</body>"
                    +"</html>";
            }catch (Exception e){
                html +=
                        "Ingreso mal los resultados, recuerde que para un decimal es un punto ('.'), y no se puede ingresar cadenas de letras <p>"
                         +"<a href=\"/index.html\"><button>Volver</button></a>"
                         +"</body>"
                         +"</html>";
            }
            return html;
        });
    }

    static int getPort(){
        if(System.getenv("PORT") != null){
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }


    private static void pruebas(){
        final String html = "<html>"
                +"<head>"
                +"<title>Resultados</title>"
                +"</head>"
                +"<body>"
                +"<h2>Resultados</h2><p>";
        get("/prueba1", (req,res) -> {
            LinkedList<Double> nums = Main.readFile("src\\main\\java\\test1.txt");
            return  html + "Datos ingresados: " + nums + "<p>"
                    +"La media de los datos es: " + medium(nums) + "<p>"
                    +"La desviacion estandar de los datos es: " + standardDesviation(nums) + "<p>"
                    +"<a href=\"/index.html\"><button>Volver</button></a>"
                    +"</body>"
                    +"</html>";});
        get("/prueba2", (req,res)->{
            LinkedList<Double> nums = Main.readFile("src\\main\\java\\test2.txt");
           return html +"Datos ingresados: " + nums + "<p>"
                   +"La media de los datos es: " + medium(nums) + "<p>"
                   +"La desviacion estandar de los datos es: " + standardDesviation(nums) + "<p>"
                   +"<a href=\"/index.html\"><button>Volver</button></a>"
                   +"</body>"
                   +"</html>";});
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
