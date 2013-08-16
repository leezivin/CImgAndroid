#define cimg_display 0
#define cimg_use_jpeg
#define cimg_use_png

#include "CImg.h"
#include <string>
#include <jni.h>

using namespace cimg_library;

void generatePrimitives(const std::string& resultName){
	CImg<unsigned char> image(1000, 1000, 1, 3, 0);
	image.fill(0);

	unsigned char colorLine[] = {255, 255, 255};
	image.draw_line(
		0,
		0,
		300,
		300,
		colorLine
	);

	unsigned char colorTriangle[] = {255, 255, 0};
	image.draw_triangle(
		700,
		150,
		700 - 250,
		150 + 250,
		700 + 250,
		150 + 250,
		colorTriangle
	);

	unsigned char colorCircle[] = {255, 0, 255};
	image.draw_circle(
		250,
		700,
		150,
		colorCircle
	);

	unsigned char colorPolygon[] = {0, 255, 255};
	unsigned short numberPoints = 4;
	CImg<unsigned int> pointsPolygon(numberPoints, 2, 1, 1, 0);

    pointsPolygon(0,0) = 700 - 150;	// X0
    pointsPolygon(0,1) = 700 - 250; // Y0

    pointsPolygon(1,0) = 700 - 250; // X1
    pointsPolygon(1,1) = 700 + 100;	// Y1

    pointsPolygon(2,0) = 700 + 200; // X2
    pointsPolygon(2,1) = 700 + 242;	// Y2

    pointsPolygon(3,0) = 700 - 50;	// X3
    pointsPolygon(3,1) = 700 + 50;	// Y3

	image.draw_polygon(pointsPolygon, colorPolygon);


	image.save_png(resultName.c_str());
	image.clear();
}

void changeColorBalance(
	const std::string& sourceName,
	const std::string& resultName,
	float r,
	float g,
	float b
){
	CImg<unsigned char> image;
	image.load(sourceName.c_str());
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

	image.save(resultName.c_str());
	image.clear();
}

#ifndef _Included_by_idev_jni_NativeUtils
#define _Included_by_idev_jni_NativeUtils
#ifdef __cplusplus
extern "C" {
#endif

std::string jStrToStr(
    JNIEnv *env,
    jstring& jstr
){
    return std::string(env->GetStringUTFChars(jstr, 0));
}

JNIEXPORT void JNICALL Java_com_cimg_android_utils_NativeUtils_generatePrimitives(
    JNIEnv *env,
    jobject thisObj,
    jstring jresultPath
) {
    std::string resultPath = jStrToStr(env, jresultPath);
    generatePrimitives(resultPath);
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
}

#ifdef __cplusplus
}
#endif
#endif