#version 150 core

in vec2 texCoord;

out vec4 outColor;

uniform vec4 ambientColor;

uniform sampler2D colors;
uniform sampler2D positions;
uniform sampler2D normals;

void main() {
	outColor = texture(colors, texCoord) * ambientColor;
}
