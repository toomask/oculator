package com.x_mega.oculator.gif;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.x_mega.oculator.motion_picture.MotionPicture;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by toomas on 18.09.2014.
 */
public class GifUtil {

    public static String  encodeGifAndSaveToSdCard(MotionPicture motionPicture) {
        String path = "/sdcard/" + generateFileName();
        saveGifToFile( encodeGif( motionPicture ) , path);
        return path;
    }

    public static String  encodeGifAndSaveToSdCard(List<Bitmap> bitmaps) {
        String path = "/sdcard/" + generateFileName();
        saveGifToFile( encodeGif( bitmaps ) , path);
        return path;
    }

    public static byte[] encodeGif(MotionPicture motionPicture) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        AnimatedGifEncoder encoder = new AnimatedGifEncoder();
        encoder.setRepeat(0);
        encoder.start(bos);
        for (int i = 0; i < motionPicture.getFrameCount() ; i++) {
            encoder.addFrame(motionPicture.getFrame(i));
            encoder.setDelay(motionPicture.getFrameDuration(i));
        }
        encoder.finish();
        return bos.toByteArray();
    }

    public static byte[] encodeGif(List<Bitmap> bitmaps) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        AnimatedGifEncoder encoder = new AnimatedGifEncoder();
        encoder.setDelay(120);
        encoder.start(bos);
        for (Bitmap bitmap : bitmaps) {
            encoder.addFrame(bitmap);
        }
        encoder.finish();
        return bos.toByteArray();
    }

    private static void saveGifToFile(byte[] gif, String path) {
        FileOutputStream outStream = null;
        try{
            outStream = new FileOutputStream(path);
            outStream.write(gif);
            outStream.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static String generateFileName() {
        return "gif_" + System.currentTimeMillis() + ".gif";
    }

    public static List<Bitmap> decodeGif(byte[] data) {
        GifDecoder gifDecoder = new GifDecoder();
        gifDecoder.read(data);
        ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
        for (int i = 0; i < gifDecoder.getFrameCount(); i++) {
            gifDecoder.advance();
            bitmaps.add(colorBlackToAlpha(gifDecoder.getNextFrame()));
        }
        return bitmaps;
    }

    private static Bitmap colorBlackToAlpha(Bitmap nextFrame) {
        Bitmap copy = nextFrame.copy(Bitmap.Config.ARGB_8888, true);
        copy.setHasAlpha(true);
        Bitmap b = copy;
        for(int x = 0; x<b.getWidth(); x++){
            for(int y = 0; y<b.getHeight(); y++){
                if(b.getPixel(x, y) == Color.BLACK){
                    b.setPixel(x, y, Color.TRANSPARENT);
                }
            }
        }
        return b;
    }
}
