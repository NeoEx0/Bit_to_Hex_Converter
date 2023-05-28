package Engin;
import java.util.*;
public interface Engin {
    public ArrayList<Bit> getList();
    public ArrayList<Bit> reverselist();
    public ArrayList<Bit> addBit(String name);
    public ArrayList<Bit> deleteBit(int position);
    public ArrayList<Bit> changeBitPosition(int old_position,int new_position);
    public ArrayList<Bit> changeBitState(int postion);
    public ArrayList<Bit> changeBitName(String name,int position);
    public String getInBits();
    public String getInHex();
    public void saveas(String path);
    public ArrayList<Bit> load(String path);
    /*getProgrammList
    Import
    Export
*/

}
