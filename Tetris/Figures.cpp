#include "Figures.h"

void Figures::randomFigure() {
	srand(time(NULL));

	int random = rand() % 7 + 1;

	switch (random)
	{
	case 1:
		figure = I;
		size = 6;
		break;
	case 2:
		figure = J;
		size = 12;
		break;
	case 3:
		figure = L;
		size = 12;
		break;
	case 4:
		figure = O;
		size = 3;
		break;
	case 5:
		figure = S;
		size = 6;
		break;
	case 6:
		figure = T;
		size = 12;
		break;
	case 7:
		figure = Z;
		size = 6;
		break;
	default:
		break;
	}
}