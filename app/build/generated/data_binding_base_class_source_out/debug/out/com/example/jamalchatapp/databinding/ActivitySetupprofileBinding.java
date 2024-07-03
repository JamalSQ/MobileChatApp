// Generated by view binder compiler. Do not edit!
package com.example.jamalchatapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.jamalchatapp.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivitySetupprofileBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ConstraintLayout main;

  @NonNull
  public final CircleImageView profilePic;

  @NonNull
  public final Button profilebtn;

  @NonNull
  public final EditText updateName;

  @NonNull
  public final TextView userName;

  private ActivitySetupprofileBinding(@NonNull ConstraintLayout rootView,
      @NonNull ConstraintLayout main, @NonNull CircleImageView profilePic,
      @NonNull Button profilebtn, @NonNull EditText updateName, @NonNull TextView userName) {
    this.rootView = rootView;
    this.main = main;
    this.profilePic = profilePic;
    this.profilebtn = profilebtn;
    this.updateName = updateName;
    this.userName = userName;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivitySetupprofileBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivitySetupprofileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_setupprofile, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivitySetupprofileBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      ConstraintLayout main = (ConstraintLayout) rootView;

      id = R.id.profilePic;
      CircleImageView profilePic = ViewBindings.findChildViewById(rootView, id);
      if (profilePic == null) {
        break missingId;
      }

      id = R.id.profilebtn;
      Button profilebtn = ViewBindings.findChildViewById(rootView, id);
      if (profilebtn == null) {
        break missingId;
      }

      id = R.id.updateName;
      EditText updateName = ViewBindings.findChildViewById(rootView, id);
      if (updateName == null) {
        break missingId;
      }

      id = R.id.userName;
      TextView userName = ViewBindings.findChildViewById(rootView, id);
      if (userName == null) {
        break missingId;
      }

      return new ActivitySetupprofileBinding((ConstraintLayout) rootView, main, profilePic,
          profilebtn, updateName, userName);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
