#include "Field.h"

Field::Field(int width, int height, int size) {
	this->width = width - (width % size);
	this->height = height - (height % size);
	this->size = size;
	m_width = width / size;
	m_height = height / size;
	field = new std::string[m_height];
	copyField = new std::string[m_height];
	for (int i = 0; i < m_height; i++) {
		field[i] = " ";
		copyField[i] = " ";
		for (int j = 1; j < m_width; j++) {
			field[i] += ' ';
			copyField[i] += ' ';
		}
	}
}

void Field::rotate() {
	if (figures.getM_Y() + 3 < figures.getSize()) {
		figures.setM_Y(figures.getM_Y() + 3);
	}
	else {
		figures.setM_Y(0);
	}
}

void Field::incrementY() {
	if (figures.getY() + 3 < m_height && field[figures.getY() + 3][figures.getX() + 1] != '_')
		figures.setY(figures.getY() + 1);
	else {
		resetFigure();
	}
}

void Field::changePosX(int dir) {
	if ((figures.getX() + dir >= 0 && dir < 0) || (figures.getX() + 3 < m_width && dir > 0)) {
		if(field[figures.getY()][figures.getX() + dir] != '_')
			figures.setX(figures.getX() + dir);
	}
}

void Field::resetField() {
	for (int i = 0; i < m_height; i++) {
		for (int j = 0; j < m_width; j++) {
			field[i][j] = copyField[i][j];
		}
	}
}

void Field::resetFigure() {

	for (int i = 0; i < m_height; i++) {
		for (int j = 0; j < m_width; j++) {
			copyField[i][j] = field[i][j];
		}
	}

	figures.setY(0);
	figures.randomFigure();
}

void Field::setFigureOnField(sf::RenderWindow& window) {
	for (int i = 0; i < 3; i++) {
		for (int j = 0; j < 3; j++) {
			if (figures.getFigure()[i + figures.getM_Y()][j] != ' ') {
				field[i + figures.getY()][j + figures.getX()] = '_';
			}
		}
	}
}

void Field::drawField(sf::RenderWindow& window) {
	resetField();
	setFigureOnField(window);
	for (int i = 0; i < height; i += size) {
		for (int j = 0; j < width; j += size) {
			sf::RectangleShape rect(sf::Vector2f(size, size));
			if (field[i / size][j / size] == ' ') {
				rect.setPosition(j + 2, i + 2);
				rect.setFillColor(sf::Color(220, 20, 60));
			}
			else {
				rect.setPosition(j + 2, i + 2);
				rect.setFillColor(sf::Color(79, 155, 59));
			}
			rect.setOutlineColor(sf::Color(2, 2, 2));
			rect.setOutlineThickness(2);
			window.draw(rect);
		}
	}
}

void Field::checkField() {
	for (int i = 0; i < m_height; i++) {
		bool checkLine = true;
		for (int j = 0; j < m_width; j++) {
			if (field[i][j] == ' ') {
				checkLine = false;
				break;
			}
		}
		if (checkLine) {
			counter++;
			for (int k = m_height - 1; k >= 1; k--) {
				field[k] = field[k - 1];
			}
			field[0] = " ";
			for (int k = 1; k < m_width; k++)
				field[0] += ' ';
		}
	}
}