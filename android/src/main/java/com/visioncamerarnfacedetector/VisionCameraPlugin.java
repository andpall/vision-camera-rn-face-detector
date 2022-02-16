package com.visioncamerarnfacedetector;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.camera.core.ImageProxy;

import com.facebook.react.bridge.WritableArray;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;
import com.mrousavy.camera.frameprocessor.FrameProcessorPlugin;
import com.visioncamerarnfacedetector.dataprocessor.FaceDataProcessor;
import com.visioncamerarnfacedetector.dataprocessor.FaceDataProcessorImpl;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class VisionCameraPlugin extends FrameProcessorPlugin {
    private static final String LOG_TAG = "FaceDetectorModuleLog";
    private final FaceDataProcessor faceDataProcessor = new FaceDataProcessorImpl();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Object callback(@NotNull ImageProxy image, @NotNull Object[] params) {
        Log.d(LOG_TAG, image.getWidth() + " x " + image.getHeight());
        InputImage inputImage = faceDataProcessor.getInputImageFromImageProxy(image);
        FaceDetectorOptions opts = faceDataProcessor.getDefaultFaceDetectorOptions();
        FaceDetector detector = FaceDetection.getClient(opts);
        List<Face> faceList = faceDataProcessor.getFaceListFromInputImage(detector, inputImage);
        WritableArray faceArray = faceDataProcessor.processFaceListToGetWritableArray(faceList, inputImage);
        return  faceArray;
    }

    VisionCameraPlugin() {
        super("vision_camera_plugin");
    }
}
