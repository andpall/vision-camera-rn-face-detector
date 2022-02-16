# vision-camera-rn-face-detector

This library provide _**google ml-kit**_ abilities from react-native.   
It's build with usage of [**react-native-vision-camera**](https://github.com/mrousavy/react-native-vision-camera).

## Installation


```sh
npm install vision-camera-rn-face-detector
```

 Add to _**AndroidManifest**_
```
<uses-permission android:name="android.permission.CAMERA" />
```

Follow [these intructions](https://docs.swmansion.com/react-native-reanimated/docs/fundamentals/installation/) to make work _reanimated_  correctly

 Add this to **_babel.config.js_** in root of project
```
module.exports = {
  ...
  plugins: [
    ...
    [
      'react-native-reanimated/plugin',
      {
        globals: ['__vision_camera_plugin'],
      },
    ],
  ],
};

```

## Usage

```js
import {...} from 'vision-camera-rn-face-detector';

const frameProcessor = useFrameProcessor((frame) => {
    'worklet';
    const faces = scanFacesFromCamera(frame);
    console.log();
    frame.close();
  }, []);

  const CameraView = () => {
  const devices = useCameraDevices();
  const device = devices.front;
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
```
To define single face landmarks you can use
```
getLandMarkFromFace(face, FaceField.SMILE_PROBABILITY)
```

## Troubleshooting

#### 1. Cmake doesn't find variables
Try to open cmake at _node_modules/react-native-vision-camera/andoid/CmakeList.txt_, then find these lines in the end of file and remove 
```

 find_library(
        JSI_LIB
        jsi
        PATHS ${LIBRN_DIR}
        NO_ CMAKE_FIND_ROOT_PATH
) <- all these line

target_link_libraries(
    ...
        ${JSI_LIB} <- this line
    ...
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

