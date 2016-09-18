#version 150 core
in vec2 texCoord;

out vec4 outColor;

uniform sampler2D colors;

uniform mat4 matrix;

void main() {
	outColor = texture(colors, texCoord);
	outColor = outColor * matrix;
}
