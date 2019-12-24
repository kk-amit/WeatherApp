#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_amitss_weatherapp_WeatherApplication_getAPIKey(JNIEnv *env,
                                                                     jobject instance) {
    return (*env)->NewStringUTF(env, "YmVjYjMzYzM4NWEyNGY0Y2FlZDMzNzM4MTkyMTEy");
}