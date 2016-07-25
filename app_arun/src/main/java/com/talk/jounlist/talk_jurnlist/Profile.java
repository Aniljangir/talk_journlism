package com.talk.jounlist.talk_jurnlist;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.talk.jounlist.talk_jurnlist.utils.Logger;
import com.talk.jounlist.talk_jurnlist.utils.LruBitmapCache;
import com.talk.jounlist.talk_jurnlist.utils.ProgressHUD;
import com.talk.jounlist.talk_jurnlist.utils.StaticData;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import eu.janmuller.android.simplecropimage.CropImage;

/**
 * Created by Juned on 25-Jun-16.
 */
public class Profile extends Activity
{
    //for image
    public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";

    public static final int REQUEST_CODE_GALLERY      = 0x1;
    public static final int REQUEST_CODE_TAKE_PICTURE = 0x2;
    public static final int REQUEST_CODE_CROP_IMAGE   = 0x3;
    public static final String TAG ="Profile";
    ImageView roundedImageView1;
    String outputFile="";
    private File mFileTemp;
    ProgressHUD mProgressHUD;

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    String username , userId , email , phone , dob;
    EditText etName ,etUserId,etMail,etDob , etPhone;
    RelativeLayout submit;
    TextView textView2;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        ImageLoader imageLoader = getImageLoader();

        roundedImageView1=(ImageView) findViewById(R.id.roundedImageView1);
        etName = (EditText)findViewById(R.id.etName);
        etUserId = (EditText)findViewById(R.id.etUserId);
        etMail = (EditText)findViewById(R.id.etMail);
        etDob = (EditText)findViewById(R.id.etDob);
        etPhone = (EditText)findViewById(R.id.etPhone);
        textView2 =(TextView) findViewById(R.id.textView2);
        textView2.setText(StaticData.pref(Profile.this).getString(StaticData.PREFKEY_USER_NAME, "hell"));

        etName.setText(StaticData.pref(Profile.this).getString(StaticData.PREFKEY_USER_NAME, "hell"));
        imageLoader.get(StaticData.pref(getApplicationContext()).getString(StaticData.PREFKEY_USER_PIC, ""),imageLoader.getImageListener(roundedImageView1, R.mipmap.user, R.mipmap.user), 100, 80);
        String  s="Reg ID "+StaticData.pref(Profile.this).getString(StaticData.PREFKEY_USER_ID, "hell");
        etUserId.setText(s);
        etMail.setText(StaticData.pref(Profile.this).getString(StaticData.PREFKEY_USER_EMAIL_ID, "hell"));
        etPhone.setText(StaticData.pref(Profile.this).getString(StaticData.PREFKEY_USER_PHONE, ""));
        String dob1=StaticData.pref(Profile.this).getString(StaticData.PREFKEY_USER_DOB, "hell");

        if (dob1==null || dob1.equals(null) || dob1.equalsIgnoreCase("null"))
        {
            dob1="";
        }
        etDob.setText(dob1);

        submit = (RelativeLayout)findViewById(R.id.submit);

        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state))
        {
            mFileTemp = new File(Environment.getExternalStorageDirectory(), TEMP_PHOTO_FILE_NAME);
        }
        else
        {
            mFileTemp = new File(Profile.this.getFilesDir(), TEMP_PHOTO_FILE_NAME);
        }

        submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               /* if(etName.getText().toString().equals(null) || etName.getText().toString().equals(""))
                {
                    Toast.makeText(Profile.this , "Please Enter Name",Toast.LENGTH_SHORT).show();
                }
                else if(etUserId.getText().toString().equals(null) || etUserId.getText().toString().equals(""))
                {
                    Toast.makeText(Profile.this , "Please Enter UserId",Toast.LENGTH_SHORT).show();
                }
                else if(etMail.getText().toString().equals(null) || etMail.getText().toString().equals(""))
                {
                    //Toast.makeText(Profile.this , "Please Enter Mail Address",Toast.LENGTH_SHORT).show();
                    email = etMail.getText().toString();
                }

                else if(etDob.getText().toString().equals(null) || etDob.getText().toString().equals(""))
                {
                    //Toast.makeText(Profile.this , "Please Enter Date Of Birth",Toast.LENGTH_SHORT).show();
                    dob = etDob.getText().toString();
                }*/
                if(etPhone.getText().toString().equals(null) || etPhone.getText().toString().equals(""))
                {
                    phone = etPhone.getText().toString();
                    Toast.makeText(Profile.this , "Please enter mobile number",Toast.LENGTH_SHORT).show();


                }
                else
                {


                    email = etMail.getText().toString();
                    dob = etDob.getText().toString();
                    phone = etPhone.getText().toString();

                    //Toast.makeText(Profile.this , "All Filled",Toast.LENGTH_SHORT).show();
                    new UpdateProfile().execute();
                }
            }
        });

        roundedImageView1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                pickImage();
            }
        });

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        333);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    private void pickImage()
    {

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(
                Profile.this);
        //	builderSingle.setIcon(R.drawable.ic_launcher);
        builderSingle.setTitle("Select Image From:");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                Profile.this,
                android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Camera");
        arrayAdapter.add("Gallery");



        builderSingle.setAdapter(arrayAdapter,
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // String strName = arrayAdapter.getItem(which);
                        if(which==1)
                        {
                            openGallery();
                        }
                        else
                        {
                            takePicture();	//startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }

                    }
                });
        builderSingle.show();

    }

    private void takePicture() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try
        {
            Uri mImageCaptureUri = null;
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                mImageCaptureUri = Uri.fromFile(mFileTemp);
            }
            else {
				/*
				 * The solution is taken from here: http://stackoverflow.com/questions/10042695/how-to-get-camera-result-as-a-uri-in-data-folder
				 */
                mImageCaptureUri = InternalStorageContentProvider.CONTENT_URI;
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
            intent.putExtra("return-data", true);
            startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
        }
        catch (ActivityNotFoundException e)
        {
        }
    }
    private void openGallery()
    {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQUEST_CODE_GALLERY);
    }

    private void startCropImage() {

        Intent intent = new Intent(Profile.this, CropImage.class);
        intent.putExtra(CropImage.IMAGE_PATH, mFileTemp.getPath());
        intent.putExtra(CropImage.SCALE, true);

        intent.putExtra(CropImage.ASPECT_X, 2);
        intent.putExtra(CropImage.ASPECT_Y, 2);

        startActivityForResult(intent, REQUEST_CODE_CROP_IMAGE);
    }

    class UpdateProfile extends AsyncTask<Void, Void, String>
    {
        ProgressDialog pd;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            mProgressHUD = ProgressHUD.show(Profile.this, "", true, true,
                    null);
        }

        @Override
        protected String doInBackground(Void... params)
        {
           /* Static_Data.SERVER_URL = "http://www.yeorder.com/adyep/rest/users/updateProfile";
            RestClient client = null;
            client = new RestClient();
            client.AddParam("username" ,username );
            client.AddParam("userid" ,userId );
            client.AddParam("email" , email);

            try
            {
                client.Execute(1);
                Logger1.e("response" , client.getResponse());
                return client.getResponse();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return "error";
            }*/

            final List<NameValuePair> nvp=new ArrayList<NameValuePair>();

            String url= StaticData.SERVER_URL = "http://www.yeorder.com/adyep/rest/users/updateProfile";

            HttpClient client=new DefaultHttpClient();
            HttpPost request=new HttpPost(url);

            MultipartEntity reqEntity = new MultipartEntity();
            StringBuilder builder =new StringBuilder();

            builder.append(url);

            try
            {
                reqEntity.addPart("action", new StringBody("updateUserProfile"));
                builder.append("action="+"updateUserProfile"+"&");


                reqEntity.addPart("userid", new StringBody(StaticData.pref(Profile.this).getString(StaticData.PREFKEY_USER_ID, "hell")));
                builder.append("userid="+StaticData.pref(Profile.this).getString(StaticData.PREFKEY_USER_ID, "hell")+"&");




                reqEntity.addPart("dob", new StringBody(dob));
                builder.append("dob="+dob+"&");


                reqEntity.addPart("mobile", new StringBody(phone));
                builder.append("mobile="+phone+"&");


                if (!outputFile.equals(""))
                {
                    File f=new File(outputFile);

                    String mime=getMimeType(outputFile);
                    FileBody body=new FileBody(f,mime);
                    reqEntity.addPart("profile_pic", body);
                    builder.append("profile_pic="+body+"&");
                }

            }
            catch (Exception e2)
            {
                // TODO Auto-generated catch block
                e2.printStackTrace();
            }

            request.setEntity(reqEntity);
            String valsss=builder.toString();

            Logger.e("hi", valsss);
            HttpResponse re;
            try
            {
                re = client.execute(request);
                Logger.e("uuuu", request.toString());
                String responseDataFromUrl= EntityUtils.toString(re.getEntity());
                Logger.e("post_res", responseDataFromUrl);
                return responseDataFromUrl;
            }
            catch (Exception e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "Error";
            }
        }

        @Override
        protected void onPostExecute(String aVoid)
        {
            super.onPostExecute(aVoid);
            if (null != mProgressHUD && mProgressHUD.isShowing()) {

                mProgressHUD.dismiss();

            }
            Logger.e("Data responce" , aVoid);

            try {
                JSONObject obj = new JSONObject(aVoid);
                //String message = obj.get("message").toString();

                //Toast.makeText(Login.this, obj.get("message")+"", Toast.LENGTH_SHORT).show();

                if (obj.optString("status").equals("true")) {
                    Toast.makeText(Profile.this, obj.get("message")+"", Toast.LENGTH_SHORT).show();

                    SharedPreferences pref = StaticData.pref(Profile.this);

                    SharedPreferences.Editor e = pref.edit();


                  //  e.putString(StaticData.PREFKEY_USER_EMAIL_ID, obj.optString("email"));
                    e.putString(StaticData.PREFKEY_USER_DOB, obj.optString("dob"));
                    e.putString(StaticData.PREFKEY_USER_PIC, obj.optString("profile_pic"));
                    //e.putString(static_data.STORE_CAT_ID, value)
                    e.putString(StaticData.PREFKEY_USER_PHONE, obj.optString("mobile"));
                    //
                    e.commit();


                } else {
                    //showToast(SignUp.this, obj.optString("message"));
                      Toast.makeText(Profile.this, obj.get("message")+"", Toast.LENGTH_SHORT).show();
                    //Staticutils.showToast(Login.this,obj.get("message")+"");

                }


            }catch (JSONException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public String getMimeType(String url)
    {
        String extension = url.substring(url.lastIndexOf("."));
        String mimeTypeMap = MimeTypeMap.getFileExtensionFromUrl(extension);
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                mimeTypeMap);
        return mimeType;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK)
        {
            return;
        }


        Bitmap bitmap;

        switch (requestCode) {

            case REQUEST_CODE_GALLERY:

                try {

                    InputStream inputStream = Profile.this.getContentResolver().openInputStream(data.getData());
                    FileOutputStream fileOutputStream = new FileOutputStream(mFileTemp);
                    copyStream(inputStream, fileOutputStream);
                    fileOutputStream.close();
                    inputStream.close();

                    startCropImage();

                } catch (Exception e) {


                }

                break;
            case REQUEST_CODE_TAKE_PICTURE:

                startCropImage();
                break;
            case REQUEST_CODE_CROP_IMAGE:

                String path = data.getStringExtra(CropImage.IMAGE_PATH);
                if (path == null) {

                    return;
                }

                bitmap = BitmapFactory.decodeFile(mFileTemp.getPath());

                outputFile=mFileTemp.getPath();
                roundedImageView1.setImageBitmap(bitmap);

                break;
        }
    }

    public static void copyStream(InputStream input, OutputStream output)
            throws IOException
    {

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }
    ///


    //
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
    //
}