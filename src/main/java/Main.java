import javax.servlet.MultipartConfigElement;
import java.io.*;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import static spark.Spark.*;


public class Main {
    public static void main(String args[]){
        port(getPort());
        staticFiles.location("/");
        index();
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
                    +"<a href=\"/\"><button>Volver</button></a>"
                    +"</body>"
                    +"</html>";
            }catch (Exception e){
                html +=
                        "Ingreso mal los resultados, recuerde que para un decimal es un punto ('.'), y no se puede ingresar cadenas de letras <p>"
                         +"<a href=\"/\"><button>Volver</button></a>"
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
            //LinkedList<Double> nums = Main.readFile("src\\main\\java\\test1.txt");
            LinkedList<Double> nums = new LinkedList<>(Arrays.asList(160.0,591.0,114.0,229.0,230.0,270.0 ,128.0 ,1657.0 ,624.0 ,1503.0));
            return  html + "Datos ingresados: " + nums + "<p>"
                    +"La media de los datos es: " + medium(nums) + "<p>"
                    +"La desviacion estandar de los datos es: " + standardDesviation(nums) + "<p>"
                    +"<a href=\"/\"><button>Volver</button></a>"
                    +"</body>"
                    +"</html>";});
        get("/prueba2", (req,res)->{
            //LinkedList<Double> nums = Main.readFile("src\\main\\java\\test2.txt");
            LinkedList<Double> nums = new LinkedList<>(Arrays.asList(15.0, 69.9,6.5,22.4,28.4,65.9,19.4,198.7,38.8,138.2));
           return html +"Datos ingresados: " + nums + "<p>"
                   +"La media de los datos es: " + medium(nums) + "<p>"
                   +"La desviacion estandar de los datos es: " + standardDesviation(nums) + "<p>"
                   +"<a href=\"/\"><button>Volver</button></a>"
                   +"</body>"
                   +"</html>";});
    }

    /**
     * Configures the index of the page
     */
    public static void index(){
        String html = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Calculadora estadistica</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h2>Calculadora Estadistica</h2>\n" +
                "<form method='POST' action=\"/resultados\">\n" +
                "    <input type='text' name='uploadText'>\n" +
                "    <p>\n" +
                "    <button>Ingresar numeros</button>\n" +
                "</form>\n" +
                "<p>\n" +
                "<form action=\"/prueba1\">\n" +
                "    <button>Correr pruebas 1</button>\n" +
                "</form>\n" +
                "<p>\n" +
                "<form action=\"/prueba2\">\n" +
                "    <button>Correr pruebas 2</button>\n" +
                "</form>\n" +
                "Para ingresar los numeros separelos por \";\"\n" +
                "<p>\n" +
                "EJ:\n" +
                "<p>\n" +
                "3.1;242;32.52;23 (NOTA: los decimales se separan por puntos)\n" +
                "</body>\n" +
                "</html>";
        get("/", (req,res)->html);
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
