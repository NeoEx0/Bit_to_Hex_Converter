package Engin;
import java.util.*;
import java.lang.Math;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException; 
public class Engin_imp implements Engin{
    private ArrayList<Bit> bitlist;
    private int current_version;

    public Engin_imp(int current_version){
        bitlist  = new ArrayList<Bit>();
        this.current_version = current_version;
    }
    public ArrayList<Bit> getList(){
        return bitlist;
    }

    public ArrayList<Bit> reverselist(){
        ArrayList<Bit> tmp = new ArrayList<Bit>();
        for(int i = bitlist.size() - 1; i >= 0; i--){
            tmp.add(bitlist.get(i));
        }
        bitlist = tmp;
        return bitlist;
    }
    public ArrayList<Bit> addBit(String name){
        bitlist.add(new Bit(name, bitlist.size()));
        return bitlist;
    }
    public ArrayList<Bit> deleteBit(int position){
        bitlist.remove(position);
        updatePositions();
        return bitlist;
    }
    public ArrayList<Bit> changeBitPosition(int old_position,int new_position){
        Bit temp = bitlist.get(new_position);
        bitlist.set(new_position, bitlist.get(old_position));
        bitlist.set(old_position,temp);
        updatePositions();
        return bitlist;
    }
    public ArrayList<Bit> changeBitState(int postion){
        bitlist.get(postion).changeState();
        return bitlist;
    }
    public ArrayList<Bit> changeBitName(String name,int position){
        bitlist.get(position).setName(name);
        return bitlist;
    }


    public String getInBits(){
        return new StringBuffer(getValues()).reverse().toString();
    }
    public String getInHex(){
        char[] hex_symbols = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        String values_in_bit = getValues();
        String values_in_hex = "";
        String tmp = "";
        int hex_as_dec = 0;
        int i = 0;
        while(values_in_bit.length() % 4 != 0){
            values_in_bit = values_in_bit + "0";
            
        }
        for(int j = 0; j < values_in_bit.length(); j++){
            tmp = tmp.concat(Character.toString(values_in_bit.charAt(j)));
            i++;
            if(i == 4){
                for(;i > 0;i--){
                    if(tmp.charAt(i -1) == '1'){

                        hex_as_dec += Math.pow(2, i-1);
                    }
                }
               values_in_hex = Character.toString(hex_symbols[hex_as_dec]) + values_in_hex;
               hex_as_dec = 0;
               i = 0;
               tmp = "";
            }
        }
        return values_in_hex;
    }

    public String getValues(){
        String values = "";
        String tmp;
        for (Bit bit : bitlist) {
            if(bit.getState()){
                tmp = "1";
            }else{
                tmp = "0";
            }
            values = values.concat(tmp);
        }
        return values;
    }

    public void updatePositions(){
        for (Bit list : bitlist) {
            list.setPosition(bitlist.indexOf(list));
        }
    }

    public void saveas(String path){
        File file = new File(path+ ".data");
        try{
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter write = new FileWriter(file);
            write.write(current_version + "|" + bitlist.size()+ "\n");
            write.write("POSITION | TRUE/FALSE | NAME\n");
            for (Bit bit : bitlist) {
                write.append(bit.getPosition() + "|" + bit.getState() + "|"+ bit.getName()+ "\n");
            }
            write.close();
        }catch(IOException i){

        }

    }
    public ArrayList<Bit> load(String path){
        String line;
        String split_line[];
        boolean state;
        int current_line = 1;
        File file = new File(path);
        if(!file.exists()){
            System.err.println("Chosen file dosent exist");
            return null;
        }
        bitlist.clear();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));

            try {
                while ((line = br.readLine()) != null) {
                    split_line = line.split("\\|");
                    if(current_line == 1){
                        if(Integer.parseInt(split_line[0]) > current_version){
                            System.err.println("Cant parse files, Software to Old");
                            return null;
                        }
                    }else if(current_line > 2){
                        if(split_line[1].contains("true")){
                            state = true;
                        }else{
                            state = false;
                        }
                        bitlist.add(new Bit(split_line[2],Integer.parseInt(split_line[0]),state ));
                        
                    }
                   current_line++;
                }
            } finally {
                br.close();
            }
        }catch(IOException i){

        } 
        return bitlist;
    }
    
}
