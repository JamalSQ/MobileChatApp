// Generated by view binder compiler. Do not edit!
package com.example.jamalchatapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.jamalchatapp.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ChatToolbarBinding implements ViewBinding {
  @NonNull
  private final Toolbar rootView;

  @NonNull
  public final TextView chatName;

  @NonNull
  public final ImageView chatbackScreen;

  @NonNull
  public final Toolbar chattoolbar;

  @NonNull
  public final CircleImageView chattoolbarImage;

  private ChatToolbarBinding(@NonNull Toolbar rootView, @NonNull TextView chatName,
      @NonNull ImageView chatbackScreen, @NonNull Toolbar chattoolbar,
      @NonNull CircleImageView chattoolbarImage) {
    this.rootView = rootView;
    this.chatName = chatName;
    this.chatbackScreen = chatbackScreen;
    this.chattoolbar = chattoolbar;
    this.chattoolbarImage = chattoolbarImage;
  }

  @Override
  @NonNull
  public Toolbar getRoot() {
    return rootView;
  }

  @NonNull
  public static ChatToolbarBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ChatToolbarBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.chat_toolbar, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ChatToolbarBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.chatName;
      TextView chatName = ViewBindings.findChildViewById(rootView, id);
      if (chatName == null) {
        break missingId;
      }

      id = R.id.chatbackScreen;
      ImageView chatbackScreen = ViewBindings.findChildViewById(rootView, id);
      if (chatbackScreen == null) {
        break missingId;
      }

      Toolbar chattoolbar = (Toolbar) rootView;

      id = R.id.chattoolbarImage;
      CircleImageView chattoolbarImage = ViewBindings.findChildViewById(rootView, id);
      if (chattoolbarImage == null) {
        break missingId;
      }

      return new ChatToolbarBinding((Toolbar) rootView, chatName, chatbackScreen, chattoolbar,
          chattoolbarImage);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}