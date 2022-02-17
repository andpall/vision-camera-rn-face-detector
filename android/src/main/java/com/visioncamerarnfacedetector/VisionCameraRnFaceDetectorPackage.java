package com.visioncamerarnfacedetector;

import androidx.annotation.NonNull;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.mrousavy.camera.frameprocessor.FrameProcessorPlugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VisionCameraRnFaceDetectorPackage implements ReactPackage {
    @NonNull
    @Override
    public List<NativeModule> createNativeModules(@NonNull ReactApplicationContext reactContext) {
        FrameProcessorPlugin.register(new VisionCameraPlugin());
        List<NativeModule> modules = new ArrayList<>();
        modules.add(new VisionCameraRnFaceDetectorModule(reactContext));
        return modules;
    }

    @NonNull
    @Override
    public List<ViewManager> createViewManagers(@NonNull ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }
}
