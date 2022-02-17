type Point = { X: number; Y: number };
type Rotation = { eulerX: number; eulerY: number; eulerZ: number };
type Bounds = {
  left: number;
  top: number;
  width: number;
  height: number;
};

export interface Face {
  bounds: Bounds;
  trackingId: number;
  rightEyeOpenProbability: number;
  leftEyeOpenProbability: number;
  smilingProbability: number;
  rotation: Rotation;
  leftEar: Point;
  rightEar: Point;
  leftCheek: Point;
  rightCheek: Point;
  noseBase: Point;
  leftEye: Point;
  rightEye: Point;
  mouthBottom: Point;
  mouthLeft: Point;
  mouthRight: Point;
  faceContour: Point[];
  leftEyeContour: Point[];
  rightEyeContour: Point[];
  leftCheekContour: Point[];
  rightCheekContour: Point[];
  noseBridgeContour: Point[];
  noseBottomContour: Point[];
  upperLipTopContour: Point[];
  upperLipBottomContour: Point[];
  lowerLipBottomContour: Point[];
  lowerLipTopContour: Point[];
  leftEyebrowTopContour: Point[];
  leftEyebrowBottomContour: Point[];
  rightEyebrowTopContour: Point[];
  rightEyebrowBottomContour: Point[];
}

export enum FaceField {
  BOUNDING_BOX = 'bounds',
  TRACKING_ID = 'trackingId',
  RIGHT_EYE_OPEN_PROBABILITY = 'rightEyeOpenProbability',
  LEFT_EYE_OPEN_PROBABILITY = 'leftEyeOpenProbability',
  SMILE_PROBABILITY = 'smilingProbability',
  ROTATION = 'rotation',
  LEFT_EAR = 'leftEar',
  RIGHT_EAR = 'rightEar',
  LEFT_CHEEK = 'leftCheek',
  RIGHT_CHEEK = 'rightCheek',
  NOSE_BASE = 'noseBase',
  LEFT_EYE = 'leftEye',
  RIGHT_EYE = 'rightEye',
  MOUTH_BOTTOM = 'mouthBottom',
  MOUTH_LEFT = 'mouthLeft',
  MOUTH_RIGHT = 'mouthRight',
  FACE_CONTOUR = 'faceContour',
  LEFT_EYE_CONTOUR = 'leftEyeContour',
  RIGHT_EYE_CONTOUR = 'rightEyeContour',
  LEFT_CHEEK_CONTOUR = 'leftCheekContour',
  RIGHT_CHEEK_CONTOUR = 'rightCheekContour',
  NOSE_BRIDGE_CONTOUR = 'noseBridgeContour',
  NOSE_BOTTOM_CONTOUR = 'noseBottomContour',
  UPPER_LIP_TOP_CONTOUR = 'upperLipTopContour',
  UPPER_LIP_BOTTOM_CONTOUR = 'upperLipBottomContour',
  LOWER_LIP_BOTTOM_CONTOUR = 'lowerLipBottomContour',
  LOWER_LIP_TOP_CONTOUR = 'lowerLipTopContour',
  LEFT_EYEBROW_TOP_CONTOUR = 'leftEyebrowTopContour',
  LEFT_EYEBROW_BOTTOM_CONTOUR = 'leftEyebrowBottomContour',
  RIGHT_EYEBROW_TOP_CONTOUR = 'rightEyebrowTopContour',
  RIGHT_EYEBROW_BOTTOM_CONTOUR = 'rightEyebrowBottomContour',
}
