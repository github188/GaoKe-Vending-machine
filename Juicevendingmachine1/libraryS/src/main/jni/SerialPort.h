/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class android_serialport_api_SerialPort */

#ifndef _Included_android_serialport_api_SerialPort
#define _Included_android_serialport_api_SerialPort
#ifdef __cplusplus

extern "C" {
#endif
/*
 * Class:     android_serialport_api_SerialPort
 * Method:    open
 * Signature: (Ljava/lang/String;II)Ljava/io/FileDescriptor;
 */
//JNIEXPORT jobject JNICALL Java_android_serialport_api_SerialPort_open
//  (JNIEnv *, jclass, jstring, jint, jint);

(JNIEnv *env, jclass thiz, jstring path, jint baudrate,
      jint databits, jint stopbits, jchar parity);


/*
 * Class:     android_serialport_api_SerialPort
 * Method:    close
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_android_serialport_api_SerialPort_close
  (JNIEnv *, jobject);

int set_opt(jint, jchar, jint);

#ifdef __cplusplus

}
#endif
#endif
