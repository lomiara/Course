package TypingPractice;

import com.thedeanda.lorem.Lorem;
import javafx.scene.control.TextField;

public class TextProvider {
    private final String type;
    private final int length;
    private final TextField mTextField;
    private final Lorem lorem;

    public static class Builder {
        private final String type;
        private int length = 0;
        private TextField mTextField = null;
        private Lorem lorem = null;

        public Builder(String type) {
            this.type = type;
        }
        public Builder length(int val) {
            length = val;
            return this;
        }
        public Builder TextField(TextField mTextField){
            this.mTextField = mTextField;
            return this;
        }
        public Builder lorem(Lorem lorem) {
            this.lorem = lorem;
            return this;
        }
        public TextProvider build() {
            return new TextProvider(this);
        }
    }

    private TextProvider(Builder builder){
        type = builder.type;
        length = builder.length;
        mTextField = builder.mTextField;
        lorem = builder.lorem;
    }

    public String provideText(){
        String s = "";
        if(type.equals("Random")){
            s = lorem.getWords(length);
        } else if(type.equals("Custom")){
            s = mTextField.getText();
        }
        return s;
    }
}
