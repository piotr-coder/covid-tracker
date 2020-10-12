package spring.covidtracker.service;


import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;

@Service
public class CoronavirusDataService {
    public static final String VIRUS_DATA_URL = "https://opendata.ecdc.europa.eu/covid19/casedistribution/csv";


//    public void fetchVirusData() throws IOException, InterruptedException {
//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(VIRUS_DATA_URL))
//                .build();
//
//        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandler.asString());
//        System.out.println(httpResponse.body());
//    }

//    @PostConstruct
//    public static void downloadWithJavaNIO() throws IOException {

//
//        URL url = new URL(VIRUS_DATA_URL);
//        try (ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
//             FileOutputStream fileOutputStream = new FileOutputStream(localFilename); FileChannel fileChannel = fileOutputStream.getChannel()) {
//
//            fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
//            fileOutputStream.close();
//        }
//    }

    public static void downloadWithJavaNIO() throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        byte[] data = null;

        DecimalFormat decimalFormat = new DecimalFormat("###.###");

        File file = new File("data.csv");

        try {
            URL url = new URL("http://dvlup.xyz/csv/us-500.csv");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            System.out.println("Connected :)");

            InputStream inputStream = connection.getInputStream();

            long read_start = System.nanoTime();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            int i;

            while ((i = reader.read()) != -1) {
                char c = (char) i;
                if (c == '\n') {
                    stringBuffer.append("\n");
                } else {
                    stringBuffer.append(String.valueOf(c));
                }
            }

            reader.close();

            long read_end = System.nanoTime();

            System.out.println("Finished reading response in " + decimalFormat.format((read_end - read_start) / Math.pow(10, 6)) + " milliseconds");


        } catch (
                MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            data = stringBuffer.toString().getBytes();
        }
    }
}