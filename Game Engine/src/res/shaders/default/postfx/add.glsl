#VS
#include "default/postfx/basic.vert"
#VS

#FS
#version 150 core
in vec2 texCoord;

out vec4 outColor;

uniform sampler2D colors0;
uniform sampler2D colors1;
uniform vec2 strength;

void main() {
	outColor = texture(colors0, texCoord) * strength.x + texture(colors1, texCoord) * strength.y;
}
#FS
