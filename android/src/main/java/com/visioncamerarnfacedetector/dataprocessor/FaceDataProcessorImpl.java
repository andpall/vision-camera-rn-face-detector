package com.visioncamerarnfacedetector.dataprocessor;

import android.annotation.SuppressLint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Surface;

import androidx.annotation.RequiresApi;
import androidx.camera.core.ImageProxy;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceContour;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;
import com.google.mlkit.vision.face.FaceLandmark;

import java.io.IOException;
import java.util.List;

public class FaceDataProcessorImpl implements FaceDataProcessor {
    private static final String BOUNDING_BOX = "bounds";
    private static final String TRACKING_ID = "trackingId";
    private static final String RIGHT_EYE_OPEN_PROBABILITY = "rightEyeOpenProbability";
    private static final String LEFT_EYE_OPEN_PROBABILITY = "leftEyeOpenProbability";
    private static final String SMILE_PROBABILITY = "smilingProbability";
    private static final String ROTATION = "rotation";
    private static final String LEFT_EAR = "leftEar";
    private static final String RIGHT_EAR = "rightEar";
    private static final String LEFT_CHEEK = "leftCheek";
    private static final String RIGHT_CHEEK = "rightCheek";
    private static final String NOSE_BASE = "noseBase";
    private static final String LEFT_EYE = "leftEye";
    private static final String RIGHT_EYE = "rightEye";
    private static final String MOUTH_BOTTOM = "mouthBottom";
    private static final String MOUTH_LEFT = "mouthLeft";
    private static final String MOUTH_RIGHT = "mouthRight";
    private static final String FACE_CONTOUR = "faceContour";
    private static final String LEFT_EYE_CONTOUR = "leftEyeContour";
    private static final String RIGHT_EYE_CONTOUR = "rightEyeContour";
    private static final String LEFT_CHEEK_CONTOUR = "leftCheekContour";
    private static final String RIGHT_CHEEK_CONTOUR = "rightCheekContour";
    private static final String NOSE_BRIDGE_CONTOUR = "noseBridgeContour";
    private static final String NOSE_BOTTOM_CONTOUR = "noseBottomContour";
    private static final String UPPER_LIP_TOP_CONTOUR = "upperLipTopContour";
    private static final String UPPER_LIP_BOTTOM_CONTOUR = "upperLipBottomContour";
    private static final String LOWER_LIP_BOTTOM_CONTOUR = "lowerLipBottomContour";
    private static final String LOWER_LIP_TOP_CONTOUR = "lowerLipTopContour";
    private static final String LEFT_EYEBROW_TOP_CONTOUR = "leftEyebrowTopContour";
    private static final String LEFT_EYEBROW_BOTTOM_CONTOUR = "leftEyebrowBottomContour";
    private static final String RIGHT_EYEBROW_TOP_CONTOUR = "rightEyebrowTopContour";
    private static final String RIGHT_EYEBROW_BOTTOM_CONTOUR = "rightEyebrowBottomContour";

    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();

    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 0);
        ORIENTATIONS.append(Surface.ROTATION_90, 90);
        ORIENTATIONS.append(Surface.ROTATION_180, 180);
        ORIENTATIONS.append(Surface.ROTATION_270, 270);
    }

    @Override
    public WritableMap getWritableMapFromRect(Face face) {
        Rect rect = face.getBoundingBox();
        WritableMap rectObject = Arguments.createMap();

        rectObject.putInt("left", rect.left);
        rectObject.putInt("top", rect.top);
        rectObject.putInt("width", rect.right - rect.left);
        rectObject.putInt("height", rect.bottom - rect.top);

        return rectObject;
    }

    @Override
    public WritableMap getWritableMapFromRotation(Face face) {
        WritableMap rotationObject = Arguments.createMap();

        rotationObject.putDouble("eulerX", face.getHeadEulerAngleZ());
        rotationObject.putDouble("eulerY", face.getHeadEulerAngleY());
        rotationObject.putDouble("eulerZ", face.getHeadEulerAngleZ());

        return rotationObject;
    }

    @Override
    public WritableMap getWritableMapFromPointF(PointF point) {
        WritableMap mapObject = Arguments.createMap();

        mapObject.putDouble("X", point.x);
        mapObject.putDouble("Y", point.y);

        return mapObject;
    }

    @Override
    public WritableArray getArrayFromPointList(List<PointF> points) {
        WritableArray writablePointsArray = Arguments.createArray();
        for (PointF point : points) {
            writablePointsArray.pushMap(getWritableMapFromPointF(point));
        }
        return writablePointsArray;
    }

    @Override
    public WritableArray parseFaceListToWritableArray(List<Face> faces) {
        WritableArray faceArray = Arguments.createArray();
        for (Face face : faces) {
            WritableMap faceMap = Arguments.createMap();
            faceMap.putMap(BOUNDING_BOX, getWritableMapFromRect(face));
            faceMap.putMap(ROTATION, getWritableMapFromRotation(face));

            FaceLandmark leftEar = face.getLandmark(FaceLandmark.LEFT_EAR);
            if (leftEar != null) {
                faceMap.putMap(LEFT_EAR, getWritableMapFromPointF(leftEar.getPosition()));
            }
            FaceLandmark rightEar = face.getLandmark(FaceLandmark.RIGHT_EAR);
            if (rightEar != null) {
                faceMap.putMap(RIGHT_EAR, getWritableMapFromPointF(rightEar.getPosition()));
            }
            FaceLandmark leftCheek = face.getLandmark(FaceLandmark.LEFT_CHEEK);
            if (leftCheek != null) {
                faceMap.putMap(LEFT_CHEEK, getWritableMapFromPointF(leftCheek.getPosition()));
            }
            FaceLandmark rightCheek = face.getLandmark(FaceLandmark.RIGHT_CHEEK);
            if (rightCheek != null) {
                faceMap.putMap(RIGHT_CHEEK, getWritableMapFromPointF(rightCheek.getPosition()));
            }
            FaceLandmark noseBase = face.getLandmark(FaceLandmark.NOSE_BASE);
            if (noseBase != null) {
                faceMap.putMap(NOSE_BASE, getWritableMapFromPointF(noseBase.getPosition()));
            }
            FaceLandmark leftEye = face.getLandmark(FaceLandmark.LEFT_EYE);
            if (leftEye != null) {
                faceMap.putMap(LEFT_EYE, getWritableMapFromPointF(leftEye.getPosition()));
            }
            FaceLandmark rightEye = face.getLandmark(FaceLandmark.RIGHT_EYE);
            if (rightEye != null) {
                faceMap.putMap(RIGHT_EYE, getWritableMapFromPointF(rightEye.getPosition()));
            }
            FaceLandmark mouthBottom = face.getLandmark(FaceLandmark.MOUTH_BOTTOM);
            FaceLandmark mouthLeft = face.getLandmark(FaceLandmark.MOUTH_LEFT);
            FaceLandmark mouthRight = face.getLandmark(FaceLandmark.MOUTH_RIGHT);
            if (mouthBottom != null) {
                faceMap.putMap(MOUTH_BOTTOM, getWritableMapFromPointF(mouthBottom.getPosition()));
                faceMap.putMap(MOUTH_LEFT, getWritableMapFromPointF(mouthLeft.getPosition()));
                faceMap.putMap(MOUTH_RIGHT, getWritableMapFromPointF(mouthRight.getPosition()));
            }

            faceMap.putArray(FACE_CONTOUR, getArrayFromPointList(face.getContour(FaceContour.FACE).getPoints()));

            faceMap.putArray(LEFT_EYEBROW_TOP_CONTOUR, getArrayFromPointList(face.getContour(FaceContour.LEFT_EYEBROW_TOP).getPoints()));
            faceMap.putArray(LEFT_EYEBROW_BOTTOM_CONTOUR, getArrayFromPointList(face.getContour(FaceContour.LEFT_EYEBROW_BOTTOM).getPoints()));
            faceMap.putArray(RIGHT_EYEBROW_TOP_CONTOUR, getArrayFromPointList(face.getContour(FaceContour.RIGHT_EYEBROW_TOP).getPoints()));
            faceMap.putArray(RIGHT_EYEBROW_BOTTOM_CONTOUR, getArrayFromPointList(face.getContour(FaceContour.RIGHT_EYEBROW_BOTTOM).getPoints()));

            faceMap.putArray(LEFT_EYE_CONTOUR, getArrayFromPointList(face.getContour(FaceContour.LEFT_EYE).getPoints()));
            faceMap.putArray(RIGHT_EYE_CONTOUR, getArrayFromPointList(face.getContour(FaceContour.RIGHT_EYE).getPoints()));
            faceMap.putArray(NOSE_BRIDGE_CONTOUR, getArrayFromPointList(face.getContour(FaceContour.NOSE_BRIDGE).getPoints()));
            faceMap.putArray(NOSE_BOTTOM_CONTOUR, getArrayFromPointList(face.getContour(FaceContour.NOSE_BOTTOM).getPoints()));
            faceMap.putArray(LEFT_CHEEK_CONTOUR, getArrayFromPointList(face.getContour(FaceContour.LEFT_CHEEK).getPoints()));
            faceMap.putArray(RIGHT_CHEEK_CONTOUR, getArrayFromPointList(face.getContour(FaceContour.RIGHT_CHEEK).getPoints()));

            faceMap.putArray(UPPER_LIP_TOP_CONTOUR, getArrayFromPointList(face.getContour(FaceContour.UPPER_LIP_TOP).getPoints()));
            faceMap.putArray(UPPER_LIP_BOTTOM_CONTOUR, getArrayFromPointList(face.getContour(FaceContour.UPPER_LIP_BOTTOM).getPoints()));
            faceMap.putArray(LOWER_LIP_TOP_CONTOUR, getArrayFromPointList(face.getContour(FaceContour.LOWER_LIP_TOP).getPoints()));
            faceMap.putArray(LOWER_LIP_BOTTOM_CONTOUR, getArrayFromPointList(face.getContour(FaceContour.LOWER_LIP_BOTTOM).getPoints()));

            if (face.getSmilingProbability() != null) {
                faceMap.putDouble(SMILE_PROBABILITY, face.getSmilingProbability());
            }
            if (face.getLeftEyeOpenProbability() != null) {
                faceMap.putDouble(LEFT_EYE_OPEN_PROBABILITY, face.getLeftEyeOpenProbability());
            }
            if (face.getRightEyeOpenProbability() != null) {
                faceMap.putDouble(RIGHT_EYE_OPEN_PROBABILITY, face.getRightEyeOpenProbability());
            }

            // If face tracking was enabled:
            if (face.getTrackingId() != null) {
                int id = face.getTrackingId();
                faceMap.putInt(TRACKING_ID, id);
            }
            faceArray.pushMap(faceMap);
        }
        return faceArray;
    }

    @Override
    public WritableArray processFaceListToGetWritableArray(List<Face> faces, InputImage image) {
        Log.d("FaceDetectorModuleLog", "There " + faces.size() + " faces" + faces.toString());
        WritableArray response = parseFaceListToWritableArray(faces);
        return response;
    }

    @Override
    public FaceDetectorOptions getDefaultFaceDetectorOptions() {
        return new FaceDetectorOptions.Builder()
                .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
                .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
                .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
                .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
                .build();
    }

    @Override
    public FaceDetectorOptions getRealTimeFaceDetectorOptions() {
        return new FaceDetectorOptions.Builder()
                .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
                .build();
    }

    @Override
    public InputImage getInputImageFromImageProxy(ImageProxy imageProxy) {
        @SuppressLint("UnsafeOptInUsageError") Image mediaImage = imageProxy.getImage();
        InputImage image = null;
        if (mediaImage != null) {
            image = InputImage.fromMediaImage(mediaImage, imageProxy.getImageInfo().getRotationDegrees());
        }
        return image;
    }

    @Override
    public InputImage getInputImageFromPath(String source, ReactContext context) {
        Uri uri = Uri.parse(source);

        InputImage image;
        try {
            image = InputImage.fromFilePath(context, uri);
            return image;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Face> getFaceListFromInputImage(FaceDetector detector, InputImage image) {
        Task<List<Face>> listTask = detector.process(image)
                .addOnSuccessListener(
                        faces -> {
                            Log.d("FaceDetectorModuleLog", faces.size() + " Faces Recognized " + faces.toString());
                        })
                .addOnFailureListener(
                        e -> Log.d("FaceDetectorModuleLog", "No Faces Recognized " + e.getMessage()))
                .addOnCompleteListener(e -> {
                    Log.d("FaceDetectorModuleLog", "Recording finished");
                });
        while (!listTask.isComplete()) {
        }
        return listTask.getResult();
    }

    @Override
    public void processImageWithPromise(FaceDetector detector, InputImage image, Promise promise) {
        detector.process(image)
                .addOnSuccessListener(
                        faces -> {
                            Log.d("FaceDetectorModuleLog", faces.size() + "Faces Recognized " + faces.toString());
                            WritableArray response = processFaceListToGetWritableArray(faces, image);
                            promise.resolve(response);
                        })
                .addOnFailureListener(
                        e -> Log.d("FaceDetectorModuleLog", "No Faces Recognized"));
    }

}
