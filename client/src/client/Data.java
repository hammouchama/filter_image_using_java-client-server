package client;

import java.io.File;
import java.io.Serializable;

public class Data implements Serializable {

    private File image;
    private String filter;

    public Data(File image,String filter) {
        this.image=image;
        this.filter=filter;
    }

    public File getImage() {
        return image;
    }

    public String getFilter() {
        return filter;
    }
}
