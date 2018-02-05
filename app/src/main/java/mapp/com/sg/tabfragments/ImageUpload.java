package mapp.com.sg.tabfragments;

/**
 * Created by NgocTri on 3/4/2017.
 */

public class ImageUpload {

    public String name;
    public String modelNumber;
    public String url;


    public String getName() {
        return name;
    }

    public String getModelNumber() {return modelNumber; }

    public String getUrl() {
        return url;
    }

    public ImageUpload(String name, String modelNumber,String url) {
        this.name = name;
        this.modelNumber = modelNumber;
        this.url = url;
    }

    public ImageUpload(){}
}
