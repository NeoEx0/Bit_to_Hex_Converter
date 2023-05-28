package Engin;

public class Bit {
    private String name;
    private int position;
    private boolean state = false;

    public Bit(String name,int position){
        this.name = name;
        this.position = position;
    }
    public Bit(String name, int position, boolean state){
        this.name = name;
        this.position = position;
        this.state = state;
    }
    
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public int getPosition(){
        return position;
    }
    public void setPosition(int position){
        this.position = position;
    }

    public void changeState(){
        state = state? false : true;
    }
    public boolean getState(){
        return state;
    }
}
