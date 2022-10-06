package fr.fyz.bts.code.Lang;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

public class Languages {

    private static Languages instance;
    private Language[] langs;


    public static Language[] getSupportLangs(String serverUrl) throws IOException {
        if(instance==null){
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet=new HttpGet(serverUrl+"/languages");
            HttpResponse httpResponse=httpClient.execute(httpGet);

            String response= EntityUtils.toString(httpResponse.getEntity());
            instance=new Gson().fromJson("{\"langs\":"+response+"}",Languages.class);
            System.out.println("{\"langs\":"+response+"}");

            httpClient.close();
        }

        return instance.langs;
    }
}
