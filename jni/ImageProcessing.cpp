#define cimg_display 0
#define cimg_use_jpeg
#define cimg_use_png

#include "CImg.h"
#include <string>
#include <fstream>
#include <jni.h>
#include <android/log.h>

using namespace cimg_library;

#define LOG_TAG "YYY"
#define LOGI(x...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG,x)

#ifdef __cplusplus
extern "C" {
#endif

int reduceFactor = 4;
void generatePrimitives(const std::string& resultName){
    LOGI("Point-0");
	CImg<unsigned char> image(1000/reduceFactor, 1000/reduceFactor, 1, 3, 0);
	image.fill(0);
    LOGI("Point-1");
	unsigned char colorLine[] = {255, 255, 255};
	image.draw_line(
		0,
		0,
		300/reduceFactor,
		300/reduceFactor,
		colorLine
	);
    LOGI("Point-2");
	unsigned char colorTriangle[] = {255, 255, 0};
	image.draw_triangle(
		700/reduceFactor,
		150/reduceFactor,
		(700 - 250)/reduceFactor,
		(150 + 250)/reduceFactor,
		(700 + 250)/reduceFactor,
		(150 + 250)/reduceFactor,
		colorTriangle
	);

    LOGI("Point-3");
	unsigned char colorCircle[] = {255, 0, 255};
	image.draw_circle(
		250/reduceFactor,
		700/reduceFactor,
		150/reduceFactor,
		colorCircle
	);

    LOGI("Point-4");
	unsigned char colorPolygon[] = {0, 255, 255};
	unsigned short numberPoints = 4;
	CImg<unsigned int> pointsPolygon(numberPoints, 2, 1, 1, 0);

    pointsPolygon(0,0) = (700 - 150)/reduceFactor;	// X0
    pointsPolygon(0,1) = (700 - 250)/reduceFactor; // Y0

    pointsPolygon(1,0) = (700 - 250)/reduceFactor; // X1
    pointsPolygon(1,1) = (700 + 100)/reduceFactor;	// Y1

    pointsPolygon(2,0) = (700 + 200)/reduceFactor; // X2
    pointsPolygon(2,1) = (700 + 242)/reduceFactor;	// Y2

    pointsPolygon(3,0) = (700 - 50)/reduceFactor;	// X3
    pointsPolygon(3,1) = (700 + 50)/reduceFactor;	// Y3

	image.draw_polygon(pointsPolygon, colorPolygon);
    LOGI("Pre save");
	image.save(resultName.c_str());
	LOGI("Post save");
	image.clear();
}

void changeColorBalance(
	const std::string& sourceName,
	const std::string& resultName,
	float r,
	float g,
	float b
){

    LOGI("Start, source path:%s", sourceName.c_str());

	CImg<unsigned char> image;
	image.load(sourceName.c_str());
	LOGI("Point-1");
	float rgb[] = {r, g, b};

	cimg_forXY(image,x,y){
		for(char channel = 0; channel < 3; channel++){
			if (image(x, y, channel)*rgb[channel] > 255){
				image(x, y, channel) = 255;
			} else {
				image(x, y, channel) *= rgb[channel];
			}
		}
	}
    LOGI("Point-2");
	image.save(resultName.c_str());
	LOGI("Point-3");
	image.clear();
	LOGI("Point-4");
}


std::string jStrToStr(
    JNIEnv *env,
    jstring& jstr
){
    return std::string(env->GetStringUTFChars(jstr, 0));
}

JNIEXPORT jstring JNICALL Java_com_cimg_android_utils_NativeUtils_getMessage(
    JNIEnv *env,
    jobject thisObj){
    return jstring("Hello, Native World!");
}

JNIEXPORT void JNICALL Java_com_cimg_android_utils_NativeUtils_generatePrimitives(
    JNIEnv *env,
    jobject thisObj,
    jstring jresultPath
) {

    std::string resultPath = jStrToStr(env, jresultPath);
    generatePrimitives(resultPath);
    generatePrimitives("/sdcard/primitives.jpg");
}

JNIEXPORT void JNICALL Java_com_cimg_android_utils_NativeUtils_changeColorBalance(
    JNIEnv *env,
    jobject thisObj,
	jstring jsourcePath,
	jstring jresultPath,
	jfloat jr,
	jfloat jg,
	jfloat jb
) {
    std::string sourcePath = jStrToStr(env, jsourcePath);
    std::string resultPath = jStrToStr(env, jresultPath);
    changeColorBalance(
        sourcePath,
        resultPath,
        (float) jr,
        (float) jg,
        (float) jb
    );
    LOGI("Finish");
}

}