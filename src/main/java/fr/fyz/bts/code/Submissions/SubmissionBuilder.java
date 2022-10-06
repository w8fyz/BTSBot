package fr.fyz.bts.code.Submissions;

import java.io.IOException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

import fr.fyz.bts.code.Error.LanguageIdNotExistExpection;
@SuppressWarnings("unused")
public class SubmissionBuilder {

    private final String source_code;
    private final int language_id;

    private String serverUrl="https://api.judge0.com";

    private boolean wait=false;
    private boolean base64_encoded=false;

    private String compiler_options;
    
	private String command_line_arguments;
    private String stdin;
    private String expected_output;
    private float cpu_time_limit=-1;
    private float cpu_extra_time=-1;
    private float wall_time_limit=-1;
    private float memory_limit=-1;
    private int stack_limit=-1;
    private int max_processes_and_or_threads=-1;
    private boolean enable_per_process_and_thread_time_limit;
    private boolean enable_per_process_and_thread_memory_limit;
    private int max_file_size;
    private int number_of_runs;

    private String args="";

    public SubmissionBuilder setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
        return this;
    }

    public SubmissionBuilder setBase64_encoded(boolean base64_encoded) {
        this.base64_encoded = base64_encoded;
        return this;
    }

    public SubmissionBuilder setWait(boolean wait) {
        this.wait = wait;
        return this;
    }

    @SuppressWarnings("deprecation")
	public SubmissionBuilder(String source_code, int language_id) {



        this.source_code = URLEncoder.encode(source_code);
        this.language_id = language_id;
    }

    public SubmissionBuilder setCompiler_options(String compiler_options) {
        args=args+"&compiler_options="+compiler_options;
        this.compiler_options = compiler_options;
        return this;
    }

    public SubmissionBuilder setCommand_line_arguments(String command_line_arguments) {
        args=args+"&command_line_arguments="+command_line_arguments;
        this.command_line_arguments = command_line_arguments;
        return this;
    }

    public SubmissionBuilder setCpu_extra_time(float cpu_extra_time) {
        args=args+"&cpu_extra_time="+cpu_extra_time;
        this.cpu_extra_time = cpu_extra_time;
        return this;
    }

    public SubmissionBuilder setCpu_time_limit(float cpu_time_limit) {
        args=args+"&cpu_time_limit="+cpu_time_limit;
        this.cpu_time_limit = cpu_time_limit;
        return this;
    }

    public SubmissionBuilder setEnable_per_process_and_thread_memory_limit(boolean enable_per_process_and_thread_memory_limit) {
        args=args+"&enable_per_process_and_thread_memory_limit="+enable_per_process_and_thread_memory_limit;
        this.enable_per_process_and_thread_memory_limit = enable_per_process_and_thread_memory_limit;
        return this;
    }

    public SubmissionBuilder setEnable_per_process_and_thread_time_limit(boolean enable_per_process_and_thread_time_limit) {
        args=args+"&enable_per_process_and_thread_time_limit="+enable_per_process_and_thread_time_limit;
        this.enable_per_process_and_thread_time_limit = enable_per_process_and_thread_time_limit;
        return this;
    }

    public SubmissionBuilder setExpected_output(String expected_output) {
        args=args+"&expected_output="+expected_output;
        this.expected_output = expected_output;
        return this;
    }

    public SubmissionBuilder setMax_file_size(int max_file_size) {
        args=args+"&max_file_size="+max_file_size;
        this.max_file_size = max_file_size;
        return this;
    }

    public SubmissionBuilder setMax_processes_and_or_threads(int max_processes_and_or_threads) {
        args=args+"&max_processes_and_or_threads="+max_processes_and_or_threads;
        this.max_processes_and_or_threads = max_processes_and_or_threads;
        return this;
    }

    public SubmissionBuilder setMemory_limit(float memory_limit) {
        args=args+"&memory_limit="+memory_limit;
        this.memory_limit = memory_limit;
        return this;
    }

    public SubmissionBuilder setNumber_of_runs(int number_of_runs) {
        args=args+"&number_of_runs="+number_of_runs;
        this.number_of_runs = number_of_runs;
        return this;
    }

    public SubmissionBuilder setStack_limit(int stack_limit) {
        args=args+"&stack_limit="+stack_limit;
        this.stack_limit = stack_limit;
        return this;
    }

    public SubmissionBuilder setStdin(String stdin) {
        args=args+"&stdin="+stdin;
        this.stdin = stdin;
        return this;
    }

    public SubmissionBuilder setWall_time_limit(float wall_time_limit) {
        args=args+"&wall_time_limit="+wall_time_limit;
        this.wall_time_limit = wall_time_limit;
        return this;
    }

    public DefaultSubmission build() throws IOException, LanguageIdNotExistExpection {

        CloseableHttpClient httpClient = HttpClients.createDefault();

        String postUrl=serverUrl+"/submissions?base64_encoded="+this.base64_encoded
                                +"&wait="+this.wait
                                +"&source_code="+source_code
                                +"&language_id="+language_id
                                +args;
        HttpPost request=new HttpPost(postUrl);
        request.setHeader("Content","application/json");
        HttpResponse httpResponse=httpClient.execute(request);
        String json= EntityUtils.toString(httpResponse.getEntity());

        if(json.contains("language_id"))
            throw new LanguageIdNotExistExpection(language_id,serverUrl);

        Gson gson=new Gson();
        token t=gson.fromJson(json,token.class);

        System.out.println(t.getToken());
        DefaultSubmission defaultSubmission=null;

        do {

            HttpGet httpGet = new HttpGet(serverUrl + "/submissions/" + t.getToken());
            httpResponse = httpClient.execute(httpGet);
            json = EntityUtils.toString(httpResponse.getEntity());

            System.out.println(json);

            defaultSubmission = gson.fromJson(json, DefaultSubmission.class);

        }while(defaultSubmission.getStatus().getId()==1 || defaultSubmission.getStatus().getId()==2);

        System.out.println(defaultSubmission.getStatus().getId());
        httpClient.close();
        return defaultSubmission;
    }
}
