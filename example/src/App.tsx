import * as React from 'react';

import { StyleSheet, View, Text } from 'react-native';
import {
  getLandMarkFromFace,
  scanFacesFromCamera,
  FaceField,
  useCameraDevices,
  useFrameProcessor,
  Camera,
} from 'vision-camera-rn-face-detector';

export default function App() {
  const devices = useCameraDevices();
  const device = devices.front;

  const getPermission = async () => {
    return await Camera.requestCameraPermission();
  };

  const frameProcessor = useFrameProcessor((frame) => {
    'worklet';
    const faces = scanFacesFromCamera(frame);
    faces[0] &&
      console.log(getLandMarkFromFace(faces[0], FaceField.SMILE_PROBABILITY));
    console.log(faces);
    frame.close();
  }, []);

  React.useEffect(() => {
    getPermission();
  }, []);

  if (device == null)
    return (
      <View style={styles.container}>
        <Text>No Device </Text>
      </View>
    );

  return (
    <Camera
      style={StyleSheet.absoluteFill}
      device={device}
      frameProcessor={frameProcessor}
      frameProcessorFps={3}
      fps={25}
      isActive={true}
    />
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
