/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isd;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author Arup
 */
public class folder {
    private String name;
    private String date;
    private Long size;
    private String path;
    private ObjectProperty image;
    
    public folder(String nam,String dte,long siz, String pth, Object ob){
        name=new String(nam);
        date=new String(dte);
        size=new Long(siz);
        path=new String(pth);
        image = new SimpleObjectProperty(ob);
    }
    public String getName(){
        return name;
    }
    public String getDate(){
        return date;
    }
    public String getPath(){
        return path;
    }
    public long getSize(){
        return size;
    }
    public Object getImage() {
      return image.get();
   }
}
