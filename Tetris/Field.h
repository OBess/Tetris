#pragma once
#include "Figures.h"
#include <string>
#include <iostream>
#include <SFML/Graphics.hpp>

class Field
{
private:
	Figures figures;
	int counter = 0;
	int width, height, size, m_width, m_height;
	
	std::string* field;
	std::string* copyField;
public:
	Field(){}
	Field(int width, int height, int size);

	~Field() {
		delete[] field;
	}

	int getCounter() { return counter; }
	std::string* getFigure() { return figures.getFigure(); }

	void rotate();
	void incrementY();
	void changePosX(int dir);
	void resetField();
	void resetFigure();
	void setFigureOnField(sf::RenderWindow& window);
	void drawField(sf::RenderWindow& window);
	void checkField();
};

