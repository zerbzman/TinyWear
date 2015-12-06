package com.example.brentzerbe.tinyversenotifier;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    static final String EXTRA_VOICE_REPLY = "extra_voice_reply";
    RemoteInput remoteInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String replyLabel = getResources().getString(R.string.reply_label);
        String[] replyChoices = getResources().getStringArray(R.array.reply_choices);
        remoteInput = new RemoteInput.Builder(EXTRA_VOICE_REPLY)
                .setLabel(replyLabel)
                .setChoices(replyChoices)
                .build();

        setContentView(R.layout.activity_main);
    }

    public void sendNotification(View view){

        // Set the notification ID
        int notificationId = 002;

        // Create the reply intent
        Intent replyIntent = new Intent(this, BogusActivity.class);

        // Create the callback
        PendingIntent replyPendingIntent =
                PendingIntent.getActivity(this, 0, replyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        // Create the reply action and add the remote input
        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(R.drawable.common_full_open_on_phone,
                        getString(R.string.reply_label), replyPendingIntent)
                        .addRemoteInput(remoteInput)
                        .build();

        // Build the notification and add the action via WearableExtender
        Notification notification =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.common_signin_btn_icon_light)
                        .setContentTitle(getString(R.string.title))
                        .setContentText(getString(R.string.content))
                        .extend(new WearableExtender().addAction(action))
                        .build();

        // Create the notification Manager
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);
        // Issue the notification
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(notificationId, notification);


    }
}
