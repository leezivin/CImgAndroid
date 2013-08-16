#define cimg_display 0
#define cimg_use_jpeg
#define cimg_use_png

#include <iostream>
#include <string>
#include "CImg.h"
using namespace cimg_library;

const std::string SOURCE_PREFIX = "./source_image/";
const std::string RESULT_PREFIX = "./result_image/";

std::string getSourePath(const std::string source){
	return (SOURCE_PREFIX + source);
}

std::string getResultPath(const std::string result){
	return (RESULT_PREFIX + result);
}

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

void processingBlocks(
	const std::string& sourceName,
	const std::string& resultName
){
	CImg<unsigned char> image;
	image.load(sourceName.c_str());	

	CImg_3x3(I,unsigned char);
	cimg_for3x3(image, x, y, 0, 0, I, unsigned char){				
		image(x,y) = (Ipp + Icp + Inp + Ipc + Inc + Ipn + Icn + Inn) / 7;
	}
	
	image.save(resultName.c_str());
	image.clear();
}

int main(){
    generatePrimitives(getResultPath("primitives.png"));			
    
    changeColorBalance(
		getSourePath("ocean.jpg"),
		getResultPath("ocean_color.jpg"),
		0.2f,
		1.2f,
		1.1f		
    );
    
    processingBlocks(
		getSourePath("ocean.jpg"),
		getResultPath("ocean_block.jpg")
    );
    
	return 0;
}
