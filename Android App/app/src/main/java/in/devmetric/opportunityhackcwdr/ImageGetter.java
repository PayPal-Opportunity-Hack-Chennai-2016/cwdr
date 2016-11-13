package in.devmetric.opportunityhackcwdr;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.Base64;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageGetter implements Html.ImageGetter {

    public Drawable getDrawable(String source) {
        Log.d("Source", source);
        if (source.startsWith(""))
//        if (!source.startsWith("http")) source = "http://" + source;
        source.replace("data:image/png;base64,", "");
        byte[] item = Base64.decode(source, Base64.DEFAULT);

        Bitmap bmp = BitmapFactory.decodeByteArray(item, 0, item.length);

        Drawable drawable = new BitmapDrawable(bmp);
        return drawable;
    }

    public static Drawable drawableFromUrl(String url) throws IOException {
        Bitmap x;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();

        x = BitmapFactory.decodeStream(input);
        return new BitmapDrawable(x);
    }
};