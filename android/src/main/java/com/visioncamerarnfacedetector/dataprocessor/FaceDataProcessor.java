package com.visioncamerarnfacedetector.dataprocessor;

import android.graphics.PointF;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.camera.core.ImageProxy;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;

import java.util.List;

public interface FaceDataProcessor {
    WritableMap getWritableMapFromRect(Face face);

    WritableArray processFaceListToGetWritableArray(List<Face> faces, InputImage image);

    WritableArray parseFaceListToWritableArray(List<Face> faces);

    WritableArray getArrayFromPointList(List<PointF> points);

    WritableMap getWritableMapFromPointF(PointF point);

    WritableMap getWritableMapFromRotation(Face face);

    FaceDetectorOptions getDefaultFaceDetectorOptions();

    FaceDetectorOptions getRealTimeFaceDetectorOptions();

    InputImage getInputImageFromImageProxy(ImageProxy imageProxy);

    InputImage getInputImageFromPath(String source, ReactContext context);

    void processImageWithPromise(FaceDetector detector, InputImage image, Promise promise);

    @RequiresApi(api = Build.VERSION_CODES.N)
    List<Face> getFaceListFromInputImage(FaceDetector detector, InputImage image);
}
