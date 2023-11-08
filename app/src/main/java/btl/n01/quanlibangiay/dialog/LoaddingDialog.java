package btl.n01.quanlibangiay.dialog;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import btl.n01.quanlibangiay.R;
import btl.n01.quanlibangiay.base.BaseDialog;

public class LoaddingDialog extends BaseDialog{
    public LoaddingDialog(Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loadding, null);
        setContentView(view);
        ImageView image = view.findViewById(R.id.img_loading);
        ObjectAnimator rotation = ObjectAnimator.ofFloat(image, "rotation", 360f, 0f);
        rotation.setDuration(800);
        rotation.setRepeatCount(ObjectAnimator.INFINITE);
        rotation.start();
        setCancelable(false);
    }
}
