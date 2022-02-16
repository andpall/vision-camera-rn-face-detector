import {PermissionsAndroid} from 'react-native';

const {
  WRITE_EXTERNAL_STORAGE,
  CAMERA,
} = PermissionsAndroid.PERMISSIONS;

export const requestWriteStoragePermission = async () => {
  const check = await PermissionsAndroid.check(WRITE_EXTERNAL_STORAGE);
  if (!check) return PermissionsAndroid.request(WRITE_EXTERNAL_STORAGE);

  return PermissionsAndroid.RESULTS.GRANTED;
};

export const requestCameraPermission = async () => {
  const check = await PermissionsAndroid.check(CAMERA);
  if (!check) return PermissionsAndroid.request(CAMERA);

  return PermissionsAndroid.RESULTS.GRANTED;
};
