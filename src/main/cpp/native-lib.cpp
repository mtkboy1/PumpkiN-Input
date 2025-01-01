#include <jni.h>
#include <string>
#include <cstdlib>
extern "C" JNIEXPORT jint JNICALL
Java_com_u063_pumpkin_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    int *hi = static_cast<int *>(malloc(sizeof(int)));
    *hi=54;
    return *hi;
}