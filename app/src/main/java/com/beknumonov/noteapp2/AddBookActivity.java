package com.beknumonov.noteapp2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.beknumonov.noteapp2.base.BaseActivity;
import com.beknumonov.noteapp2.databinding.ActivityAddBookBinding;
import com.beknumonov.noteapp2.model.Book;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBookActivity extends BaseActivity<ActivityAddBookBinding> {

    private File selectFile;

    @Override
    protected ActivityAddBookBinding inflateViewBinding(LayoutInflater inflater) {
        return ActivityAddBookBinding.inflate(inflater);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2000);
        }


        binding.bookImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "SELECT PHOTOS"), 1000);
            }
        });


        binding.createNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = binding.titleEditText.getText().toString();
                String description = binding.contentEditText.getText().toString();

                if (title.isEmpty())
                    return;
                if (description.isEmpty())
                    return;

                if (selectFile == null)
                    return;


                RequestBody titleRb = RequestBody.create(MultipartBody.FORM, title);
                RequestBody descriptionRb = RequestBody.create(MultipartBody.FORM, description);
                String mediaType = "jpg";
                RequestBody imageRb = RequestBody.create(MediaType.parse(mediaType), selectFile);
                MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", selectFile.getName(), imageRb);

                Call<Book> call = mainApi.createBook( titleRb, descriptionRb, imagePart);

                showLoading();
                call.enqueue(new Callback<Book>() {
                    @Override
                    public void onResponse(Call<Book> call, Response<Book> response) {
                        hideLoading();
                    }

                    @Override
                    public void onFailure(Call<Book> call, Throwable t) {
                        Log.e("error", t.getMessage());
                        hideLoading();
                    }
                });


            }
        });

    }

    private File getPathFromUri(Uri contentUri) {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Cursor returnCursor = getContentResolver().query(contentUri, null, null, null);
            int columnIndex = returnCursor.getColumnIndex(MediaStore.Images.Media.DATA);
            returnCursor.moveToFirst();
            String filePath = returnCursor.getString(columnIndex);
            return new File(filePath);
        }

        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000 && resultCode == RESULT_OK) {
            Uri selectImageUri = data.getData();
            binding.bookImageView.setImageURI(selectImageUri);
            selectFile = getPathFromUri(selectImageUri);
        }
    }
}
