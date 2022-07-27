package com.example.findfun;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;

public class SettingsFragment extends Fragment {

    Button btncapture;
    Button btnSubmit;
    ImageView ivImage;
    private File photoFile;
    public String photoFileName = "photo.jpg";
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 396;
    public static final String TAG = "SettingsFragment";

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onViewCreated(view, savedInstanceState);
        btncapture = view.findViewById(R.id.btnCapture);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        ivImage = view.findViewById(R.id.ivImage);

        btncapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCamera();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (photoFile == null || ivImage.getDrawable() == null) {
                    Toast.makeText(getContext(), "Photo cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                savePhoto(photoFile);
            }
        });
    }
    private void launchCamera() {
        //create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //create a File reference for future access
        photoFile = getPhotoFileUri(photoFileName);

        //wrap File object into a content provider
        //required for API >= 24
        Uri fileProvider = FileProvider.getUriForFile(requireContext(), "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        //if you call startActivityForResult() using an intent that no app can handle, your app will crash.
        //so as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            //start the image capture intent to take photo
            //noinspection deprecation
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                //by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                //RESIZE BITMAP, see section below
                //load the taken image into a preview
                ivImage.setImageBitmap(takenImage);
            } else { // Result was a failure
                Toast.makeText(getContext(), "Error taking picture!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private File getPhotoFileUri(String photoFileName) {
        //get safe storage directory for photos
        //use `getExternalFilesDir` on Context to access package-specific directories.
        //this way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        //create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) { Log.d(TAG, "Failed to create directory"); }
        //return the file target for the photo based on filename
        return new File(mediaStorageDir.getPath() + File.separator + photoFileName);
    }

    private void savePhoto(File photoFile) {
        Log.i(TAG, "Saved Photo");
        ivImage.setImageResource(R.drawable.camera1);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        inflater.inflate(R.menu.menu_sort, menu);
        menu.findItem(R.id.DateRO).setVisible(false);
        menu.findItem(R.id.DateOR).setVisible(false);
//        menu.findItem(R.id.Ascen).setVisible(false);
//        menu.findItem(R.id.Descen).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menulogoutbtn) {
            ParseUser.logOut();
            ParseUser currentUser = ParseUser.getCurrentUser();
            Intent i = new Intent(getContext(), LoginActivity.class);
            startActivity(i);
            return true;
        }
        if (item.getItemId() == R.id.menubackbtn) {
            Intent i = new Intent(getContext(), CityTypeActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}