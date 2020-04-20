#include <iostream>
#include <SFML/Graphics.hpp>

#include "Figures.h"
#include "Field.h"

static const int W = 350;
static const int H = 540;
static const int S = 30;

int main()
{
	float time, timer = 0;
	bool click = true;


	sf::RenderWindow window(sf::VideoMode(W, H), "Tetris", sf::Style::Titlebar | sf::Style::Close);
	Field field(W, H, S);

	sf::Clock clock;
	while (window.isOpen())
	{
		time = clock.getElapsedTime().asMilliseconds();
		clock.restart();
		time = time / 800;

		timer += time;
		if (timer >= 1) {
			timer = 0;
			field.incrementY();
		}

		sf::Event event;
		while (window.pollEvent(event))
		{
			switch (event.type)
			{
			case sf::Event::Closed:
				window.close();
				break;
			case sf::Event::KeyPressed:
				if (sf::Keyboard::isKeyPressed(sf::Keyboard::A) && click) {
					field.changePosX(-1);
				}
				if (sf::Keyboard::isKeyPressed(sf::Keyboard::D) && click) {
					field.changePosX(1);
				}
				if (sf::Keyboard::isKeyPressed(sf::Keyboard::S) && click) {
					field.incrementY();
				}
				if (sf::Keyboard::isKeyPressed(sf::Keyboard::Space) && click) {
					field.rotate();
				}
				click = false;
				break;
			case sf::Event::KeyReleased:
				click = true;
				break;
			default:
				break;
			}
		}

		//start check line
		field.checkField();
		//end check line

		window.clear();

		//start draw field
		field.drawField(window);
		//end draw field

		window.display();
	}
	return 0;
}