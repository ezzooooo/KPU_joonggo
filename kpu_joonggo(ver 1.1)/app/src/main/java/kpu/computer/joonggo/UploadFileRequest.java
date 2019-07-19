package kpu.computer.joonggo;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UploadFileRequest extends AsyncTask<String, String, String> {

    Context context;
    ProgressDialog mProgressDialog;
    String fileName;

    HttpURLConnection conn = null;
    DataOutputStream dos = null;

    String lineEnd = "\r\n";
    String twoHyphens = "--";
    String boundary = "*****";

    int bytesRead, bytesAvailable, bufferSize;
    byte[] buffer;
    int maxBufferSize = 1024;
    File sourceFile;
    int serverResponseCode;
    String TAG = "FileUpload";

    public UploadFileRequest(Context context) {
        this.context = context;
    }

    public void setPath(String uploadFilePath) {
        this.fileName = uploadFilePath;
        this.sourceFile = new File(uploadFilePath);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setTitle("Loading...");
        mProgressDialog.setMessage("Image uploading...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
        if(s.equals("Success")) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    protected String doInBackground(String... strings) {
        if(!sourceFile.isFile()) {

            return null;
        } else {
            String success = "Success";

            try {
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(strings[0]);


                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);


                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"data1\"" + lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes("productImage");
                dos.writeBytes(lineEnd);

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\"; filename=\"" + fileName + "\"" + lineEnd);
                dos.writeBytes(lineEnd);

                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                bytesRead = fileInputStream.read(buffer , 0, bufferSize);

                while (bytesRead >0) {
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }

                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();



                if (serverResponseCode == 200) {

                }

                BufferedReader rd = null;

                rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                String line = null;
                while ((line = rd.readLine()) != null) {

                }

                fileInputStream.close();
                dos.flush();
                dos.close();
            } catch (Exception e) {

            }
            return success;
        }
    }
}
