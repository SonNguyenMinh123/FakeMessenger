package minhson.com.fakemessenger.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.slider.AlphaSlider;
import com.flask.colorpicker.slider.LightnessSlider;

import minhson.com.fakemessenger.R;
import minhson.com.fakemessenger.utils.PreferenceUtils;

/**
 * Created by Administrator on 7/6/2017.
 */

public class ChangColorDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private ColorPickerView colorPickerView;
    private getColorPicker colorPicker;
    private Button btChon , btHuy;
    private AlphaSlider alphaSlider;
    private LightnessSlider lightnessSlider;

    public ChangColorDialog(@NonNull Context context , getColorPicker getColorPicker) {
        super(context);
        this.context = context;
        this.colorPicker = getColorPicker;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_color);

        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        setCancelable(true);

        btChon = (Button) findViewById(R.id.bt_chon);
        btChon.setTypeface(Typeface.createFromAsset(context.getAssets() , "fonts/Roboto-Regular.ttf"));
        btHuy= (Button) findViewById(R.id.bt_huy);
        btHuy.setTypeface(Typeface.createFromAsset(context.getAssets() , "fonts/Roboto-Regular.ttf"));

        colorPickerView = (ColorPickerView) findViewById(R.id.color_picker_view);
        alphaSlider = (AlphaSlider) findViewById(R.id.v_alpha_slider);
        lightnessSlider = (LightnessSlider) findViewById(R.id.v_lightness_slider);

        btChon.setOnClickListener(this);
        btHuy.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_chon:
                colorPicker.btnChon(colorPickerView.getSelectedColor());
                PreferenceUtils.save("isClick", true, context);
                dismiss();
                break;
            case R.id.bt_huy:
                dismiss();
                break;
            default:
                break;
        }
    }

    public interface getColorPicker{
        void btnChon(int color);
    }
}
