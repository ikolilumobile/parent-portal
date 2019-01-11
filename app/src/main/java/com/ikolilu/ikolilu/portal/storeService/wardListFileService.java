package com.ikolilu.ikolilu.portal.storeService;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Genuis on 30/08/2018.
 */

public class wardListFileService {

    public static String read(Context context, String fileName) {
        try {
            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (FileNotFoundException fileNotFound) {
            return null;
        } catch (IOException ioException) {
            return null;
        }
    }

    public static boolean create(Context context, String fileName, String jsonString){
        String FILENAME = "storage.json";
        try {
            // openFileOutput -> Context File
            FileOutputStream fos = context.openFileOutput(fileName,Context.MODE_PRIVATE);
            if (jsonString != null) {
                fos.write(jsonString.getBytes());
            }
            fos.close();
            return true;
        } catch (FileNotFoundException fileNotFound) {
            return false;
        } catch (IOException ioException) {
            return false;
        }
    }


    public static boolean isFilePresent(Context context, String fileName) {
        String path = context.getFilesDir().getAbsolutePath() + "/" + fileName;
        File file = new File(path);
        return file.exists();
    }

    public static boolean isDelete(Context context, String fileName) {
        String path = context.getFilesDir().getAbsolutePath() + "/" + fileName;
        File file = new File(path);
        return file.delete();
    }




//    public static String objectToFile(Object object) throws IOException {
//        String path = Environment.getExternalStorageDirectory() + File.separator + "/AppName/App_cache" + File.separator;
//        File dir = new File(path);
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
//        path += "data";
//        File data = new File(path);
//        if (!data.createNewFile()) {
//            data.delete();
//            data.createNewFile();
//        }
//        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(data));
//        objectOutputStream.writeObject(object);
//        objectOutputStream.close();
//        return path;
//    }
//
//    // path = Environment.getExternalStorageDirectory() + File.separator + "/AppName/App_cache/data" + File.separator;
//    public static Object objectFromFile(String path) throws IOException, ClassNotFoundException {
//        Object object = null;
//        File data = new File(path);
//        if(data.exists()) {
//            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(data));
//            object = objectInputStream.readObject();
//            objectInputStream.close();
//        }
//
//        // Log -> object
//        Log.i("object-data", object + "");
//        return object;
//    }
}

