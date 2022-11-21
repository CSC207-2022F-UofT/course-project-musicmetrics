package interface_adapters;

public class Alert {
    int stream;
    int stream_in_period;
    boolean hit;
    static int growth_rate = 100;
    public Alert(int stream, int stream_in_period, boolean hit){
        this.stream = stream;
        this.stream_in_period = stream_in_period;
        this.hit = false;


    }
    public void trigger(int stream, int stream_in_period){
        if(stream * 100 >= stream_in_period){
            this.hit = true;

        }
    }
    public void sendAlert(){

    }
}
