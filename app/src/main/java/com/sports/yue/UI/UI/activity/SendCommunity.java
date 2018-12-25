package com.sports.yue.UI.UI.activity;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.MediaStore.Video;
import android.provider.SyncStateContract;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.sports.yue.R;
import com.sports.yue.UI.UI.Data.locationData;
import com.sports.yue.UI.UI.Database_operation.Db_operation;
import com.sports.yue.UI.UI.api.BmobService;
import com.sports.yue.UI.UI.api.Client;
import com.sports.yue.UI.UI.api.MyHttpServer;
import com.sports.yue.UI.UI.fragment.CommunityFragment;
import com.sports.yue.UI.UI.fragment.HomeFragment;
import com.sports.yue.UI.UI.local_db.DbManager;
import com.sports.yue.UI.UI.models.Community;
import com.sports.yue.UI.UI.models.CurrentUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.logging.Handler;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

public class SendCommunity extends AppCompatActivity {

    private TextView etContent;
    private android.os.Handler mHandler = new android.os.Handler(){
      @Override
      public void handleMessage(Message msg){
          if(msg.what == 0){
              location.setText((String)msg.obj);
          }
      }
    };
    private ImageView addpic;
    private ImageView getlocation;
    private TextView location;
    private TextView send;
    private Bitmap bitmap;
    private String path;
    private String pictureUrl;
    private static final int CHOOSE_PHOTO = 603;
    private static final int CHOOSE_VIDEO = 598;
    private static int max = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_community);


        etContent= (TextView) findViewById(R.id.community_add_message);
        location = findViewById(R.id.tv);
        addpic = findViewById(R.id.community_add_pic);
        addpic.setDrawingCacheEnabled(true);
        addpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击添加图片，完成之后将图片放上去
                addpic.setDrawingCacheEnabled(true);
                openAbulm();
            }
        });


        getlocation = findViewById(R.id.get_community_location);
        getlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击获取当前定位，并再location上显示
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        locationData location = new locationData();
                        while(locationData.location == null);
                        location.StopLocation();
                        Message msg = new Message();
                        msg.what = 0;
                        msg.obj = locationData.location;
                        mHandler.sendMessage(msg);
                    }
                }).start();
            }
        });

        send = findViewById(R.id.community_send_button);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //发送Community

                Bitmap obmp = Bitmap.createBitmap(addpic.getDrawingCache());
                addpic.setDrawingCacheEnabled(false);

                Community community = new Community();
                community.setUserName(CurrentUser.getInstance(getApplicationContext()).getUserName());
                community.setEmail(etContent.getText().toString());
                community.setRoomId("none");
                community.setVideo("video" + max + ".mp4");

                community.setLikes(0);

                max++;
                if (max >=2){
                    Toast.makeText(getApplicationContext(),"Send Failed",Toast.LENGTH_LONG).show();
                    finish();
                }
                Db_operation.getDb_op().add(community);
                DbManager.getDbManager().insert(community,null);

                finish();
            }
        });

    }

    private String put(int methodType){
        String url = "";

        File file = new File(path);
        if(file.exists()) {

            if( methodType == 1)
            new Thread(new Runnable() {
                @Override
                public void run() {
                    MyHttpServer myHttpServer = new MyHttpServer(file);
                    myHttpServer.uploadVideoToServer();
                }
            }).start();

            else if( methodType == 2){
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

                BmobService service = Client.retrofit.create(BmobService.class);
                Call<ResponseBody> call = service.upLoadVideo(requestBody);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String rep = response.body().string();
                            JSONObject obj = new JSONObject(rep);
                            String videoURL = obj.getString("url");



                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        System.out.print(2);
                    }
                });
            }
        }
        return url;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    path = "";
                    path = getPath(getApplicationContext(), data.getData());
                    bitmap = getBitmap(path);
                    addpic.setImageBitmap(bitmap);
                }
                break;

            case CHOOSE_VIDEO:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    path = "";
                    path = getPath(getApplicationContext(), data.getData());
                    bitmap = getVideoThumbnail(path);
                    addpic.setImageBitmap(bitmap);
                    put(1);
//                    Cursor cursor1 = getContentResolver().query(uri, new String[]{MediaStore.Video.Media.DISPLAY_NAME,
//                            MediaStore.Video.Media.SIZE, MediaStore.Video.Media.DURATION, MediaStore.Video.Media.DATA}, null, null, null);
//                    cursor1.moveToFirst();
//
//                    if (null != cursor1) {
//                        cursor1.getString(0);//获取视频的名字
//                        cursor1.getLong(1);//获取视频的大小
//                        cursor1.getLong(3);//获取视频的时长
//                        bitmap = getVideoThumbnail(cursor1.getString(4));//获取视频的地址 ，顺序是和上面的对应的
//                        addpic.setImageBitmap(bitmap);
//                    }
//                    cursor1.close();
                    break;
                }
            default:
                break;
        }
    }

    /**
     * 打开相册
     */
    public void openAbulm() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
//        intent.setType("*/*");
        intent.setType("video/*");
        startActivityForResult(intent, CHOOSE_VIDEO);
    }

    /**
     * 获取小尺寸的照片
     * @param filePath 压缩照片的路径
     * @return 返回压缩处理后的照片
     */
    public static Bitmap getBitmap(String filePath) {

        Bitmap bitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = Math.max(1, (int) Math.ceil(Math.max(
                (double) options.outWidth / 1024f,
                (double) options.outHeight / 1024f)));
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(filePath, options);

        return bitmap;
    }



    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // 文件提供
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // 外部存储设备提供
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }
                // TODO handle non-primary volumes
            }
            // 下载文件提供
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }

            // 媒体提供
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = MediaStore.MediaColumns._ID + "=?";
                final String[] selectionArgs = new String[] { split[1] };
                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri . This is useful for
     * MediaStore Uris , and other file - based ContentProviders.
     *
     * @param context
     *            The context.
     * @param uri
     *            The Uri to query.
     * @param selection
     *            (Optional) Filter used in the query.
     * @param selectionArgs
     *            (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = MediaStore.MediaColumns.DATA;
        final String[] projection = { column };
        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri
                .getAuthority());
    }



    /**

     * 获取视频的缩略图

     * 先通过ThumbnailUtils来创建一个视频的缩略图，然后再利用ThumbnailUtils来生成指定大小的缩略图。

     * 如果想要的缩略图的宽和高都小于MICRO_KIND，则类型要使用MICRO_KIND作为kind的值，这样会节省内存。

     * @param videoPath 视频的路径

     * @param width 指定输出视频缩略图的宽度

     * @param height 指定输出视频缩略图的高度度

     * @param kind 参照MediaStore.Images.Thumbnails类中的常量MINI_KIND和MICRO_KIND。

     *            其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96

     * @return 指定大小的视频缩略图

     */



    private Bitmap getVideoThumbnail(String videoPath, int width, int height,

                                      int kind) {

        Bitmap bitmap = null;

        // 获取视频的缩略图

        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);





        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,

                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);

        return bitmap;

    }

    private Bitmap getVideoThumbnail(String videoPath) {

        // 获取视频的缩略图
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(videoPath);
        return  media.getFrameAtTime();
    }


    /**
     * 根据指定的图像路径和大小来获取缩略图
     * 此方法有两点好处：
     *     1. 使用较小的内存空间，第一次获取的bitmap实际上为null，只是为了读取宽度和高度，
     *        第二次读取的bitmap是根据比例压缩过的图像，第三次读取的bitmap是所要的缩略图。
     *     2. 缩略图对于原图像来讲没有拉伸，这里使用了2.2版本的新工具ThumbnailUtils，使
     *        用这个工具生成的图像不会被拉伸。
     * @param imagePath 图像的路径
     * @param width 指定输出图像的宽度
     * @param height 指定输出图像的高度
     * @return 生成的缩略图
     */

    private Bitmap getImageThumbnail(String imagePath, int width, int height) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 获取这个图片的宽和高，注意此处的bitmap为null
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        options.inJustDecodeBounds = false; // 设为 false
        // 计算缩放比
        int h = options.outHeight;
        int w = options.outWidth;
        int beWidth = w / width;
        int beHeight = h / height;
        int be = 1;
        if (beWidth < beHeight) {
            be = beWidth;
        } else {
            be = beHeight;
        }
        if (be <= 0) {
            be = 1;
        }

        options.inSampleSize = be;
        // 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        // 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

}