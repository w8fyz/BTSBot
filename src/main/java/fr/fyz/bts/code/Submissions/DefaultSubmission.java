package fr.fyz.bts.code.Submissions;

public class DefaultSubmission {

    private String stdout;
    private float time;
    private float memory;
    private String stderr;
    private String token;
    private String compile_output;
    private String messange;
    private Statue status;

    public String getStdout() {
        return stdout;
    }

    public String getStderr() {
        return stderr;
    }

    public void setStdout(String stdout) {
        this.stdout = stdout;
    }

    public String getToken() {
        return token;
    }

    public float getMemory() {
        return memory;
    }

    public float getTime() {
        return time;
    }

    public Statue getStatus() {
        return status;
    }

    public String getCompile_output() {
        return compile_output;
    }

    public String getMessange() {
        return messange;
    }

}
