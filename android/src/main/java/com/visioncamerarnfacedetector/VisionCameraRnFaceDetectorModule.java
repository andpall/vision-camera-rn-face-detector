package com.visioncamerarnfacedetector;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;
import com.visioncamerarnfacedetector.dataprocessor.FaceDataProcessor;
import com.visioncamerarnfacedetector.dataprocessor.FaceDataProcessorImpl;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@ReactModule(name = VisionCameraRnFaceDetectorModule.NAME)
public class VisionCameraRnFaceDetectorModule extends ReactContextBaseJavaModule {
  public static final String NAME = "VisionCameraRnFaceDetector";
  private static ReactApplicationContext reactContext;
  @SuppressLint("StaticFieldLeak")
  private static Activity activity;
  private static FaceDetector detector;
  private final FaceDataProcessor faceDataProcessor = new FaceDataProcessorImpl();

  public VisionCameraRnFaceDetectorModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
    activity = getCurrentActivity();
    Log.d("FaceDetectorModuleLog", "Context is defined ");
  }

  @Override
  @NonNull
  public String getName() {
    return NAME;
  }

  public static Activity getActivity() {
    return activity;
  }

  public static ReactApplicationContext getContext() {
    return reactContext;
  }

  public static FaceDetector getDetector() {
    return detector;
  }

  @Override
  public Map<String, Object> getConstants() {
    final Map<String, Object> constants = new HashMap<>();
    String android_id = Settings.System.getString(getReactApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    constants.put("uniqueId", android_id);
    return constants;
  }

  @ReactMethod
  public void recognizeFaceFromImage(String source, Promise promise) {
    InputImage image = faceDataProcessor.getInputImageFromPath(source, getReactApplicationContext());
    FaceDetectorOptions opts = faceDataProcessor.getDefaultFaceDetectorOptions();
    detector = FaceDetection.getClient(opts);
    faceDataProcessor.processImageWithPromise(detector, image, promise);

    Log.d("FaceDetectorModuleLog", "source: " + source);
  }
}
