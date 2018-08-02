package minhson.com.fakemessenger.item;

/**
 * Created by Administrator on 14/8/2017.
 */

public class ObjectTutorial {
    private int image;
    private String function;

    public ObjectTutorial(int image, String function) {
        this.image = image;
        this.function = function;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }
}
