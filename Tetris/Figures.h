#pragma once
#include <string>
#include <ctime>
#include <cstdlib>

class Figures {
private:
	int x = 0, y = 0, m_y = 0, size = 0;
	std::string* figure;
	
	std::string I[6] = {
		" _ ",
		" _ ",
		" _ ",
		"   ",
		"___",
		"   "
	};

	std::string J[12] = {
		"_  ",
		"___",
		"   ",
		" __",
		" _ ",
		" _ ",
		"   ",
		"___",
		"  _",
		" _ ",
		" _ ",
		"__ "
	};

	std::string L[12] = {
		"  _",
		"___",
		"   ",
		"__ ",
		" _ ",
		" _ ",
		"   ",
		"___",
		"_  ",
		" _ ",
		" _ ",
		" __"
	};

	std::string O[3] = {
		"   ",
		" __",
		" __"
	};

	std::string S[6] = {
		" __",
		"__ ",
		"   ",
		" _ ",
		" __",
		"  _"
	};

	std::string T[12] = {
		" _ ",
		"___",
		"   ",
		" _ ",
		" __",
		" _ ",
		"   ",
		"___",
		" _ ",
		" _ ",
		"__ ",
		" _ "
	};

	std::string Z[6] = {
		"__ ",
		" __",
		"   ",
		"  _",
		" __",
		" _ "
	};

public:
	Figures() { randomFigure(); }

	void setM_Y(int m_y) { this->m_y = m_y; }
	int getM_Y() {return m_y; }

	void setX(int x) { this->x = x; }
	void setY(int y) { this->y = y; }

	int getX() { return x; }
	int getY() { return y; }
	
	int getSize() { return size; }

	void setFigure(std::string* figure) { this->figure = figure; }
	std::string* getFigure() { return figure; }

	void randomFigure();
};
