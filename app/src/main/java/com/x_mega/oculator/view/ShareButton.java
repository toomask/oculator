package com.x_mega.oculator.view;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.x_mega.oculator.R;

/**
 * Created by toomas on 17.10.2014.
 */
public class ShareButton extends FrameLayout implements View.OnClickListener {

    public interface IntentExtrasProvider {
        public void addExtrasToIntent(Intent intent);
    }

    ResolveInfo resolveInfo;
    ImageView iconImageView;
    TextView nameTextView;
    IntentExtrasProvider intentExtrasProvider;

    public ShareButton(Context context, ResolveInfo resolveInfo, IntentExtrasProvider intentExtrasProvider) {
        super(context);
        this.resolveInfo = resolveInfo;
        this.intentExtrasProvider = intentExtrasProvider;
        LayoutInflater.from(context).inflate(R.layout.view_share_button, this, true);
        findViewById(R.id.mainLayout).setOnClickListener(this);
        iconImageView = (ImageView) findViewById(R.id.iconImageView);
        nameTextView = (TextView) findViewById(R.id.nameTextView);

        Drawable icon = resolveInfo.loadIcon(context.getPackageManager());
        iconImageView.setImageDrawable(icon);

        String name = resolveInfo.loadLabel(context.getPackageManager()).toString();
        nameTextView.setText(name);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setClassName(resolveInfo.activityInfo.applicationInfo.packageName,
                resolveInfo.activityInfo.name);
        intentExtrasProvider.addExtrasToIntent(intent);
        getContext().startActivity(intent);
    }
}
