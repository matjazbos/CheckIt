package com.mbostic;

import android.content.Intent;
import android.net.Uri;
import android.content.Context;
import com.mbostic.game.OpenUrl;
public class OpenUrlAndroid implements OpenUrl {
    Context context;
    OpenUrlAndroid(Context context){
        this.context = context;
    }
    public void openUrl(String url){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(browserIntent);
    }

}
