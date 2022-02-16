import { NativeModules, Platform } from 'react-native';
import 'react-native-reanimated';
import type { Frame } from 'react-native-vision-camera';
import type { Face, FaceField } from './types/face';

const LINKING_ERROR =
  `The package 'vision-camera-rn-face-detector' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo managed workflow\n';

const VisionCameraRnFaceDetector = NativeModules.VisionCameraRnFaceDetector
  ? NativeModules.VisionCameraRnFaceDetector
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

import * as Landmarks from './constants/face';
export { Landmarks };

export { Face as FaceLandmark, FaceField } from './types/face';

export const recognizeFaceFromImage = async (source: string) => {
  return await VisionCameraRnFaceDetector.recognizeFaceFromImage(source);
};

export const scanFacesFromCamera = (frame: Frame) => {
  'worklet';
  // @ts-ignore
  // eslint-disable-next-line no-undef
  return __vision_camera_plugin(frame);
};

export const getLandMarkFromFace = (face: Face, landmark: FaceField) => {
  'worklet';
  return face[landmark];
};
